package com.finalPrj.reggie.filter;


import com.alibaba.fastjson.JSON;
import com.finalPrj.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//检查用户是都已经完成登录
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    //路径匹配器，支持通配符
    public static final AntPathMatcher PathMatcher = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //1.获取本次请求URI
        String requestURI = request.getRequestURI(); // /backend/index.html
        log.info("Intercepted: {}", requestURI);

        //定义不需要处理的请求路径
        String[] urls = new String[] {
          "/employee/login",
          "/employee/logout",
          "/backend/**",
        };
        //2.判断本次请求是否需要处理
        boolean check = check (urls, requestURI);

        //3.如果不需要处理，则直接放行
        if(check) {
            log.info("This Request {} doesn't need to be processed", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        //4.判断登陆状态，如果已登录，则直接放行
        if (request.getSession().getAttribute("employee") != null) {
            log.info("The user in logged in, userid: {}", request.getSession().getAttribute("employee"));
            filterChain.doFilter(request, response);
            return;
        }

        log.info("The user is not logged in");
        //5.如果未登录则返回未登录，通过输出流的方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }
    /**
     * 路径匹配，检查本次请求是否需要放行
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PathMatcher.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
