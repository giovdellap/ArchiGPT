/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.shenakht.paint.domain.Gallery;
import ir.shenakht.paint.interceptors.InterceptorConfrimUserCodeUserAndJudgeUser;
import ir.shenakht.paint.interceptors.InterceptorIsAdmin;
import ir.shenakht.paint.logic.interfaces.GalleryLogicIntf;
import ir.shenakht.paint.util.ConfigMapper;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
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
@Path("gallerys")
public class GalleryResource {

    protected ObjectMapper mapper;

    @EJB
    private GalleryLogicIntf gl;

    /**
     * Creates a new instance of GalleryResource
     */
    public GalleryResource() {
        mapper = ConfigMapper.getInstance();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Interceptors({InterceptorIsAdmin.class})
    public Response createGallery(@HeaderParam("user_code") String userCode,
            String data) throws IOException {
        Gallery gallery = mapper.readValue(data, Gallery.class);
        gallery = gl.createGallery(gallery);
        if (gallery == null) {
            return Response.status(Response.Status.CONFLICT).header("Access-Control-Allow-Origin", "*").build();
        }
        String json = mapper.writeValueAsString(gallery);
        return Response.status(Response.Status.CREATED).entity(json).header("Access-Control-Allow-Origin", "*").build();
    }

    @PUT
    @Path("{gallery_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Interceptors({InterceptorIsAdmin.class})
    public Response updateGallery(@HeaderParam("user_code") String userCode,
            @PathParam("gallery_id") Integer galleryId,
            String data) throws IOException {
        Gallery gallery = mapper.readValue(data, Gallery.class);
        gallery.setId(galleryId);
        if (gl.updateGallery(gallery)) {
            String json = mapper.writeValueAsString(gallery);
            return Response.status(Response.Status.OK).entity(json).header("Access-Control-Allow-Origin", "*").build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).header("Access-Control-Allow-Origin", "*").build();
    }

    @DELETE
    @Path("{gallery_id}")
    @Interceptors({InterceptorIsAdmin.class})
    public Response deleteGallery(@HeaderParam("user_code") String userCode,
            @PathParam("gallery_id") Integer galleryId) {
        if (gl.deleteGallery(galleryId)) {
            return Response.status(Response.Status.OK).header("Access-Control-Allow-Origin", "*").build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllGallery(
            @HeaderParam("user_code") String userCode) throws JsonProcessingException {
        List<Gallery> gallerys = gl.findAllGallery();
        if (gallerys == null) {
            return Response.status(Response.Status.NO_CONTENT).header("Access-Control-Allow-Origin", "*").build();
        }

        String json = mapper.writeValueAsString(gallerys);
        return Response.status(Response.Status.OK).entity(json).header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("{gallery_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGallery(@HeaderParam("user_code") String userCode,
            @PathParam("gallery_id") Integer galleryId) throws JsonProcessingException {
        Gallery gallery = gl.findGalleryById(galleryId);
        if (gallery == null) {
            return Response.status(Response.Status.NO_CONTENT).header("Access-Control-Allow-Origin", "*").build();
        }
        String json = mapper.writeValueAsString(gallery);
        return Response.status(Response.Status.OK).entity(json).header("Access-Control-Allow-Origin", "*").build();

    }

}
