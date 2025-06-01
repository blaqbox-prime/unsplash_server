package com.blaqboxdev.unsplash.Models.Requests;

import java.util.List;


public record UploadImageRequest(String label, List<String> tags) {
}
