package com.ihsinformatics.endtb.utils;

import android.content.Context;

import com.ihsinformatics.endtb.R;
import static com.ihsinformatics.endtb.utils.ELimsApplication.context;
/**
 * Created by Naveed Iqbal on 11/4/2017.
 * Email: h.naveediqbal@gmail.com
 */

public class OpenMRSMappings {

    public static String ORDER_ACTION_DISCONTINUE = "DISCONTINUE";
    public static String ORDER_ACTION_NEW = "NEW";
    public static MappingHolder ROLE_CLINICAL_COORDINATOR = new MappingHolder("Clinical Coordinator", "d30aa5c8-be92-410f-b14c-a90d51913931");
    public static MappingHolder ROLE_DATA_ENTR_OFFICER = new MappingHolder("Data Entry Officer", "67125d08-f74b-461e-911f-97d80b080915");
    public static MappingHolder ROLE_FACILITY_DOT_PROVIDER = new MappingHolder("Facility DOT Provider", "e0b667e9-8e3d-4fae-8f2d-c068fd199ca1");
    public static MappingHolder ROLE_FIELD_MANAGER = new MappingHolder("Field Manager", "2072dbb7-c0ea-41c6-9782-11b0fdea910b");
    public static MappingHolder ROLE_MDR_COUNSELOR = new MappingHolder("MDR Counselor", "d402e900-ad4b-4bec-a271-24b99d4628bc");
    public static MappingHolder ROLE_MEDICAL_OFFICER = new MappingHolder("Medical Officer", "b9332815-14dd-4c1b-90bc-cbf59bbe2de3");
    public static MappingHolder ROLE_TREATMENT_COORDINATOR = new MappingHolder("Treatment Coordinator", "70fa0a30-0b95-4a79-8991-052108de8982");
    public static MappingHolder ROLE_TREATMENT_SUPPORTER = new MappingHolder("Treatment Supporter", "2dd05c2f-eedf-45e2-b05c-ccb76e98a9b3");

    public static MappingHolder ENCOUNTER_TYPE_PROVIDER_REGISTRATION = new MappingHolder("Provider Registration", "de7d3a01-2133-4444-950c-ad6ddb64ae73");
    public static MappingHolder ENCOUNTER_TYPE_SECOND_LINE_TB_TX_REGISTER = new MappingHolder("Second-line TB treatment register", "3da3d965-7b20-4afb-8efb-26ceedeb36b3");
    public static MappingHolder ENCOUNTER_TYPE_DTM = new MappingHolder("Daily Treatment Monitoring", "8520573f-3333-44e7-a453-a52d6f04d92f");
    public static MappingHolder ENCOUNTER_TYPE_MONTHLY_SUMMARY = new MappingHolder("Monthly Summary", "e2a3d8ed-2baa-48a3-a02c-5515a194a468");
    public static MappingHolder ENCOUNTER_BASIC_MANAGEMENT_TB_REGISTER = new MappingHolder("Basic management unit TB register", "22dffad3-1a3c-4fc2-8207-a48086675e02");
    public static MappingHolder ENCOUNTER_IHN_REGISTER = new MappingHolder("IHN register", "e0ebd8cb-999c-4291-91ef-65cf6278c3e3");
    public static MappingHolder ENCOUNTER_ADVERSE_EVENT = new MappingHolder("Adverse Event", "d0b1c1b3-1611-4547-88cc-81ca73f21030");
    public static MappingHolder ENCOUNTER_TYPE_CONTACT_REGISTRY = new MappingHolder("Contact Registry", "a54beb0b-6b4a-4156-8819-7dc20fae7390");
    public static MappingHolder ENCOUNTER_TYPE_CONTACT_SYMPTOM_SCREENING = new MappingHolder("Contact Symptom Screening", "37f87add-f78e-429e-9f8e-6ab44084904f");
    public static MappingHolder ENCOUNTER_TYPE_CONTACT_DIAGNOSTIC = new MappingHolder("Contact Diagnostic Screening Results", "a982bac1-b4a9-4248-bf2e-bbf33e6ba043");
    public static MappingHolder ENCOUNTER_TYPE_TS_MONITORING = new MappingHolder("TS Monitoring", "3b9b303f-a001-4a0d-bf0c-99b869de53b0");

    public static MappingHolder ORDER_TYPE_DRUG_ORDER = new MappingHolder("Drug Order", "131168f4-15f5-102d-96e4-000c29c2a5d7");

    public static MappingHolder ATTRIBUTE_TYPE_PRIMARY_CONTACT = new MappingHolder("Primary Contact Number", "a335a98d-202a-11e8-a5ff-080027c4294b");
    public static MappingHolder ATTRIBUTE_TYPE_PRIMARY_SECONDARY_CONTACT = new MappingHolder("Secodary Contact Number", "b3d7ac79-aea6-4b41-a573-7618f381c912");
    public static MappingHolder ATTRIBUTE_TYPE_OCCUPATION = new MappingHolder("Occupation", "a3334ab2-202a-11e8-a5ff-080027c4294b");
    public static MappingHolder ATTRIBUTE_TYPE_WAS_HW = new MappingHolder("Was a health worker", "82f4ab56-a42a-45cd-a453-7f4e2a6b63a0");
    public static MappingHolder ATTRIBUTE_TYPE_IS_HW = new MappingHolder("Is currentlly a health worker", "21d0615f-6856-41de-88da-0cdbaa0c9c63");
    public static MappingHolder ATTRIBUTE_TYPE_CAN_INJECT = new MappingHolder("Knows how to administer injection", "800b86b6-83c2-4b59-884c-5c19447ec0c4");
    public static MappingHolder ATTRIBUTE_TYPE_IS_TRAINED_AT_FACILITY = new MappingHolder("Is trained at facility", "97c606b0-4107-47a5-8c34-71320e29004a");
    public static MappingHolder ATTRIBUTE_TYPE_TRAINING_FACILITY = new MappingHolder("Training facility", "e9dc3565-2e70-4177-97ec-03931e19f018");
    public static MappingHolder ATTRIBUTE_TYPE_TRAINED_BY = new MappingHolder("Trained by", "2877f13c-c315-40fd-9de0-7f14a80740ca");
    public static MappingHolder ATTRIBUTE_TYPE_LANGUAGE = new MappingHolder("Spoken Language", "28dde2fa-e971-4aff-a2e5-0c83f542ac3e");
    public static MappingHolder ATTRIBUTE_TYPE_TS = new MappingHolder("Treatment Supporter", "b2b549fd-265c-49c6-bc91-fcaec71a8c48");
    public static MappingHolder ATTRIBUTE_TYPE_RELATION = new MappingHolder("Relation", "64e994e9-f36a-4267-a97c-f0e6bccb8f95");
    public static MappingHolder ATTRIBUTE_TYPE_RELATIVE_ID = new MappingHolder("Relative ID", "ff1ed9c3-ddca-40e5-9945-5acd5810e49b");

    public static MappingHolder PATIENT_IDENTIFIER_TYPE_PATIENT_IDENTIFIER = new MappingHolder("Patient Identifier", "8967f9d3-3a91-11e5-b380-0050568236ae");
    public static MappingHolder PATIENT_IDENTIFIER_TYPE_CONTACT_IDENTIFIER = new MappingHolder("Contact Identifier", "a7bc1ce2-5e8d-4ff7-9bf7-a8092ea40ffd");


    public static MappingHolder CONCEPT_COLLECTION_DATE = new MappingHolder("Collection Date", "159951AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    public static MappingHolder CONCEPT_TEST_ORDER = new MappingHolder("Test Order", "4bbfcb2b-ec36-48de-b101-a97119425501");

    public static MappingHolder CONCEPT_ELIGIBLE_FOR_NEW_DRUG = new MappingHolder("TI, Eligible for new drugs", "Eligible for new drugs?", "e5eb9144-c3dc-4c15-bb5e-28dbe3f6ccfa");
    public static MappingHolder CONCEPT_DATE_OF_ELIGIBILITY = new MappingHolder("TI, Date of eligibility for new drugs", "Date eligible new drugs", "262a0265-e48a-4f45-881b-f750ed6f4ebf");
    public static MappingHolder CONCEPT_INDICATION_FOR_NEW_TB_DRUGS = new MappingHolder("TI, Indication for new TB drugs", "Indication For New Drug", "ccf94ca2-84ca-47e3-b17b-00adc1408428");
    public static MappingHolder CONCEPT_PATIENT_SITUATION = new MappingHolder("TI, Situation that applies to this patient", "Patient situation", "10819e0e-1584-4bd5-88b7-efdacc7499ef");
    public static MappingHolder CONCEPT_ADDITIONAL_COMMENTS = new MappingHolder("TI, Additional comments (about why the patient is being started on new TB drugs)", "Additional comments for treatment initiation", "0f6e20e3-271a-4eaf-b55d-c89fc26df375");
    public static MappingHolder CONCEPT_CONSENT_FOR_NEW_DRUGS = new MappingHolder("TI, Has the Treatment with New Drug Consent Form been explained and signed", "Consent for New Drug Signed?", "a90d738e-8e55-4580-bfcb-fc6039a4b9be");
    public static MappingHolder CONCEPT_CONSENT_FOR_OBS_STUDY = new MappingHolder("TI, Has the endTB Observational Study Consent Form been explained and signed", "Consent for endTB Obs Study signed?", "6b806f15-031c-4efb-8362-cbe9c4a0b003");
    public static MappingHolder CONCEPT_PREGNANT_AT_TX_START = new MappingHolder("TI, Currently pregnant", "Pregnant at Treatment Start", "c8acbb9e-a034-402b-8df4-7cc03296d2c7");
    public static MappingHolder CONCEPT_EXPECTED_DELIVERY_DATE = new MappingHolder("Estimated date of confinement", "Expected Delivery Date", "5596AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    public static MappingHolder CONCEPT_BREAST_FEEDING_AT_TX_START = new MappingHolder("TI, Currently breastfeeding", "Breast Feeding at Treatment Start", "54377652-3f60-41d4-b9bb-cdf83a86950a");
    public static MappingHolder CONCEPT_STARTED_TX = new MappingHolder("TI, Did the patient start treatment", "Start treatment?", "6ed59490-264a-447c-acf7-e06db4129c3c");
    public static MappingHolder CONCEPT_NATIONAL_DR_TB_ID = new MappingHolder("TI, National DR TB Identifier", "National DR TB ID", "6e185bf1-0ac2-4d41-8f84-6e5ef0e19984");
    public static MappingHolder CONCEPT_TX_START_DATE = new MappingHolder("TUBERCULOSIS DRUG TREATMENT START DATE", "Treatment start date", "1113AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    public static MappingHolder CONCEPT_TX_FACILITY_AT_START = new MappingHolder("TI, Treatment facility at start", "Treatment facility at start", "c35e626a-f9eb-401c-a7a6-e8a74bbb8837");
    public static MappingHolder CONCEPT_FACILITY_PATIENT_ID = new MappingHolder("Facility patient ID", "Facility patient ID", "d66664af-fa60-42e8-9b10-6b34114b7db9");
    public static MappingHolder CONCEPT_TS_ID = new MappingHolder("Treatment Supporter ID number", "TS ID", "1c662ec9-4ad1-4bd7-bcd1-47fa1d7aaf51");
    public static MappingHolder CONCEPT_TS_FIRST_NAME = new MappingHolder("Treatment Supporter first name", "TS First name", "9ba50464-adae-409b-9c8d-9f71daa5e92a");
    public static MappingHolder CONCEPT_TS_LAST_NAME = new MappingHolder("Treatment Supporter last name", "TS Last name", "ef2ef27f-fbbc-4967-814b-0efb37eacc34");
    public static MappingHolder CONCEPT_REASON_NOT_STARTING_TX = new MappingHolder("TI, Reason for not starting treatment", "If no, reason for not starting treatment", "213ef0cb-cc0d-421f-a715-aa1022a02fb6");
    public static MappingHolder CONCEPT_DEATH_DATE_BEFORE_TX_START = new MappingHolder("TI, Date of death before treatment start", "Date of death before treatment start", "a638cc74-6566-487b-8f98-2dc995aa2ab8");
    public static MappingHolder CONCEPT_NEXT_VISIT = new MappingHolder("RETURN VISIT DATE", "Next visit", "5096AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    public static MappingHolder CONCEPT_NEXT_VISIT_REASON = new MappingHolder("TI, Reason for next assessment", "Reason for next visit", "9408be14-a7af-42cd-b241-61f0bf66657f");
    public static MappingHolder CONCEPT_OTHER_ASSESSMENT_REASON = new MappingHolder("TI, Other assessment reason", "Other assessment reason", "df8a2214-8523-45b5-b4a2-4fb9ac27bbf7");
    public static MappingHolder CONCEPT_OTHER_DELIVERY_METHOD = new MappingHolder("Medication log, Other treatment delivery method", "Other treatment delivery method", "39e54ecb-6af7-4fc3-9276-b62a364c3d0d");
    public static MappingHolder CONCEPT_TX_DELIVERY_METHOD = new MappingHolder("Medication log, Treatment delivery method", "Treatment delivery method", "6fabbcfc-057d-4883-8b48-96671f070a24");
    public static MappingHolder CONCEPT_IS_NON_PRESCRIBED = new MappingHolder("Is Non Prescribed", "Is Non Prescribed", "be5f0e46-5b54-4cf0-8a16-9df526ae326c");
    public static MappingHolder CONCEPT_TX_DELIVERY_METHOD_OTHER = new MappingHolder("Medication log, Other treatment delivery method", "Other treatment delivery method", "39e54ecb-6af7-4fc3-9276-b62a364c3d0d");
    public static MappingHolder CONCEPT_DRUG_ADMINISTRATION = new MappingHolder("DTM, Drug Administration", "Drug Administration", "c7372550-af40-4e39-b54b-6731b805915c");
    public static MappingHolder CONCEPT_FULLY_OBSERVED_PRESCRIBED_DAY = new MappingHolder("Fully observed prescribed day", "Fully observed prescribed day", "edcd1d02-4a97-4c15-a286-109ccbd327e8");
    public static MappingHolder CONCEPT_INCOMPLETE_PRESCRIBED_DAY = new MappingHolder("Incomplete prescribed day", "Incomplete prescribed day", "1de52277-ca6c-4669-968b-d0b154fe6dc5");
    public static MappingHolder CONCEPT_MISSED_PRESCRIBED_DAY = new MappingHolder("Missed prescribed day", "Missed prescribed day", "4896ad03-064a-49dd-b585-13d7e3556e98");
    public static MappingHolder CONCEPT_MISSED_DOSE = new MappingHolder("Missed Dose", "Missed Dose", "039899c5-c99b-4ce7-a99f-26400490d0ee");
    public static MappingHolder CONCEPT_DOT_RATE = new MappingHolder("MTC, DOT rate", "DOT rate (i)", "0b98b0ee-444a-46f4-9f4a-6d341f6fd3c0");

    public static MappingHolder CONCEPT_ADMINISTERED_DOSE_QUANTITY = new MappingHolder("Administered dose quantity", "Administered dose quantity", "02f86ae2-587c-4fa4-a6fd-7f591d28830a");
    public static MappingHolder CONCEPT_LONGITUDE = new MappingHolder("Longitude", "Longitude", "87994f42-9dcc-4e01-a014-169c85f3709d");
    public static MappingHolder CONCEPT_DRUG_ADMINISTRATION_OF_DAY = new MappingHolder("Drug administration of day", "Drug administration of day", "75387a00-1fa7-498a-9054-2a6cc645a225");
    public static MappingHolder CONCEPT_LATITUDE = new MappingHolder("Latitude", "Latitude", "753c43fd-e5e7-4720-9100-957ef76b065e");

    public static MappingHolder LOCATION_TAG_CITY = new MappingHolder("City", "2a8e4f85-b22a-11e7-bac3-28d24461d6c9");
    public static MappingHolder LOCATION_TAG_TOWN = new MappingHolder("Town", "97eb1f2b-84f6-47dd-9790-980c6b9c6cfd");
    public static MappingHolder LOCATION_TAG_UC = new MappingHolder("UC", "b2f574a5-991b-4fd3-9c5d-6459bd2b191e");
    public static MappingHolder LOCATION_TAG_ADDRESS = new MappingHolder("Address", "468af067-7d6e-4cbe-967f-3b5558d9be03");
    public static MappingHolder LOCATION_TAG_FACILITY = new MappingHolder("Facility", "df11baa4-f5dd-481a-9970-1d5325c68fd6");

    public static MappingHolder LOCATION_ATTRIBUTE_TYPE_IDENTIFIER = new MappingHolder("Identifier", "2a905bef-b22a-11e7-bac3-28d24461d6c9");

    public static MappingHolder CONCEPT_INPATIENT = new MappingHolder(R.string.inpatient, R.string.inpatient, "c374eb7e-6bf3-44fe-824a-70c2c355bce2", context);
    public static MappingHolder CONCEPT_OUTPATIENT_FACILITY = new MappingHolder(R.string.outpatient_facility_based, R.string.outpatient_facility_based, "e07f9253-90bb-42af-82b9-0da224995f07", context);
    public static MappingHolder CONCEPT_OUTPATIENT_COMMUNITY = new MappingHolder(R.string.outpatient_community_based, R.string.outpatient_community_based, "8b009550-4f73-45a6-9669-6d46f5b32aa2", context);
    public static MappingHolder CONCEPT_SELF_ADMINISTERED = new MappingHolder(R.string.self_administered_tx, R.string.self_administered_tx, "fe1fc436-856e-4d3f-9795-41b3f6c954eb", context);
    public static MappingHolder CONCEPT_SAT_DOT_COMBINATION = new MappingHolder(R.string.sat_and_dot, R.string.sat_and_dot, "21f0727e-d7d5-4549-8902-a32d310069f6", context);
    public static MappingHolder CONCEPT_OTHER = new MappingHolder(R.string.other, R.string.other, "b31c6dab-b80f-413c-946d-29fa3f79fa3d", context);


    public static MappingHolder CONCEPT_TRUE = new MappingHolder("Yes", "Yes", "3dc73b47-05f0-4351-8162-6e8c51b5dc74");
    public static MappingHolder CONCEPT_FALSE = new MappingHolder("No", "No", "06046205-bccd-4ea8-ad75-aeac53f2660b");
    public static MappingHolder CONCEPT_REFUSE = new MappingHolder("Refuse", "Refuse", "1c473cda-b8d5-45d0-b023-2e19883ae86b");
    public static MappingHolder CONCEPT_UNKNOWN = new MappingHolder("Unknown", "Unknown", "ca286b31-d129-4a6d-ae82-e6878b15ede9");
    public static MappingHolder CONCEPT_NOT_APPLICABLE = new MappingHolder("Not Applicable", "Not Applicable", "c1c75b5d-df7f-43e1-bfd4-ef4726c3a81a");

    public static MappingHolder CONCEPT_WEIGHT = new MappingHolder("Weight", "Weight", "5089AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    public static MappingHolder CONCEPT_HEIGHT = new MappingHolder("Height", "Height", "5090AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    public static MappingHolder CONCEPT_FACILITY_ID = new MappingHolder("Facility ID", "Facility ID", "d66664af-fa60-42e8-9b10-6b34114b7db9");

    public static MappingHolder CONCEPT_CONTACT_COUGH = new MappingHolder("Does the contact have cough for more than two weeks?", "Does the contact have cough for more than two weeks?", "480a33bc-e19a-4a70-9d4d-cd13a9fa50f8");
    public static MappingHolder CONCEPT_CONTACT_SYMPTOMATIC = new MappingHolder("Is the contact symptomatic?", "Is the contact symptomatic?", "e3c29778-ea54-4370-aef0-fe322b6bc7a7");
    public static MappingHolder CONCEPT_CONTACT_PREGNANT = new MappingHolder("Is the contact pregnant?", "Is the contact pregnant?", "3843e94b-fa6d-451d-8da6-c9fc8e4fc77f");
    public static MappingHolder CONCEPT_CONTACT_NIGHT_SWEATS = new MappingHolder("Does the contact have night sweats?", "Does the contact have night sweats?", "639e18af-abfa-4fbb-a99c-086de269b404");
    public static MappingHolder CONCEPT_CONTACT_HEMOPTYSIS = new MappingHolder("Does the contact have hemoptysis?", "Does the contact have hemoptysis?", "ee6b2738-1dc8-408b-ba16-a3cce2402a3d");
    public static MappingHolder CONCEPT_CONTACT_ADENOPATHY = new MappingHolder("Does the contact have adenopathy for greater than 1 month?", "Does the contact have adenopathy for greater than 1 month?", "0f43da45-7f03-4f6a-981a-be7e59023187");
    public static MappingHolder CONCEPT_CONTACT_PLAYFULNESS = new MappingHolder("Does the contact have reduced palyfulness?", "Does the contact have reduced palyfulness?", "bb462001-6b74-43e0-b297-752e60033081");
    public static MappingHolder CONCEPT_CONTACT_WEIGHT_LOSS = new MappingHolder("Does the contact have weight loss?", "Does the contact have weight loss?", "04f620e5-94a3-476e-b9c7-fbb01d39f229");
    public static MappingHolder CONCEPT_CONTACT_PAST_TB_TX = new MappingHolder("Has the contact received anti TB treatment in the past?", "Has the contact received anti TB treatment in the past?", "bee578d7-1d55-4c2c-b09a-9a383926a000");
    public static MappingHolder CONCEPT_CONTACT_TB_TX_OUTCOME = new MappingHolder("What was the outcome of the last TB treatment?", "What was the outcome of the last TB treatment?", "23a364c1-eb0d-43f6-a334-2f9a818c184a");
    public static MappingHolder CONCEPT_CONTACT_TB_KIND = new MappingHolder("What kind of TB it was?", "What kind of TB it was?", "f7388cdf-cf2d-4b66-bd6c-dfd52553dba4");
    public static MappingHolder CONCEPT_CONTACT_VISIT_CLINIC = new MappingHolder("Did the contact visit the clinic for symptom screening?", "Did the contact visit the clinic for symptom screening?", "7d8b3d6e-f625-43fb-9b05-4c486bdcc6d4");

    public static MappingHolder CONCEPT_FULLY_OBSERVED_PRESCRIBED_DOSE;
    public static MappingHolder CONCEPT_INCOMPLETE_PRESCRIBES_DOSE;
    public static MappingHolder CONCEPT_MISSED_PRESCRIBED_DOSE;

    static{
        CONCEPT_FULLY_OBSERVED_PRESCRIBED_DOSE = new MappingHolder(R.string.fully_observed_dose, R.string.fully_observed_dose, "e0b7ebd8-7cf4-45e3-944a-0d43997739f5", context);
        CONCEPT_INCOMPLETE_PRESCRIBES_DOSE = new MappingHolder((R.string.incomplete_dose), (R.string.incomplete_dose), "3262fc17-e810-4b6d-ab65-c029c6e8f7be", context);
        CONCEPT_MISSED_PRESCRIBED_DOSE = new MappingHolder((R.string.missed_dose), (R.string.missed_dose), "a949cc4f-6c65-4d2b-8038-0b1672447afc", context);
    }

    public static void reload() {
        CONCEPT_FULLY_OBSERVED_PRESCRIBED_DOSE = new MappingHolder(R.string.fully_observed_dose, R.string.fully_observed_dose, "e0b7ebd8-7cf4-45e3-944a-0d43997739f5", context);
        CONCEPT_INCOMPLETE_PRESCRIBES_DOSE = new MappingHolder((R.string.incomplete_dose), (R.string.incomplete_dose), "3262fc17-e810-4b6d-ab65-c029c6e8f7be", context);
        CONCEPT_MISSED_PRESCRIBED_DOSE = new MappingHolder((R.string.missed_dose), (R.string.missed_dose), "a949cc4f-6c65-4d2b-8038-0b1672447afc", context);
    }
}

