/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.shenakht.paint.domain.JudgeUser;
import ir.shenakht.paint.domain.Judgment;
import ir.shenakht.paint.domain.Participants;
import ir.shenakht.paint.domain.Question;
import ir.shenakht.paint.domain.Racing;
import ir.shenakht.paint.interceptors.InterceptorConfrimUserCodeUserAndJudgeUser;
import ir.shenakht.paint.interceptors.InterceptorIsAdmin;
import ir.shenakht.paint.interceptors.InterceptorJudgeUserCodeValidation;
import ir.shenakht.paint.logic.interfaces.JudgeUserLogicIntf;
import ir.shenakht.paint.logic.interfaces.JudgmentLogicIntf;
import ir.shenakht.paint.logic.interfaces.ParticipantsLogicIntf;
import ir.shenakht.paint.logic.interfaces.QuestionLogicIntf;
import ir.shenakht.paint.logic.interfaces.RacingLogicIntf;
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
@Path("racings/{racing_id}/questions/{question_id}/participants/{participants_id}/judgments")
public class JudgmentResource {

    @EJB
    private JudgmentLogicIntf jl;

    @EJB
    private JudgeUserLogicIntf jul;

    @EJB
    private RacingLogicIntf rl;

    @EJB
    private QuestionLogicIntf ql;

    @EJB
    private ParticipantsLogicIntf pl;

    protected ObjectMapper mapper;

    public JudgmentResource() {
        mapper = ConfigMapper.getInstance();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Interceptors({InterceptorJudgeUserCodeValidation.class})
    public Response createJudgment(@HeaderParam("user_code") String userCode,
            @PathParam("racing_id") Integer racingId,
            @PathParam("question_id") Integer questionId,
            @PathParam("participants_id") Integer participantsId,
            String data) throws IOException {
        Judgment judgment = mapper.readValue(data, Judgment.class);
        JudgeUser judgeUser = jul.findJudgeUserByUserCode(userCode);
        Racing racing = rl.findRaingWithId(racingId);
        Question question = ql.findQuestion(questionId);
        Participants participants = pl.findParitcipants(participantsId);
        if (racing != null && question != null && participants != null && judgeUser != null) {
            judgment = jl.createJudgment(question, racing, judgeUser, participants, judgment);
            if (judgment != null) {
                String json = mapper.writeValueAsString(judgment);
                return Response.status(Response.Status.CREATED).entity(json).build();
            }
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @PUT
    @Path("{judgment_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Interceptors({InterceptorIsAdmin.class})
    public Response updateJudgment(@HeaderParam("user_code") String userCode,
            @PathParam("racing_id") Integer racingId,
            @PathParam("question_id") Integer questionId,
            @PathParam("participants_id") Integer participantsId,
            @PathParam("judgment_id") Integer judgmentId,
            String data) throws IOException {
        Judgment judgment = mapper.readValue(data, Judgment.class);
        judgment.setId(judgmentId);
        if (jl.updateJudgment(judgment)) {
            String json = mapper.writeValueAsString(judgment);
            return Response.status(Response.Status.OK).entity(json).build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).build();
    }

    @DELETE
    @Path("{judgment_id}")
    @Interceptors({InterceptorIsAdmin.class})
    public Response deleteJudgment(@HeaderParam("user_code") String userCode,
            @PathParam("racing_id") Integer racingId,
            @PathParam("question_id") Integer questionId,
            @PathParam("participants_id") Integer participantsId,
            @PathParam("judgment_id") Integer judgmentId) throws IOException {
        if (jl.deleteJudgment(judgmentId)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).build();
    }

    @GET
    @Path("based-on-participants")
    @Produces(MediaType.APPLICATION_JSON)
    @Interceptors({InterceptorConfrimUserCodeUserAndJudgeUser.class})
    public Response getJudgmentBasedOnParticipants(@HeaderParam("user_code") String userCode,
            @PathParam("racing_id") Integer racingId,
            @PathParam("question_id") Integer questionId,
            @PathParam("participants_id") Integer participantsId) throws JsonProcessingException {
        Participants participants = pl.findParitcipants(participantsId);
        if (participants != null) {
            List<Judgment> judgments = jl.findListJudgmentByParticipants(participants);
            if (judgments != null) {
                String json = mapper.writeValueAsString(judgments);
                return Response.status(Response.Status.OK).entity(json).build();

            }
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("based-on-judge-users/{judge_user_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Interceptors({InterceptorConfrimUserCodeUserAndJudgeUser.class})
    public Response getJudgmentBasedOnJudgeUser(@HeaderParam("user_code") String userCode,
            @PathParam("racing_id") Integer racingId,
            @PathParam("question_id") Integer questionId,
            @PathParam("participants_id") Integer participantsId,
            @PathParam("judge_user_id") Integer judgeUserId) throws JsonProcessingException {
        JudgeUser judgeUser = jul.findJudgeUserById(judgeUserId);
        if (judgeUser != null) {
            List<Judgment> judgments = jl.findListJudgmentByJudgeUser(judgeUser);
            if (judgments != null) {
                String json = mapper.writeValueAsString(judgments);
                return Response.status(Response.Status.OK).entity(json).build();

            }
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
