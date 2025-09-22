package com.example.jinkaccess.repository;

import com.example.jinkaccess.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // 只启动 JPA 相关的组件，专门用于测试数据库
    @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testInsertAndFindUser() {
        // 1. 创建用户对象
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("123456");
        user.setRole("ADMIN");

        // 2. 保存到数据库
        userRepository.save(user);

        // 3. 查询刚插入的用户
        User found = userRepository.findByUsername("testuser").orElse(null);

        // 4. 断言验证（确保查出来的用户和存进去的匹配）
        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo("testuser");
        assertThat(found.getRole()).isEqualTo("ADMIN");

        System.out.println("✅ 测试通过，用户已成功插入并查询出来");
    }
}
