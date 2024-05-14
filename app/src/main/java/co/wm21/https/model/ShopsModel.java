package co.wm21.https.model;

public class ShopsModel {
    String shop_id;
    String type_name;
    int type_id;
    String img;
    String name;
    String mobile;
    String district;
    String thana;
    String union;
    String photo;

    public ShopsModel(String shop_id, String type_name, String img, String name, String mobile, String district, String thana, String union, String photo,int type_id) {
        this.shop_id = shop_id;
        this.type_name = type_name;
        this.img = img;
        this.name = name;
        this.mobile = mobile;
        this.district = district;
        this.thana = thana;
        this.union = union;
        this.photo = photo;
        this.type_id = type_id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getThana() {
        return thana;
    }

    public void setThana(String thana) {
        this.thana = thana;
    }

    public String getUnion() {
        return union;
    }

    public void setUnion(String union) {
        this.union = union;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }
}
