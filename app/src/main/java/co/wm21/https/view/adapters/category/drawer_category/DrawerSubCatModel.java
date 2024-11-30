package co.wm21.https.view.adapters.category.drawer_category;

public class DrawerSubCatModel {
    String catId;
    String subCatId;
    String name;
    boolean isExpanded;

    public DrawerSubCatModel(String subCatId, String name, String catId ) {
        this.subCatId = subCatId;
        this.name = name;
        this.catId = catId;
        this.isExpanded=false;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
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
