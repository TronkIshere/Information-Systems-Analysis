package com.tronk.analysis.entity;

import com.tronk.analysis.entity.common.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

@Entity
@Table(name = "app_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends AbstractEntity<UUID> {
    String name;
    String email;
    String phoneNumber;
    String status;
    String loginName;
    String password;
    LocalDate birthDay;
    boolean gender;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    Collection<Role> roles = new HashSet<>();

    @Column(name = "refresh_token", columnDefinition = "TEXT")
    String refreshToken;

    @OneToOne(mappedBy = "app_user", cascade = CascadeType.ALL, orphanRemoval = true)
    Student student;

    @OneToOne(mappedBy = "app_user", cascade = CascadeType.ALL, orphanRemoval = true)
    Lecturer lecturer;

}


