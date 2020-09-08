package com.ihsinformatics.endtb.Screens.adapter_models;

/**
 * Created by Nabil shafi on 4/26/2018.
 */

public class DrugSummaryModel {


    private String name;
    private String totalIncomplete;
    private String totalMissedPrescribed;
    private String totalFullyObserved;


    public DrugSummaryModel() {
    }

    public DrugSummaryModel(String name, String totalIncomplete, String totalMissedPrescribed, String totalFullyObserved) {
        this.name = name;
        this.totalIncomplete = totalIncomplete;
        this.totalMissedPrescribed = totalMissedPrescribed;
        this.totalFullyObserved = totalFullyObserved;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalIncomplete() {
        return totalIncomplete;
    }

    public void setTotalIncomplete(String totalIncomplete) {
        this.totalIncomplete = totalIncomplete;
    }

    public String getTotalMissedPrescribed() {
        return totalMissedPrescribed;
    }

    public void setTotalMissedPrescribed(String totalMissedPrescribed) {
        this.totalMissedPrescribed = totalMissedPrescribed;
    }

    public String getTotalFullyObserved() {
        return totalFullyObserved;
    }

    public void setTotalFullyObserved(String totalFullyObserved) {
        this.totalFullyObserved = totalFullyObserved;
    }
}
