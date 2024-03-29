package br.com.zrteam.managementapp.presentation.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class JwtAuthenticationEntrypoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest req,
                         HttpServletResponse res,
                         AuthenticationException authException) throws IOException {
        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject obj = new JSONObject();

        try {
            obj.accumulate("timestamp", new Date().getTime());
            obj.accumulate("status", HttpServletResponse.SC_UNAUTHORIZED);
            obj.accumulate("title", "Acesso Negado!");
            obj.accumulate("message", "Você não possui autorização para acessar esse recurso!");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        res.getWriter().write(obj.toString());
    }

}
