/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 *
 * @author Mohammad Kazemifard
 */
public class UploadImage {

    public static String uploadImage(/*FormDataContentDisposition*//*FormDataContentDisposition*/String fileDetail, InputStream uploadedInputStream, String userCode) throws IOException {
        if (fileDetail != null) {
//            String extension = "";
//            int lastpoint = fileDetail.getFileName().lastIndexOf('.');
//            if (lastpoint > 0) {
//                extension = fileDetail.getFileName().substring(lastpoint + 1);
//            }
            String extension = fileDetail;
            if ((extension.equals("jpg"))
                    || (extension.equals("jpeg"))
                    || (extension.equals("png"))
                    || (extension.equals("bmp"))
                    || (extension.equals("jpe"))) {
                String fileName = UUID.randomUUID().toString() + '.' + extension;
                String uploadedFileLocation = ConstantValues.AddressServers.SERVER_UPLOAD_LOCATION_FOLDER + "img/" + userCode + "/" + fileName;
                writeToFile(uploadedInputStream, uploadedFileLocation);
                return ConstantValues.AddressServers.LINK_SERVER + "img/" + userCode + "/" + fileName;
            }
        }
        return null;
    }

    private static void writeToFile(InputStream uploadedInputStream,
            String uploadedFileLocation) throws FileNotFoundException, IOException {
        File file = new File(uploadedFileLocation);
        File folder = new File(file.getParent());
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
        OutputStream out = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int bytes = 0;
        while ((bytes = uploadedInputStream.read(buffer)) != -1) {
            out.write(buffer, 0, bytes);
        }
        out.flush();
        out.close();
    }

}
