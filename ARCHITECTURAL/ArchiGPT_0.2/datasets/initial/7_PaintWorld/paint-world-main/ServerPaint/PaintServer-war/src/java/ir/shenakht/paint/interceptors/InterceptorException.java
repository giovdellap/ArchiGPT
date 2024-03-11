/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.interceptors;

import ir.shenakht.paint.util.UnAuthorizedAcsessException;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;

/**
 *
 * @author Mohammad Kazemifard
 */
public class InterceptorException {

    @AroundInvoke
    public Object intercept(InvocationContext ctx) {
        try {
            return ctx.proceed();
        } catch (UnAuthorizedAcsessException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("InterceptorException").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
}
