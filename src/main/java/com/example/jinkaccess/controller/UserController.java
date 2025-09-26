package com.example.jinkaccess.controller;

import com.example.jinkaccess.model.User;
import com.example.jinkaccess.model.UserVO;
import com.example.jinkaccess.repository.UserRepository;
import com.example.jinkaccess.service.UserService;
import com.example.jinkaccess.util.JwtUtil;
import com.example.jinkaccess.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // 构造函数注入依赖
    public UserController(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 注册接口
    @PostMapping("/register")
    public Result<User> register(@RequestBody User user) {
        User saveUser = userService.saveUser(user);
        return Result.success("注册成功", saveUser);
    }

    // 登录接口，登录成功返回统一结果（含 token）
    @PostMapping("/login")
    public Result<String> login(@RequestBody User loginUser) {
        // 1. 根据用户名查询用户（Optional 处理）
        Optional<User> dbUserOpt = userRepository.findByUsername(loginUser.getUsername());
        if (dbUserOpt.isEmpty()) {
            return Result.error(400, "用户不存在");
        }
        User dbUser = dbUserOpt.get();

        // 2. 校验密码
        if (!passwordEncoder.matches(loginUser.getPassword(), dbUser.getPassword())) {
            return Result.error(401, "密码错误");
        }

        // 3. 登录成功，生成 JWT token（包含用户名和角色）
        String token = jwtUtil.generateToken(dbUser.getUsername(),dbUser.getRole());

        // 4. 返回统一格式
        return Result.success("登录成功", token);
    }

    // 获取当前登录用户信息
    @GetMapping("/info")
    public Result<UserVO> getUserInfo(Authentication authentication) {
        // 从认证信息里拿到当前用户名
        String username = authentication.getName();

        // 根据用户名查数据库
        User user = userRepository.findByUsername(username)
                .orElseThrow((->new RuntimeException("用户不存在")));

        // 转换成 UserVO（只保留安全字段）
        UserVO userVO = new UserVO(user.getId(), user.getUsername(),user.getRole());

        return Result.success(userVO);
    }

}
