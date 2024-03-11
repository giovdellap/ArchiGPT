/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.interceptors;

import ir.shenakht.paint.logic.interfaces.JudgeUserLogicIntf;
import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;

/**
 *
 * @author hossien
 */
//It is always the second parameter
public class InterceptorJudgeUserCodeValidation {

    @EJB
    private JudgeUserLogicIntf ul;

    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {
        Object[] parameters = ctx.getParameters();
        String userCode = (String) parameters[0];
        if (userCode != null && ul.isJudgeUserCodeValid(userCode)) {
            if (ul.isJudgeUserLastLogin(userCode)) {
                return ctx.proceed();
            } else {
                return Response.status(Response.Status.GATEWAY_TIMEOUT).build();
            }
        } else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }
}
