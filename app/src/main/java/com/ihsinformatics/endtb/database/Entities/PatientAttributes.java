package com.ihsinformatics.endtb.database.Entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import java.io.Serializable;

/**
 * Created by Naveed Iqbal on 10/24/2017.
 * Email: h.naveediqbal@gmail.com
 */
@Entity
public class PatientAttributes implements Serializable {
    public static final long serialVersionUID = 1l;

    @Id(autoincrement = true)
    private Long id;

    private String value;

    private Long patientId;
    @ToOne(joinProperty = "patientId")
    private Patient patient;

    private Long attributeId;
    @ToOne(joinProperty = "attributeId")
    private PersonAttributeType personAttributeType;
    private String uuid;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 359888429)
    private transient PatientAttributesDao myDao;
    @Generated(hash = 1566793385)
    public PatientAttributes(Long id, String value, Long patientId, Long attributeId,
            String uuid) {
        this.id = id;
        this.value = value;
        this.patientId = patientId;
        this.attributeId = attributeId;
        this.uuid = uuid;
    }
    @Generated(hash = 836172795)
    public PatientAttributes() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getValue() {
        return this.value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public Long getPatientId() {
        return this.patientId;
    }
    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
    public Long getAttributeId() {
        return this.attributeId;
    }
    public void setAttributeId(Long attributeId) {
        this.attributeId = attributeId;
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
    @Generated(hash = 410084558)
    private transient Long personAttributeType__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 592308667)
    public PersonAttributeType getPersonAttributeType() {
        Long __key = this.attributeId;
        if (personAttributeType__resolvedKey == null
                || !personAttributeType__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PersonAttributeTypeDao targetDao = daoSession
                    .getPersonAttributeTypeDao();
            PersonAttributeType personAttributeTypeNew = targetDao.load(__key);
            synchronized (this) {
                personAttributeType = personAttributeTypeNew;
                personAttributeType__resolvedKey = __key;
            }
        }
        return personAttributeType;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2000371341)
    public void setPersonAttributeType(PersonAttributeType personAttributeType) {
        synchronized (this) {
            this.personAttributeType = personAttributeType;
            attributeId = personAttributeType == null ? null
                    : personAttributeType.getAttributeId();
            personAttributeType__resolvedKey = attributeId;
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
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 715751994)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPatientAttributesDao() : null;
    }

}
