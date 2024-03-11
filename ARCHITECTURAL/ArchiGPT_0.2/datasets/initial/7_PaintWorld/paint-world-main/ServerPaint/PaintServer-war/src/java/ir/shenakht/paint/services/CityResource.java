/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.shenakht.paint.domain.City;
import ir.shenakht.paint.domain.Provice;
import ir.shenakht.paint.interceptors.InterceptorConfrimUserCodeUserAndJudgeUser;
import ir.shenakht.paint.interceptors.InterceptorIsAdmin;
import ir.shenakht.paint.logic.interfaces.CityLogicIntf;
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
@Path("provices/{provice_id}/citys")
public class CityResource {

    protected ObjectMapper mapper;

    @EJB
    private CityLogicIntf cl;

    @EJB
    private ProviceLogicIntf pl;

    public CityResource() {
        mapper = ConfigMapper.getInstance();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Interceptors({InterceptorIsAdmin.class})
    public Response createCity(@HeaderParam("user_code") String userCode,
            @PathParam("provice_id") Integer prociveId, String data) throws IOException {
        City city = mapper.readValue(data, City.class);
        Provice provice = pl.findProvice(prociveId);
        city = cl.createCity(provice, city);
        if (city != null) {
            String json = mapper.writeValueAsString(city);
            return Response.status(Response.Status.CREATED).entity(json).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @PUT
    @Path("{city_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Interceptors({InterceptorIsAdmin.class})
    public Response updateCity(@HeaderParam("user_code") String userCode,
            @PathParam("provice_id") Integer proviceId,
            @PathParam("city_id") Integer cityId, String data) throws IOException {
        City city = mapper.readValue(data, City.class);
        city.setId(cityId);
        if (cl.updateCity(city)) {
            String json = mapper.writeValueAsString(city);
            return Response.status(Response.Status.OK).entity(json).build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).build();
    }

    @DELETE
    @Path("{city_id}")
    @Interceptors({InterceptorIsAdmin.class})
    public Response deleteCity(@HeaderParam("user_code") String userCode,
            @PathParam("provice_id") Integer proviceId,
            @PathParam("city_id") Integer cityId) {
        if (cl.deleteCity(cityId)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Interceptors({InterceptorConfrimUserCodeUserAndJudgeUser.class})
    public Response getAllCity(@HeaderParam("user_code") String userCode) throws JsonProcessingException {
        List<City> citys = cl.findAllCity();
        if (citys != null) {
            String json = mapper.writeValueAsString(citys);
            return Response.status(Response.Status.OK).entity(json).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
