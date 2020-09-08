package com.ihsinformatics.endtb.utils;

import android.content.Context;

public class MappingHolder {
    private String name;
    private String uuid;
    private String shortName;
    private int nameStringResourceId;
    private int shortStringNameResourceId;

    MappingHolder(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    MappingHolder(String name, String shortName, String uuid) {
        this.name = name;
        this.shortName = shortName;
        this.uuid = uuid;
    }

    MappingHolder(int name, int shortName, String uuid, Context context) {
        this.name = context.getString(name);
        this.shortName = context.getString(shortName);

        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public int getNameStringResourceId() {
        return nameStringResourceId;
    }

    public void setNameStringResourceId(int nameStringResourceId) {
        this.nameStringResourceId = nameStringResourceId;
    }

    public int getShortStringNameResourceId() {
        return shortStringNameResourceId;
    }

    public void setShortStringNameResourceId(int shortStringNameResourceId) {
        this.shortStringNameResourceId = shortStringNameResourceId;
    }


}
