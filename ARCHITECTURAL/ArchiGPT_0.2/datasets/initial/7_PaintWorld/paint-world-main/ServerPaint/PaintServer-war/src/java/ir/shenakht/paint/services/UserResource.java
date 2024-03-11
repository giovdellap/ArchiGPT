/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.shenakht.paint.domain.User;
import ir.shenakht.paint.logic.interfaces.UserLogicIntf;
import ir.shenakht.paint.util.ConfigMapper;
import ir.shenakht.paint.util.ConvertPhoneToRightFormat;
import ir.shenakht.paint.util.SendSms;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
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
@Path("users")
public class UserResource {

    protected ObjectMapper mapper;

    @EJB
    private UserLogicIntf ul;

    public UserResource() {
        mapper = ConfigMapper.getInstance();
    }

    @POST
    @Path("phones")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccount(@HeaderParam("phone") String phone) throws Exception {
        phone = ConvertPhoneToRightFormat.makePhone(phone);
        String confirmCode = ul.createConfirmCode(phone);
        if (confirmCode != null) {
            String text = "this is code = " + confirmCode + " for active app";
            SendSms.SendMessage(phone, text);
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @POST
    @Path("withoutSms/phones")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccountWithoutSms(@HeaderParam("phone") String phone) throws Exception {
        phone = ConvertPhoneToRightFormat.makePhone(phone);
        User user = new User();
        user.setPhone(phone);
        user = ul.createUser(user);
        if (user != null) {
            String json = mapper.writeValueAsString(user);
            return Response.status(Response.Status.CREATED).entity(json).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @GET
    @Path("confirm-codes/{confirm-code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserWithConfirmCode(@HeaderParam("phone") String phone,
            @PathParam("confirm-code") String confirmCode) throws JsonProcessingException {
        phone = ConvertPhoneToRightFormat.makePhone(phone);
        User user = ul.CheckStatusConfirmUser(phone, confirmCode);
        if (user != null) {
            String json = mapper.writeValueAsString(user);
            return Response.status(Response.Status.OK).entity(json).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }
}
