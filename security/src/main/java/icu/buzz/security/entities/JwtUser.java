package icu.buzz.security.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
public class JwtUser implements UserDetails {
    private UserBo user;
    private Collection<GrantedAuthority> authorities;
    private LocalDateTime unlockedTime;

    public JwtUser() {
        this.unlockedTime = LocalDateTime.now();
    }


    public JwtUser(UserBo user) {
        this.user = user;
        this.authorities = user.getRole().getAuthority();
        authorities.add((GrantedAuthority) () -> "ROLE_" + user.getRole().name());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return LocalDateTime.now().isAfter(user.getUnlockedTime());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }
}
