package com.ihsinformatics.endtb.utils.views;

import android.app.ProgressDialog;
import android.content.Context;

public class NetworkProgressDialog extends ProgressDialog {

	public NetworkProgressDialog(Context context) {
		super(context);
		setCanceledOnTouchOutside(false);
		setCancelable(false);
		setMessage("Please wait...");
		setProgressStyle(ProgressDialog.STYLE_SPINNER);
	}

	public void show(String message) {
		setMessage(message);
		super.show();
	}

}
