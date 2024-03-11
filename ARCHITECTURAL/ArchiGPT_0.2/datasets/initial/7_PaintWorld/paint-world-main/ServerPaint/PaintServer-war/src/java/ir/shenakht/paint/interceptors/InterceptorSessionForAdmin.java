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
public class InterceptorSessionForAdmin {

    @EJB
    private JudgeUserLogicIntf ul;

    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {
        Object[] parameter = ctx.getParameters();
        String userCode = (String) parameter[0];
        if (ul.isAdmin(userCode)) {
            if (ul.isAdminLogin(userCode)) {
                return ctx.proceed();
            } else {
                return Response.status(Response.Status.GATEWAY_TIMEOUT).build();
            }
        } else {
            return ctx.proceed();
        }
    }
}
