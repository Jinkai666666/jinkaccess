package com.example.jinkaccess.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity // 实体类
@Table(name = "users") // 表名
@Data // 自动生成 getter/setter 等
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增
    private Long id;

    @Column(nullable = false, unique = true, length = 50) // 用户名，唯一，不能为空
    private String username;

    @Column(nullable = false, length = 100) // 密码（加密后存储）
    private String password;

    @Column(nullable = false, length = 20) // 角色（ADMIN / USER）
    private String role;

    @Column(nullable = false, updatable = false) // 创建时间
    private java.time.LocalDateTime createdAt;

    @Column(nullable = false) // 更新时间
    private java.time.LocalDateTime updatedAt;

    @PrePersist // 插入前自动设置时间
    protected void onCreate() {
        this.createdAt = java.time.LocalDateTime.now();
        this.updatedAt = java.time.LocalDateTime.now();
    }

    @PreUpdate // 更新前自动刷新时间
    protected void onUpdate() {
        this.updatedAt = java.time.LocalDateTime.now();
    }
}
