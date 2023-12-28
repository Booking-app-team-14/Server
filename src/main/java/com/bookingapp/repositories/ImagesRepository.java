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

}
