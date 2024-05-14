package co.wm21.https.model;

import java.io.Serializable;

public class OrderEshopModel implements Serializable {
    private String id,name,mobile,address,word,union,thana,district,division,country,fullAddress;
    private boolean selected=false;

    public OrderEshopModel(String id, String name, String mobile, String address, String word, String union, String thana, String district, String division, String country, String fullAddress) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.word = word;
        this.union = union;
        this.thana = thana;
        this.district = district;
        this.division = division;
        this.country = country;
        this.fullAddress = fullAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getUnion() {
        return union;
    }

    public void setUnion(String union) {
        this.union = union;
    }

    public String getThana() {
        return thana;
    }

    public void setThana(String thana) {
        this.thana = thana;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

