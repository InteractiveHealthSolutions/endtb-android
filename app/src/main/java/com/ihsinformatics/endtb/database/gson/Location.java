
package com.ihsinformatics.endtb.database.gson;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("display")
    @Expose
    private String display;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cityVillage")
    @Expose
    private Object cityVillage;
    @SerializedName("stateProvince")
    @Expose
    private String stateProvince;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("latitude")
    @Expose
    private Object latitude;
    @SerializedName("longitude")
    @Expose
    private Object longitude;
    @SerializedName("countyDistrict")
    @Expose
    private String countyDistrict;
    @SerializedName("address3")
    @Expose
    private Object address3;
    @SerializedName("address4")
    @Expose
    private Object address4;
    @SerializedName("address5")
    @Expose
    private Object address5;
    @SerializedName("address6")
    @Expose
    private String address6;
    @SerializedName("tags")
    @Expose
    private List<Tag> tags = null;
    @SerializedName("parentLocation")
    @Expose
    private ParentLocation parentLocation;
    @SerializedName("retired")
    @Expose
    private Boolean retired;
    @SerializedName("attributes")
    @Expose
    private List<Attribute> attributes = null;
    @SerializedName("resourceVersion")
    @Expose
    private String resourceVersion;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getCityVillage() {
        return cityVillage;
    }

    public void setCityVillage(Object cityVillage) {
        this.cityVillage = cityVillage;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Object getLatitude() {
        return latitude;
    }

    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }

    public Object getLongitude() {
        return longitude;
    }

    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

    public String getCountyDistrict() {
        return countyDistrict;
    }

    public void setCountyDistrict(String countyDistrict) {
        this.countyDistrict = countyDistrict;
    }

    public Object getAddress3() {
        return address3;
    }

    public void setAddress3(Object address3) {
        this.address3 = address3;
    }

    public Object getAddress4() {
        return address4;
    }

    public void setAddress4(Object address4) {
        this.address4 = address4;
    }

    public Object getAddress5() {
        return address5;
    }

    public void setAddress5(Object address5) {
        this.address5 = address5;
    }

    public String getAddress6() {
        return address6;
    }

    public void setAddress6(String address6) {
        this.address6 = address6;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public ParentLocation getParentLocation() {
        return parentLocation;
    }

    public void setParentLocation(ParentLocation parentLocation) {
        this.parentLocation = parentLocation;
    }

    public Boolean getRetired() {
        return retired;
    }

    public void setRetired(Boolean retired) {
        this.retired = retired;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public String getResourceVersion() {
        return resourceVersion;
    }

    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

}
