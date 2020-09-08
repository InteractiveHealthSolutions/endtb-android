package com.ihsinformatics.endtb.database.Entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Naveed Iqbal on 3/8/2018.
 * Email: h.naveediqbal@gmail.com
 */

@Entity
public class Role {

    public static final long serialVersionUID = 1l;

    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String roleName;
    private String uuid;
    @Generated(hash = 2131392334)
    public Role(Long id, @NotNull String roleName, String uuid) {
        this.id = id;
        this.roleName = roleName;
        this.uuid = uuid;
    }
    @Generated(hash = 844947497)
    public Role() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getRoleName() {
        return this.roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public String getUuid() {
        return this.uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
