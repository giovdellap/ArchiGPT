/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.shenakht.paint.domain.Picture;
import ir.shenakht.paint.interceptors.InterceptorConfrimUserCodeUserAndJudgeUser;
import ir.shenakht.paint.interceptors.InterceptorIsAdmin;
import ir.shenakht.paint.logic.interfaces.PictureLogicIntf;
import ir.shenakht.paint.util.ConfigMapper;
import ir.shenakht.paint.util.UploadImage;
import ir.shenakht.paint.util.image.ConvertImage;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;
import javax.ejb.EJB;
import javax.imageio.ImageIO;
import javax.interceptor.Interceptors;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author hossien
 */
@Path("gallerys/{gallery_id}/pictures")
public class PictureResource {

    protected ObjectMapper mapper;

    @EJB
    private PictureLogicIntf pil;

    /**
     * Creates a new instance of PictureResource
     */
    public PictureResource() {
        mapper = ConfigMapper.getInstance();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Interceptors({InterceptorIsAdmin.class})
    public Response createPicture(
            @HeaderParam("user_code") String userCode,
            @PathParam("gallery_id") String galleryIdS,
            String data) throws IOException, ParseException {
        Integer galleryId = Integer.parseInt(galleryIdS);
        Picture picture = mapper.readValue(data, Picture.class);

        if (picture.getUrlMain() != null) {
            String imageString = ConvertImage.removeBase64Header(picture.getUrlMain());
            String imageType = ConvertImage.getImageType(picture.getUrlMain());
            BufferedImage image = ConvertImage.decodeToImage(imageString);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, imageType, baos);
            InputStream uploadedInputStream = new ByteArrayInputStream(baos.toByteArray());
            picture.setUrlMain(UploadImage.uploadImage(imageType, uploadedInputStream, userCode));
        }
        if (picture.getUrlThumbnail() != null) {
            String imageThumdnailString = ConvertImage.removeBase64Header(picture.getUrlThumbnail());
            String imageThumdnailType = ConvertImage.getImageType(picture.getUrlThumbnail());
            BufferedImage imageThumdnail = ConvertImage.decodeToImage(imageThumdnailString);

            ByteArrayOutputStream baosThumdnail = new ByteArrayOutputStream();
            ImageIO.write(imageThumdnail, imageThumdnailType, baosThumdnail);
            InputStream uploadedInputStreamThumdnail = new ByteArrayInputStream(baosThumdnail.toByteArray());

            picture.setUrlThumbnail(UploadImage.uploadImage(imageThumdnailType, uploadedInputStreamThumdnail, userCode));
        }
        picture = pil.createPicture(galleryId, picture);
        if (picture == null) {
            return Response.status(Response.Status.CONFLICT).header("Access-Control-Allow-Origin", "*").build();
        }
        String json = mapper.writeValueAsString(picture);
        return Response.status(Response.Status.CREATED).entity(json).header("Access-Control-Allow-Origin", "*").build();
    }

    @PUT
    @Path("{picture_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Interceptors({InterceptorIsAdmin.class})
    public Response updatePicture(
            @HeaderParam("user_code") String userCode,
            @PathParam("gallery_id") Integer galleryId,
            @PathParam("picture_id") Integer pictureId,
            String data) throws IOException {
        Picture picture = mapper.readValue(data, Picture.class);
        picture.setId(pictureId);
        if (picture.getUrlMain() != null) {
            String imageString = ConvertImage.removeBase64Header(picture.getUrlMain());
            String imageType = ConvertImage.getImageType(picture.getUrlMain());
            BufferedImage image = ConvertImage.decodeToImage(imageString);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, imageType, baos);
            InputStream uploadedInputStream = new ByteArrayInputStream(baos.toByteArray());
            picture.setUrlMain(UploadImage.uploadImage(imageType, uploadedInputStream, userCode));
        }
        if (picture.getUrlThumbnail() != null) {
            String imageThumdnailString = ConvertImage.removeBase64Header(picture.getUrlThumbnail());
            String imageThumdnailType = ConvertImage.getImageType(picture.getUrlThumbnail());
            BufferedImage imageThumdnail = ConvertImage.decodeToImage(imageThumdnailString);

            ByteArrayOutputStream baosThumdnail = new ByteArrayOutputStream();
            ImageIO.write(imageThumdnail, imageThumdnailType, baosThumdnail);
            InputStream uploadedInputStreamThumdnail = new ByteArrayInputStream(baosThumdnail.toByteArray());
            picture.setUrlThumbnail(UploadImage.uploadImage(imageThumdnailType, uploadedInputStreamThumdnail, userCode));
        }
        if (pil.updatePicture(galleryId, picture)) {
            return Response.status(Response.Status.OK).header("Access-Control-Allow-Origin", "*").build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).header("Access-Control-Allow-Origin", "*").build();
    }

    @DELETE
    @Path("{picture_id}")
    @Interceptors({InterceptorIsAdmin.class})
    public Response deletePicture(
            @HeaderParam("user_code") String userCode,
            @PathParam("gallery_id") String galleryIdS,
            @PathParam("picture_id") String pictureIdS) throws IOException {
        Integer galleryId = Integer.parseInt(galleryIdS);
        Integer pictureId = Integer.parseInt(pictureIdS);
        if (pil.deletePicture(galleryId, pictureId)) {
            return Response.status(Response.Status.OK).header("Access-Control-Allow-Origin", "*").build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Interceptors({InterceptorConfrimUserCodeUserAndJudgeUser.class})
    public Response getPictureByGallery(
            @HeaderParam("user_code") String userCode,
            @PathParam("gallery_id") String galleryIdS) throws JsonProcessingException {
        Integer galleryId = Integer.parseInt(galleryIdS);
        List<Picture> pictures = pil.findAllPictureForGallery(galleryId);
        if (pictures == null) {
            return Response.status(Response.Status.NO_CONTENT).header("Access-Control-Allow-Origin", "*").build();
        }
        String json = mapper.writeValueAsString(pictures);
        return Response.status(Response.Status.OK).entity(json).header("Access-Control-Allow-Origin", "*").build();
    }

}
