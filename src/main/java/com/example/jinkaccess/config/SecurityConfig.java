package com.example.jinkaccess.config;
import com.example.jinkaccess.config.JwtAuthFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // 声明这是一个配置类
public class SecurityConfig {

    // 定义 PasswordEncoder Bean，项目里任何地方都能注入
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //  SecurityFilterChain，配置 Spring Security 规则
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 关闭 CSRF（前后端分离常用）
                .authorizeHttpRequests(auth -> auth
                        // 允许匿名访问的接口
                        .requestMatchers("/api/user/register", "/api/user/login").permitAll()
                        // 管理员接口只能 ADMIN 角色访问
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        // 其他接口都需要认证
                        .anyRequest().authenticated()
                )
                // 在 UsernamePasswordAuthenticationFilter 之前加入我们自定义的 JwtAuthFilter
                .addFilterBefore(new JwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
