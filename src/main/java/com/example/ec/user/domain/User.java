package com.example.ec.user.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name="users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @PrePersist void onCreate(){ createdAt = updatedAt = LocalDateTime.now(); }
    @PreUpdate  void onUpdate(){ updatedAt = LocalDateTime.now(); }

    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getEmail(){return email;} public void setEmail(String e){this.email=e;}
    public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String h){this.passwordHash=h;}
    public UserRole getRole(){return role;} public void setRole(UserRole r){this.role=r;}
    public String getDisplayName(){return displayName;} public void setDisplayName(String d){this.displayName=d;}
    public Boolean getEnabled(){return enabled;} public void setEnabled(Boolean e){this.enabled=e;}
    public LocalDateTime getCreatedAt(){return createdAt;}
    public LocalDateTime getUpdatedAt(){return updatedAt;}
}
