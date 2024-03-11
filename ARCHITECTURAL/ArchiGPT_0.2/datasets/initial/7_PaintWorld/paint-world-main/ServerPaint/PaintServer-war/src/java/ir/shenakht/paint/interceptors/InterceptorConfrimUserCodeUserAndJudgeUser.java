/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.shenakht.paint.domain.JudgeUser;
import ir.shenakht.paint.domain.User;
import ir.shenakht.paint.logic.interfaces.JudgeUserLogicIntf;
import ir.shenakht.paint.logic.interfaces.UserLogicIntf;
import ir.shenakht.paint.util.ConfigMapper;
import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;

/**
 *
 * @author hossien
 */
public class InterceptorConfrimUserCodeUserAndJudgeUser {

    protected ObjectMapper mapper;

    @EJB
    private UserLogicIntf ul;

    @EJB
    private JudgeUserLogicIntf jul;

    public InterceptorConfrimUserCodeUserAndJudgeUser() {
        mapper = ConfigMapper.getInstance();
    }

    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {

        Object[] paramaters = ctx.getParameters();
        String userCode = (String) paramaters[0];
        User user = ul.findUserWithUserCode(userCode);
        JudgeUser judgeUser = jul.findJudgeUserByUserCode(userCode);
        if (user != null || judgeUser != null) {
            return ctx.proceed();
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }

}
