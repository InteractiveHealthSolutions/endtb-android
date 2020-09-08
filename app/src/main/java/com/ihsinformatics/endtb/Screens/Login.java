package com.ihsinformatics.endtb.Screens;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ihsinformatics.endtb.R;
import com.ihsinformatics.endtb.Screens.settings.PreferencesActivity;
import com.ihsinformatics.endtb.database.Entities.User;
import com.ihsinformatics.endtb.database.data.DbContentHelper;
import com.ihsinformatics.endtb.database.json_helper.UserHelper;
import com.ihsinformatics.endtb.network.Config;
import com.ihsinformatics.endtb.network.DataSender;
import com.ihsinformatics.endtb.network.ParamNames;
import com.ihsinformatics.endtb.network.Sendable;
import com.ihsinformatics.endtb.utils.preferences.AppPreferences;
import com.ihsinformatics.endtb.utils.CredentialsHelper;
import com.ihsinformatics.endtb.utils.ELimsApplication;
import com.ihsinformatics.endtb.utils.views.NetworkProgressDialog;
import com.ihsinformatics.endtb.utils.preferences.PreferencesManager;
import com.ihsinformatics.endtb.utils.views.Toast;

import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity implements View.OnClickListener, Sendable {

    private Button btnSignIn;
    private EditText etUserName;
    private EditText etPassword;
    private NetworkProgressDialog networkProgressDialog;
    private String username;
    private String password;
    private TextView tvVersionNumber;
    private CheckBox cbRemember;
    private TextView tvSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        boolean isFirst = AppPreferences.getInstance(this).findBooleanPrferenceValue(AppPreferences.KEY.IS_FIRST_RUN,true);

        if (isFirst)
            findViewById(R.id.llServerSelection).setVisibility(View.VISIBLE);

        User user = new PreferencesManager().fetchUser(this);
        // startActivity(ProviderRegistration.class);
        if(user != null) {
            username = user.getUserName();
            password = user.getPassword();
            logIn(user, true);
        }
        btnSignIn = (Button) findViewById(R.id.btnSignin);
        btnSignIn.setOnClickListener(this);
        etUserName = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvVersionNumber = (TextView) findViewById(R.id.tvVersionNumber);
        networkProgressDialog = new NetworkProgressDialog(this);
        cbRemember = (CheckBox) findViewById(R.id.cbRemember);
        tvSettings = (TextView) findViewById(R.id.tvSettings);
        tvSettings.setOnClickListener(this);
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            tvVersionNumber.setText("Version "+version);
        } catch (PackageManager.NameNotFoundException e) {
        e.printStackTrace();
    }
    }

    private void startActivity(Class aClass) {
        Intent intent = new Intent(this, aClass);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tvSettings:
            startActivity(PreferencesActivity.class);
            LocalDate now = new LocalDate();
            LocalDate monday = now.withDayOfMonth(1);
            System.out.println(now+" | "+monday);
            break;
            case R.id.btnSignin:
                if(findViewById(R.id.llServerSelection).getVisibility() == View.VISIBLE) {
                    RadioGroup radioGroup = findViewById(R.id.rgServerSelection);
                    RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                    if(radioButton.getId() == R.id.rbLive)
                        AppPreferences.getInstance(this).addOrUpdateBooleanPreference(AppPreferences.KEY.IS_LIVE, true);
                    else
                        AppPreferences.getInstance(this).addOrUpdateBooleanPreference(AppPreferences.KEY.IS_LIVE, false);
                }
            username = etUserName.getText().toString();
            password = etPassword.getText().toString();
            if(username.isEmpty() && password.isEmpty()) {
                Toast.makeText(this, getResources().getString(R.string.provide_credentials), Toast.LENGTH_LONG).show();
                return;
            }
            if(username.isEmpty()) {
                Toast.makeText(this, getResources().getString(R.string.enter_username), Toast.LENGTH_LONG).show();
                return;
            }
            if(password.isEmpty()) {
                Toast.makeText(this, getResources().getString(R.string.enter_password), Toast.LENGTH_LONG).show();
                return;
            }


            networkProgressDialog.show();
            attemptLogin(username, password);


            break;
        }
    }

    private void attemptLogin(String username, String password) {
        CredentialsHelper.setUsername(username);
        CredentialsHelper.setPassword(password);
        User user = tryOfflineLogin(username, password);
        if(user != null) {
            this.username = user.getUserName();
            this.password = user.getPassword();
            logIn(user, false);
        } else {
            new DataSender(this, this, 0, null, DataSender.REQUEST_TYPE.GET).execute(Config.USER_DATA_RESOURCE,"/"+ CredentialsHelper.getUsername());
            return;
        }
    }

    private User tryOfflineLogin(String username, String password) {
        User user = UserHelper.isValidUser(username, password);
        return user;
    }

    @Override
    public void send(JSONArray data, int respId, String responseReference) {
        // TODO useless for this project
    }

    @Override
    public void onResponseReceived(JSONObject resp, int respId, String responseReference) throws JSONException {
        networkProgressDialog.dismiss();
        if(resp.has(ParamNames.SERVER_ERROR)) {
            Toast.makeText(this, resp.getString(ParamNames.SERVER_ERROR), Toast.LENGTH_LONG).show();
            return;
        }
        User user = UserHelper.parseUserFromJson(username, password, resp);
        if(user == null) {
            CredentialsHelper.nullifyCredentials();
            Toast.makeText(this, getResources().getString(R.string.could_not_parse_response), Toast.LENGTH_LONG).show();
            return;
        }
        /*if(user.getLabUUID() == null) {
            CredentialsHelper.nullifyCredentials();
            Toast.makeText(this, getResources().getString(R.string.not_mapped_to_lab), Toast.LENGTH_LONG).show();
            return;
        }*/

        this.username = user.getUserName();
        this.password = user.getPassword();
        logIn(user, false);
    }

    private void logIn(User user, boolean autoLogin) {
        String lastLoggedinUsername = new PreferencesManager().fetchLastLoggedInUsername(this);
        if(lastLoggedinUsername!=null)
            if(!username.equals(lastLoggedinUsername)) {
                AppPreferences.getInstance(this).addOrUpdateBooleanPreference(AppPreferences.KEY.IS_FIRST_RUN, true);
                DbContentHelper.getInstance().reCreateDatabase();
            }

        // save or update user
        Long userId;
        User existingUser = DbContentHelper.getInstance().fetchUserByUsername(user.getUserName());
        if(existingUser == null)
            userId = ELimsApplication.daoSession.getUserDao().insert(user);
        else {
            userId = existingUser.getId();
            user.setId(userId);
            ELimsApplication.daoSession.getUserDao().update(user);

        }
        user.setId(userId);

        PreferencesManager preferencesManager = new PreferencesManager();
        CredentialsHelper.setUserId(userId);
        CredentialsHelper.setUsername(username);
        CredentialsHelper.setPassword(password);
        preferencesManager.writeLastLoggedInUsername(this, username);

        if(networkProgressDialog != null)
            if(networkProgressDialog.isShowing())
                networkProgressDialog.dismiss();

        if(!autoLogin) {
            Toast.makeText(this, getResources().getString(R.string.login_successful), Toast.LENGTH_LONG).show();
            if (cbRemember.isChecked()) {
                preferencesManager.writeUser(this, user);
            }
        }

        if(findViewById(R.id.llServerSelection).getVisibility() == View.VISIBLE) {
            RadioGroup radioGroup = findViewById(R.id.rgServerSelection);
            RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
            if(radioButton.getId() == R.id.rbLive)
                AppPreferences.getInstance(this).addOrUpdateBooleanPreference(AppPreferences.KEY.IS_LIVE, true);
            else
                AppPreferences.getInstance(this).addOrUpdateBooleanPreference(AppPreferences.KEY.IS_LIVE, false);
        }

        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }
}
