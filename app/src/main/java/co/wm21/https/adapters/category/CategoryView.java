package co.wm21.https.adapters.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryView {
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("orderingID")
    @Expose
    private String orderingID;
    @SerializedName("catID")
    @Expose
    private String catID;
    @SerializedName("categoryImageUrl")
    @Expose
    private String categoryImageUrl;

    public CategoryView(String categoryName, String categoryImageUrl) {
        this.categoryName = categoryName;
        this.categoryImageUrl = categoryImageUrl;
    }
    public CategoryView(String categoryName, String categoryImageUrl,String catID) {
        this.categoryName = categoryName;
        this.categoryImageUrl = categoryImageUrl;
        this.catID = catID;
    }

    public String getCatID() {
        return catID;
    }

    public void setCatID(String catID) {
        this.catID = catID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImageUrl() {
        return categoryImageUrl;
    }

    public void setCategoryImageUrl(String categoryImageUrl) {
        this.categoryImageUrl = categoryImageUrl;
    }

    public String getOrderingID() {
        return orderingID;
    }

    public void setOrderingID(String orderingID) {
        this.orderingID = orderingID;
    }
}
