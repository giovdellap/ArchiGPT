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
public class InterceptorJudgeUsernameValidation {

    protected ObjectMapper mapper;

    private final static String user_pattren = "\\w{6,}";

    public InterceptorJudgeUsernameValidation() {
        mapper = ConfigMapper.getInstance();
    }

    @AroundInvoke
    public Object intercept(InvocationContext ctx) throws Exception {
        Object[] parameters = ctx.getParameters();
        Pattern p = Pattern.compile(user_pattren);
        String jsonUser = (String) parameters[1];
        JudgeUser user = mapper.readValue(jsonUser, JudgeUser.class);
        if (user.getUsername() != null) {
            Matcher m = p.matcher(user.getUsername());
            if (m.matches()) {
                return ctx.proceed();
            } else {
                return Response.status(1002).build();
            }
        } else {
            return ctx.proceed();
        }
    }
}
