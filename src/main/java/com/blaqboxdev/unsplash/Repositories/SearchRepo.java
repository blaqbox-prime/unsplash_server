package com.blaqboxdev.unsplash.Repositories;

import com.blaqboxdev.unsplash.Models.Image;

import java.util.List;

public interface SearchRepo {
    List<Image> findByText(String text);
}
