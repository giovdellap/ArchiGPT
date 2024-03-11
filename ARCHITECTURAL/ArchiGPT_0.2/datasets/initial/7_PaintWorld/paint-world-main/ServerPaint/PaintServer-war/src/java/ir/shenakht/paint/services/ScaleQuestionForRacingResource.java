/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.shenakht.paint.domain.Question;
import ir.shenakht.paint.domain.Racing;
import ir.shenakht.paint.domain.ScaleQuestionForRacing;
import ir.shenakht.paint.interceptors.InterceptorIsAdmin;
import ir.shenakht.paint.logic.interfaces.QuestionLogicIntf;
import ir.shenakht.paint.logic.interfaces.RacingLogicIntf;
import ir.shenakht.paint.logic.interfaces.ScaleQuestionForRacingLogicIntf;
import ir.shenakht.paint.util.ConfigMapper;
import java.io.IOException;
import javax.ejb.EJB;
import javax.interceptor.Interceptors;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
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
@Path("racings/{racing_id}/questions/{question_id}/scales")
public class ScaleQuestionForRacingResource {

    @EJB
    private ScaleQuestionForRacingLogicIntf sqrl;

    @EJB
    private RacingLogicIntf rl;

    @EJB
    private QuestionLogicIntf ql;

    protected ObjectMapper mapper;

    public ScaleQuestionForRacingResource() {
        mapper = ConfigMapper.getInstance();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Interceptors({InterceptorIsAdmin.class})
    public Response createScaleQuestionForRacing(@HeaderParam("user_code") String userCode,
            @PathParam("racing_id") Integer racingId,
            @PathParam("question_id") Integer questionId,
            String data) throws IOException {
        ScaleQuestionForRacing scaleQuestionForRacing = mapper.readValue(data, ScaleQuestionForRacing.class);
        Racing racing = rl.findRaingWithId(racingId);
        Question question = ql.findQuestion(questionId);
        if (question != null && racing != null) {
            scaleQuestionForRacing = sqrl.createScaleQuestionForRacing(racing, question, scaleQuestionForRacing);
            if (scaleQuestionForRacing != null) {
                String json = mapper.writeValueAsString(scaleQuestionForRacing);
                return Response.status(Response.Status.CREATED).entity(json).build();
            }
            return Response.status(Response.Status.CONFLICT).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

    @PUT
    @Path("{scale_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Interceptors({InterceptorIsAdmin.class})
    public Response updateScaleQuestionForRacing(@HeaderParam("user_code") String userCode,
            @PathParam("racing_id") Integer racingId,
            @PathParam("question_id") Integer questionId,
            @PathParam("scale_id") Integer scaleId,
            String data) throws IOException {
        ScaleQuestionForRacing scaleQuestionForRacing = mapper.readValue(data, ScaleQuestionForRacing.class);
        scaleQuestionForRacing.setId(scaleId);
        Racing racing = rl.findRaingWithId(racingId);
        Question question = ql.findQuestion(questionId);
        if (question != null && racing != null) {
            if (sqrl.updateScaleQuestionForRacing(racing, question, scaleQuestionForRacing)) {
                String json = mapper.writeValueAsString(scaleQuestionForRacing);
                return Response.status(Response.Status.OK).entity(json).build();
            }
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).build();
    }

    @DELETE
    @Path("{scale_id}")
    @Interceptors({InterceptorIsAdmin.class})
    public Response deleteScaleQuestionForRacing(@HeaderParam("user_code") String userCode,
            @PathParam("scale_id") Integer scaleId) {
        if (sqrl.deleteScaleQuestionForRacing(scaleId)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).build();
    }

}
