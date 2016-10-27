package com.asu.cloudclan.enums;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by rubinder on 10/4/16.
 */

public enum ImageFormat {
    JPG("jpg", "image/jpeg"),
    JPEG("jpeg", "image/jpeg");

    String extension;
    String mimeType;

    private static Map<String, ImageFormat> allFormats;
    private static Map<String, ImageFormat> supportedFormats;
    private static Set<String> supportedMimeTypes;
    static {
        allFormats = new HashMap<>();
        for (ImageFormat imageFormat : ImageFormat.values()) {
            allFormats.put(imageFormat.extension, imageFormat);
        }

        supportedFormats = new HashMap<>();
        supportedFormats.put(JPG.extension, JPG);
        supportedFormats.put(JPEG.extension, JPEG);

        supportedMimeTypes = new HashSet<>();
        supportedMimeTypes.add(JPEG.mimeType);
        supportedMimeTypes.add(JPG.mimeType);
    }

    ImageFormat(String extension, String mimeType) {
        this.extension = extension;
        this.mimeType = mimeType;
    }

    public static boolean isFormatSupported(String extension) {
        return supportedFormats.containsKey(extension);
    }

    public static boolean isMimeTypeSupported(String mimeType) {
        return supportedMimeTypes.contains(mimeType);
    }

    public static ImageFormat getByExtension(String extension) {
        return allFormats.get(extension);
    }

}
