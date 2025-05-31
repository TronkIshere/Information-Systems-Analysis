package com.tronk.analysis.configuration;

import com.tronk.analysis.entity.Cashier;
import com.tronk.analysis.entity.Lecturer;
import com.tronk.analysis.entity.Student;
import com.tronk.analysis.entity.common.User;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPrincipal implements UserDetails {
    UUID id;
    String name;
    String loginName;
    String password;
    String userType;
    Collection<? extends GrantedAuthority> authorities;
    Map<String, Object> attributes;

    public static UserPrincipal create(User user) {
        return new UserPrincipal(
                user.getId(),
                user.getName(),
                user.getLoginName(),
                user.getPassword(),
                determineUserType(user),
                parseAuthorities(user.getRoles()),
                null
        );
    }

    private static String determineUserType(User user) {
        if (user instanceof Student) return "STUDENT";
        if (user instanceof Lecturer) return "LECTURER";
        if (user instanceof Cashier) return "CASHIER";
        return "UNKNOWN";
    }

    private static Collection<GrantedAuthority> parseAuthorities(String roles) {
        if (!StringUtils.hasText(roles)) return Collections.emptyList();
        return Arrays.stream(roles.split(","))
                .map(String::trim)
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getUsername() {
        return this.loginName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
