package com.tronk.analysis.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class User extends AbstractEntity<UUID> {
    String name;
    String email;
    String phoneNumber;
    String status;
    String loginName;
    String password;
    LocalDate birthDay;
    boolean gender;
    String roles;

    @Column(name = "refresh_token", columnDefinition = "TEXT")
    String refreshToken;
}


