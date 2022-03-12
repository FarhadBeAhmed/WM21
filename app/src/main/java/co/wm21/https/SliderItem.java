package co.wm21.https;

public class SliderItem {

    public String description;
    public String imageUrl;

    public SliderItem(String description, String imageUrl) {
        setImageUrl(imageUrl);
        try {
            setDescription(description);
        } catch (Exception e) {
            setDescription("");
            e.printStackTrace();
        }
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
