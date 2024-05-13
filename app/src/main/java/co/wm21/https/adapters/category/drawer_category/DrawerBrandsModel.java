package co.wm21.https.adapters.category.drawer_category;

public class DrawerSubCatModel {
    String catId;
    String name;
    boolean isExpanded;

    public DrawerSubCatModel(String catId, String name ) {
        this.catId = catId;
        this.name = name;
        this.isExpanded=false;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
