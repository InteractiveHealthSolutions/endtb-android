package com.ihsinformatics.endtb.database.Entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by Naveed Iqbal on 10/12/2017.
 * Email: h.naveediqbal@gmail.com
 */
@Entity
public class EncounterType implements Serializable {
    public static final long serialVersionUID = 1l;
    @Id (autoincrement = true)
    private Long id;
    @Unique
    private String typeName;
    @Unique
    private String uuid;
    @Generated(hash = 1923442741)
    public EncounterType(Long id, String typeName, String uuid) {
        this.id = id;
        this.typeName = typeName;
        this.uuid = uuid;
    }
    @Generated(hash = 2065539989)
    public EncounterType() {
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
    public String getUuid() {
        return this.uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
