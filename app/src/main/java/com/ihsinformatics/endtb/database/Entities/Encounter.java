package com.ihsinformatics.endtb.database.Entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;

import java.io.Serializable;
import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by Naveed Iqbal on 10/12/2017.
 * Email: h.naveediqbal@gmail.com
 */
@Entity
public class Encounter implements Serializable {
    public static final long serialVersionUID = 1l;
    @Id (autoincrement = true)
    private Long id;
    @Unique private String uuid;
    private Date encounterDate;

    // Foreign key
    private Long patientId;
    @ToOne(joinProperty = "patientId")
    private Patient patient;

    private Long encounterTypeId;
    @ToOne(joinProperty = "encounterTypeId")
    private EncounterType encounterType;

    private Long locationId;
    @ToOne(joinProperty = "locationId")
    private Location location;

    private Long createdBy;
    @ToOne(joinProperty = "createdBy")
    private User user;

    private Boolean isUploaded;
    private Boolean voided;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 555098507)
    private transient EncounterDao myDao;
    @Generated(hash = 764536544)
    public Encounter(Long id, String uuid, Date encounterDate, Long patientId,
            Long encounterTypeId, Long locationId, Long createdBy, Boolean isUploaded,
            Boolean voided) {
        this.id = id;
        this.uuid = uuid;
        this.encounterDate = encounterDate;
        this.patientId = patientId;
        this.encounterTypeId = encounterTypeId;
        this.locationId = locationId;
        this.createdBy = createdBy;
        this.isUploaded = isUploaded;
        this.voided = voided;
    }
    @Generated(hash = 1764769048)
    public Encounter() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUuid() {
        return this.uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public Date getEncounterDate() {
        return this.encounterDate;
    }
    public void setEncounterDate(Date encounterDate) {
        this.encounterDate = encounterDate;
    }
    public Long getPatientId() {
        return this.patientId;
    }
    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
    public Long getEncounterTypeId() {
        return this.encounterTypeId;
    }
    public void setEncounterTypeId(Long encounterTypeId) {
        this.encounterTypeId = encounterTypeId;
    }
    public Long getCreatedBy() {
        return this.createdBy;
    }
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
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
    @Generated(hash = 1612205726)
    private transient Long encounterType__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 299983866)
    public EncounterType getEncounterType() {
        Long __key = this.encounterTypeId;
        if (encounterType__resolvedKey == null
                || !encounterType__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            EncounterTypeDao targetDao = daoSession.getEncounterTypeDao();
            EncounterType encounterTypeNew = targetDao.load(__key);
            synchronized (this) {
                encounterType = encounterTypeNew;
                encounterType__resolvedKey = __key;
            }
        }
        return encounterType;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 180787144)
    public void setEncounterType(EncounterType encounterType) {
        synchronized (this) {
            this.encounterType = encounterType;
            encounterTypeId = encounterType == null ? null : encounterType.getId();
            encounterType__resolvedKey = encounterTypeId;
        }
    }
    @Generated(hash = 251390918)
    private transient Long user__resolvedKey;
    @Generated(hash = 1068795426)
    private transient Long location__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 309875216)
    public User getUser() {
        Long __key = this.createdBy;
        if (user__resolvedKey == null || !user__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            User userNew = targetDao.load(__key);
            synchronized (this) {
                user = userNew;
                user__resolvedKey = __key;
            }
        }
        return user;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 297253008)
    public void setUser(User user) {
        synchronized (this) {
            this.user = user;
            createdBy = user == null ? null : user.getId();
            user__resolvedKey = createdBy;
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
    public Long getLocationId() {
        return this.locationId;
    }
    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1449045962)
    public Location getLocation() {
        Long __key = this.locationId;
        if (location__resolvedKey == null || !location__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            LocationDao targetDao = daoSession.getLocationDao();
            Location locationNew = targetDao.load(__key);
            synchronized (this) {
                location = locationNew;
                location__resolvedKey = __key;
            }
        }
        return location;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 340671030)
    public void setLocation(Location location) {
        synchronized (this) {
            this.location = location;
            locationId = location == null ? null : location.getId();
            location__resolvedKey = locationId;
        }
    }
    public Boolean getIsUploaded() {
        return this.isUploaded;
    }
    public void setIsUploaded(Boolean isUploaded) {
        this.isUploaded = isUploaded;
    }
    public Boolean getVoided() {
        return this.voided;
    }
    public void setVoided(Boolean voided) {
        this.voided = voided;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1576892833)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getEncounterDao() : null;
    }
    
}
