package icu.buzz.security.entities;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class JwtUser extends User implements UserDetails {
    public JwtUser() {
    }

    public JwtUser(User user) {
        super(user.getUid(), user.getUsername(), user.getPassword(), user.getEnabled(), user.getRole());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role role = super.getRole();
        List<GrantedAuthority> authority = role.getAuthority();
        authority.add((GrantedAuthority) () -> "ROLE_" + role.name());
        return authority;
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
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
        return super.getEnabled();
    }
}
