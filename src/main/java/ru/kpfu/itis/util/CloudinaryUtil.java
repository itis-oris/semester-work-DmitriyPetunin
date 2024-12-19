package ru.kpfu.itis.util;

import com.cloudinary.Cloudinary;
import java.util.*;

public class CloudinaryUtil {
    private static Cloudinary cloudinary;
    public static Cloudinary getInstance() {
        if (cloudinary == null) {
            Map<String, String> configMap = new HashMap<>();
            configMap.put("cloud_name", "imagesoftravel");
            configMap.put("api_key", "988734555618679");
            configMap.put("api_secret", "fgvyq6ggETaDWIyAmBICg4CVls8");
            cloudinary = new Cloudinary(configMap);
        }
        return cloudinary;
    }
}
