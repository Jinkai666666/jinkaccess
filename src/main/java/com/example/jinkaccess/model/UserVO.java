package com.example.jinkaccess.model;

// UserVO 用来给前端返回安全的用户信息（不包含密码）
public class UserVO {
    private Long id;        // 用户 ID
    private String username; // 用户名
    private String role;     // 角色（USER / ADMIN）


    public UserVO(Long id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    // Getter 和 Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
