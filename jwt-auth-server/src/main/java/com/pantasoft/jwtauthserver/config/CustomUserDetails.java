package com.pantasoft.jwtauthserver.config;

import com.pantasoft.jwtauthserver.repositories.entities.RoleEntity;
import com.pantasoft.jwtauthserver.repositories.entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Provides a basic implementation of the UserDetails interface
 */
public class CustomUserDetails implements UserDetails {

    private Collection<? extends GrantedAuthority> authorities;
    private String password;
    private String username;

    public CustomUserDetails(UserEntity user) {

        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = translate(user.getRoles());
    }

    /**
     * Translates the List<Role> to a List<GrantedAuthority>
     *
     * @param roles the input list of roles.
     * @return a list of granted authorities
     */
    private Collection<? extends GrantedAuthority> translate(List<RoleEntity> roles) {

        var grantedAuthorities = new ArrayList<GrantedAuthority>();

        roles.forEach(role -> {
            String name = role.getName().toUpperCase();
            //Make sure that all roles start with "ROLE_"
            if (!name.startsWith("ROLE_"))
                name = "ROLE_" + name;
            grantedAuthorities.add(new SimpleGrantedAuthority(name));
        });

        return grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
