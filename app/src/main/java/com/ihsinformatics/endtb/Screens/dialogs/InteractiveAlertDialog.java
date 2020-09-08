package com.ihsinformatics.endtb.Screens.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ihsinformatics.endtb.R;

public class InteractiveAlertDialog extends Dialog implements View.OnClickListener {

    private Button btnYes;
    private Button btnNo;
    private TextView tvTitle;
    private GifView gifView;
    private ImageView ivLoading;

    protected InteractiveAlertDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onClick(View view) {

    }

    public void show(Builder builder) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_interactive_alert_dialog);

        btnYes = (Button) findViewById(R.id.btn_yes);
        btnNo = (Button) findViewById(R.id.btn_no);
        btnYes.setVisibility(View.GONE);
        btnNo.setVisibility(View.GONE);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        // gifView = (GifView) findViewById(R.id.gvLoading);
        ivLoading = (ImageView) findViewById(R.id.ivLoading);


        setCancelable(builder.mIsCancelable);
        setCanceledOnTouchOutside(builder.mIsCancelableOnTouchOutSide);
        setTitle(builder.mTitle);
        // if(builder.mDisplayImageResource!=0)
            // setGifView(builder.mDisplayImageResource);
        if(builder.mTitleBackgroundColor!=0)
            setGifViewBackgroundColor(builder.mTitleBackgroundColor);
        if(builder.mPositiveButtonText!=null)
            setPositiveButtonText(builder.mPositiveButtonText);
        if(builder.mNegativeButtonText!=null)
            setNegativeButtonText(builder.mNegativeButtonText);
        super.show();
    }

    private void setTitle(String text) { tvTitle.setText(text); }
    private void setPositiveButtonText(String text) { btnYes.setText(text); btnYes.setVisibility(View.VISIBLE); }
    private void setNegativeButtonText(String text) { btnNo.setText(text); btnNo.setVisibility(View.VISIBLE); }
    // private void setGifView(int resource) { gifView.setImageDrawable(getContext().getResources().getDrawable(resource)); }
    private void setGifViewBackgroundColor(int resource) { gifView.setBackgroundColor(resource); }



    public static class Builder {
        private String mPositiveButtonText;
        private String mNegativeButtonText;
        private String mNeutralButtonText;
        private boolean mIsCancelableOnTouchOutSide;
        private boolean mIsCancelable;
        private String mTitle;
        private int mDisplayImageResource;
        private int mTitleBackgroundColor;
        InteractiveAlertDialog alertDialog;
        Context context;

        public Builder(Context context) {

            this.context = context;
        }

        public Builder setPositiveButtonText(String text) {
            mPositiveButtonText = text;
            return this;
        }

        public Builder setNegativeButtonText(String text) {
            mNegativeButtonText = text;
            return this;
        }

        public Builder setTitle(String text) {
            if(alertDialog != null) {alertDialog.setTitle(text);}
            else mTitle = text;
            return this;
        }

        public Builder setGif(int gifResource) {
            mDisplayImageResource = gifResource;
            return this;
        }

        public Builder setCancelable(boolean isCancelable) {
            mIsCancelable = isCancelable;
            return this;
        }

        public Builder setCancelableOnTouchOutSide(boolean isCancelableOnTouchOutSide) {
            mIsCancelableOnTouchOutSide = isCancelableOnTouchOutSide;
            return this;
        }

        public Builder setTitleBackgroundColor(int titleBackgroundColor) {
            mTitleBackgroundColor = titleBackgroundColor;
            return this;
        }

        public void show() {
            alertDialog = new InteractiveAlertDialog(context);
            alertDialog.show(this);
        }

        public void show(String message) {
            mTitle = message;
            alertDialog = new InteractiveAlertDialog(context);
            alertDialog.show(this);
        }

        public void dismiss() {
            alertDialog.dismiss();
        }
    }
}
