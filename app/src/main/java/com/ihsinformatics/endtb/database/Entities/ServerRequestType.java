package com.ihsinformatics.endtb.database.Entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by Naveed Iqbal on 11/10/2017.
 * Email: h.naveediqbal@gmail.com
 */

@Entity
public class ServerRequestType implements Serializable {
    public static final long serialVersionUID = 1l;

    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String typeName;
    @Generated(hash = 2123290585)
    public ServerRequestType(Long id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }
    @Generated(hash = 1265994643)
    public ServerRequestType() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTypeName() {
        return this.typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
