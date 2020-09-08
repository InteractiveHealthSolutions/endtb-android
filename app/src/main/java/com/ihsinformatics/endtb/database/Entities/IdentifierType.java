package com.ihsinformatics.endtb.database.Entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by Naveed Iqbal on 10/30/2017.
 * Email: h.naveediqbal@gmail.com
 */

@Entity
public class IdentifierType implements Serializable {
    public static final long serialVersionUID = 1l;
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String identifierTypeName;
    @Unique
    private String uuid;
    @Generated(hash = 1378966266)
    public IdentifierType(Long id, String identifierTypeName, String uuid) {
        this.id = id;
        this.identifierTypeName = identifierTypeName;
        this.uuid = uuid;
    }
    @Generated(hash = 959844165)
    public IdentifierType() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getIdentifierTypeName() {
        return this.identifierTypeName;
    }
    public void setIdentifierTypeName(String identifierTypeName) {
        this.identifierTypeName = identifierTypeName;
    }
    public String getUuid() {
        return this.uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
