package com.blaqboxdev.unsplash.Models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "photos")
public class Photo {
    private String label;

    private String _id;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Instant getDate_added() {
        return date_added;
    }

    public Photo() {
    }

    public void setDate_added(Instant date_added) {

        this.date_added = date_added;
    }

    private String url;
    private Instant date_added;

    public Photo(String label, String id, String url, Instant date_added) {
        this.label = label;
        _id = id;
        this.url = url;
        this.date_added = date_added;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
