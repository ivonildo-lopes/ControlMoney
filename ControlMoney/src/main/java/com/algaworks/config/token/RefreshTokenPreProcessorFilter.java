package com.algaworks.config.token;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Map;


/**
 *  CLASSE COM PRIORIDADE ALTA
 *  Esse filtro funciona para tirar o refresh_token de forma explicita
 *  e coloca como filtro da requisição onde ninguem consegue vê qual o token
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenPreProcessorFilter implements Filter {


    /**
     * Só entrará nesse filtro quando a url requisitada for /oauth/token
     * e tiver como parametro refresh_token
     * e os cookies for diferente de null
     */

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        if("/oauth/token".equalsIgnoreCase(req.getRequestURI())
                && "refresh_token".equalsIgnoreCase(req.getParameter("grant_type"))
                && req.getCookies() != null) {


            for (Cookie cookie: req.getCookies()) {
                if(cookie.getName().equalsIgnoreCase("refreshToken")) {
                    String refreshToken = cookie.getValue();
                    System.out.println(refreshToken);
                    req = new MyServletRequestWrapper(req,refreshToken);
                }
            }
        }

        chain.doFilter(req,response);
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void destroy() { }

    static class MyServletRequestWrapper extends HttpServletRequestWrapper {

        private String refreshToken;

        public MyServletRequestWrapper(HttpServletRequest request, String refreshToken){
            super(request);
            this.refreshToken = refreshToken;
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            ParameterMap<String, String[]> map = new ParameterMap<>(getRequest().getParameterMap());
            map.put("refresh_token", new String[] {refreshToken});
            map.setLocked(true);
            return map;
        }
    }
}
