package com.example.ec.domain.user;

import java.time.LocalDateTime;

/** ビジネスルールを持つ */
public class User {
    private Long id;
    private String email;
    private String passwordHash;
    private UserRole role;
    private String displayName;
    private Boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User()
    {
        this.role = UserRole.USER;
        this.enabled = true;
    }

    public User(
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
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getEmail(){return email;} public void setEmail(String e){this.email=e;}
    public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String h){this.passwordHash=h;}
    public UserRole getRole(){return role;} public void setRole(UserRole r){this.role=r;}
    public String getDisplayName(){return displayName;} public void setDisplayName(String d){this.displayName=d;}
    public Boolean getEnabled(){return enabled;} public void setEnabled(Boolean e){this.enabled=e;}
    public LocalDateTime getCreatedAt(){return createdAt;}
    public LocalDateTime getUpdatedAt(){return updatedAt;}
}
