package com.ihsinformatics.endtb.database.Entities;


import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Naveed Iqbal on 10/11/2017.
 * Email: h.naveediqbal@gmail.com
 */
// @Entity --Not mapping for the time
public class Visit implements Serializable {
    public static final long serialVersionUID = 1l;
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String uuid;
    @NotNull
    private Date visitDate;

    private Long patientId;
    @ToOne(joinProperty = "patientId")
    Patient patient;

    private Long creator;
    @ToOne(joinProperty = "creator")
    private User user;
    private Boolean voided;
}
