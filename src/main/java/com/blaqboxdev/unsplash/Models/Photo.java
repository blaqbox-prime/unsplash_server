package com.blaqboxdev.unsplash.Models;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;
import java.time.LocalDateTime;

@Document(collection = "photos")
public class Photo {
    private String label;

    @MongoId
    private String _id;

    private String url;

    private LocalDateTime date_added;

    private int likes = 0;

    @DocumentReference(collection = "profiles")
    private String profile_id;

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

    public LocalDateTime getDate_added() {
        return date_added;
    }

    public Photo() {
    }

    public void setDate_added(LocalDateTime date_added) {

        this.date_added = date_added;
    }

    public void increment_likes(){
        this.likes = this.likes + 1;
    }

    public void decrement_likes(){
        if(this.likes != 0){
            this.likes = this.likes - 1;
        }
    }


    public Photo(String label, String id, String url, LocalDateTime date_added) {
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

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }
}
