package com.tronk.analysis.configuration;

import com.tronk.analysis.entity.Role;
import com.tronk.analysis.entity.User;
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
    String email;
    String password;
    String principalName;
    Collection<Role> roles = new HashSet<>();
    Map<String, Object> attributes;

    public UserPrincipal(UUID id, String email, String name, String password, Collection<Role> roles, String principalName) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.roles = roles;
        this.principalName = principalName;
    }

    public static UserPrincipal create(User user) {
        return new UserPrincipal(
                user.getId(),
                user.getLoginName(),
                user.getName(),
                user.getPassword(),
                user.getRoles(),
                determinePrincipalName(user)
        );
    }

    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        userPrincipal.setAttributes(attributes);

        if (attributes != null && attributes.containsKey("name")) {
            userPrincipal.setName((String) attributes.get("name"));
        }

        return userPrincipal;
    }

    private static String determinePrincipalName(User user) {
        if (StringUtils.hasText(user.getLoginName())) {
            return user.getLoginName();
        }
        throw new IllegalArgumentException("Cannot determine principal name - user has neither email nor any auth provider");
    }

    @Override
    public String toString() {
        return "UserPrincipal{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", attributes=" + attributes +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return this.email;
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
