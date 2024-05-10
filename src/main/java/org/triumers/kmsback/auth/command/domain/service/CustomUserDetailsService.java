package org.triumers.kmsback.auth.command.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.triumers.kmsback.auth.command.Application.dto.CustomUserDetails;
import org.triumers.kmsback.auth.command.domain.aggregate.entity.Auth;
import org.triumers.kmsback.auth.command.domain.repository.AuthRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {// Git 충돌 복구용 주석

    private final AuthRepository userRepository;

    @Autowired
    public CustomUserDetailsService(AuthRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Auth userData = userRepository.findByEmail(email);

        if (userData != null) {

            return new CustomUserDetails(userData);
        }

        throw new UsernameNotFoundException("존재하지 않는 유저입니다.");
    }
}
