package com.ihsinformatics.endtb.database.Entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

import java.io.Serializable;

import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Naveed Iqbal on 10/13/2017.
 * Email: h.naveediqbal@gmail.com
 */
@Entity
public class User implements Serializable {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!userName.equals(user.userName)) return false;
        if (!uuid.equals(user.uuid)) return false;
        return prividerUUID != null ? prividerUUID.equals(user.prividerUUID) : user.prividerUUID == null;
    }

    @Override
    public int hashCode() {
        int result = userName.hashCode();
        result = 31 * result + uuid.hashCode();
        result = 31 * result + (prividerUUID != null ? prividerUUID.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", uuid='" + uuid + '\'' +
                ", prividerUUID='" + prividerUUID + '\'' +
                '}';
    }

    public static final long serialVersionUID = 1l;
    @Id
    private Long id;
    @Unique
    private String userName;
    private String password;
    private String givenName;
    private String familyName;
    private String gender;
    private int age;
    @Unique
    private String uuid;

    private String prividerUUID;
    private String personUUID;
    @Unique
    private String sessionId;
    private String labUUID;
    private Boolean voided;
    @Generated(hash = 1393394865)
    public User(Long id, String userName, String password, String givenName, String familyName, String gender,
            int age, String uuid, String prividerUUID, String personUUID, String sessionId, String labUUID,
            Boolean voided) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.givenName = givenName;
        this.familyName = familyName;
        this.gender = gender;
        this.age = age;
        this.uuid = uuid;
        this.prividerUUID = prividerUUID;
        this.personUUID = personUUID;
        this.sessionId = sessionId;
        this.labUUID = labUUID;
        this.voided = voided;
    }

    @Generated(hash = 586692638)
    public User() {
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUuid() {
        return this.uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getSessionId() {
        return this.sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getLabUUID() {
        return this.labUUID;
    }
    public void setLabUUID(String labUUID) {
        this.labUUID = labUUID;
    }
    public String getPrividerUUID() {
        return this.prividerUUID;
    }
    public void setPrividerUUID(String prividerUUID) {
        this.prividerUUID = prividerUUID;
    }

    public Boolean getVoided() {
        return this.voided;
    }

    public void setVoided(Boolean voided) {
        this.voided = voided;
    }

    public String getGivenName() {
        return this.givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return this.familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPersonUUID() {
        return this.personUUID;
    }

    public void setPersonUUID(String personUUID) {
        this.personUUID = personUUID;
    }

}
