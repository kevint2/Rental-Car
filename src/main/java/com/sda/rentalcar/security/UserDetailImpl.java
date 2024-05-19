package com.sda.rentalcar.security;

import com.sda.rentalcar.entities.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailImpl implements UserDetails {
    private String username;
    private String password;
    private Boolean active;
    private List<GrantedAuthority>roles;
    public  UserDetailImpl(Employee user){
        this.username=user.getUsername();
        this.password=user.getPassword();
        this.active=user.getActive();
        this.roles=List.of(new SimpleGrantedAuthority(user.getPosition().getPosition()));
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
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
