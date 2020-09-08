package com.ihsinformatics.endtb.utils.views;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.ihsinformatics.endtb.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Naveed Iqbal on 10/18/2017.
 * Email: h.naveediqbal@gmail.com
 */

public class InputWidget {

    private ViewGroup wrapperView;
    private TextView tvQuestion;
    private TextView tvMessage;
    private View vAnswer;
    private Activity context;
    private boolean isMandatory = true;
    private View.OnClickListener onClickListener;
    private String[] uuids = null;
    private AdapterView.OnItemSelectedListener onItemSelectedListener;

    public static enum INPUT_WIDGET_TYPE {
        RADIO_GROUP,
        EDIT_TEXT,
        SPINNER,
        DATE
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
        if(vAnswer instanceof Spinner)
            ((Spinner) vAnswer).setOnItemSelectedListener(onItemSelectedListener);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        getClickableView().setOnClickListener(onClickListener);
    }

    public void requestFocus() {
        vAnswer.requestFocus();
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }

    public void setVisibility(int visibility) {
        wrapperView.setVisibility(visibility);
        if(onClickListener != null)
            onClickListener.onClick(getClickableView());
        if(onItemSelectedListener != null) {
            Spinner spinner = ((Spinner) vAnswer);
            onItemSelectedListener.onItemSelected(spinner, vAnswer, spinner.getSelectedItemPosition(), spinner.getSelectedItemId());
        }
    }

    public int getViewId() {
        return vAnswer.getId();
    }

    public int getVisibility() {
        return wrapperView.getVisibility();
    }

    public View getClickableView() {
        return vAnswer;
    }

    public InputWidget(Activity context, View parentView, int layoutId, int tvQuestionId, int tvMessageId, int inputWidgetId, INPUT_WIDGET_TYPE input_widget_type) {
        this.context = context;


        wrapperView = (ViewGroup) parentView.findViewById(layoutId);

        tvQuestion = (TextView) parentView.findViewById(tvQuestionId);
        tvMessage = (TextView) parentView.findViewById(tvMessageId);
        switch (input_widget_type) {
            case EDIT_TEXT:
                vAnswer = (EditText) parentView.findViewById(inputWidgetId);
                break;
            case SPINNER:
                vAnswer = (Spinner) parentView.findViewById(inputWidgetId);
                break;
            case DATE:
                vAnswer = (EditText) parentView.findViewById(inputWidgetId);
                break;
            case RADIO_GROUP:
                vAnswer = (RadioGroup) parentView.findViewById(inputWidgetId);
                break;


        }
    }
    public InputWidget(Activity context, int layoutId, int tvQuestionId, int tvMessageId, int inputWidgetId, INPUT_WIDGET_TYPE input_widget_type) {
        this.context = context;


        wrapperView = (ViewGroup) context.findViewById(layoutId);

        tvQuestion = (TextView) context.findViewById(tvQuestionId);
        tvMessage = (TextView) context.findViewById(tvMessageId);
        switch (input_widget_type) {
            case EDIT_TEXT:
                vAnswer = (EditText) context.findViewById(inputWidgetId);
                break;
            case SPINNER:
                vAnswer = (Spinner) context.findViewById(inputWidgetId);
                break;
            case DATE:
                vAnswer = (EditText) context.findViewById(inputWidgetId);
                break;
            case RADIO_GROUP:
                vAnswer = (RadioGroup) context.findViewById(inputWidgetId);
                break;
        }
    }

    public void setValue(String value) {
        if(value == null)
            return;
        if(vAnswer instanceof EditText) {
            ((EditText)vAnswer).setText(value);
        } else if(vAnswer instanceof Spinner) {
            Spinner sp = (Spinner)vAnswer;
            int index = ((ArrayAdapter<String>)sp.getAdapter()).getPosition(value);
            sp.setSelection(index);
            //throw new UnsupportedOperationException(context.getString(R.string.method_call_spinner));
        } else if(vAnswer instanceof RadioGroup) {
            RadioGroup rg = (RadioGroup) vAnswer;
            for(int i=0; i<rg.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) rg.getChildAt(i);
                if(radioButton.getText().toString().equals(value)) {
                    radioButton.setChecked(true);
                }
            }
        }
    }

    public String getValue() {
        String value = "";

        if(vAnswer instanceof EditText) {
            EditText etAnswer = (EditText) vAnswer;
            value = etAnswer.getText().toString();

        } else if(vAnswer instanceof Spinner) {
            Spinner spAnswer = (Spinner) vAnswer;
            if(uuids == null) {
                value = spAnswer.getSelectedItem().toString();
            } else {
                int index = spAnswer.getSelectedItemPosition();
                value = uuids[index];
            }

        } else if(vAnswer instanceof RadioGroup) {
            RadioGroup rgAnswer = (RadioGroup) vAnswer;
            int selectedId = rgAnswer.getCheckedRadioButtonId();
            if(selectedId>0) {
                RadioButton rbAnswer = (RadioButton) context.findViewById(selectedId);
                value = rbAnswer.getText().toString();
            }
        }

        return value;
    }

    public void setSpinnerValues(List values) throws UnsupportedOperationException {
        if (vAnswer instanceof Spinner) {
            ArrayAdapter<String> adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, values);
            ((Spinner)vAnswer).setAdapter(adapter);
        } else {
            throw new UnsupportedOperationException(context.getString(R.string.method_call_spinner));
        }
    }

    public void setSpinnerValues(Object... objs) throws UnsupportedOperationException {
        setSpinnerValues(Arrays.asList(objs));
    }

    public void setSpinnerValues(String[] uuids, Object... objs) throws UnsupportedOperationException {
        setSpinnerValues(Arrays.asList(objs));
        this.uuids = uuids;
    }

    public void setMessage(String message) {
        tvMessage.setText(message);
    }

    public void setMessage(String message, int color) {
        setMessage(message);
        tvMessage.setTextColor(color);
    }

    public String getMessage() {
        String message = tvMessage.getText().toString();

        return  message;
    }

    public void setBackground(Drawable background) {
        vAnswer.setBackground(background);
    }
}
