package co.wm21.https.FHelper.networks.Models.application;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EShopStatusData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("join")
    @Expose
    private String join;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("start")
    @Expose
    private Object start;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("shop_physicial_address")
    @Expose
    private Object shopPhysicialAddress;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("trade_lisence_number")
    @Expose
    private Object tradeLisenceNumber;
    @SerializedName("trade_lisence_expire")
    @Expose
    private Object tradeLisenceExpire;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("bank_accname")
    @Expose
    private String bankAccname;
    @SerializedName("bank")
    @Expose
    private String bank;
    @SerializedName("bank_acc")
    @Expose
    private String bankAcc;
    @SerializedName("bank_bkash")
    @Expose
    private String bankBkash;
    @SerializedName("bank_ucash")
    @Expose
    private String bankUcash;
    @SerializedName("bank_dbbl")
    @Expose
    private String bankDbbl;
    @SerializedName("type_name")
    @Expose
    private String typeName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getJoin() {
        return join;
    }

    public void setJoin(String join) {
        this.join = join;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Object getStart() {
        return start;
    }

    public void setStart(Object start) {
        this.start = start;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Object getShopPhysicialAddress() {
        return shopPhysicialAddress;
    }

    public void setShopPhysicialAddress(Object shopPhysicialAddress) {
        this.shopPhysicialAddress = shopPhysicialAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getTradeLisenceNumber() {
        return tradeLisenceNumber;
    }

    public void setTradeLisenceNumber(Object tradeLisenceNumber) {
        this.tradeLisenceNumber = tradeLisenceNumber;
    }

    public Object getTradeLisenceExpire() {
        return tradeLisenceExpire;
    }

    public void setTradeLisenceExpire(Object tradeLisenceExpire) {
        this.tradeLisenceExpire = tradeLisenceExpire;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBankAccname() {
        return bankAccname;
    }

    public void setBankAccname(String bankAccname) {
        this.bankAccname = bankAccname;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankAcc() {
        return bankAcc;
    }

    public void setBankAcc(String bankAcc) {
        this.bankAcc = bankAcc;
    }

    public String getBankBkash() {
        return bankBkash;
    }

    public void setBankBkash(String bankBkash) {
        this.bankBkash = bankBkash;
    }

    public String getBankUcash() {
        return bankUcash;
    }

    public void setBankUcash(String bankUcash) {
        this.bankUcash = bankUcash;
    }

    public String getBankDbbl() {
        return bankDbbl;
    }

    public void setBankDbbl(String bankDbbl) {
        this.bankDbbl = bankDbbl;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
