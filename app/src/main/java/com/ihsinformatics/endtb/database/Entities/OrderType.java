package com.ihsinformatics.endtb.database.Entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by Naveed Iqbal on 11/2/2017.
 * Email: h.naveediqbal@gmail.com
 */
@Entity
public class OrderType implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String name;
    @Unique
    private String uuid;
    @Generated(hash = 189053880)
    public OrderType(Long id, String name, String uuid) {
        this.id = id;
        this.name = name;
        this.uuid = uuid;
    }
    @Generated(hash = 40568524)
    public OrderType() {
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
    public String getUuid() {
        return this.uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
