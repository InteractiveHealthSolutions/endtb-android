package com.ihsinformatics.endtb.Screens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ihsinformatics.endtb.R;
import com.ihsinformatics.endtb.Screens.adapter.ErrorRecordAdapter;
import com.ihsinformatics.endtb.database.Entities.SendableData;
import com.ihsinformatics.endtb.database.data.DbContentHelper;

import java.util.List;

public class ErrorRecordsDisplayActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_records_display);
        recyclerView = (RecyclerView) findViewById(R.id.rvErrorForms);
        List<SendableData> sendableData = DbContentHelper.getInstance().fetchSenableDataWithError();
        ErrorRecordAdapter errorRecordAdapter = new ErrorRecordAdapter(this, sendableData);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(errorRecordAdapter);
        errorRecordAdapter.notifyDataSetChanged();
    }
}
