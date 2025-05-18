package com.tronk.analysis.service.impl;

import com.tronk.analysis.configuration.UserPrincipal;
import com.tronk.analysis.entity.User;
import com.tronk.analysis.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceCustomizer implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserPrincipal loadUserByUsername(String loginName) throws UsernameNotFoundException {
        User user = userRepository.findByLoginName(loginName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return UserPrincipal.create(user);
    }
}
