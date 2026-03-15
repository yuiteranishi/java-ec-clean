package com.example.ec.infrastructure.user;

import com.example.ec.domain.user.User;
import com.example.ec.domain.user.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository と JpaUserRepository の橋渡し
 * */
@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository jpa;

    public UserRepositoryImpl(JpaUserRepository jpa)
    {
        this.jpa = jpa;
    }

    @Override
    public Optional<User> findByEmail(String email)
    {
        return jpa.findByEmail(email).map(UserJpaEntity::toDomain);
    }

    @Override
    public boolean existsByEmail(String email)
    {
        return jpa.existsByEmail(email);
    }

    @Override
    public User save(User user)
    {
        UserJpaEntity entity = UserJpaEntity.fromDomain(user);
        UserJpaEntity saved = jpa.save(entity);

        return saved.toDomain();
    }
}
