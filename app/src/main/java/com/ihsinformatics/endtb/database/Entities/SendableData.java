package com.ihsinformatics.endtb.database.Entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import java.io.Serializable;

/**
 * Created by Naveed Iqbal on 11/10/2017.
 * Email: h.naveediqbal@gmail.com
 */

@Entity
public class SendableData implements Serializable {
    public static final long serialVersionUID = 1l;

    public static transient final String DATA_TYPE_CREATE_PROVIDER = "create_provider";
    public static transient final String DATA_TYPE_CREATE_PATIENT = "create_patient";
    public static transient final String DATA_TYPE_CREATE_ENCOUNTER = "create_encounter";
    public static transient final String DATA_TYPE_UPDATE_ENCOUNTER = "update_encounter";
    public static transient final String DATA_TYPE_CREATE_TEST_ORDER = "create_test_order";
    public static transient final String DATA_TYPE_UPDATE_TEST_ORDER = "update_test_order";

    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String jsonData;
    private String header;
    private String urlPostfix;
    private boolean isSent;
    private String dataype;

    private Long patientId;
    @ToOne(joinProperty = "patientId")
    private Patient patient;
    private Long referenceId;
    private Integer numberOfUploadAttempts;
    private String errorMessage;
    // private Long encounterReferenceId;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 52634008)
    private transient SendableDataDao myDao;
    @Generated(hash = 391381774)
    private transient Long patient__resolvedKey;

    @Generated(hash = 1318366798)
    public SendableData(Long id, @NotNull String jsonData, String header, String urlPostfix,
            boolean isSent, String dataype, Long patientId, Long referenceId,
            Integer numberOfUploadAttempts, String errorMessage) {
        this.id = id;
        this.jsonData = jsonData;
        this.header = header;
        this.urlPostfix = urlPostfix;
        this.isSent = isSent;
        this.dataype = dataype;
        this.patientId = patientId;
        this.referenceId = referenceId;
        this.numberOfUploadAttempts = numberOfUploadAttempts;
        this.errorMessage = errorMessage;
    }
    @Generated(hash = 1150886210)
    public SendableData() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getJsonData() {
        return this.jsonData;
    }
    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }
    public String getHeader() {
        return this.header;
    }
    public void setHeader(String header) {
        this.header = header;
    }
    public String getUrlPostfix() {
        return this.urlPostfix;
    }
    public void setUrlPostfix(String urlPostfix) {
        this.urlPostfix = urlPostfix;
    }
    public boolean getIsSent() {
        return this.isSent;
    }
    public void setIsSent(boolean isSent) {
        this.isSent = isSent;
    }

    public String getDataype() {
        return this.dataype;
    }
    public void setDataype(String dataype) {
        this.dataype = dataype;
    }
    public Long getPatientId() {
        return this.patientId;
    }
    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
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
    public Long getReferenceId() {
        return this.referenceId;
    }
    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }
    public Integer getNumberOfUploadAttempts() {
        return this.numberOfUploadAttempts;
    }
    public void setNumberOfUploadAttempts(Integer numberOfUploadAttempts) {
        this.numberOfUploadAttempts = numberOfUploadAttempts;
    }
    public String getErrorMessage() {
        return this.errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1976675199)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSendableDataDao() : null;
    }
}
