package co.wm21.https.adapters.category.drawer_category;

public class DrawerBrandsModel {
    String brandId;
    String brandName;

    public DrawerBrandsModel(String brandId, String brandName) {
        this.brandId = brandId;
        this.brandName = brandName;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
