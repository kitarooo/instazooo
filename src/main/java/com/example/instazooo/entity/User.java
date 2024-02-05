package com.example.instazooo.entity;

import com.example.instazooo.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(unique = true, updatable = false)
    String username;

    @Column(nullable = false)
    String lastname;

    @Column(unique = true)
    String email;

    @Column(columnDefinition = "text")
    String bio;

    @Column(length = 3000)
    String password;

    @ElementCollection(targetClass = Role.class)
    @CollectionTable(name = "user_role",
    joinColumns = @JoinColumn("user_id"))
    Set<Role> role = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "user", orphanRemoval = true)
    List<Post> posts;

    @JsonFormat(pattern = "yyyy-MM-dd HH-mm:ss")
    @Column(updatable = false)
    LocalDateTime createdDate;

    @Transient
    Collection<? extends GrantedAuthority> authorities;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }
}
