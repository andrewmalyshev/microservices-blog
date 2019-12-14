package com.gl.idp.users.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails extends User implements UserDetails {
    User user;
    public CustomUserDetails(final User users) {
        super(users.getId(),
                users.getFirstName(),
                users.getLastName(),
                users.getEmail(),
                users.getRoleId(),
                users.getPassword(),
                users.getToken(),
                users.getRoleByRoleId(),
                users.isEnabled()
/*                users.getBlogsById(),
                users.getLikesUnlikesById()*/);
        this.user = users;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_" + user.getRoleByRoleId().getName().toUpperCase());
    }

    @Override
    public String getUsername() {
        return super.getEmail();
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
        return super.isEnabled();
    }
}
