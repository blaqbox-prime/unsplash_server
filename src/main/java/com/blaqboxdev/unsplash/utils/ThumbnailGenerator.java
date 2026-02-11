package com.blaqboxdev.unsplash.utils;

import net.coobird.thumbnailator.Thumbnails;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class ThumbnailGenerator {

    public static byte[] createThumbnail(File imageFile, int width, int height) throws Exception {


        ByteArrayOutputStream output = new ByteArrayOutputStream();

        Thumbnails.of(imageFile)
                  .size(width, height)
                  .keepAspectRatio(true)
                  .outputFormat("jpg")
                  .toOutputStream(output);

        return output.toByteArray();
    }
}
