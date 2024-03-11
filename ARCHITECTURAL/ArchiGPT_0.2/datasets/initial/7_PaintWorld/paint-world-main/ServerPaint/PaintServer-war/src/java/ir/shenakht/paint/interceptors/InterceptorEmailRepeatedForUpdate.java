/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.shenakht.paint.domain.JudgeUser;
import ir.shenakht.paint.logic.interfaces.JudgeUserLogicIntf;
import ir.shenakht.paint.util.ConfigMapper;
import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;

/**
 *
 * @author hossien
 */
public class InterceptorEmailRepeatedForUpdate {

    protected ObjectMapper mapper;

    public InterceptorEmailRepeatedForUpdate() {
        mapper = ConfigMapper.getInstance();
    }

    @EJB
    private JudgeUserLogicIntf ul;

    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {
        Object[] parameters = ctx.getParameters();
        String jsonUser = (String) parameters[1];
        String idS = (String) parameters[2];
        Integer id = Integer.parseInt(idS);
        JudgeUser user = mapper.readValue(jsonUser, JudgeUser.class);
        if (ul.isEmailNotRepeatedForUpdate(id, user.getEmail()) || user.getEmail() == null) {
            return ctx.proceed();
        } else {
            return Response.status(1003).build();
        }
    }

}
