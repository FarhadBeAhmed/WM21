package co.wm21.https.model;

public class Blogs {

    String serial,name,bloggerImage,blogImage,subject,text;
    String blogDate;

    public Blogs(String serial, String name, String bloggerImage, String subject,String text,String blogDate, String blogImage) {
        this.serial = serial;
        this.name = name;
        this.bloggerImage = bloggerImage;
        this.subject = subject;
        this.text = text;
        this.blogDate = blogDate;
        this.blogImage = blogImage;
    }

    public Blogs(String serial, String name, String bloggerImage, String subject) {
        this.serial = serial;
        this.name = name;
        this.bloggerImage = bloggerImage;
        this.subject = subject;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBloggerImage() {
        return bloggerImage;
    }

    public void setBloggerImage(String bloggerImage) {
        this.bloggerImage = bloggerImage;
    }

    public String getBlogImage() {
        return blogImage;
    }

    public void setBlogImage(String blogImage) {
        this.blogImage = blogImage;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getBlogDate() {
        return blogDate;
    }

    public void setBlogDate(String blogDate) {
        this.blogDate = blogDate;
    }
}
