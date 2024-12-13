package ru.kpfu.itis.util;

import com.cloudinary.Cloudinary;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.*;

public class CloudinaryUtil {
    private static Cloudinary cloudinary = CloudinaryUtil.getInstance();
    private static final String UPLOAD_DIR = "/Users/dmitriypetunin/Documents";

    public static Cloudinary getInstance() {
        if (cloudinary == null) {
            Map<String, String> configMap = new HashMap<>();
            configMap.put("cloud_name", "dra33yahs");
            configMap.put("api_key", "156662612335371");
            configMap.put("api_secret", "dcfHvLTXuD7HXfwnDcqDJKRWUcA");
            cloudinary = new Cloudinary(configMap);
        }
        return cloudinary;
    }
    public static List<String> upload (Collection<Part> parts){
        List<String> urls = new ArrayList<>();
        for (Part part: parts){
            try {
                if (part.getSubmittedFileName() != null && !part.getSubmittedFileName().isEmpty()){

                    String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                    String path = UPLOAD_DIR + File.separator + filename.hashCode() % 10
                            + File.separator + filename;
                    File file = new File(path);

                    InputStream content = part.getInputStream();
                    file.getParentFile().mkdirs();
                    file.createNewFile();

                    String directoryPath = file.getParent();
                    System.out.println("Файлы были записаны в директорию: " + directoryPath);
                    System.out.println("Файл будет записан в: " + file.getAbsolutePath());

                    FileOutputStream outputStream = new FileOutputStream(file);
                    byte[] buffer = new byte[content.available()];
                    content.read(buffer);
                    outputStream.write(buffer);
                    outputStream.close();

                    Map<String,Object> uploadsParams = new HashMap<>();
                    uploadsParams.put("folder","travelImages");
                    uploadsParams.put("public_id","my_image_id_" + filename);


                    Map<String,Object> uploadResult = cloudinary.uploader().upload(file, uploadsParams);
                    String url = (String) uploadResult.get("url");
                    urls.add(url);
                    System.out.println("Загруженное изображение доступно по адресу: " + url);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return urls;
    }
}
