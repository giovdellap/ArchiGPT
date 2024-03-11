/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import ir.shenakht.paint.domain.JudgeUser;
import ir.shenakht.paint.interceptors.InterceptorEmailExist;
import ir.shenakht.paint.logic.interfaces.JudgeUserLogicIntf;
import ir.shenakht.paint.util.ConfigMapper;
import ir.shenakht.paint.util.ConstantValues;
import ir.shenakht.paint.util.SendEmail;
import ir.shenakht.paint.util.SendSms;
import ir.shenakht.paint.util.UnAuthorizedAcsessException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;
import javax.ejb.EJB;
import javax.interceptor.Interceptors;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author hossien
 */
@Path("judge-users")
public class LoginResource {

    @EJB
    private JudgeUserLogicIntf ul;

    protected ObjectMapper mapper;

    SendEmail se = new SendEmail();

    public LoginResource() {
        mapper = ConfigMapper.getInstance();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(
            String data) throws IOException, UnAuthorizedAcsessException {
        JsonNode iNode = mapper.readTree(data);
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        JudgeUser user = null;
        if (iNode.get("installation_id") != null) {
            user = ul.login(iNode.get("username").textValue(), iNode.get("password").asText(), iNode.get("installation_id").textValue());
        } else {
            user = ul.login(iNode.get("username").textValue(), iNode.get("password").asText(), null);
        }
        if (user != null) {
            Set<String> parameters = new HashSet<String>();
            FilterProvider filters = new SimpleFilterProvider().addFilter("judgeUserViewfilter",
                    SimpleBeanPropertyFilter.serializeAllExcept(parameters));
            String json = mapper.writer(filters).writeValueAsString(user);
            return Response.ok(json, MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).header("Access-Control-Allow-Origin", "*").build();
    }

    @PUT
    @Path("forget-password")
    @Interceptors({
        InterceptorEmailExist.class
    })
    public Response forgetPassword(
            @QueryParam("email") String email,
            @QueryParam("username") String username) throws UnsupportedEncodingException, Exception {

        String pass = ul.updatePasswordRandomByEmail(email, username);
        if (pass == null) {
            return Response.status(Response.Status.NO_CONTENT).header("Access-Control-Allow-Origin", "*").build();
        }
        JudgeUser judgeUser = null;
        if (email != null) {
            judgeUser = ul.findByEmail(email);
        } else {
            judgeUser = ul.findJudgeUserByUsername(username);
        }
        if (judgeUser != null) {
//            if (email != null) {
//                String[] to = {email};
//                se.sendMessageToEmail(to, judgeUser, pass, "paint");
//            }
            if (username != null) {
                String textSms = "رمز جدید شما با نام کاربری: " + username + "\n رمز : " + pass;
                SendSms.SendMessage(judgeUser.getUsername(), textSms);
            }
            String textForAdmin = "کاربر " + judgeUser.getUsername() + " در خواست رمز جدید داشته.\n" + "رمز ارسالی برای کاربر: " + pass;
            SendSms.SendMessage(ConstantValues.SmsInfo.NUMBER_PHONE_ADMIN, textForAdmin);
            return Response.status(Response.Status.OK).header("Access-Control-Allow-Origin", "*").build();
        }
        return Response.status(Response.Status.NO_CONTENT).header("Access-Control-Allow-Origin", "*").build();

    }

    @POST
    @Path("/login-panel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUserToPanel(
            String data) throws IOException, UnAuthorizedAcsessException {
        JudgeUser judgeUser = mapper.readValue(data, JudgeUser.class);
        JsonNode iNode = mapper.readTree(data);
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        judgeUser = ul.loginPanel(iNode.get("username").textValue(), iNode.get("password").asText());
        if (judgeUser != null) {
            Set<String> parameters = new HashSet<String>();
            FilterProvider filters = new SimpleFilterProvider().addFilter("judgeUserViewfilter",
                    SimpleBeanPropertyFilter.serializeAllExcept(parameters));
            String json = mapper.writer(filters).writeValueAsString(judgeUser);
            return Response.ok(json, MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).header("Access-Control-Allow-Origin", "*").build();
    }
}
