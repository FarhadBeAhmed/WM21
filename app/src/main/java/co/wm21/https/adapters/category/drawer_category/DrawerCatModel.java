package co.wm21.https.adapters.category.drawer_category;

public class DrawerCatModel {
    private String catId;
    private String name;
    private String icon;
    private boolean isExpanded;

    public DrawerCatModel(String catId, String name, String icon) {
        this.catId = catId;
        this.name = name;
        this.icon = icon;
        this.isExpanded = false;
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
