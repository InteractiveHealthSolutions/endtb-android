package com.ihsinformatics.endtb.Screens.patient_list_recycler;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ihsinformatics.endtb.R;
import com.ihsinformatics.endtb.Screens.DailyTreatmentMonitoring;
import com.ihsinformatics.endtb.Screens.FormsList;
import com.ihsinformatics.endtb.database.json_helper.MonthlyReview;
import com.ihsinformatics.endtb.utils.Global;

import java.util.List;

/**
 * Created by Naveed Iqbal on 11/1/2017.
 * Email: h.naveediqbal@gmail.com
 */

public class PatientsAdapter extends RecyclerView.Adapter<PatientsAdapter.MyViewHolder> {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private List<PatientModel> patientModelList;
    private Context context;

    public PatientsAdapter(Context context, List<PatientModel> patientsList) {
        this.patientModelList = patientsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final PatientModel patientModel = patientModelList.get(position);
        holder.id.setText(patientModel.getId());
        holder.name.setText(patientModel.getName());
        holder.occupation.setText(patientModel.getOccupation());
        holder.age.setText(patientModel.getAge()+"");
        if(patientModel.getGender().toLowerCase().startsWith("f")) {
            holder.gender.setImageDrawable(context.getResources().getDrawable(R.drawable.female_icon));
        } else {
            holder.gender.setImageDrawable(context.getResources().getDrawable(R.drawable.male_icon));
        }
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FormsList.class);
                intent.putExtra(Global.PATIENT_MODEL, patientModel);
                context.startActivity(intent);
            }
        });
        holder.ivView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MonthlyReview.class);
                intent.putExtra(Global.PATIENT_MODEL, patientModel);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return patientModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView id, name, occupation, age;
        public ImageView gender;
        public ImageView ivView;
        public  View view;

        public MyViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.tvPatientId);
            name = (TextView) view.findViewById(R.id.tvPatientName);
            occupation = (TextView) view.findViewById(R.id.tvContact);
            age = (TextView) view.findViewById(R.id.tvAge);
            gender = (ImageView) view.findViewById(R.id.ivGender);
            ivView = (ImageView) view.findViewById(R.id.ivView);
            this.view = view.findViewById(R.id.rlMain);
        }
    }
}
