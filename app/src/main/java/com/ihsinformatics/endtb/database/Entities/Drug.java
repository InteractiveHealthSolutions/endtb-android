package com.ihsinformatics.endtb.database.Entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;

/**
 * Created by Naveed Iqbal on 2/23/2018.
 * Email: h.naveediqbal@gmail.com
 */
@Entity
public class Drug {
    public static final long serialVersionUID = 1l;
    @Id (autoincrement = true)
    private Long id;
    private String name;
    private String uuid;
    private String dosageForm;

    private Long conceptId;
    @ToOne(joinProperty = "conceptId")
    private Concept concept;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1215447009)
    private transient DrugDao myDao;
    @Generated(hash = 1081675409)
    private transient Long concept__resolvedKey;

    @Generated(hash = 1088748532)
    public Drug(Long id, String name, String uuid, String dosageForm,
            Long conceptId) {
        this.id = id;
        this.name = name;
        this.uuid = uuid;
        this.dosageForm = dosageForm;
        this.conceptId = conceptId;
    }
    @Generated(hash = 90337038)
    public Drug() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUuid() {
        return this.uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getDosageForm() {
        return this.dosageForm;
    }
    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }
    public Long getConceptId() {
        return this.conceptId;
    }
    public void setConceptId(Long conceptId) {
        this.conceptId = conceptId;
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 304709649)
    public Concept getConcept() {
        Long __key = this.conceptId;
        if (concept__resolvedKey == null || !concept__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ConceptDao targetDao = daoSession.getConceptDao();
            Concept conceptNew = targetDao.load(__key);
            synchronized (this) {
                concept = conceptNew;
                concept__resolvedKey = __key;
            }
        }
        return concept;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 480327366)
    public void setConcept(Concept concept) {
        synchronized (this) {
            this.concept = concept;
            conceptId = concept == null ? null : concept.getId();
            concept__resolvedKey = conceptId;
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
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 435001453)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDrugDao() : null;
    }
}
