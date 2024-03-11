/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.shenakht.paint.domain.PaymentIrancel;
import ir.shenakht.paint.logic.interfaces.PaymentIrancelLogicIntf;
import ir.shenakht.paint.util.ConfigMapper;
import java.io.IOException;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author hossien
 */
@Path("PaymentIrancel")
public class PaymentIrancelResource {

    protected ObjectMapper mapper;

    @EJB
    private PaymentIrancelLogicIntf pil;

    public PaymentIrancelResource() {
        mapper = ConfigMapper.getInstance();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(String data) throws IOException {
        System.out.println("String data Irancel === " + data);
        PaymentIrancel pi = mapper.readValue(data, PaymentIrancel.class);
        pi = pil.create(pi);
        if (pi != null) {
            String json = mapper.writeValueAsString(pi);
            return Response.status(Response.Status.CREATED).entity(json).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @GET
    @Path("{phone}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLatest(@PathParam("phone") String phone) throws JsonProcessingException {
        System.out.println(phone);
        PaymentIrancel pi = pil.findLatest(phone);
//        System.out.println(pi);
        if (pi != null) {
            String json = mapper.writeValueAsString(pi);
            return Response.status(Response.Status.OK).entity(json).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
