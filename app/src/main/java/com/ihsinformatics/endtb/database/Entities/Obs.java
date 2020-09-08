package com.ihsinformatics.endtb.database.Entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Naveed Iqbal on 10/16/2017.
 * Email: h.naveediqbal@gmail.com
 */
@Entity
public class Obs implements Serializable {
    public static final long serialVersionUID = 1l;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Obs obs = (Obs) o;

        if (!localUUID.equals(obs.localUUID)) return false;
        if (value != null ? !value.equals(obs.value) : obs.value != null) return false;
        if (uuid != null ? !uuid.equals(obs.uuid) : obs.uuid != null) return false;
        if (!encounterId.equals(obs.encounterId)) return false;
        if (!conceptId.equals(obs.conceptId)) return false;
        return voided != null ? voided.equals(obs.voided) : obs.voided == null;
    }

    @Override
    public int hashCode() {
        int result = localUUID.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + encounterId.hashCode();
        result = 31 * result + conceptId.hashCode();
        result = 31 * result + (voided != null ? voided.hashCode() : 0);
        return result;
    }

    public Obs(String localUUID, String value, String uuid, Long creator, Long encounterId,
               Concept concept, Boolean voided, String parentObsLocalUUID) {
        this.localUUID = localUUID;
        this.value = value;
        this.uuid = uuid;
        this.creator = creator;
        this.encounterId = encounterId;
        this.concept = concept;
        this.voided = voided;
        this.parentObsLocalUUID = parentObsLocalUUID;
    }

    public Concept getConceptSimply() {
        return  concept;
    }

    public String getLocalUUID() {
        return this.localUUID;
    }

    public void setLocalUUID(String localUUID) {
        this.localUUID = localUUID;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getCreator() {
        return this.creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Long getEncounterId() {
        return this.encounterId;
    }

    public void setEncounterId(Long encounterId) {
        this.encounterId = encounterId;
    }

    public Long getConceptId() {
        return this.conceptId;
    }

    public void setConceptId(Long conceptId) {
        this.conceptId = conceptId;
    }

    public Boolean getVoided() {
        return this.voided;
    }

    public void setVoided(Boolean voided) {
        this.voided = voided;
    }

    public String getParentObsLocalUUID() {
        return this.parentObsLocalUUID;
    }

    public void setParentObsLocalUUID(String parentObsLocalUUID) {
        this.parentObsLocalUUID = parentObsLocalUUID;
    }

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

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1983213285)
    public Obs getParentObs() {
        String __key = this.parentObsLocalUUID;
        if (parentObs__resolvedKey == null || parentObs__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ObsDao targetDao = daoSession.getObsDao();
            Obs parentObsNew = targetDao.load(__key);
            synchronized (this) {
                parentObs = parentObsNew;
                parentObs__resolvedKey = __key;
            }
        }
        return parentObs;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 483492861)
    public void setParentObs(Obs parentObs) {
        synchronized (this) {
            this.parentObs = parentObs;
            parentObsLocalUUID = parentObs == null ? null : parentObs.getLocalUUID();
            parentObs__resolvedKey = parentObsLocalUUID;
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
    @Generated(hash = 1915869318)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getObsDao() : null;
    }

    @Id
    private String localUUID;
    private String value;
    private String uuid;

    private Long creator;
    @ToOne(joinProperty = "creator")
    private User user;

    private Long encounterId;
    @ToOne(joinProperty = "encounterId")
    private Encounter encounter;

    private Long conceptId;
    @ToOne(joinProperty = "conceptId")
    private Concept concept;
    private Boolean voided;

    private String parentObsLocalUUID;
    @ToOne(joinProperty = "parentObsLocalUUID")
    private Obs parentObs;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1345951651)
    private transient ObsDao myDao;

    @Generated(hash = 1716911637)
    public Obs(String localUUID, String value, String uuid, Long creator, Long encounterId,
            Long conceptId, Boolean voided, String parentObsLocalUUID) {
        this.localUUID = localUUID;
        this.value = value;
        this.uuid = uuid;
        this.creator = creator;
        this.encounterId = encounterId;
        this.conceptId = conceptId;
        this.voided = voided;
        this.parentObsLocalUUID = parentObsLocalUUID;
    }

    @Generated(hash = 2041834711)
    public Obs() {
    }

    @Generated(hash = 251390918)
    private transient Long user__resolvedKey;
    @Generated(hash = 1379606207)
    private transient Long encounter__resolvedKey;
    @Generated(hash = 1081675409)
    private transient Long concept__resolvedKey;
    @Generated(hash = 216896543)
    private transient String parentObs__resolvedKey;


}
