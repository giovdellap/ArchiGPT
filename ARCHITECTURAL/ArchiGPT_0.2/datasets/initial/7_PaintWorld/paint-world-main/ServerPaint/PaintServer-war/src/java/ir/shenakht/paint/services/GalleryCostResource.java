/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.shenakht.paint.domain.GalleryCost;
import ir.shenakht.paint.interceptors.InterceptorConfrimUserCodeUserAndJudgeUser;
import ir.shenakht.paint.interceptors.InterceptorIsAdmin;
import ir.shenakht.paint.logic.interfaces.GalleryCostLogicIntf;
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
@Path("gallery-costs")
public class GalleryCostResource {

    @EJB
    private GalleryCostLogicIntf gcl;

    protected ObjectMapper mapper;

    public GalleryCostResource() {
        mapper = ConfigMapper.getInstance();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Interceptors({InterceptorIsAdmin.class})
    public Response createGalleryCost(@HeaderParam("user_code") String userCode,
            String data) throws IOException {
        GalleryCost galleryCost = mapper.readValue(data, GalleryCost.class);
        galleryCost = gcl.createGalleryCost(galleryCost);
        if (galleryCost != null) {
            String json = mapper.writeValueAsString(galleryCost);
            return Response.status(Response.Status.CREATED).entity(json).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @PUT
    @Path("{gallery_cost_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Interceptors({InterceptorIsAdmin.class})
    public Response updateGalleryCost(@HeaderParam("user_code") String userCode,
            @PathParam("gallery_cost_id") Integer galleryCostId,
            String data) throws IOException {
        GalleryCost galleryCost = mapper.readValue(data, GalleryCost.class);
        galleryCost.setId(galleryCostId);
        if (gcl.updateGalleryCost(galleryCost)) {
            String json = mapper.writeValueAsString(galleryCost);
            return Response.status(Response.Status.OK).entity(json).build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).build();
    }

    @DELETE
    @Path("{gallery_cost_id}")
    @Interceptors({InterceptorIsAdmin.class})
    public Response deleteGalleryCost(@HeaderParam("user_code") String userCode,
            @PathParam("gallery_cost_id") Integer galleryCostId) {
        if (gcl.deleteGalleryCost(galleryCostId)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Interceptors({InterceptorConfrimUserCodeUserAndJudgeUser.class})
    public Response findAllGalleryCost(@HeaderParam("user_code") String userCode) throws JsonProcessingException {
        List<GalleryCost> galleryCosts = gcl.findAllGalleryCost();
        if (galleryCosts != null) {
            String json = mapper.writeValueAsString(galleryCosts);
            return Response.status(Response.Status.OK).entity(json).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
