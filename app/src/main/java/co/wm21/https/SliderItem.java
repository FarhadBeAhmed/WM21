package co.wm21.https;

public class SliderItem {

    public String imageUrl;
    public String description;

    public SliderItem(String imageUrl, String description) {
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
