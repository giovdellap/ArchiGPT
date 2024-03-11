/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.shenakht.paint.domain.JudgeUser;
import ir.shenakht.paint.util.ConfigMapper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;

/**
 *
 * @author hossien
 */
public class InterceptorEmailValidation {

    protected ObjectMapper mapper;

    private final static String email_pattren = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public InterceptorEmailValidation() {
        mapper = ConfigMapper.getInstance();
    }

    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {
        Object[] parameters = ctx.getParameters();
        Pattern p = Pattern.compile(email_pattren);
        String jsonUser = (String) parameters[1];
        JudgeUser user = mapper.readValue(jsonUser, JudgeUser.class);
        if (user.getEmail() != null) {
            Matcher m = p.matcher(user.getEmail());
            if (m.matches()) {
                return ctx.proceed();
            } else {
                return Response.status(1004).build();
            }
        } else {
            return ctx.proceed();
        }
    }
}
