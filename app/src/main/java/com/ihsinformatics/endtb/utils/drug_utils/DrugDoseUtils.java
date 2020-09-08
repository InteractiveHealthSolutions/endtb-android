package com.ihsinformatics.endtb.utils.drug_utils;

import com.ihsinformatics.endtb.database.Entities.DrugOrders;
import com.ihsinformatics.endtb.database.Entities.EncounterType;
import com.ihsinformatics.endtb.database.Entities.Obs;
import com.ihsinformatics.endtb.database.data.DbContentHelper;
import com.ihsinformatics.endtb.utils.OpenMRSMappings;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import java.util.List;

import static com.ihsinformatics.endtb.database.data.DbContentHelper.*;

/**
 * Created by Nabil shafi on 4/4/2018.
 */

public class DrugDoseUtils {

    private enum FREQUENCY {
        EVERY_OTHER_DAY
    }

    public boolean isPrescribed(DrugOrders drugOrders){
        String drugName = drugOrders.getDrug().getName();
        EncounterType encounterType = getInstance().fetchEncounterType(OpenMRSMappings.ENCOUNTER_TYPE_DTM.getName());

        if(drugName.startsWith("Bedaquiline")
                && (drugOrders.getFrequency().equalsIgnoreCase("3 days per week") || drugOrders.getFrequency().equalsIgnoreCase("every other day"))) {
                if(fetchTodayDoses(drugOrders).size() == 0)
                    if(fetchThisWeekDoses(drugOrders).size()<3)
                        if(fetchYesterdayDoses(drugOrders).size() == 0)
                            return true;
        } else {
            if (drugOrders.getFrequency().equalsIgnoreCase("7 days per week")
                    || drugOrders.getFrequency().equalsIgnoreCase("once per day")) {

                if (fetchTodayDoses(drugOrders).size() == 0)
                    if (fetchThisWeekDoses(drugOrders).size() < 7)
                        return true;

            } else if (drugOrders.getFrequency().equalsIgnoreCase("6 days per week")) {
                if (fetchTodayDoses(drugOrders).size() == 0)
                    if (fetchThisWeekDoses(drugOrders).size() < 6)
                        return true;
            } else if (drugOrders.getFrequency().equalsIgnoreCase("3 days per week")) {
                if (fetchTodayDoses(drugOrders).size() == 0)
                    if (fetchThisWeekDoses(drugOrders).size() < 3)
                        return true;
            } else if (drugOrders.getFrequency().equalsIgnoreCase("every other day")) {
                if (fetchTodayDoses(drugOrders).size() == 0)
                    if (fetchYesterdayDoses(drugOrders).size() == 0)
                        return true;
            }
        }

        return false;
    }

    private List<Obs> fetchThisWeekDoses(DrugOrders drugOrders) {
        LocalDate now = new LocalDate();
        LocalDate monday = now.withDayOfWeek(DateTimeConstants.MONDAY);
        System.out.println(now+" | "+monday);
        System.out.println(now.toDate().getTime());
        System.out.println(monday.toDate().getTime());
        long epochStart = monday.toDate().getTime();
        long epochEnd = now.toDate().getTime()+95599999;
        // Date
        return getInstance().fetchAdministeredDrugDoses(epochStart, epochEnd, drugOrders.getDrug().getConceptId(), drugOrders.getPatientId());
    }

    private List<Obs> fetchTodayDoses(DrugOrders drugOrders) {
        LocalDate now = new LocalDate();
        long epochTodayStart = now.toDate().getTime();
        long epochTodayEnd = now.toDate().getTime()+95599999;
        // Date
        return getInstance().fetchAdministeredDrugDoses(epochTodayStart, epochTodayEnd, drugOrders.getDrug().getConceptId(), drugOrders.getPatientId());
    }

    private List<Obs> fetchYesterdayDoses(DrugOrders drugOrders) {
        LocalDate now = new LocalDate();
        LocalDate yesterday = now.minusDays(1);
        long epochTodayStart = yesterday.toDate().getTime();
        long epochTodayEnd = yesterday.toDate().getTime()+95599999;
        // Date
        return getInstance().fetchAdministeredDrugDoses(epochTodayStart, epochTodayEnd, drugOrders.getDrug().getConceptId(), drugOrders.getPatientId());
    }
}
