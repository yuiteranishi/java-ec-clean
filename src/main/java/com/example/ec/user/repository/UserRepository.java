package com.example.ec.user.repository;

import com.example.ec.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * ユーザーの検索・存在確認を行う JPA リポジトリ
 * - Spring Data の命名規約で existsByEmail / findByEmail が自動実装される
 * */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
