/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.shenakht.paint.domain.Provice;
import ir.shenakht.paint.interceptors.InterceptorConfrimUserCodeUserAndJudgeUser;
import ir.shenakht.paint.interceptors.InterceptorIsAdmin;
import ir.shenakht.paint.logic.interfaces.ProviceLogicIntf;
import ir.shenakht.paint.util.ConfigMapper;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.interceptor.Interceptors;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author hossien
 */
@Path("provices")
public class ProviceResource {

    protected ObjectMapper mapper;

    @EJB
    private ProviceLogicIntf pl;

    public ProviceResource() {
        mapper = ConfigMapper.getInstance();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Interceptors({InterceptorIsAdmin.class})
    public Response createPovice(@HeaderParam("user_code") String userCode,
            String data) throws IOException {
        Provice provice = mapper.readValue(data, Provice.class);
        provice = pl.createProvice(provice);
        if (provice != null) {
            String json = mapper.writeValueAsString(provice);
            return Response.status(Response.Status.CREATED).entity(json).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @PUT
    @Path("{provice_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Interceptors({InterceptorIsAdmin.class})
    public Response updateProvice(@HeaderParam("user_code") String userCode,
            @PathParam("provice_id") Integer proviceId,
            String data) throws IOException {
        Provice provice = mapper.readValue(data, Provice.class);
        provice.setId(proviceId);
        if (pl.updateProcvice(provice)) {
            String json = mapper.writeValueAsString(provice);
            return Response.status(Response.Status.OK).entity(json).build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).build();
    }

    @DELETE
    @Path("{provice_id}")
    @Interceptors({InterceptorIsAdmin.class})
    public Response deleteProvice(@HeaderParam("user_code") String userCode,
            @PathParam("provice_id") Integer proviceId) {
        if (pl.deleteProvice(proviceId)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Interceptors({InterceptorConfrimUserCodeUserAndJudgeUser.class})
    public Response getProvice(@HeaderParam("user_code") String userCode) throws JsonProcessingException {
        List<Provice> provices = pl.findAllProvice();
        if (provices != null) {
            String json = mapper.writeValueAsString(provices);
            return Response.status(Response.Status.OK).entity(json).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
