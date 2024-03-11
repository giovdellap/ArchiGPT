/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.shenakht.paint.domain.Participants;
import ir.shenakht.paint.interceptors.InterceptorConfrimUserCodeUserAndJudgeUser;
import ir.shenakht.paint.logic.interfaces.ParticipantsLogicIntf;
import ir.shenakht.paint.util.ConfigMapper;
import ir.shenakht.paint.util.image.ConvertImage;
import ir.shenakht.paint.util.image.UtilUploadImage;
import ir.shenakht.paint.view.customizations.AgeCalculate;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.ejb.EJB;
import javax.imageio.ImageIO;
import javax.interceptor.Interceptors;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author hossien
 */
@Path("participants")
public class ParticipantsResource {

    protected ObjectMapper mapper;

    @EJB
    private ParticipantsLogicIntf pl;

    public ParticipantsResource() {
        mapper = ConfigMapper.getInstance();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Interceptors({InterceptorConfrimUserCodeUserAndJudgeUser.class})
    public Response createParticipants(@HeaderParam("user_code") String userCode,
            @HeaderParam("racing_id") Integer racingID,
            String data) throws IOException {
        Participants participants = mapper.readValue(data, Participants.class);

        if (participants.getUrl() != null) {
            String imageString = ConvertImage.removeBase64Header(participants.getUrl());
            String imageType = ConvertImage.getImageType(participants.getUrl());
            BufferedImage image = ConvertImage.decodeToImage(imageString);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, imageType, baos);
            InputStream uploadedInputStream = new ByteArrayInputStream(baos.toByteArray());

            participants.setUrl(UtilUploadImage.uploadImage(imageType, uploadedInputStream, racingID.toString()));
            participants = pl.createParticipants(userCode, racingID, participants);
            if (participants == null) {
                return Response.status(Response.Status.CONFLICT).header("Access-Control-Allow-Origin", "*").build();
            }
            String json = mapper.writeValueAsString(participants);
            return Response.status(Response.Status.CREATED).entity(json).header("Access-Control-Allow-Origin", "*").build();
        } else {
            return Response.status(Response.Status.CONFLICT).header("Access-Control-Allow-Origin", "*").build();
        }
    }

    @PUT
    @Path("{participants_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Interceptors({InterceptorConfrimUserCodeUserAndJudgeUser.class})
    public Response updateParticipants(@HeaderParam("user_code") String userCode,
            @HeaderParam("racing_id") Integer racingID,
            @PathParam("participants_id") Integer participantsId,
            String data) throws IOException {
        Participants participants = mapper.readValue(data, Participants.class);
        participants.setId(participantsId);
        if (participants.getUrl() != null) {
            String imageString = ConvertImage.removeBase64Header(participants.getUrl());
            String imageType = ConvertImage.getImageType(participants.getUrl());
            BufferedImage image = ConvertImage.decodeToImage(imageString);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, imageType, baos);
            InputStream uploadedInputStream = new ByteArrayInputStream(baos.toByteArray());

            participants.setUrl(UtilUploadImage.uploadImage(imageType, uploadedInputStream, racingID.toString()));
        }
        if (pl.updateParticipants(participants)) {
            String json = mapper.writeValueAsString(participants);
            return Response.status(Response.Status.OK).entity(json).header("Access-Control-Allow-Origin", "*").build();
        } else {
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }

    @DELETE
    @Path("{participants_id}")
    @Interceptors({InterceptorConfrimUserCodeUserAndJudgeUser.class})
    public Response deleteParticipants(@HeaderParam("user_code") String userCode,
            @PathParam("participants_id") Integer participantsId) {
        if (pl.deleteParticipants(participantsId)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).build();
    }

    @GET
    @Path("racings/{racing_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Interceptors({InterceptorConfrimUserCodeUserAndJudgeUser.class})
    public Response findListParticipantsByRacing(@HeaderParam("user_code") String userCode,
            @PathParam("racing_id") Integer racingId) throws JsonProcessingException {
        List<Participants> participantses = pl.findListParticipantsWithRacing(racingId);
        if (participantses != null) {
            AgeCalculate.value(participantses);
            String json = mapper.writeValueAsString(participantses);
            return Response.status(Response.Status.OK).entity(json).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("users")
    @Produces(MediaType.APPLICATION_JSON)
//    @Interceptors({InterceptorConfrimUserCodeUserAndJudgeUser.class})
    public Response findListParticipantsByUser(@HeaderParam("user_code") String userCode) throws JsonProcessingException {
        List<Participants> participantses = pl.findListParticipantsWithUserCode(userCode);
        if (participantses != null) {
            AgeCalculate.value(participantses);
            String json = mapper.writeValueAsString(participantses);
            return Response.status(Response.Status.OK).entity(json).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("condition-type/{condition-type}")
    @Produces(MediaType.APPLICATION_JSON)
    @Interceptors({InterceptorConfrimUserCodeUserAndJudgeUser.class})
    public Response findListParticipantsByConditionType(@HeaderParam("user_code") String userCode,
            @PathParam("condition-type") Integer conditionType) throws JsonProcessingException {
        List<Participants> participantses = pl.findListParticipantsWithConditionType(conditionType);
        if (participantses != null) {
            AgeCalculate.value(participantses);
            String json = mapper.writeValueAsString(participantses);
            return Response.status(Response.Status.OK).entity(json).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("today")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findListParticipantsForToday(@HeaderParam("user_code") String userCode) throws JsonProcessingException {
        List<Participants> participantses = pl.findListPaticipantsOnToday();
        if (participantses != null) {
            AgeCalculate.value(participantses);
            String json = mapper.writeValueAsString(participantses);
            return Response.status(Response.Status.OK).entity(json).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("admin")
    @Produces(MediaType.APPLICATION_JSON)
//    @Interceptors({InterceptorConfrimUserCodeUserAndJudgeUser.class})
    public Response findListParticipants(@HeaderParam("user_code") String userCode) throws JsonProcessingException {
        List<Participants> participantses = pl.findAllPaticipants();
        if (participantses != null) {
            AgeCalculate.value(participantses);
            String json = mapper.writeValueAsString(participantses);
            return Response.status(Response.Status.OK).entity(json).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
