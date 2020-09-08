package com.ihsinformatics.endtb.utils.drug_utils;

import java.util.HashMap;

/**
 * Created by Nabil shafi on 4/3/2018.
 */

public class DrugConversionData {

    private HashMap<String, DrugData> drugsData;

    public DrugConversionData() {
        drugsData = new HashMap<>();
        drugsData.put("Isoniazid (H)", new DrugData(100, "Tablet"));
        drugsData.put("Ethambutol (E)", new DrugData(100, "Tablet"));
        drugsData.put("Pyrazinamide (Z)", new DrugData(400, "Tablet"));
        drugsData.put("Amikacin (Am)", new DrugData(500, "Vial"));
        drugsData.put("Kanamycin (Km)", new DrugData(1000, "Vial"));
        drugsData.put("Capreomycin (Cm)", new DrugData(1000, "Vial"));
        drugsData.put("Levofloxacin (Lfx)", new DrugData(250, "Tablet"));
        drugsData.put("Moxifloxacin (Mfx)", new DrugData(400, "Tablet"));
        drugsData.put("Ethionamide (Eto)", new DrugData(250, "Tablet"));
        drugsData.put("Cycloserine (Cs)", new DrugData(250, "Capsule"));
        drugsData.put("Para-aminosalicylic acid (PAS)", new DrugData(4000, "Sachet"));
        drugsData.put("Para-aminosalicylic acid - sodium (PAS-Na)", new DrugData(4600, "Scoop"));
        drugsData.put("Bedaquiline (Bdq)", new DrugData(100, "Tablet"));
        drugsData.put("Delamanid (Dlm)", new DrugData(50, "Tablet"));
        drugsData.put("Linezolid (Lzd)", new DrugData(600, "Tablet"));
        drugsData.put("Clofazimine (Cfz)", new DrugData(50, "Capsule"));
        drugsData.put("Amoxicillin/Clavulanate (Amx/Clv)", new DrugData(625, "Tablet"));
        drugsData.put("Clarythromycin (Clr)", new DrugData(500, "Tablet"));
        drugsData.put("Clarythromicin (Clr)", new DrugData(500, "Tablet"));
    }

    public DrugData getDrugData(String drugName) {
        return drugsData.get(drugName);
    }

    public class DrugData {
        int packing;
        String unit;

        public DrugData(int packing, String unit) {
            this.packing = packing;
            this.unit = unit;
        }

        public int getPacking() {
            return packing;
        }

        public void setPacking(int packing) {
            this.packing = packing;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }
}
