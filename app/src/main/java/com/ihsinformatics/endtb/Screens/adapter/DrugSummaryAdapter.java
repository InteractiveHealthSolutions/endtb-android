package com.ihsinformatics.endtb.Screens.adapter;

/**
 * Created by Nabil shafi on 4/26/2018.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ihsinformatics.endtb.R;
import com.ihsinformatics.endtb.Screens.adapter_models.DrugSummaryModel;

import java.util.ArrayList;

public class DrugSummaryAdapter extends RecyclerView.Adapter<DrugSummaryAdapter.SingleItemRowHolder> {

    private ArrayList<DrugSummaryModel> itemsList;
    private Context mContext;

    public DrugSummaryAdapter(Context context, ArrayList<DrugSummaryModel> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drug_summary_view, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);



        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder mh, int i) {

        DrugSummaryModel singleItem = itemsList.get(i);

        mh.tvDrugName.setText(singleItem.getName());
        mh.tvTotalFullyObserved.setText(singleItem.getTotalFullyObserved());
        mh.tvTotalIncomplete.setText(singleItem.getTotalIncomplete());
        mh.tvTotalMissedPrescribed.setText(singleItem.getTotalMissedPrescribed());

        /*Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvDrugName;
        protected TextView tvTotalIncomplete;
        protected TextView tvTotalFullyObserved;
        protected TextView tvTotalMissedPrescribed;

        public SingleItemRowHolder(View view) {
            super(view);
            this.tvDrugName = (TextView) view.findViewById(R.id.tvDrugName);
            this.tvTotalIncomplete = (TextView) view.findViewById(R.id.etAnswer_totalIncomplete);
            this.tvTotalFullyObserved = (TextView) view.findViewById(R.id.etAnswer_totalFullyObserved);
            this.tvTotalMissedPrescribed = (TextView) view.findViewById(R.id.etAnswer_totalMissedPrescribed);
        }

    }

}
