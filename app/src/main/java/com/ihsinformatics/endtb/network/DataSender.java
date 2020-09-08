package com.ihsinformatics.endtb.network;
/**
 * @author naveed.iqbal
 * @description this class run in background on request of other class, communicate with server,
 *  and receive response. If class calling it need it to return the response then calling class
 *  must have to implement AsyncTaskResponseBridge interface, and call its 
 *  SendDataToserver(AsyncTaskResponseBridge atrb, int respId)constructor. Response will be returned as string
 *  in recievedResponse(String resp, int respId) method of AsyncTaskResponseBridge interface. 
 *
 * */

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Base64;

import com.ihsinformatics.endtb.utils.CredentialsHelper;
import com.ihsinformatics.endtb.utils.ErrorMessages;
import com.ihsinformatics.endtb.utils.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.security.KeyStore;

public class DataSender extends AsyncTask<String, integer, JSONObject> {

	private int respId;
	private Sendable sendable;
	private Context context;
	private REQUEST_TYPE requestType;
	private boolean isFull = false;
	private String responseReference;
	public static enum REQUEST_TYPE{
		POST,
		GET,
		DELETE
	}

	public DataSender(Context context, Sendable sendable, int respId, String responseReference, REQUEST_TYPE requestType) {
		this.sendable = sendable;
		this.respId = respId;
		this.responseReference = responseReference;
		this.context = context;
		this.requestType = requestType;
	}
	public DataSender(Context context, Sendable sendable, int respId, String responseReference, REQUEST_TYPE requestType, boolean isFull) {
		this(context, sendable, respId, responseReference, requestType);
		this.isFull = isFull;
	}

	private HttpParams buildHttpParams(int ms) {
		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		// The default value is zero, that means the timeout is not used.
		int timeoutConnection = ms;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		// Set the default socket timeout (SO_TIMEOUT)
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = ms;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

		return httpParameters;
	}

	public HttpClient getNewHttpClient(HttpParams params) {
		try {
			// ProviderInstaller.installIfNeeded(context.getApplicationContext());
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);

			MySSLSocketFactory sf = new MySSLSocketFactory(trustStore, context);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			// HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}

	@Override
	protected JSONObject doInBackground(String... params) {
		String resp = "";
		try {
			if(!isConnected()) {
				return new JSONObject().put(ParamNames.SERVER_ERROR, ErrorMessages.getErrorMessage(context, ErrorMessages.ERROR_TYPE.NOT_CONNECTED));
			}
			String serverAddress = Config.getApiEndPoint(params[0], context);
			HttpResponse response;
			if(requestType == REQUEST_TYPE.POST) {
				response = makePostCall(serverAddress, params[1]);
				System.out.println(serverAddress);
				System.out.println(params[1]);
			} else {
				if(params.length>1) {
					if(!params[1].startsWith("/"))
						serverAddress += "?" + params[1];
					else
						serverAddress += params[1];
				}

				if (isFull) {
					if(params.length>1 && !params[1].startsWith("/"))
						serverAddress += "&v=full";
					else
						serverAddress += "?v=full";
				}
				response = makeGetCall(serverAddress);
				System.out.println(serverAddress);
			}

			int statusCode = response.getStatusLine().getStatusCode();
			System.out.println(statusCode);
			InputStreamReader ir = new InputStreamReader(response.getEntity().getContent());
			BufferedReader br = new BufferedReader(ir);
			String b;
			while ((b = br.readLine()) != null) {
				resp += b;
			}
			if (statusCode == 200 || statusCode ==201) {
				return new JSONObject(resp);
			} else if(statusCode == 401) {
				resp = ErrorMessages.getErrorMessage(context, ErrorMessages.ERROR_TYPE.UNAUTHORIZED)+" "+statusCode;
			}

		} catch (JSONException e) {
			Logger.log(e);
			resp = e.getMessage();
		} catch (UnsupportedEncodingException e) {
			Logger.log(e);
			resp = e.getMessage();
		} catch (HttpHostConnectException | UnknownHostException e) {
			resp = ErrorMessages.getErrorMessage(context, ErrorMessages.ERROR_TYPE.HOST_CONNECT)+"\n"+Config.getServerAddress(context);
			Logger.log(e);
		} catch (ClientProtocolException e) {
			resp = ErrorMessages.getErrorMessage(context, ErrorMessages.ERROR_TYPE.INVALID_REQUEST);
			Logger.log(e);
		} catch (IOException e) {
			resp = ErrorMessages.getErrorMessage(context, ErrorMessages.ERROR_TYPE.WENT_WRONG);
			Logger.log(e);
		}
		try {
			return new JSONObject().put(ParamNames.SERVER_ERROR, resp);
		} catch (JSONException e) {
			Logger.log(e);
		}
		return null;
	}

	private HttpResponse makeGetCall(String serverAddress) throws IOException {
		HttpGet httpGet = buildHttpGetObject(serverAddress);
		HttpParams httpParameters = buildHttpParams(180000);
		HttpClient httpclient = getNewHttpClient(httpParameters); //new DefaultHttpClient(httpParameters);

		HttpResponse response = httpclient.execute(httpGet);
		return response;
	}

	private HttpResponse makePostCall(String serverAddress, String body) throws IOException {
		HttpPost httpPost = buildHttpPostObject(serverAddress, body);
		HttpParams httpParameters = buildHttpParams(120000);
		HttpClient httpclient = getNewHttpClient(httpParameters); //new DefaultHttpClient(httpParameters);

		HttpResponse response = httpclient.execute(httpPost);
		return response;
	}

	private HttpPost buildHttpPostObject(String serverAddress, String body) throws UnsupportedEncodingException {
		HttpPost httppost = new HttpPost(serverAddress);
		httppost.setHeader("Accept", "application/json");
		httppost.setHeader("Content-Type", "application/json");
		StringEntity stringEntity = new StringEntity(body);
		httppost.setEntity(stringEntity);

		String auth = Base64.encodeToString((CredentialsHelper.getUsername() + ":" + CredentialsHelper.getPassword()).getBytes("UTF-8"), Base64.NO_WRAP);
				/*HttpUriRequest request = httppost;
				request.addHeader("Authorization", "Basic " + auth);*/

		httppost.addHeader("Authorization", "Basic " + auth);
		return httppost;

	}

	private HttpGet buildHttpGetObject(String serverAddress) throws UnsupportedEncodingException {
		HttpGet httpGet = new HttpGet(serverAddress);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");



		String username = CredentialsHelper.getUsername();
		String password = CredentialsHelper.getPassword();
		String auth = Base64.encodeToString(( username+ ":" + password).getBytes("UTF-8"), Base64.NO_WRAP);
		/*HttpUriRequest request = httpGet;
		request.addHeader("Authorization", "Basic " + auth);*/

		httpGet.addHeader("Authorization", "Basic " + auth);
		return httpGet;
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		super.onPostExecute(result);
		try {
			sendable.onResponseReceived(result, respId, responseReference);
		} catch (JSONException e) {
			Logger.log(e);
		}

		// TODO make a string based onResponseReceived and call that here --No need though
	}


	private boolean isConnected() {
		ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected())
			return true;
		else
			return false;
	}
}
