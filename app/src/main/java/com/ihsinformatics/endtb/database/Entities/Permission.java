package com.ihsinformatics.endtb.database.Entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by Naveed Iqbal on 10/13/2017.
 * Email: h.naveediqbal@gmail.com
 */
@Entity
public class Permission implements Serializable {
    public static final long serialVersionUID = 1l;
    @Id (autoincrement = true)
    private Long id;
    @Unique private String permissionName;
    @Unique
    private String uuid;
    @Generated(hash = 1355173694)
    public Permission(Long id, String permissionName, String uuid) {
        this.id = id;
        this.permissionName = permissionName;
        this.uuid = uuid;
    }
    @Generated(hash = 600656733)
    public Permission() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPermissionName() {
        return this.permissionName;
    }
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
    public String getUuid() {
        return this.uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
