package com.ihsinformatics.endtb.database.Entities;

import com.ihsinformatics.endtb.utils.Global;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

/**
 * Created by Naveed Iqbal on 11/2/2017.
 * Email: h.naveediqbal@gmail.com
 */
@Entity
public class Order implements Serializable {
    @Override
    public String toString() {
        return Global.DISPLAY_TIMESTAMP_FORMAT.format(getEncounter().getEncounterDate());
    }

    @Transient
    public static final long serialVersionUID = 42L;
    @Id(autoincrement = true)
    private Long id;

    private String accessionNumber;

    private Long creator;
    @ToOne(joinProperty = "creator")
    private User user;

    private Long patientId;
    @ToOne(joinProperty = "patientId")
    private Patient patient;

    private Long orderTypeId;
    @ToOne(joinProperty = "orderTypeId")
    private OrderType orderType;

    private Long encounterId;
    @ToOne(joinProperty = "encounterId")
    private Encounter encounter;

    private Boolean isUploaded;

    private String uuid;
    private Boolean voided;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 949219203)
    private transient OrderDao myDao;
    @Generated(hash = 1215779048)
    public Order(Long id, String accessionNumber, Long creator, Long patientId,
            Long orderTypeId, Long encounterId, Boolean isUploaded, String uuid,
            Boolean voided) {
        this.id = id;
        this.accessionNumber = accessionNumber;
        this.creator = creator;
        this.patientId = patientId;
        this.orderTypeId = orderTypeId;
        this.encounterId = encounterId;
        this.isUploaded = isUploaded;
        this.uuid = uuid;
        this.voided = voided;
    }
    @Generated(hash = 1105174599)
    public Order() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getCreator() {
        return this.creator;
    }
    public void setCreator(Long creator) {
        this.creator = creator;
    }
    public Long getPatientId() {
        return this.patientId;
    }
    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
    public Long getOrderTypeId() {
        return this.orderTypeId;
    }
    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }
    public Long getEncounterId() {
        return this.encounterId;
    }
    public void setEncounterId(Long encounterId) {
        this.encounterId = encounterId;
    }
    @Generated(hash = 251390918)
    private transient Long user__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 22419050)
    public User getUser() {
        Long __key = this.creator;
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
    @Generated(hash = 1875415486)
    public void setUser(User user) {
        synchronized (this) {
            this.user = user;
            creator = user == null ? null : user.getId();
            user__resolvedKey = creator;
        }
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
    @Generated(hash = 406995047)
    private transient Long orderType__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 55914695)
    public OrderType getOrderType() {
        Long __key = this.orderTypeId;
        if (orderType__resolvedKey == null
                || !orderType__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            OrderTypeDao targetDao = daoSession.getOrderTypeDao();
            OrderType orderTypeNew = targetDao.load(__key);
            synchronized (this) {
                orderType = orderTypeNew;
                orderType__resolvedKey = __key;
            }
        }
        return orderType;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 91117962)
    public void setOrderType(OrderType orderType) {
        synchronized (this) {
            this.orderType = orderType;
            orderTypeId = orderType == null ? null : orderType.getId();
            orderType__resolvedKey = orderTypeId;
        }
    }
    @Generated(hash = 1379606207)
    private transient Long encounter__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 996224206)
    public Encounter getEncounter() {
        Long __key = this.encounterId;
        if (encounter__resolvedKey == null
                || !encounter__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            EncounterDao targetDao = daoSession.getEncounterDao();
            Encounter encounterNew = targetDao.load(__key);
            synchronized (this) {
                encounter = encounterNew;
                encounter__resolvedKey = __key;
            }
        }
        return encounter;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1430051392)
    public void setEncounter(Encounter encounter) {
        synchronized (this) {
            this.encounter = encounter;
            encounterId = encounter == null ? null : encounter.getId();
            encounter__resolvedKey = encounterId;
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
    public String getAccessionNumber() {
        return this.accessionNumber;
    }
    public void setAccessionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
    }
    public Boolean getIsUploaded() {
        return this.isUploaded;
    }
    public void setIsUploaded(Boolean isUploaded) {
        this.isUploaded = isUploaded;
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
    @Generated(hash = 965731666)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getOrderDao() : null;
    }


}
