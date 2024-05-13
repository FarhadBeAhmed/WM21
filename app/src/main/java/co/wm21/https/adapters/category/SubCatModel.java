package co.wm21.https.adapters.category.drawer_category;

public class DrawerSubCatModel {
    String subCatId;
    String name;
    boolean isExpanded;

    public DrawerSubCatModel(String subCatId, String name ) {
        this.subCatId = subCatId;
        this.name = name;
        this.isExpanded=false;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public String getSubCatId() {
        return subCatId;
    }

    public void setSubCatId(String subCatId) {
        this.subCatId = subCatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
