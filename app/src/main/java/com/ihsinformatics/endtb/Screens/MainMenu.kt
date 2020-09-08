package com.ihsinformatics.endtb.Screens

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView

import com.ihsinformatics.endtb.R
import com.ihsinformatics.endtb.Screens.dialogs.InteractiveAlertDialog
import com.ihsinformatics.endtb.Screens.settings.PreferencesActivity
import com.ihsinformatics.endtb.database.data.DefaultData
import com.ihsinformatics.endtb.network.Downloader.MetaDataDownloader
import com.ihsinformatics.endtb.network.Downloader.MetaDataUpdater
import com.ihsinformatics.endtb.network.Downloader.PatientsDataDownloader
import com.ihsinformatics.endtb.network.listeners.OnMetaDataDownloadListener
import com.ihsinformatics.endtb.network.UnifiedBroadcastReceiver
import com.ihsinformatics.endtb.network.listeners.OnPatientsDataDownloadListener
import com.ihsinformatics.endtb.utils.OpenMRSMappings
import com.ihsinformatics.endtb.utils.preferences.AppPreferences
import com.ihsinformatics.endtb.utils.preferences.PreferencesManager

import java.util.Date

class MainMenu : AppCompatActivity(), View.OnClickListener, OnMetaDataDownloadListener, OnPatientsDataDownloadListener {

    private var btnRegister: Button? = null
    private var btnSearchPatient: Button? = null
    private var btnSearchTest: Button? = null
    private var networkProgressDialog: InteractiveAlertDialog.Builder? = null
    private var tvLogout: TextView? = null
    private var tvSync: TextView? = null
    private var tvSettings: TextView? = null
    private var tvMetadata: TextView? = null
    private var btnErrorRecords: Button? = null

    private val MY_PERMISSIONS_REQUEST_READ_WRITE_STORAGE = 0
    private var firstRun: Boolean = false

    //permission is automatically granted on sdk<23 upon installation
    val isReadStoragePermissionGranted: Boolean
        get() {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    return true
                } else {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 3)
                    return false
                }
            } else {
                return true
            }
        }

    //permission is automatically granted on sdk<23 upon installation
    val isWriteStoragePermissionGranted: Boolean
        get() {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    return true
                } else {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 2)
                    return false
                }
            } else {
                return true
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
displayServerInfo();
        tvLogout = findViewById<View>(R.id.tvLogout) as TextView
        tvSync = findViewById<View>(R.id.tvSync) as TextView
        btnRegister = findViewById<View>(R.id.btnRegisterPatient) as Button
        btnSearchPatient = findViewById<View>(R.id.btnSearchPatient) as Button
        btnErrorRecords = findViewById<View>(R.id.btnViewErrorRecords) as Button
        btnSearchTest = findViewById<View>(R.id.btnSearchTest) as Button
        tvSettings = findViewById<View>(R.id.tvSettings) as TextView
        tvMetadata = findViewById<View>(R.id.tvMetadata) as TextView
        tvMetadata!!.setOnClickListener(this)
        tvSettings!!.setOnClickListener(this)
        btnRegister!!.setOnClickListener(this)
        btnSearchPatient!!.setOnClickListener(this)
        btnSearchTest!!.setOnClickListener(this)
        tvLogout!!.setOnClickListener(this)
        tvSync!!.setOnClickListener(this)
        btnErrorRecords!!.setOnClickListener(this)
        /**/
        networkProgressDialog = InteractiveAlertDialog.Builder(this).setTitle("Downloading metadata...")
        firstRun = AppPreferences.getInstance(this).findBooleanPrferenceValue(AppPreferences.KEY.IS_FIRST_RUN, true)
        // firstRun = false;
        if (!firstRun) {
            initSearchViews()
        } else {
            networkProgressDialog!!.show()
            MetaDataDownloader(this, this).start()
        }
    }

    private fun displayServerInfo() {
        val isLive = AppPreferences.getInstance(this).findBooleanPrferenceValue(AppPreferences.KEY.IS_LIVE, false)
        var tv: TextView? = findViewById<TextView>(R.id.tvServerStatus)
        if (isLive){

            tv!!.setText("Running in LIVE mode")
            tv!!.setBackgroundColor(resources.getColor(R.color.IndianRed))
        } else{
            tv!!.setText("Running in TEST mode")
            tv!!.setBackgroundColor(resources.getColor(R.color.DarkOliveGreen))
        }
    }


    override fun onResume() {
        if (!firstRun) {
            val intent = Intent(UnifiedBroadcastReceiver.ACTION_ATTEPT_TO_UPLOAD_DATA)
            sendBroadcast(intent)
        }
        super.onResume()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            3 -> {

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //resume tasks needing this permission
                } else {

                }
                isWriteStoragePermissionGranted
            }

            2 -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //resume tasks needing this permission
            } else {

            }
        }
    }

    private fun initSearchViews() {
        if (isReadStoragePermissionGranted) {
            isWriteStoragePermissionGranted
        }
        if (firstRun) {
            AppPreferences.getInstance(this).addOrUpdateBooleanPreference(AppPreferences.KEY.IS_FIRST_RUN, false)
            firstRun = false
        }
    }

    override fun onClick(v: View) {
        // new PatientsDataDownloader(this).start(this);
        val id = v.id
        when (id) {
            R.id.btnRegisterPatient ->
                // if(CredentialsHelper.hasPermissions(OpenMRSMappings.PRIVILEGE_ADD_PATIENT.getName()))
                startActivity(ProviderRegistration::class.java)
            R.id.btnSearchPatient -> startActivity(PatientListActivity::class.java)


            R.id.tvLogout -> {
                PreferencesManager().deleteUser(this)
                startActivity(Login::class.java)
                finish()
            }
            R.id.tvSync -> {
                networkProgressDialog!!.show("Synchronizing records...")
                PatientsDataDownloader(this).start(this, Date())
            }
            R.id.tvSettings -> startActivity(PreferencesActivity::class.java)
            R.id.tvMetadata -> {
                networkProgressDialog!!.show()
                MetaDataUpdater(this, OnMetaDataUpdateListener()).start(Date())
            }
            R.id.btnViewErrorRecords -> startActivity(ErrorRecordsDisplayActivity::class.java)
        }/* else
                    Toast.makeText(this, R.string.insufficient_privileges, Toast.LENGTH_LONG).show();*/
    }

    private inner class OnMetaDataUpdateListener : OnMetaDataDownloadListener {
        override fun onMetaDataDownloaded() {
            networkProgressDialog!!.dismiss()
        }

        override fun update(message: String) {
            runOnUiThread { networkProgressDialog!!.setTitle(message) }

        }
    }

    override fun onMetaDataDownloaded() {
        DefaultData().insertDefaultData()
        PreferencesManager().writeLastMetadataSyncDate(this, Date())
        PatientsDataDownloader(this).start(this)
        // onPatientsDataDownloaded();
    }

    override fun update(message: String) {
        runOnUiThread { networkProgressDialog!!.setTitle(message) }
    }

    private fun startActivity(aClass: Class<*>) {
        val intent = Intent(this, aClass)
        startActivity(intent)
    }

    override fun onPatientsDataDownloaded() {
        networkProgressDialog!!.dismiss()
        PreferencesManager().writeLastSyncDate(this, Date())
        initSearchViews()
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
                .setPositiveButton(resources.getString(R.string.leave)) { dialog, which -> super@MainMenu.onBackPressed() }
                .setNegativeButton(resources.getString(R.string.cancel), null)
                .setMessage(resources.getString(R.string.form_not_saved))
                .setTitle(resources.getString(R.string.exit_app)).show()
    }

    companion object {

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }
}
