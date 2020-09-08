package com.ihsinformatics.endtb.database.Entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by Naveed Iqbal on 3/8/2018.
 * Email: h.naveediqbal@gmail.com
 */
@Entity
public class RolePermissions {

    public static final long serialVersionUID = 1l;
    @Id(autoincrement = true)
    private Long id;

    private Long roleId;
    @ToOne(joinProperty = "roleId")
    private Role role;

    private Long permissionId;
    @ToOne(joinProperty = "permissionId")
    private Permission permission;

    private Boolean voided;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1211731739)
    private transient RolePermissionsDao myDao;

    @Generated(hash = 257031053)
    public RolePermissions(Long id, Long roleId, Long permissionId,
            Boolean voided) {
        this.id = id;
        this.roleId = roleId;
        this.permissionId = permissionId;
        this.voided = voided;
    }

    @Generated(hash = 862064153)
    public RolePermissions() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return this.permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public Boolean getVoided() {
        return this.voided;
    }

    public void setVoided(Boolean voided) {
        this.voided = voided;
    }

    @Generated(hash = 312471022)
    private transient Long role__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1948065557)
    public Role getRole() {
        Long __key = this.roleId;
        if (role__resolvedKey == null || !role__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RoleDao targetDao = daoSession.getRoleDao();
            Role roleNew = targetDao.load(__key);
            synchronized (this) {
                role = roleNew;
                role__resolvedKey = __key;
            }
        }
        return role;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 736917490)
    public void setRole(Role role) {
        synchronized (this) {
            this.role = role;
            roleId = role == null ? null : role.getId();
            role__resolvedKey = roleId;
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

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1040521076)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRolePermissionsDao() : null;
    }
}
