package az.unitech.service.impl;

import az.unitech.dao.entity.UserDetailsEntity;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class UserDetailsService implements UserDetails {

    private String pin;
    private String password;
    private Collection<GrantedAuthority> roles;

    public UserDetailsService(UserDetailsEntity userDetails) {
        this.pin = userDetails.getPin();
        this.password = userDetails.getPassword();
        this.roles = new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return pin;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
