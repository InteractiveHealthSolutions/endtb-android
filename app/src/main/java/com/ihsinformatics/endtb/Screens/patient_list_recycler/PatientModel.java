package com.ihsinformatics.endtb.Screens.patient_list_recycler;

import com.ihsinformatics.endtb.database.Entities.Patient;
import com.ihsinformatics.endtb.database.Entities.PatientAttributes;
import com.ihsinformatics.endtb.database.Entities.PatientIdentifier;
import com.ihsinformatics.endtb.database.data.DbContentHelper;
import com.ihsinformatics.endtb.utils.OpenMRSMappings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by Naveed Iqbal on 11/1/2017.
 * Email: h.naveediqbal@gmail.com
 */

public class PatientModel implements Serializable {

    private String id;
    private String name;
    private String gender;
    private String occupation;
    private int age;
    private Long dbId;

    public PatientModel(String id, String name, String gender, String contact, int age, Long dbId) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.occupation = contact;
        this.age = age;
        this.dbId = dbId;
    }

    public Long getDbId() {
        return dbId;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static ArrayList<PatientModel> castFromPatients(Collection<Patient> patientList) {
        DbContentHelper dbContentHelper = DbContentHelper.getInstance();
        ArrayList<PatientModel> patientModelList = new ArrayList<>();
        for(Patient p : patientList) {
            PatientAttributes occupation = dbContentHelper.fetchPatientAttribute(p.getPatientId(), OpenMRSMappings.ATTRIBUTE_TYPE_OCCUPATION.getName());
            PatientIdentifier identifier = dbContentHelper.fetchPatientIdentifier(p.getPatientId(), OpenMRSMappings.PATIENT_IDENTIFIER_TYPE_PATIENT_IDENTIFIER.getName());
            if (identifier == null)
                continue;
            String occupationString = "";
            if(occupation!=null) {
                occupationString = occupation.getValue();
            }
            patientModelList.add(
                new PatientModel(
                    identifier.getIdentifier(),
                    p.getGivenName()+" "+p.getFamilyName(),
                    p.getGender(),
                    occupationString,
                    p.getAge(),
                    p.getPatientId()));

        }
        return patientModelList;
    }
}
