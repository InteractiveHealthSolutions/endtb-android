package com.ihsinformatics.endtb.Screens.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ihsinformatics.endtb.R;
import com.ihsinformatics.endtb.database.Entities.SendableData;

import java.util.List;

/**
 * Created by Naveed Iqbal on 1/30/2018.
 */

public class ErrorRecordAdapter extends RecyclerView.Adapter<ErrorRecordAdapter.MyViewHolder> {

    private List<SendableData> sendableDataList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, error, tvDataType, attemptCount, showData;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tvPatientName);
            tvDataType = (TextView) view.findViewById(R.id.tvFormType);
            error = (TextView) view.findViewById(R.id.tvError);
            attemptCount = (TextView) view.findViewById(R.id.tvCount);
            showData = (TextView) view.findViewById(R.id.tvData);
        }
    }


    public ErrorRecordAdapter(Context context, List<SendableData> sendableDataList) {
        this.context = context;
        this.sendableDataList = sendableDataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.error_record_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final SendableData movie = sendableDataList.get(position);
        holder.title.setText(movie.getPatient().getGivenName() +" "+ movie.getPatient().getFamilyName());
        holder.tvDataType.setText(movie.getDataype());
        holder.error.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle(context.getResources().getString(R.string.error))
                        .setMessage(movie.getErrorMessage())
                        .setNegativeButton(context.getResources().getString(R.string.dismiss), null)
                        .show();
            }
        });
        holder.attemptCount.setText(movie.getNumberOfUploadAttempts()+"");
    }

    @Override
    public int getItemCount() {
        return sendableDataList.size();
    }
}