package com.bookingapp.repositories;

import com.fasterxml.jackson.databind.ser.std.ByteArraySerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

@Component
public class ImagesRepository {

    public void addImage(String imageBytes, String imageType, String relativePath) throws IOException {
        byte[] bytes = Base64.getDecoder().decode(imageBytes);
        ByteArrayInputStream inStream = new ByteArrayInputStream(bytes);
        BufferedImage newImage = ImageIO.read(inStream);
        ImageIO.write(newImage, imageType, new File("src\\main\\resources\\images\\" + relativePath));
    }

    public String getImageType(String imageBytes) throws IOException {
        byte[] bytes = Base64.getDecoder().decode(imageBytes);
        String imageType = null;
        ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(bytes));
        ImageReader reader = ImageIO.getImageReaders(iis).next();
        reader.setInput(iis, true);
        imageType = reader.getFormatName();
        return imageType;
    }

    public String getImageBytes(String relativePath) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/images/" + relativePath);
        if (inputStream == null) {
            throw new IOException("Image not found");
        }
        int imageSize = inputStream.available();
        byte[] buffer = new byte[imageSize];
        int bytesRead = inputStream.read(buffer);
        if (bytesRead != imageSize) {
            throw new IOException("Error reading image:");
        }
        inputStream.close();
        return Base64.getEncoder().encodeToString(buffer);
    }

    public boolean deleteImage(String relativePath) {
        File file = new File("src\\main\\resources\\images\\" + relativePath);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    public void deleteAllImages(String relativePath) {
        File mainDirectory = new File("src\\main\\resources\\images\\" + relativePath);
        File[] files = mainDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    public String getUserImage(Long id) {
        String relativePath = findUserImageName(id);
        if (relativePath == null) {
            return null;
        }
        try {
            return getImageBytes(relativePath);
        } catch (IOException e) {
            return null;
        }
    }
    private String findUserImageName(Long id) {
        File directory1 = new File("src\\main\\resources\\images\\userAvatars");
        File[] files = directory1.listFiles();
        if (files != null) {
            for (File file : files) {
                String filename = file.getName();
                if (filename.startsWith("user-" + id)) {
                    return "userAvatars\\" + file.getName();
                }
            }
        }
        return null;
    }

}
