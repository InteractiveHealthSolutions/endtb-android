package com.ihsinformatics.endtb.database.Entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import java.io.Serializable;

/**
 * Created by Naveed Iqbal on 10/16/2017.
 * Email: h.naveediqbal@gmail.com
 */
@Entity
public class UserPermissions implements Serializable {
    public static final long serialVersionUID = 1l;
    @Id(autoincrement = true)
    private Long id;

    private Long userId;
    @ToOne(joinProperty = "userId")
    private User user;

    private Long permissionId;
    @ToOne(joinProperty = "permissionId")
    private Permission permission;

    private Boolean voided;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 257264764)
    private transient UserPermissionsDao myDao;
    @Generated(hash = 2112658567)
    public UserPermissions(Long id, Long userId, Long permissionId, Boolean voided) {
        this.id = id;
        this.userId = userId;
        this.permissionId = permissionId;
        this.voided = voided;
    }
    @Generated(hash = 1690250668)
    public UserPermissions() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return this.userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getPermissionId() {
        return this.permissionId;
    }
    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }
    @Generated(hash = 251390918)
    private transient Long user__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 859885876)
    public User getUser() {
        Long __key = this.userId;
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
    @Generated(hash = 1065606912)
    public void setUser(User user) {
        synchronized (this) {
            this.user = user;
            userId = user == null ? null : user.getId();
            user__resolvedKey = userId;
        }
    }
    @Generated(hash = 1449986052)
    private transient Long permission__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 146401205)
    public Permission getPermission() {
        Long __key = this.permissionId;
        if (permission__resolvedKey == null
                || !permission__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PermissionDao targetDao = daoSession.getPermissionDao();
            Permission permissionNew = targetDao.load(__key);
            synchronized (this) {
                permission = permissionNew;
                permission__resolvedKey = __key;
            }
        }
        return permission;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2056299129)
    public void setPermission(Permission permission) {
        synchronized (this) {
            this.permission = permission;
            permissionId = permission == null ? null : permission.getId();
            permission__resolvedKey = permissionId;
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
    public Boolean getVoided() {
        return this.voided;
    }
    public void setVoided(Boolean voided) {
        this.voided = voided;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 367713486)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserPermissionsDao() : null;
    }


}
