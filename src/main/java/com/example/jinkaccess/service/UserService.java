package com.example.jinkaccess.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.jinkaccess.model.User;
import com.example.jinkaccess.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    //加密
    private  final PasswordEncoder passwordEncoder= new BCryptPasswordEncoder() ;

    //定义 UserRepository用来操作数据库
    private final UserRepository userRepository;

    //进行服务类时拿到数据库user实体
    public UserService(UserRepository userRepository){
        // 把传进来的 repository 保存到成员变量里
        this.userRepository = userRepository;
    }


    /*************
      保存用户到数据库
      @param user 传入的用户实体
      @return 保存后的用户对象（包含数据库生成的 id 等）
    */
    public User saveUser(User user){
        //对传入的明文密码进行加密
        String encondedPssword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encondedPssword);
        return userRepository.save(user);
    }
    /*****************
     * 根据用户名查询用户
     * @param username 用户名
     * @return 查询到的 User 对象，如果没有则返回 null
     */
    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElse(null);
    }


    /**
     * 校验用户输入的密码是否正确
     *
     * @param rawPassword     用户输入的明文密码（例如 "123456"）
     * @param encodedPassword 数据库里存储的加密后的密码（BCrypt 哈希串）
     * @return true 表示密码正确，false 表示密码错误
     */
    public boolean passwordMatches(String rawPassword, String encodedPassword) {
        // 使用 BCrypt 的 matches 方法进行校验
        //  Spring Security 内部会把 rawPassword 再次加密
        //  然后和 encodedPassword（数据库里的密文）做比对
        //  如果一致，返回 true；否则返回 false。
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
