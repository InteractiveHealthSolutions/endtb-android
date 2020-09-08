package com.ihsinformatics.endtb.database.Entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

import java.io.Serializable;

/**
 * Created by Naveed Iqbal on 10/11/2017.
 * Email: h.naveediqbal@gmail.com
 */

@Entity
public class Patient implements Serializable {
    public static final long serialVersionUID = 1l;

    @Id
    private Long patientId;
    private String uuid;
    private String givenName;
    private String familyName;
    private String gender;
    private int age;
    private String nameUUID;
    private Boolean voided;
    @Generated(hash = 632806378)
    public Patient(Long patientId, String uuid, String givenName, String familyName,
            String gender, int age, String nameUUID, Boolean voided) {
        this.patientId = patientId;
        this.uuid = uuid;
        this.givenName = givenName;
        this.familyName = familyName;
        this.gender = gender;
        this.age = age;
        this.nameUUID = nameUUID;
        this.voided = voided;
    }
    @Generated(hash = 1655646460)
    public Patient() {
    }
    public Long getPatientId() {
        return this.patientId;
    }
    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
    public String getUuid() {
        return this.uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getGivenName() {
        return this.givenName;
    }
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }
    public String getFamilyName() {
        return this.familyName;
    }
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }
    public String getGender() {
        return this.gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getNameUUID() {
        return this.nameUUID;
    }
    public void setNameUUID(String nameUUID) {
        this.nameUUID = nameUUID;
    }
    public Boolean getVoided() {
        return this.voided;
    }
    public void setVoided(Boolean voided) {
        this.voided = voided;
    }

}
