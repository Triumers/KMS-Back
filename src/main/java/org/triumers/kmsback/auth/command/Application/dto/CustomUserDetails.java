package org.triumers.kmsback.auth.command.Application.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.triumers.kmsback.auth.command.domain.aggregate.entity.Auth;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {// Git 충돌 복구용 주석

    private final Auth user;

    public CustomUserDetails(Auth auth) {
        this.user = auth;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getUserRole().name();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    public String getName() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {

        return user.getEndDate() == null;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getEndDate() == null;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getEndDate() == null;
    }
}
