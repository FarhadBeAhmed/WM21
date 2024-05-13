package com.wm21ltd.wm21.networks.Models;

import java.io.Serializable;

public class ContactListModel implements Serializable {
    private  int contactImage;
    private String contactName;
    private String contactNumber;


    public ContactListModel(String contactName, String contactNumber) {
        this.contactName = contactName;
        this.contactNumber = contactNumber;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }


    public int getContactImage() {
        return contactImage;
    }

    public void setContactImage(int contactImage) {
        this.contactImage = contactImage;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
