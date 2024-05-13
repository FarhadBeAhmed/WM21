package co.wm21.https.adapters.category.drawer_category;

public class DrawerCatModel {
    String catId;
    String name;
    String icon;
    boolean isExpanded;

    public DrawerCatModel(String id, String name, String icon) {
        this.catId = catId;
        this.name = name;
        this.icon = icon;
        isExpanded=false;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
