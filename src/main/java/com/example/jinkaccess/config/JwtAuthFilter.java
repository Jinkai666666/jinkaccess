package com.example.jinkaccess.config;

import com.example.jinkaccess.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
// JwtAuthFilter 继承 OncePerRequestFilter，保证每个请求只执行一次
public class JwtAuthFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)throws  ServletException,IOException{

        //  从请求头里获取 Authorization 字段
        String authHeader = request.getHeader("Authorization");

        String username =null;
        String token =null ;
        //  检查 Authorization 是否存在且以 "Bearer " 开头
        if(authHeader!=null && authHeader.startsWith("Bearer"))
            token = authHeader.substring(7);
            try{
                //解析 Token，获取用户名
                username = JwtUtil.getUsername(token);
            }catch (Exception e){
                System.out.println("JWT 验证失败: "+e.getMessage());
            }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 创建认证对象（用户名 + 空权限列表）
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username, null, null);

            // 绑定请求的详细信息
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 把认证对象放到 Spring Security 的上下文中
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }



        //  放行请求
        filterChain.doFilter(request,response);
    }

}
