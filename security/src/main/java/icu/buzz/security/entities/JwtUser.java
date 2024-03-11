package icu.buzz.security.entities;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class JwtUser implements UserDetails {

    private String uid;
    private String username;
    private String password;
    private Role role;
    private boolean enable;

    public JwtUser() {
    }

    public JwtUser(User user) {
        this.uid = user.getUid();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.enable = user.getEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authority = role.getAuthority();
        authority.add((GrantedAuthority) () -> "ROLE_" + role.name());
        return authority;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
        return this.enable;
    }
}
