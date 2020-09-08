package com.ihsinformatics.endtb.database.data;

import com.ihsinformatics.endtb.database.Entities.Concept;
import com.ihsinformatics.endtb.utils.ELimsApplication;
import com.ihsinformatics.endtb.utils.MappingHolder;
import com.ihsinformatics.endtb.utils.OpenMRSMappings;

import java.util.LinkedHashMap;

/**
 * Created by Naveed Iqbal on 10/29/2017.
 * Email: h.naveediqbal@gmail.com
 */

public class DefaultData {

    private LinkedHashMap<String, Concept> concepts;

    public DefaultData() {
        initConcepts();

    }
    public void insertDefaultData() {
        insertConcepts();
    }

    private void insertConcepts() {

        for(Concept c: concepts.values()) {
            System.out.println(c.getUuid());
            ELimsApplication.daoSession.getConceptDao().insertOrReplace(c);
        }
        // ELimsApplication.daoSession.getConceptDao().insertInTx(concepts.values());
    }

    private void initConcepts() {
        concepts = new LinkedHashMap<>();
        // Patient Registration Form
        concepts.put(OpenMRSMappings.CONCEPT_OTHER.getName(),new Concept(null, "Other", OpenMRSMappings.CONCEPT_OTHER.getName(), OpenMRSMappings.CONCEPT_OTHER.getUuid(), DataProvider.CONCEPT_DATA_TYPE.NONE.toString()));

        // Add TeputInfo Form
        concepts.put(OpenMRSMappings.CONCEPT_COLLECTION_DATE.getName(),new Concept(null, "Date Of Collection", OpenMRSMappings.CONCEPT_COLLECTION_DATE.getName(), OpenMRSMappings.CONCEPT_COLLECTION_DATE.getUuid(), DataProvider.CONCEPT_DATA_TYPE.TEXT.toString()));

        putConcept(OpenMRSMappings.CONCEPT_ELIGIBLE_FOR_NEW_DRUG, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_DATE_OF_ELIGIBILITY, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_INDICATION_FOR_NEW_TB_DRUGS, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_PATIENT_SITUATION, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_ADDITIONAL_COMMENTS, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_CONSENT_FOR_NEW_DRUGS, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_CONSENT_FOR_OBS_STUDY, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_PREGNANT_AT_TX_START, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_EXPECTED_DELIVERY_DATE, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_BREAST_FEEDING_AT_TX_START, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_STARTED_TX, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_NATIONAL_DR_TB_ID, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_TX_START_DATE, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_TX_FACILITY_AT_START, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_FACILITY_PATIENT_ID, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_TS_ID, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_TS_FIRST_NAME, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_TS_LAST_NAME, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_REASON_NOT_STARTING_TX, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_DEATH_DATE_BEFORE_TX_START, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_NEXT_VISIT, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_NEXT_VISIT_REASON, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_OTHER_ASSESSMENT_REASON, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);

        putConcept(OpenMRSMappings.CONCEPT_OTHER_DELIVERY_METHOD, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_TX_DELIVERY_METHOD, DataProvider.CONCEPT_DATA_TYPE.CODED);
        putConcept(OpenMRSMappings.CONCEPT_IS_NON_PRESCRIBED, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_INPATIENT, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_OUTPATIENT_FACILITY, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_OUTPATIENT_COMMUNITY, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_SELF_ADMINISTERED, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_SAT_DOT_COMBINATION, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_OTHER, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_ADMINISTERED_DOSE_QUANTITY, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_DRUG_ADMINISTRATION_OF_DAY, DataProvider.CONCEPT_DATA_TYPE.CODED);
        putConcept(OpenMRSMappings.CONCEPT_LONGITUDE, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_LATITUDE, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_FULLY_OBSERVED_PRESCRIBED_DAY, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_INCOMPLETE_PRESCRIBED_DAY, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_MISSED_PRESCRIBED_DAY, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);

        putConcept(OpenMRSMappings.CONCEPT_DOT_RATE, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_DRUG_ADMINISTRATION, DataProvider.CONCEPT_DATA_TYPE.CODED);
        putConcept(OpenMRSMappings.CONCEPT_MISSED_DOSE, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_ADMINISTERED_DOSE_QUANTITY, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);

        putConcept(OpenMRSMappings.CONCEPT_FULLY_OBSERVED_PRESCRIBED_DOSE, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_MISSED_PRESCRIBED_DOSE, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_INCOMPLETE_PRESCRIBES_DOSE, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);


        putConcept(OpenMRSMappings.CONCEPT_TRUE, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_FALSE, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_REFUSE, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_UNKNOWN, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_NOT_APPLICABLE, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_WEIGHT, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_HEIGHT, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_FACILITY_ID, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_CONTACT_COUGH, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_CONTACT_SYMPTOMATIC, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_CONTACT_PREGNANT, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_CONTACT_NIGHT_SWEATS, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_CONTACT_HEMOPTYSIS, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_CONTACT_ADENOPATHY, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_CONTACT_PLAYFULNESS, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_CONTACT_WEIGHT_LOSS, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_CONTACT_PAST_TB_TX, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_CONTACT_TB_TX_OUTCOME, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_CONTACT_TB_KIND, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
        putConcept(OpenMRSMappings.CONCEPT_CONTACT_VISIT_CLINIC, DataProvider.CONCEPT_DATA_TYPE.UNKNOWN);
    }

    private void putConcept(MappingHolder mappingHolder, DataProvider.CONCEPT_DATA_TYPE concept_data_type) {
        concepts.put(mappingHolder.getName(),
                new Concept(null,
                        mappingHolder.getName(),
                        mappingHolder.getShortName(),
                        mappingHolder.getUuid(),
                        concept_data_type.toString()));
    }

    public LinkedHashMap<String, Concept> getConcepts() {
        return concepts;
    }
}
