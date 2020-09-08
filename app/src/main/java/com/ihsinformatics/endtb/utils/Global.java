package com.ihsinformatics.endtb.utils;

import java.text.SimpleDateFormat;

/**
 * Created by Naveed Iqbal on 10/26/2017.
 * Email: h.naveediqbal@gmail.com
 */

public class Global {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyy");
    public static final SimpleDateFormat MYSQL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat OPENMRS_TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    public static final SimpleDateFormat DISPLAY_TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static final String OPENMRS_DATE_FORMAT_REGEX = "[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1])";

    public static final SimpleDateFormat OPENMRS_CONCEPT_DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMdd'T'HH:mm:ss.SSSZ");
    public static final SimpleDateFormat OPENMRS_CONCEPT_DATE_FORMAT = new SimpleDateFormat("12-26-2017");

    public static final String PATIENT_MODEL = "patient_model";
    public static final String UPDATE = "update";
    public static final String ENCOUNTER = "encounter";
    public static final String ORDER = "order";
    public static final String SPECIMEN_NATURE = "specimen_nature";
    public static final String TEST_TYPE = "test_type";
    public static final String DATA_START_DATE = "2017-01-01";

    public static final String[] SUPPORTED_FORMS = new String[] {
            OpenMRSMappings.ENCOUNTER_TYPE_SECOND_LINE_TB_TX_REGISTER.getName(),
            OpenMRSMappings.ENCOUNTER_TYPE_DTM.getName(),
            OpenMRSMappings.ENCOUNTER_TYPE_MONTHLY_SUMMARY.getName(),
            OpenMRSMappings.ENCOUNTER_BASIC_MANAGEMENT_TB_REGISTER.getName(),
            OpenMRSMappings.ENCOUNTER_IHN_REGISTER.getName(),
            OpenMRSMappings.ENCOUNTER_ADVERSE_EVENT.getName()
    };

    public static String getFirstLetters(String text) {
        String firstLetters = "";
        text = text.replaceAll("[.,]", ""); // Replace dots, etc (optional)
        for (String s : text.split(" ")) {
            if(s.length()>0)
                firstLetters += s.charAt(0);
        }

        return firstLetters.toUpperCase();
    }
}
