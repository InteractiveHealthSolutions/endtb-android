package com.ihsinformatics.endtb.database.data;

import android.content.Context;

import com.ihsinformatics.endtb.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Naveed Iqbal on 10/28/2017.
 * Email: h.naveediqbal@gmail.com
 */
public class DataProvider {

    public enum CONCEPT_DATA_TYPE {
        DATE,
        SINGLE_SELECT,
        TEXT,
        BOOLEAN,
        MULTI_SELECT,
        NONE,
        CODED,
        UNKNOWN;
    }

    private List<String> treatmentDeliveryMethod;
    private List<String> referringFacilities;
    private List<String> occupations;
    private List<String> patientHistoryOptions;
    private List<String> testingReason;
    private List<String> diseaseClassification;
    private List<String> specimenNature;
    private List<String> languages;
    private List<String> organizationType;
    private List<String> patientStatus;
    private List<String> genders;
    private List<String> testTypes;
    private List<String> epSites;
    private List<String> visualAppearances;
    private List<String> specimenNumbers;
    private List<String> microscopyTestTypes;
    private List<String> results;
    private List<String> cxrResults;
    private List<String> mtbRifResults;
    private List<String> eqaControllers;

    private static DataProvider ourInstance = new DataProvider();

    public static synchronized DataProvider getInstance() {
        return ourInstance;
    }

    public Set<String> getAllOptions() {
        Set<String> allOptions = new HashSet<>();

        allOptions.addAll(referringFacilities);
        allOptions.addAll(occupations);
        allOptions.addAll(patientHistoryOptions);
        allOptions.addAll(testingReason);
        allOptions.addAll(diseaseClassification);
        allOptions.addAll(specimenNature);
        allOptions.addAll(languages);
        allOptions.addAll(organizationType);
        allOptions.addAll(patientStatus);
        allOptions.addAll(genders);
        allOptions.addAll(testTypes);
        allOptions.addAll(epSites);
        allOptions.addAll(visualAppearances);
        allOptions.addAll(specimenNumbers);
        allOptions.addAll(microscopyTestTypes);
        allOptions.addAll(results);
        allOptions.addAll(cxrResults);
        allOptions.addAll(mtbRifResults);
        allOptions.addAll(eqaControllers);
        allOptions.addAll(treatmentDeliveryMethod);

        return allOptions;
    }

    private DataProvider() {
        initReferringFacilities();
        initOccupations();
        initPatientHistoryOptions();
        initPatientStatus();
        initGenders();
        initTestTypes();
        initEpSites();
        initVisualAppearances();
        initSpecimenNumbers();
        initMicroscopyTestTypes();
        initResults();
        initCxrResults();
        initMtbRifResults();
        initEqaControllers();
        // initLanguages();
        initTestingReason();
        initDiseaseClassification();
        initSpecimenNature();
        initOrganizationType();
    }

    private void initEqaControllers() {
        eqaControllers = new ArrayList<>();
        eqaControllers.add("EQA 1");
        eqaControllers.add("EQA 2");
    }

    private void initMtbRifResults() {
        mtbRifResults = new ArrayList<>();
        mtbRifResults.add("PR = MTB Detected, RIF Resistance Detected");
        mtbRifResults.add("T = MTB Detected, RIF Resistance Not Detected");
        mtbRifResults.add("N = MTB Not Detected");
        mtbRifResults.add("I = Invalid / No Result / Error");
    }

    private void initCxrResults() {
        cxrResults = new ArrayList<>();
        cxrResults.add("Positive");
        cxrResults.add("Negative");
    }

    private void initResults() {
        results = new ArrayList<>();
        results.add("Negative");
        results.add("1+");
        results.add("2+");
        results.add("3+");
    }

    private void initMicroscopyTestTypes() {
        microscopyTestTypes = new ArrayList<>();
        microscopyTestTypes.add("ZN");
        microscopyTestTypes.add("LED");
    }

    private void initSpecimenNumbers() {
        specimenNumbers = new ArrayList<>();
        specimenNumbers.add("Specimen 1");
        specimenNumbers.add("Specimen 2");
    }

    private void initVisualAppearances() {
        visualAppearances = new ArrayList<>();
        visualAppearances.add("Muco-purulent");
        visualAppearances.add("Blood stained");
        visualAppearances.add("Saliva");
    }

    private void initEpSites() {
        epSites = new ArrayList<>();
        epSites.add("Mediastinal / Hilar lymph nodes");
        epSites.add("Larynx");
        epSites.add("Cervical lymph nodes");
        epSites.add("Pleura");
        epSites.add("Meninges");
        epSites.add("Central nervous system");
        epSites.add("Spine");
        epSites.add("Bones and Joints");
        epSites.add("Pericardium");
        epSites.add("Intestines");
        epSites.add("Peritoneum");
        epSites.add("Skin");
    }

    private void initGenders() {
        genders = new ArrayList<>();
        genders.add("Male");
        genders.add("Female");
        genders.add("Other");
    }

    private void initTestTypes() {
        testTypes = new ArrayList<>();
        testTypes.add("Microscopy");
        testTypes.add("Microscopy (EQA)");
        testTypes.add("CXR");
        testTypes.add("Xpert MTB/ RIF");
    }

    private void initTestingReason() {
        testingReason = new ArrayList<>();
        testingReason.add("Diagnosis");
        testingReason.add("Follow-up");
    }

    private void initDiseaseClassification() {
        diseaseClassification = new ArrayList<>();
        diseaseClassification.add("Pulmonary");
        diseaseClassification.add("Extra Pulmonary (EP)");
    }

    private void initSpecimenNature() {
        specimenNature = new ArrayList<>();
        specimenNature.add("Sputum");
        specimenNature.add("Urine");
        specimenNature.add("Pus");
        specimenNature.add("Sputum in preservative");
        specimenNature.add("Other");
    }

    private void initOrganizationType() {
        organizationType = new ArrayList<>();
        organizationType.add("Govt.");
        organizationType.add("NGO");
    }



    private void initReferringFacilities() {
        referringFacilities = new ArrayList<>();
        referringFacilities.add("Government Outpatient Patient Department - GOPD");
        referringFacilities.add("Government Field Staff – GFS");
        referringFacilities.add("In patient Department – IPD");
        referringFacilities.add("Private Practitioner - PP");
        referringFacilities.add("Village Doctor – VD");
        referringFacilities.add("Shasta Sebeka – SS");
        referringFacilities.add("NGO Field Staff – NFS");
        referringFacilities.add("Cured TB patient – CTP");
        referringFacilities.add("Community Health Care Provider – CHCP");
        referringFacilities.add("Other");
    }

    private void initOccupations() {
        occupations = new ArrayList<>();
        occupations.add("NOT WORKING");
        occupations.add("HOUSEWIFE");
        occupations.add("STUDENT");
        occupations.add("AGRICULTURAL WORK");
        occupations.add("GARMENT WORKER");
        occupations.add("DOMESTIC WORKER");
        occupations.add("GOVT. SERVICE");
        occupations.add("PRIVATE SERVICE");
        occupations.add("SHOPKEEPER");
        occupations.add("TAILORING");
        occupations.add("NURSE");
        occupations.add("DAY LABOUR");
        occupations.add("EARN MONEY AT HOME");
        occupations.add("COOK");
        occupations.add("Driver(car/cng/etc)");
        occupations.add("Riksha puller/van puller");
        occupations.add("SMALL TRADE");
        occupations.add("OTHER");
    }



    private void initPatientHistoryOptions() {
        patientHistoryOptions = new ArrayList<>();
        patientHistoryOptions.add("Failures of Category I");
        patientHistoryOptions.add("Failures of Category II");
        patientHistoryOptions.add("Non-Converters of Category I");
        patientHistoryOptions.add("Non-Converters of Category II");
        patientHistoryOptions.add("Relapses");
        patientHistoryOptions.add("Return after lost to follow up");
        patientHistoryOptions.add("Close contacts of DR TB patient");
        patientHistoryOptions.add("HIV infected");
        patientHistoryOptions.add("New Patient");
        patientHistoryOptions.add("Other");
    }

    private void initPatientStatus() {
        patientStatus = new ArrayList<>();
        patientStatus.add("Cured");
        patientStatus.add("Treatment Completed");
        patientStatus.add("Died");
        patientStatus.add("Treatment Failure");
        patientStatus.add("Lost to follow-up");
        patientStatus.add("Transferred/ Nor Evaluated");
        patientStatus.add("Not Available");
        patientStatus.add("New Patient");
    }



    public List<String> getReferringFacilities() {
        return referringFacilities;
    }

    public List<String> getOccupations() {
        return occupations;
    }

    public List<String> getGenders() {
        return genders;
    }

    public List<String> getPatientHistoryOptions() {
        return patientHistoryOptions;
    }

    public List<String> getPatientStatus() {
        return patientStatus;
    }

    public static DataProvider getOurInstance() {
        return ourInstance;
    }

    public List<String> getTestingReason() {
        return testingReason;
    }

    public List<String> getDiseaseClassification() {
        return diseaseClassification;
    }

    public List<String> getSpecimenNature() {
        return specimenNature;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public List<String> getOrganizationType() {
        return organizationType;
    }

    public List<String> getTestTypes() {
        return testTypes;
    }

    public List<String> getEpSites() {
        return epSites;
    }

    public List<String> getVisualAppearances() {
        return visualAppearances;
    }

    public List<String> getSpecimenNumbers() {
        return specimenNumbers;
    }

    public List<String> getMicroscopyTestTypes() {
        return microscopyTestTypes;
    }

    public List<String> getResults() {
        return results;
    }

    public List<String> getCxrResults() {
        return cxrResults;
    }

    public List<String> getMtbRifResults() {
        return mtbRifResults;
    }

    public List<String> getEqaControllers() {
        return eqaControllers;
    }

    private class Location {
        TreeMap<String, String> locationsMap;
        public Location() {
            initLocations();
        }

        private void initLocations() {
            locationsMap = new TreeMap();


            locationsMap.put("Bangladesh", "Khulna");
            locationsMap.put("Bangladesh", "Chittagong\n");
            locationsMap.put("Bangladesh", "Barisal\n");
            locationsMap.put("Bangladesh", "Rajshahi\n");
            locationsMap.put("Bangladesh", "Dhaka\n");
            locationsMap.put("Bangladesh", "Rangpur");
            locationsMap.put("Bangladesh", "Sylhet");
            locationsMap.put("Bangladesh", "Sylhet");
            locationsMap.put("Bangladesh", "Sylhet");
            locationsMap.put("Bangladesh", "Sylhet");
            locationsMap.put("Bangladesh", "Sylhet");
        }
    }


}
