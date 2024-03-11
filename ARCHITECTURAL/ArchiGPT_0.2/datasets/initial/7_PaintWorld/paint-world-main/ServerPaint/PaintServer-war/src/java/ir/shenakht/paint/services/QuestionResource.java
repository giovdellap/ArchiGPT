/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.shenakht.paint.domain.Question;
import ir.shenakht.paint.interceptors.InterceptorConfrimUserCodeUserAndJudgeUser;
import ir.shenakht.paint.interceptors.InterceptorIsAdmin;
import ir.shenakht.paint.logic.interfaces.QuestionLogicIntf;
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
@Path("questions")
public class QuestionResource {

    @EJB
    private QuestionLogicIntf ql;

    protected ObjectMapper mapper;

    public QuestionResource() {
        mapper = ConfigMapper.getInstance();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Interceptors({InterceptorIsAdmin.class})
    public Response createQuestion(@HeaderParam("user_code") String userCode,
            String data) throws IOException {
        Question question = mapper.readValue(data, Question.class);
        question = ql.createQuestion(question);
        if (question != null) {
            String json = mapper.writeValueAsString(question);
            return Response.status(Response.Status.CREATED).entity(json).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @PUT
    @Path("{question_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Interceptors({InterceptorIsAdmin.class})
    public Response updateQuestion(@HeaderParam("user_code") String userCode,
            @PathParam("question_id") Integer questionId,
            String data) throws IOException {
        Question question = mapper.readValue(data, Question.class);
        question.setId(questionId);
        if (ql.updateQuestion(question)) {
            String json = mapper.writeValueAsString(question);
            return Response.status(Response.Status.OK).entity(json).build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).build();
    }

    @DELETE
    @Path("{question_id}")
    @Interceptors({InterceptorIsAdmin.class})
    public Response deleteQuestion(@HeaderParam("user_code") String userCode,
            @PathParam("question_id") Integer questionId) {
        if (ql.deleteQuestion(questionId)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Interceptors({InterceptorConfrimUserCodeUserAndJudgeUser.class})
    public Response findAllQuestion(@HeaderParam("user_code") String userCode) throws JsonProcessingException {
        List<Question> questions = ql.findAllQuestion();
        if (questions != null) {
            String json = mapper.writeValueAsString(questions);
            return Response.status(Response.Status.OK).entity(json).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
