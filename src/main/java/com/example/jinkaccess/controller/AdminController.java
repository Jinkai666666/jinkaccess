package com.example.jinkaccess.controller;

import com.example.jinkaccess.model.User;
import com.example.jinkaccess.model.UserVO;
import com.example.jinkaccess.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired UserRepository userRepository;

    @GetMapping("/hello")
    public String helloAdmin() {
        return "管理员接口";
    }

    @GetMapping("/users")
    public List<UserVO> getAllUsers() {
        //咨询所有用户
        List<User> users = userRepository.findAll();

        // 转换成 UserVO 列表（只保留 id、username、role）
        return users.stream()
                .map(user -> new UserVO(user.getId(), user.getUsername(), user.getRole()))
                .toList();
    }

}
