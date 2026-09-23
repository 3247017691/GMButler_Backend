package com.itheima.interceptor;

import cn.hutool.core.util.StrUtil;
import com.itheima.context.UserContext;
import com.itheima.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    public LoginInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        UserContext.clear();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle....");
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String jwt = request.getHeader("token");

        if (!StrUtil.hasLetter(jwt)){
            log.info("获取到jwt令牌为空, 返回错误结果");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        try {
            Claims claims = jwtUtil.parseToken(jwt);

            Integer id = claims.get("id", Integer.class);
            String username = claims.get("username", String.class);
            String roleLabel = claims.get("roleLabel", String.class);
            UserContext.set(new UserContext.CurrentUser(id, username, roleLabel));
        } catch (Exception e) {
            log.info("jwt令牌解析异常, 返回错误结果");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        log.info("jwt令牌解析成功, 放行请求");
        return true;
    }
}
