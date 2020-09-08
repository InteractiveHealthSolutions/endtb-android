package com.ihsinformatics.endtb.database.Entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by Naveed Iqbal on 10/16/2017.
 * Email: h.naveediqbal@gmail.com
 */
@Entity
public class Concept implements Serializable {
    public static final long serialVersionUID = 1l;
    @Id (autoincrement = true)
    private Long id;
    @Unique private String name;
    private String shortName;
    @Unique private String uuid;
    private String dataType;
    @Generated(hash = 1425570162)
    public Concept(Long id, String name, String shortName, String uuid,
            String dataType) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.uuid = uuid;
        this.dataType = dataType;
    }
    @Generated(hash = 110699436)
    public Concept() {
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
    public String getShortName() {
        return this.shortName;
    }
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    public String getUuid() {
        return this.uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getDataType() {
        return this.dataType;
    }
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

}
