package com.example.ec.infrastructure.user;

import com.example.ec.domain.user.User;
import com.example.ec.domain.user.UserRole;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/** DBテーブルとマッピング */
@Entity
@Table(name="users")
public class UserJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private UserRole role = UserRole.USER;

    private String displayName;
    private Boolean enabled = true;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    protected UserJpaEntity() {}

    private UserJpaEntity(
            Long id,
            String email,
            String passwordHash,
            UserRole role,
            String displayName,
            Boolean enabled,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.displayName = displayName;
        this.enabled = enabled;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @PrePersist void onCreate(){ createdAt = updatedAt = LocalDateTime.now(); }
    @PreUpdate  void onUpdate(){ updatedAt = LocalDateTime.now(); }

    /** JPA Entity → Domain Entity */
    public User toDomain()
    {
        return new User(
                id,
                email,
                passwordHash,
                role,
                displayName,
                enabled,
                createdAt,
                updatedAt
        );
    }

    /** Domain Entity → JPA Entity */
    public static UserJpaEntity fromDomain(User u)
    {
        return new UserJpaEntity(
                u.getId(),
                u.getEmail(),
                u.getPasswordHash(),
                u.getRole(),
                u.getDisplayName(),
                u.getEnabled(),
                u.getCreatedAt(),
                u.getUpdatedAt()
        );
    }
}
