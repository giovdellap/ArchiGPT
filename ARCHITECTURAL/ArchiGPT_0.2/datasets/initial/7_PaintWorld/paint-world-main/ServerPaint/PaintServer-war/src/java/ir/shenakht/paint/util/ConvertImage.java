/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author hossien
 */
public class ConvertImage {

    public static BufferedImage decodeToImage(String imageString) throws IOException {
        BufferedImage image = null;
        byte[] imageByte;
        BASE64Decoder decoder = new BASE64Decoder();
        imageByte = decoder.decodeBuffer(imageString);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
        image = ImageIO.read(bis);
        bis.close();
        return image;
    }

    /**
     * Encode image to string
     *
     * @param imageFile The image to encode
     * @param type jpeg, bmp, gif, png
     * @return encoded string
     */
    public static String encodeToString(File imageFile, String type) throws IOException {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        BufferedImage image = ImageIO.read(imageFile);
        ImageIO.write(image, type, bos);
        byte[] imageBytes = bos.toByteArray();

        BASE64Encoder encoder = new BASE64Encoder();
        imageString = encoder.encode(imageBytes);

        bos.close();
        return imageString;
    }

    /**
     * To make base64 string decoded properly, We need to remove the base64
     * header from a base64 string.
     *
     * @param base64 The Base64 string of an image.
     * @return Base64 string without header.
     */
    public static String removeBase64Header(String base64) {
        if (base64 == null) {
            return null;
        }
        return base64.trim().replaceFirst("data[:]image[/]([a-z])+;base64,", "");
    }

    /**
     * Get the image type.
     *
     * @param base64 The base64 string of an image.
     * @return jpg, png, gif
     */
    public static String getImageType(String base64) {
        String[] header = base64.split("[;]");
        if (header == null || header.length == 0) {
            return null;
        }
        return header[0].split("[/]")[1];
    }
}
