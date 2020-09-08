package com.ihsinformatics.endtb.database.Entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import java.util.Date;

/**
 * Created by Naveed Iqbal on 2/23/2018.
 * Email: h.naveediqbal@gmail.com
 */
@Entity
public class DrugOrders {

    @Id
    private String orderNumber;
    private String uuid;
    private int dose;
    private String doseUnit;
    private String frequency;
    private int quantity;
    private String quantityUnit;
    private String instructions;
    private int duration;
    private String durationUnit;
    private String route;
    private String orderAction;

    private Long drugId;
    @ToOne(joinProperty = "drugId")
    private Drug drug;

    private Long orderTypeId;
    @ToOne(joinProperty = "orderTypeId")
    private OrderType orderType;

    private Long patientId;
    @ToOne(joinProperty = "patientId")
    private Patient patient;

    private Long encounterId;
    @ToOne(joinProperty = "encounterId")
    private Encounter encounter;

    private Long createdBy;
    @ToOne(joinProperty = "createdBy")
    private User user;

    private boolean voided;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 671394823)
    private transient DrugOrdersDao myDao;

    private Date dateActivated;
    private Date scheduledDate;
    private Date dateStopped;
    private Date autoExpireDate;

    @Generated(hash = 101106956)
    public DrugOrders(String orderNumber, String uuid, int dose, String doseUnit,
            String frequency, int quantity, String quantityUnit, String instructions,
            int duration, String durationUnit, String route, String orderAction, Long drugId,
            Long orderTypeId, Long patientId, Long encounterId, Long createdBy,
            boolean voided, Date dateActivated, Date scheduledDate, Date dateStopped,
            Date autoExpireDate) {
        this.orderNumber = orderNumber;
        this.uuid = uuid;
        this.dose = dose;
        this.doseUnit = doseUnit;
        this.frequency = frequency;
        this.quantity = quantity;
        this.quantityUnit = quantityUnit;
        this.instructions = instructions;
        this.duration = duration;
        this.durationUnit = durationUnit;
        this.route = route;
        this.orderAction = orderAction;
        this.drugId = drugId;
        this.orderTypeId = orderTypeId;
        this.patientId = patientId;
        this.encounterId = encounterId;
        this.createdBy = createdBy;
        this.voided = voided;
        this.dateActivated = dateActivated;
        this.scheduledDate = scheduledDate;
        this.dateStopped = dateStopped;
        this.autoExpireDate = autoExpireDate;
    }

    @Generated(hash = 805200115)
    public DrugOrders() {
    }

    public String getOrderNumber() {
        return this.orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getDose() {
        return this.dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    public String getDoseUnit() {
        return this.doseUnit;
    }

    public void setDoseUnit(String doseUnit) {
        this.doseUnit = doseUnit;
    }

    public String getFrequency() {
        return this.frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getQuantityUnit() {
        return this.quantityUnit;
    }

    public void setQuantityUnit(String quantityUnit) {
        this.quantityUnit = quantityUnit;
    }

    public String getInstructions() {
        return this.instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDurationUnit() {
        return this.durationUnit;
    }

    public void setDurationUnit(String durationUnit) {
        this.durationUnit = durationUnit;
    }

    public String getRoute() {
        return this.route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Long getPatientId() {
        return this.patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public boolean getVoided() {
        return this.voided;
    }

    public void setVoided(boolean voided) {
        this.voided = voided;
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

    @Generated(hash = 251390918)
    private transient Long user__resolvedKey;
    @Generated(hash = 406995047)
    private transient Long orderType__resolvedKey;
    @Generated(hash = 1379606207)
    private transient Long encounter__resolvedKey;
    @Generated(hash = 656532220)
    private transient Long drug__resolvedKey;

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

    public Long getOrderTypeId() {
        return this.orderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 55914695)
    public OrderType getOrderType() {
        Long __key = this.orderTypeId;
        if (orderType__resolvedKey == null || !orderType__resolvedKey.equals(__key)) {
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

    public Long getEncounterId() {
        return this.encounterId;
    }

    public void setEncounterId(Long encounterId) {
        this.encounterId = encounterId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 996224206)
    public Encounter getEncounter() {
        Long __key = this.encounterId;
        if (encounter__resolvedKey == null || !encounter__resolvedKey.equals(__key)) {
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

    public Long getDrugId() {
        return this.drugId;
    }

    public void setDrugId(Long drugId) {
        this.drugId = drugId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 145662505)
    public Drug getDrug() {
        Long __key = this.drugId;
        if (drug__resolvedKey == null || !drug__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DrugDao targetDao = daoSession.getDrugDao();
            Drug drugNew = targetDao.load(__key);
            synchronized (this) {
                drug = drugNew;
                drug__resolvedKey = __key;
            }
        }
        return drug;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 323948464)
    public void setDrug(Drug drug) {
        synchronized (this) {
            this.drug = drug;
            drugId = drug == null ? null : drug.getId();
            drug__resolvedKey = drugId;
        }
    }

    public Date getDateActivated() {
        return this.dateActivated;
    }

    public void setDateActivated(Date dateActivated) {
        this.dateActivated = dateActivated;
    }

    public Date getScheduledDate() {
        return this.scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public Date getDateStopped() {
        return this.dateStopped;
    }

    public void setDateStopped(Date dateStopped) {
        this.dateStopped = dateStopped;
    }

    public Date getAutoExpireDate() {
        return this.autoExpireDate;
    }

    public void setAutoExpireDate(Date autoExpireDate) {
        this.autoExpireDate = autoExpireDate;
    }

    public String getOrderAction() {
        return this.orderAction;
    }

    public void setOrderAction(String orderAction) {
        this.orderAction = orderAction;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1092838892)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDrugOrdersDao() : null;
    }
}
