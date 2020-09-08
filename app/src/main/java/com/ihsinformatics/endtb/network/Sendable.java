package com.ihsinformatics.endtb.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author naveed.iqbal
 * @description: this interface serves as a communication adapter between any
 *               class that needs network communication and DataSender
 *               class. Requester class need to implement this interface's
 *               onResponseReceived(JSONObject resp, int repId) method,
 *               DataSender class will call that onResponseReceived(JSONObject
 *               resp, int repId) after completing the communication task
 * 
 * 
 */
public interface Sendable {

	public void send(JSONArray data, int respId, String responseReference);
	public void onResponseReceived (JSONObject resp, int respId, String responseReference) throws JSONException;

}
