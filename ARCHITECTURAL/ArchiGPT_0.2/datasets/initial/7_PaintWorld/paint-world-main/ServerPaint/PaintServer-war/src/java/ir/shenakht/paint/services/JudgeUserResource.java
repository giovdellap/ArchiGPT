/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import ir.shenakht.paint.domain.JudgeUser;
import ir.shenakht.paint.enums.JudgeUserType;
import ir.shenakht.paint.interceptors.InterceptorEmailRepeated;
import ir.shenakht.paint.interceptors.InterceptorEmailRepeatedForUpdate;
import ir.shenakht.paint.interceptors.InterceptorEmailValidation;
import ir.shenakht.paint.interceptors.InterceptorIsAdmin;
import ir.shenakht.paint.interceptors.InterceptorIsJudgeUserCodeForId;
import ir.shenakht.paint.interceptors.InterceptorJudgeUserCodeValidation;
import ir.shenakht.paint.interceptors.InterceptorJudgeUsernameRepeated;
import ir.shenakht.paint.interceptors.InterceptorJudgeUsernameRepeatedForUpdate;
import ir.shenakht.paint.interceptors.InterceptorJudgeUsernameValidation;
import ir.shenakht.paint.interceptors.InterceptorPasswordValidation;
import ir.shenakht.paint.interceptors.InterceptorPasswordValidationForUpdateJudgeUser;
import ir.shenakht.paint.interceptors.InterceptorSessionForAdmin;
import ir.shenakht.paint.logic.interfaces.JudgeUserLogicIntf;
import ir.shenakht.paint.util.ConfigMapper;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author hossien
 */
@Path("judge-users")
public class JudgeUserResource {

    @EJB
    private JudgeUserLogicIntf ul;

    protected ObjectMapper mapper;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

    public JudgeUserResource() {
        mapper = ConfigMapper.getInstance();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Interceptors({
        InterceptorJudgeUsernameRepeated.class,
        InterceptorJudgeUsernameValidation.class,
        InterceptorEmailValidation.class,
        InterceptorPasswordValidation.class,
        InterceptorEmailRepeated.class})
    public Response createJudgeUser(
            @HeaderParam("user_code") String judgeUserCode,
            String data) throws IOException, Exception {
        JudgeUser judgeUser = mapper.readValue(data, JudgeUser.class);
        judgeUser = ul.createRegularJudgeUser(judgeUser);
        if (judgeUser == null) {
            return Response.status(Response.Status.CONFLICT).header("Access-Control-Allow-Origin", "*").build();
        } else {
            if (judgeUser.getActive() == false) {
                judgeUser.setUserCode(null);
            }
            Set<String> parameters = new HashSet<String>();
            parameters.add("user_code");
            parameters.add("password");
            FilterProvider filters = new SimpleFilterProvider().addFilter("judgeUserviewfilter",
                    SimpleBeanPropertyFilter.serializeAllExcept(parameters));
            String json = mapper.writer(filters).writeValueAsString(judgeUser);
            return Response.ok(json, MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Interceptors({
        InterceptorJudgeUsernameRepeatedForUpdate.class,
        InterceptorJudgeUsernameValidation.class,
        InterceptorEmailValidation.class,
        InterceptorEmailRepeatedForUpdate.class,
        InterceptorPasswordValidationForUpdateJudgeUser.class,
        InterceptorJudgeUserCodeValidation.class,
        InterceptorIsJudgeUserCodeForId.class
    })
    public Response updateJudgeUser(
            @HeaderParam("user_code") String judgeUserCode,
            String data,
            @PathParam("id") String idS) throws IOException, Exception {
        Integer id = Integer.parseInt(idS);
        JudgeUser judgeUser = mapper.readValue(data, JudgeUser.class);
        judgeUser.setId(id);
        if (ul.updateJudgeUser(judgeUser)) {
            Set<String> parameters = new HashSet<String>();
            FilterProvider filters = new SimpleFilterProvider().addFilter("judgeUserViewfilter",
                    SimpleBeanPropertyFilter.serializeAllExcept(parameters));
            String json = mapper.writer(filters).writeValueAsString(judgeUser);
            return Response.ok(json, MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).build();
    }

    @PUT
    @Path("/change-to-admin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Interceptors({
        InterceptorIsAdmin.class,
        InterceptorSessionForAdmin.class,
        InterceptorJudgeUsernameValidation.class
    })
    public Response changeToAdmin(
            @HeaderParam("user_code") String judgeUserCode,
            String data) throws IOException, Exception {
        JudgeUser judgeUser = mapper.readValue(data, JudgeUser.class);
        judgeUser.setTypeUser(JudgeUserType.ADMIN.ordinal());
        boolean flag = ul.updateJudgeUserType(judgeUserCode, judgeUser);
        if (flag) {
            return Response.ok().header("Access-Control-Allow-Origin", "*").build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).header("Access-Control-Allow-Origin", "*").build();
    }

    @PUT
    @Path("change-confirm/{judge_user_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Interceptors({
        InterceptorIsAdmin.class,
        InterceptorSessionForAdmin.class
    })
    public Response confirmJudgeUser(
            @HeaderParam("user_code") String judgeUserCode,
            @PathParam("judge_user_id") String judgeUserId,
            String data) throws IOException, Exception {
        JudgeUser judgeUser = mapper.readValue(data, JudgeUser.class);
        Integer id = Integer.parseInt(judgeUserId);
        judgeUser.setId(id);
        if (ul.confirmJudgeUser(judgeUserCode, judgeUser)) {
            Set<String> parameters = new HashSet<String>();
            parameters.add("password");
            parameters.add("user_code");
            FilterProvider filters = new SimpleFilterProvider().addFilter("judgeUserViewfilter",
                    SimpleBeanPropertyFilter.serializeAllExcept(parameters));
            String json = mapper.writer(filters).writeValueAsString(judgeUser);
            return Response.status(Response.Status.OK).entity(json).build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).header("Access-Control-Allow-Origin", "*").build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Interceptors({
        InterceptorIsAdmin.class,
        InterceptorSessionForAdmin.class
    })
    public Response deleteJudgeUser(
            @HeaderParam("user_code") String judgeUserCode,
            @PathParam("id") String id) {
        Integer idJudgeUser = Integer.parseInt(id);
        if (ul.deleteJudgeUser(judgeUserCode, idJudgeUser)) {
            return Response.ok().header("Access-Control-Allow-Origin", "*").build();
        } else {
            return Response.notModified().header("Access-Control-Allow-Origin", "*").build();
        }
    }

    @GET
    @Path("check-email")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkEmail(
            @HeaderParam("user_code") String judgeUserCode,
            @QueryParam("email") String email,
            @QueryParam("id") String idS) {
        boolean flag;
        if (judgeUserCode == null) {
            flag = ul.isEmailNotRepeated(email);
        } else {
            Integer id = Integer.parseInt(idS);
            flag = ul.isEmailNotRepeatedForUpdate(id, email);
        }
        if (flag) {
            return Response.ok().header("Access-Control-Allow-Origin", "*").build();
        } else {
            return Response.notAcceptable(null).header("Access-Control-Allow-Origin", "*").build();
        }

    }

    @GET
    @Path("check-username")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkJudgeUsername(
            @HeaderParam("user_code") String judgeUserCode,
            @QueryParam("username") String username,
            @QueryParam("id") String idS) {
        boolean flag;
        if (judgeUserCode == null) {
            flag = ul.isJudgeUsernameNotRepeated(username);
        } else {
            Integer id = Integer.parseInt(idS);
            flag = ul.isJudgeUsernameNotRepeatedForUpdate(id, username);
        }
        if (flag) {
            return Response.ok().header("Access-Control-Allow-Origin", "*").build();
        } else {
            return Response.notAcceptable(null).header("Access-Control-Allow-Origin", "*").build();
        }
    }

    @GET
    @Path("admin-online")
    @Produces(MediaType.APPLICATION_JSON)
    @Interceptors({
        InterceptorJudgeUserCodeValidation.class
    })
    public Response getAdminOnline(
            @HeaderParam("user_code") String judgeUserCode) throws JsonProcessingException {
        List<JudgeUser> judgeUsers = ul.findAdminIsOnline();
        if (judgeUsers == null) {
            return Response.status(Response.Status.NO_CONTENT).header("Access-Control-Allow-Origin", "*").build();
        }
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

        String json = null;
        Set<String> parameters = new HashSet<String>();
        parameters.add("email");
        parameters.add("phone");
        parameters.add("user_code");
        parameters.add("password");
        FilterProvider filters = new SimpleFilterProvider().addFilter("judgeUserViewfilter",
                SimpleBeanPropertyFilter.serializeAllExcept(parameters));
        json = mapper.writer(filters).writeValueAsString(judgeUsers);
        return Response.status(Response.Status.OK).entity(json).header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("admin")
    @Produces(MediaType.APPLICATION_JSON)
    @Interceptors({
        InterceptorIsAdmin.class,
        InterceptorSessionForAdmin.class
    })
    public Response getAllJudgeUserForAdmin(
            @HeaderParam("user_code") String judgeUserCode,
            @QueryParam("time") String lastTimeS) throws JsonProcessingException, ParseException {
        List<JudgeUser> judgeUsers = null;
        if (lastTimeS != null) {
            Date date = dateFormat.parse(lastTimeS);
            judgeUsers = ul.findAllJudgeUsersWithLastTimeCreate(date);
        } else {
            judgeUsers = ul.findAllJudgeUsers();
        }
        if (judgeUsers == null) {
            return Response.status(Response.Status.NO_CONTENT).header("Access-Control-Allow-Origin", "*").build();
        }
        Set<String> parameters = new HashSet<String>();
        FilterProvider filters = new SimpleFilterProvider().addFilter("judgeUserViewfilter",
                SimpleBeanPropertyFilter.serializeAllExcept(parameters));
        String json = mapper.writer(filters).writeValueAsString(judgeUsers);
        return Response.ok(json, MediaType.APPLICATION_JSON).header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Interceptors({
        InterceptorSessionForAdmin.class,
        InterceptorJudgeUserCodeValidation.class
    })
    public Response getAllJudgeUser(
            @HeaderParam("user_code") String judgeUserCode,
            @QueryParam("expansion") String expansion,
            @QueryParam("time") String lastTimeS) throws JsonProcessingException, ParseException {
        List<JudgeUser> judgeUsers = null;
        if (lastTimeS != null) {
            Date date = dateFormat.parse(lastTimeS);
            judgeUsers = ul.findAllJudgeUsersWithLastTimeCreate(date);
        } else {
            judgeUsers = ul.findAllJudgeUsers();
        }
        if (judgeUsers == null) {
            return Response.status(Response.Status.NO_CONTENT).header("Access-Control-Allow-Origin", "*").build();
        }
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

        String json = null;
        Set<String> parameters = new HashSet<String>();
        parameters.add("address");
        parameters.add("email");
        parameters.add("phone");
        parameters.add("uer_code");
        parameters.add("password");
        FilterProvider filters = new SimpleFilterProvider().addFilter("judgeUserViewfilter",
                SimpleBeanPropertyFilter.serializeAllExcept(parameters));
        json = mapper.writer(filters).writeValueAsString(judgeUsers);
        return Response.status(Response.Status.OK).entity(json).header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @Interceptors({
        InterceptorSessionForAdmin.class,
        InterceptorJudgeUserCodeValidation.class
    })
    public Response getJudgeUser(
            @HeaderParam("user_code") String judgeUserCode,
            @QueryParam("expansion") String expansion,
            @PathParam("username") String username) throws JsonProcessingException {
        JudgeUser judgeUser = ul.findJudgeUserByUsername(username);
        JudgeUser judgeUserMain = ul.findJudgeUserByUserCode(judgeUserCode);
        if (judgeUser == null) {
            return Response.status(Response.Status.NO_CONTENT).header("Access-Control-Allow-Origin", "*").build();
        }
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        String json = null;
        FilterProvider filters = null;
        if ((judgeUserMain.getTypeUser() == JudgeUserType.ADMIN.ordinal())
                || (judgeUser.getUserCode() == judgeUserCode)) {
            Set<String> parameters = new HashSet<String>();
            filters = new SimpleFilterProvider().addFilter("judgeUserViewfilter",
                    SimpleBeanPropertyFilter.serializeAllExcept(parameters));
            json = mapper.writer(filters).writeValueAsString(judgeUser);
        } else {
            Set<String> parameters = new HashSet<String>();
            parameters.add("user_code");
            parameters.add("password");
            filters = new SimpleFilterProvider().addFilter("judgeUserViewfilter",
                    SimpleBeanPropertyFilter.serializeAllExcept(parameters));
            json = mapper.writer(filters).writeValueAsString(judgeUser);
        }
        return Response.status(Response.Status.OK).entity(json).header("Access-Control-Allow-Origin", "*").build();
    }

}
