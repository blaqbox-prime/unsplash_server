package com.blaqboxdev.unsplash.Repositories;

import com.blaqboxdev.unsplash.Models.Entities.Image;

import java.util.List;

public interface SearchRepo {
    List<Image> findByText(List<String> text);
}
