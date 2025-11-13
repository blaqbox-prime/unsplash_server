package com.blaqboxdev.unsplash.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO  {

    String label;

    List<String> tags;

    String _id;

    String url;

    LocalDateTime date_added;

    int likes = 0;

    ProfileDTO profile;

    public ImageDTO(String label, List<String> tags, String id, String url, LocalDateTime dateAdded, ProfileDTO profile) {
        this.label = label;
        this.tags = tags;
        _id = id;
        this.url = url;
        date_added = dateAdded;
        this.profile = profile;
    }
}
