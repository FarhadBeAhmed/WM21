package co.wm21.https.helpers;

public enum FileType {
    APNG("apng"),
    AVIF("avif"),
    GIF("gif"),
    JPEG("jpeg"),
    PNG("png"),
    SVG("svg"),
    WEBP("webp"),
    BMP("bmp"),
    ICO("ico"),
    TIFF("tiff"),
    XBM("xbm"),
    PHP("php"),
    JS("js"),
    JSON("json"),
    HTML("html"),
    CSS("css"),
    XML("xml"),
    XAML("xaml");
    private final String fileType;
    FileType(String fileType) { this.fileType = fileType.toLowerCase(); }
    public String getFileType() { return fileType; }
}
