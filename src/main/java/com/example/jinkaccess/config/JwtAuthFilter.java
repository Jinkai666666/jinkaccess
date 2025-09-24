package com.example.jinkaccess.config;

import com.example.jinkaccess.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

// JwtAuthFilter 继承 OncePerRequestFilter，保证每个请求只执行一次
public class JwtAuthFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 从请求头里获取 Authorization 字段
        String authHeader = request.getHeader("Authorization");

        String username = null;
        String role = null;
        String token = null;

        //  检查 Authorization 是否存在且以 "Bearer " 开头
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // 去掉 "Bearer "
            try {
                // 3. 解析 Token，获取用户名和角色
                username = JwtUtil.getUsername(token);
                role = JwtUtil.getUserRole(token);
            } catch (Exception e) {
                System.out.println("JWT 验证失败: " + e.getMessage());
            }
        }

        //  如果用户名不为空，并且上下文中还没有认证信息
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

            //  创建认证对象（用户名 + 角色）
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username, null, List.of(authority));

            // 绑定请求的详细信息
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 把认证对象放到 Spring Security 的上下文中
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        //  放行请求
        filterChain.doFilter(request, response);
    }
}
