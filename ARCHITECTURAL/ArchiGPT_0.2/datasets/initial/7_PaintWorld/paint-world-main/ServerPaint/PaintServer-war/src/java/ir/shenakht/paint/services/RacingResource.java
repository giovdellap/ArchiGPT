/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.shenakht.paint.domain.Racing;
import ir.shenakht.paint.interceptors.InterceptorIsAdmin;
import ir.shenakht.paint.logic.interfaces.RacingLogicIntf;
import ir.shenakht.paint.util.ConfigMapper;
import ir.shenakht.paint.util.image.ConvertImage;
import ir.shenakht.paint.util.image.UtilUploadImage;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.ejb.EJB;
import javax.imageio.ImageIO;
import javax.interceptor.Interceptors;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
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
@Path("racings")
public class RacingResource {

    protected ObjectMapper mapper;

    @EJB
    private RacingLogicIntf rl;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

    public RacingResource() {
        mapper = ConfigMapper.getInstance();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Interceptors({InterceptorIsAdmin.class})
    public Response createRacing(@HeaderParam("user_code") String userCode,
            String data) throws IOException {
        Racing racing = mapper.readValue(data, Racing.class);
        if (racing.getUrl() != null) {
            String imageString = ConvertImage.removeBase64Header(racing.getUrl());
            String imageType = ConvertImage.getImageType(racing.getUrl());
            BufferedImage image = ConvertImage.decodeToImage(imageString);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, imageType, baos);
            InputStream uploadedInputStream = new ByteArrayInputStream(baos.toByteArray());

            racing.setUrl(UtilUploadImage.uploadImage(imageType, uploadedInputStream, "racings"));
        }
        racing = rl.createRacing(racing);
        if (racing != null) {
            String json = mapper.writeValueAsString(racing);
            return Response.status(Response.Status.CREATED).entity(json).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Interceptors({InterceptorIsAdmin.class})
    public Response updateRacing(@HeaderParam("user_code") String userCode,
            @PathParam("id") Integer id, String data) throws IOException {
        Racing racing = mapper.readValue(data, Racing.class);
        racing.setId(id);
        if (racing.getUrl() != null) {
            String imageString = ConvertImage.removeBase64Header(racing.getUrl());
            String imageType = ConvertImage.getImageType(racing.getUrl());
            BufferedImage image = ConvertImage.decodeToImage(imageString);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, imageType, baos);
            InputStream uploadedInputStream = new ByteArrayInputStream(baos.toByteArray());

            racing.setUrl(UtilUploadImage.uploadImage(imageType, uploadedInputStream, "racings"));
        }
        if (rl.updateRacing(racing)) {
            String json = mapper.writeValueAsString(racing);
            return Response.status(Response.Status.OK).entity(json).build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).build();
    }

    @DELETE
    @Path("{id}")
    @Interceptors({InterceptorIsAdmin.class})
    public Response deleteRacing(@HeaderParam("user_code") String userCode,
            @PathParam("id") Integer id) {
        if (rl.deleteRacing(id)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    @Interceptors({InterceptorConfrimUserCodeUserAndJudgeUser.class})
    public Response findAllRacing(@HeaderParam("user_code") String userCode) throws JsonProcessingException {
        List<Racing> racings = rl.findAllRacing();
        if (racings != null) {
            String json = mapper.writeValueAsString(racings);
            return Response.status(Response.Status.OK).entity(json).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
