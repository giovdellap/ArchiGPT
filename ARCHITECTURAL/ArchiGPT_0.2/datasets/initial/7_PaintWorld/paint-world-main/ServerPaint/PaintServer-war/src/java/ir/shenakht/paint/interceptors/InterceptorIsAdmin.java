/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.interceptors;

import ir.shenakht.paint.logic.interfaces.JudgeUserLogicIntf;
import ir.shenakht.paint.util.UnAuthorizedAcsessException;
import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;

/**
 *
 *
 * @author hossien
 */
public class InterceptorIsAdmin {

    @EJB
    private JudgeUserLogicIntf ul;

    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws UnAuthorizedAcsessException, Exception {
        Object[] parameters = ctx.getParameters();
        String userCode = (String) parameters[0];
        boolean flag = ul.isAdmin(userCode);
        if (flag) {
            return ctx.proceed();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity("InterceptorIsAdmin").build();
    }

}
