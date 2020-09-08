package com.ihsinformatics.endtb.database.Entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by Naveed Iqbal on 10/24/2017.
 * Email: h.naveediqbal@gmail.com
 */

@Entity
public class PersonAttributeType implements Serializable {
    public static final long serialVersionUID = 1l;
    @Id(autoincrement = true)
    private Long attributeId;
    @Unique
    private String attributeName;
    @Unique
    private String uuid;
    @Generated(hash = 592647553)
    public PersonAttributeType(Long attributeId, String attributeName,
            String uuid) {
        this.attributeId = attributeId;
        this.attributeName = attributeName;
        this.uuid = uuid;
    }
    @Generated(hash = 1945704371)
    public PersonAttributeType() {
    }
    public Long getAttributeId() {
        return this.attributeId;
    }
    public void setAttributeId(Long attributeId) {
        this.attributeId = attributeId;
    }
    public String getAttributeName() {
        return this.attributeName;
    }
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
    public String getUuid() {
        return this.uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
