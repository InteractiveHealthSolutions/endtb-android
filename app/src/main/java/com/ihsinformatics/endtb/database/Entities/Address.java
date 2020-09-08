package com.ihsinformatics.endtb.database.Entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import java.io.Serializable;

/**
 * Created by Naveed Iqbal on 10/24/2017.
 * Email: h.naveediqbal@gmail.com
 */

@Entity
public class Address implements Serializable {
    // TODO implement openmrs like address class
    public static final long serialVersionUID = 1l;
    @Id(autoincrement = true)
    private Long id;
    private String city;
    private String town;
    private String uc;
    private String landMark;
    private String address;
    private String uuid;
    private Boolean voided;

    @Unique
    private Long patientId;
    @ToOne(joinProperty = "patientId")
    private Patient patient;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1580986028)
    private transient AddressDao myDao;
    @Generated(hash = 1140912835)
    public Address(Long id, String city, String town, String uc, String landMark, String address,
            String uuid, Boolean voided, Long patientId) {
        this.id = id;
        this.city = city;
        this.town = town;
        this.uc = uc;
        this.landMark = landMark;
        this.address = address;
        this.uuid = uuid;
        this.voided = voided;
        this.patientId = patientId;
    }
    @Generated(hash = 388317431)
    public Address() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getTown() {
        return this.town;
    }
    public void setTown(String town) {
        this.town = town;
    }
    public String getUc() {
        return this.uc;
    }
    public void setUc(String uc) {
        this.uc = uc;
    }
    public String getLandMark() {
        return this.landMark;
    }
    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Long getPatientId() {
        return this.patientId;
    }
    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
    @Generated(hash = 391381774)
    private transient Long patient__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1923256233)
    public Patient getPatient() {
        Long __key = this.patientId;
        if (patient__resolvedKey == null || !patient__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PatientDao targetDao = daoSession.getPatientDao();
            Patient patientNew = targetDao.load(__key);
            synchronized (this) {
                patient = patientNew;
                patient__resolvedKey = __key;
            }
        }
        return patient;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 751603965)
    public void setPatient(Patient patient) {
        synchronized (this) {
            this.patient = patient;
            patientId = patient == null ? null : patient.getPatientId();
            patient__resolvedKey = patientId;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    public String getUuid() {
        return this.uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public Boolean getVoided() {
        return this.voided;
    }
    public void setVoided(Boolean voided) {
        this.voided = voided;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 543375780)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getAddressDao() : null;
    }

}
