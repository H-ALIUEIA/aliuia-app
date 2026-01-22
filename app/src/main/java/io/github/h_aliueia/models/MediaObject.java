package io.github.h_aliueia.models;

public class MediaObject
{
    private String media_url;
    private String thumbnail;
    private String source;
    private String category;

    public MediaObject(String media_url, String thumbnail, String source, String category)
    {
        this.media_url = media_url;
        this.thumbnail = thumbnail;
        this.source = source;
        this.category = category;
    }

    public MediaObject() {
    }

    public String getCategory() {return category;}

    public void setCategory(String category) {this.category = category;}

    public String getSource() {return source;}

    public void setSource(String source) {this.source = source;}

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
