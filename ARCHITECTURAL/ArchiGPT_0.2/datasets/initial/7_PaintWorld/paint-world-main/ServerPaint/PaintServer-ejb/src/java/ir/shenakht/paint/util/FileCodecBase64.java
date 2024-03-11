/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author hossien
 */
public class FileCodecBase64 {

    public static String uploadFile(String base64String, String extension) throws Exception {
        if (base64String != null) {
            UUID nameFile = UUID.randomUUID();
            String addressFile = ConstantValues.AddressServers.SERVER_UPLOAD_LOCATION_FOLDER + "file/" + nameFile + "." + extension;
            decode(base64String, addressFile);
            return ConstantValues.AddressServers.LINK_SERVER + "file/" + nameFile + "." + extension;
        }
        return null;
    }

    /**
     * This method converts the content of a source file into Base64 encoded
     * data and saves that to a target file. If isChunked parameter is set to
     * true, there is a hard wrap of the output encoded text.
     */
    private static void encode(String sourceFile, String targetFile, boolean isChunked) throws Exception {

        byte[] base64EncodedData = Base64.encodeBase64(loadFileAsBytesArray(sourceFile), isChunked);

        writeByteArraysToFile(targetFile, base64EncodedData);
    }

    private static void decode(String sourceData, String targetFile) throws Exception {

        byte[] decodedBytes = Base64.decodeBase64(sourceData);

        writeByteArraysToFile(targetFile, decodedBytes);
    }

    /**
     * This method loads a file from file system and returns the byte array of
     * the content.
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    private static byte[] loadFileAsBytesArray(String fileName) throws Exception {

        File file = new File(fileName);
        int length = (int) file.length();
        BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
        byte[] bytes = new byte[length];
        reader.read(bytes, 0, length);
        reader.close();
        return bytes;

    }

    /**
     * This method writes byte array content into a file.
     *
     * @param fileName
     * @param content
     * @throws IOException
     */
    private static void writeByteArraysToFile(String fileName, byte[] content) throws IOException {

        File file = new File(fileName);
        BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file));
        writer.write(content);
        writer.flush();
        writer.close();

    }

    private static String getFileType(String base64) {
        String[] header = base64.split("[;]");
        if (header == null || header.length == 0) {
            return null;
        }
        return header[0].split("[/]")[1];
    }

}
