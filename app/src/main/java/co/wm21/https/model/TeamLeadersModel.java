package co.wm21.https.model;

public class TeamLeadersModel {
    String name, designation,image;

    public TeamLeadersModel(String name, String designation, String image) {
        this.name = name;
        this.designation = designation;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
