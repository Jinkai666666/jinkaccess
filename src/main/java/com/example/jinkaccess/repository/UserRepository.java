package com.example.jinkaccess.repository;
import com.example.jinkaccess.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>{

    Optional<User> findByUsername(String username);
}
