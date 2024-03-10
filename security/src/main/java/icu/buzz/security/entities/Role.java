package icu.buzz.security.entities;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum Role {
    ADMIN(1, Set.of(Privilege.READ, Privilege.WRITE, Privilege.UPDATE, Privilege.DELETE), "admin"),
    USER(2, Set.of(Privilege.READ, Privilege.WRITE), "user"),
    ;

    @EnumValue
    private final int id;
    private final Set<Privilege> privileges;
    private final String desc;

    Role(int id, Set<Privilege> privileges, String desc) {
        this.id = id;
        this.privileges = privileges;
        this.desc = desc;
    }

    public List<GrantedAuthority> getAuthority() {
        return privileges
                .stream()
                .map((p) -> new SimpleGrantedAuthority(p.name()))
                .collect(Collectors.toList());
    }
}
