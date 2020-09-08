package com.ihsinformatics.endtb.database.json_helper;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ihsinformatics.endtb.R;
import com.ihsinformatics.endtb.Screens.adapter.DrugSummaryAdapter;
import com.ihsinformatics.endtb.Screens.adapter_models.DrugSummaryModel;
import com.ihsinformatics.endtb.Screens.patient_list_recycler.PatientModel;
import com.ihsinformatics.endtb.database.Entities.Concept;
import com.ihsinformatics.endtb.database.Entities.DrugOrders;
import com.ihsinformatics.endtb.database.Entities.Obs;
import com.ihsinformatics.endtb.database.data.DbContentHelper;
import com.ihsinformatics.endtb.databinding.ActivityMonthlyReviewBinding;
import com.ihsinformatics.endtb.utils.Global;
import com.ihsinformatics.endtb.utils.MappingHolder;
import com.ihsinformatics.endtb.utils.OpenMRSMappings;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class MonthlyReview extends AppCompatActivity {

    private ActivityMonthlyReviewBinding binding;
    private PatientModel patientModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_monthly_review);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_monthly_review);

        patientModel = (PatientModel) getIntent().getSerializableExtra(Global.PATIENT_MODEL);
        LocalDate now = new LocalDate();
        LocalDate monday = now.withDayOfMonth(1);
        System.out.println(now+" | "+monday);

        ((TextView)findViewById(R.id.tvPatientId)).setText(patientModel.getId());
        ((TextView)findViewById(R.id.tvPatientName)).setText(patientModel.getName());
        ((TextView)findViewById(R.id.tvAge)).setText(patientModel.getAge()+" yr");
        if(patientModel.getGender().toLowerCase().startsWith("f")) {
            ((ImageView)findViewById(R.id.ivGender)).setImageDrawable(getResources().getDrawable(R.drawable.female_icon));
        } else {
            ((ImageView)findViewById(R.id.ivGender)).setImageDrawable(getResources().getDrawable(R.drawable.male_icon));
        }

        initDeliveryMethodSummary(monday, now);
        initDrugAdministrationSummary(monday, now);
        initDrugMonthlySummary(monday, now);
    }

    private void initDeliveryMethodSummary(LocalDate dateFrom, LocalDate dateTo) {
        long txDeliveryMethodConceptId = fetchConceptId(OpenMRSMappings.CONCEPT_TX_DELIVERY_METHOD);
        List<Obs> txDeliveryMethodsThisMonth = DbContentHelper.getInstance().fetchAdministeredDrugDoses(dateFrom.toDate().getTime(), dateTo.toDate().getTime(), txDeliveryMethodConceptId, patientModel.getDbId());

        int inpatient = 0;
        int outpatientFacilityBased = 0;
        int outpatientCommunitybased = 0;
        int sat = 0;
        int satAndDot = 0;
        int other = 0;

        long inpatientConceptId = fetchConceptId(OpenMRSMappings.CONCEPT_INPATIENT);
        long outpatientFacilityBasedConceptId = fetchConceptId(OpenMRSMappings.CONCEPT_OUTPATIENT_FACILITY);
        long outpatientCommunitybasedConceptId = fetchConceptId(OpenMRSMappings.CONCEPT_OUTPATIENT_COMMUNITY);
        long satConceptId = fetchConceptId(OpenMRSMappings.CONCEPT_SELF_ADMINISTERED);
        long satAndDotConceptId = fetchConceptId(OpenMRSMappings.CONCEPT_SAT_DOT_COMBINATION);
        long otherConceptId = fetchConceptId(OpenMRSMappings.CONCEPT_OTHER);

        for(Obs obs: txDeliveryMethodsThisMonth) {
            if(inpatientConceptId == obs.getConceptId() || obs.getValue().equals(OpenMRSMappings.CONCEPT_INPATIENT.getShortName())) {
                inpatient++;
            } else if(outpatientFacilityBasedConceptId == obs.getConceptId() || obs.getValue().equals(OpenMRSMappings.CONCEPT_OUTPATIENT_FACILITY.getShortName())) {
                outpatientFacilityBased++;
            } else if(outpatientCommunitybasedConceptId == obs.getConceptId() || obs.getValue().equals(OpenMRSMappings.CONCEPT_OUTPATIENT_COMMUNITY.getShortName())) {
                outpatientCommunitybased++;
            } else if(satConceptId == obs.getConceptId() || obs.getValue().equals(OpenMRSMappings.CONCEPT_SELF_ADMINISTERED.getShortName())) {
                sat++;
            } else if(satAndDotConceptId == obs.getConceptId() || obs.getValue().equals(OpenMRSMappings.CONCEPT_SAT_DOT_COMBINATION.getShortName())) {
                satAndDot++;
            } else if(otherConceptId == obs.getConceptId() || obs.getValue().equals(OpenMRSMappings.CONCEPT_OTHER.getShortName())) {
                other++;
            }
        }

        binding.etAnswerTxMethodInpatient.setText(inpatient+"");
        binding.etAnswerTxMethodOutpatientFacility.setText(outpatientFacilityBased+"");
        binding.etAnswerTxMethodOutpatientCommunity.setText(outpatientCommunitybased+"");
        binding.etAnswerTxMethodSAT.setText(sat+"");
        binding.etAnswerTxMethodSATnDOT.setText(satAndDot+"");
        binding.etAnswerTxMethodOther.setText(other+"");
    }


    private void initDrugAdministrationSummary(LocalDate dateFrom, LocalDate dateTo) {
        long drugAdministrationConceptId = fetchConceptId(OpenMRSMappings.CONCEPT_DRUG_ADMINISTRATION_OF_DAY);
        List<Obs> drugAdministrationThisMonth = DbContentHelper.getInstance().fetchAdministeredDrugDoses(dateFrom.toDate().getTime(), dateTo.toDate().getTime(), drugAdministrationConceptId, patientModel.getDbId());

        int missedPrescribedDays = 0;
        int incompleteDays = 0;
        int fullyObservedDays = 0;

        long missedPrescribedDaysConceptId = fetchConceptId(OpenMRSMappings.CONCEPT_MISSED_PRESCRIBED_DAY);
        long incompleteDaysConceptId = fetchConceptId(OpenMRSMappings.CONCEPT_INCOMPLETE_PRESCRIBED_DAY);
        long fullyObservedDaysConceptId = fetchConceptId(OpenMRSMappings.CONCEPT_FULLY_OBSERVED_PRESCRIBED_DAY);

        for(Obs obs: drugAdministrationThisMonth) {
            if(missedPrescribedDaysConceptId == obs.getConceptId() || obs.getValue().equals(OpenMRSMappings.CONCEPT_MISSED_PRESCRIBED_DAY.getShortName())) {
                missedPrescribedDays++;
            } else if(incompleteDaysConceptId == obs.getConceptId() || obs.getValue().equals(OpenMRSMappings.CONCEPT_INCOMPLETE_PRESCRIBED_DAY.getShortName())) {
                incompleteDays++;
            } else if(fullyObservedDaysConceptId == obs.getConceptId() || obs.getValue().equals(OpenMRSMappings.CONCEPT_FULLY_OBSERVED_PRESCRIBED_DAY.getShortName())) {
                fullyObservedDays++;
            }
        }

        binding.etAnswerTotalMissedPrescribed.setText(missedPrescribedDays+"");
        binding.etAnswerTotalIncomplete.setText(incompleteDays+"");
        binding.etAnswerTotalFullyObserved.setText(fullyObservedDays+"");
    }

    private long fetchConceptId(MappingHolder mappingHolder) {
        Concept concept = DbContentHelper.getInstance().fetchConcept(mappingHolder.getShortName());
        return concept==null?-1: concept.getId();
    }

    private void initDrugMonthlySummary(LocalDate dateFrom, LocalDate dateTo) {
        ArrayList<DrugSummaryModel> allItemsInSection = createDrugSummary(dateFrom, dateTo);
        RecyclerView rvDrugsSummary = binding.rvDrugsSummary;
        rvDrugsSummary.setHasFixedSize(true);
        rvDrugsSummary.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        DrugSummaryAdapter itemListDataAdapter = new DrugSummaryAdapter(this, allItemsInSection);
        rvDrugsSummary.setAdapter(itemListDataAdapter);

    }

    private ArrayList<DrugSummaryModel> createDrugSummary(LocalDate dateFrom, LocalDate dateTo) {
        ArrayList<DrugSummaryModel> models = new ArrayList<>();
        List<DrugOrders> drugOrders = DbContentHelper.getInstance().fetchActiveDrugOrdersByPatientId(patientModel.getDbId());
        long drugAdministrationConceptId = fetchConceptId(OpenMRSMappings.CONCEPT_DRUG_ADMINISTRATION);
        for (DrugOrders d: drugOrders) {
            List<Obs> drugAdministered = DbContentHelper.getInstance().fetchAdministeredDrugDoses(dateFrom.toDate().getTime(), dateTo.toDate().getTime(), d.getDrug().getConceptId(), patientModel.getDbId());
            String[] parents = new String[drugAdministered.size()];
            for(int i=0; i<drugAdministered.size(); i++) {
                parents[i] = drugAdministered.get(i).getLocalUUID();
            }
            List<Obs> drugAdministerationThisMonth = DbContentHelper.getInstance().fetchObsByParentConcept(drugAdministrationConceptId, parents);

            int missedPrescribedDays = 0;
            int incompleteDays = 0;
            int fullyObservedDays = 0;

            long missedPrescribedDaysConceptId = fetchConceptId(OpenMRSMappings.CONCEPT_MISSED_PRESCRIBED_DOSE);
            long incompleteDaysConceptId = fetchConceptId(OpenMRSMappings.CONCEPT_INCOMPLETE_PRESCRIBES_DOSE);
            long fullyObservedDaysConceptId = fetchConceptId(OpenMRSMappings.CONCEPT_FULLY_OBSERVED_PRESCRIBED_DOSE);

            for(Obs obs: drugAdministerationThisMonth ) {
                if(missedPrescribedDaysConceptId == obs.getConceptId() || obs.getValue().equals(OpenMRSMappings.CONCEPT_MISSED_PRESCRIBED_DOSE.getShortName())) {
                    missedPrescribedDays++;
                } else if(incompleteDaysConceptId == obs.getConceptId() || obs.getValue().equals(OpenMRSMappings.CONCEPT_INCOMPLETE_PRESCRIBES_DOSE.getShortName())) {
                    incompleteDays++;
                } else if(fullyObservedDaysConceptId == obs.getConceptId() || obs.getValue().equals(OpenMRSMappings.CONCEPT_FULLY_OBSERVED_PRESCRIBED_DOSE.getShortName())) {
                    fullyObservedDays++;
                }
            }

            /*binding.etAnswerTotalMissedPrescribed.setText(missedPrescribedDays+"");
            binding.etAnswerTotalIncomplete.setText(incompleteDays+"");
            binding.etAnswerTotalFullyObserved.setText(fullyObservedDays+"");*/

            models.add(new DrugSummaryModel(
                    d.getDrug().getName(),
                    incompleteDays+"",
                    missedPrescribedDays+"",
                    fullyObservedDays+""));
        }

        return models;
    }
}
