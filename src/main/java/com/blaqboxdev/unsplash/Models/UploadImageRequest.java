package com.blaqboxdev.unsplash.Models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;


public record UploadImageRequest(String label, List<String> tags) {
}
