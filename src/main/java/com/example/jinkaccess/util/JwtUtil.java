package com.example.jinkaccess.util;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


import org.springframework.stereotype.Component;

import java.util.Date;

import java.util.Date;

import javax.xml.crypto.Data;
@Component
public class JwtUtil {

    //JWT密钥 用于生成.验证token
    private static final String SECRET = "JINKSJWTTOKENSHENGCHENGJIEXIMIYAOYONGYUKAIFACESHI";
    // Token 有效期
    private static final long EXPIRATION_TIME = 1000*60*60*24;


    // 根据用户名生成 JWT Token
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // 设置主题，用户名
                .setIssuedAt(new Date()) // 签发时间（当前时间）
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 过期时间
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS256) // 签名算法和密钥
                .compact(); // 生成最终的 Token 字符串
    }


    //从Token中解析出用户名
    public static String getUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))// 设置签名密钥
                .build()
                .parseClaimsJws(token) // 解析 token
                .getBody()
                .getSubject();// 取出 setSubject(username) 存进去的用户名
    }


}
