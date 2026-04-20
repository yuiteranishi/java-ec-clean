package com.example.ec.domain.user;

import java.util.Optional;

/** ビジネス用語で定義 */
public interface UserRepository
{
    // emailが一致するユーザーを返す
    Optional<User> findByEmail(String email);
    // emailが一致するユーザーが存在するか
    boolean existsByEmail(String email);
    // ユーザーを保存
    User save(User user);
}
