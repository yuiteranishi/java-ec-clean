package com.example.ec.infrastructure.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/** 技術的な操作を行う */
public interface JpaUserRepository extends JpaRepository<UserJpaEntity, Long>
{
    /**
     * UserRepositoryImpl@findByEmail から呼ばれる
     */
    Optional<UserJpaEntity> findByEmail(String email);

    /**
     * UserRepositoryImpl@existsByEmail から呼ばれる
     */
    boolean existsByEmail(String email);
}
