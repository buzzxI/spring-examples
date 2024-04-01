package icu.buzz.security.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserBo extends User {
    private LocalDateTime unlockedTime;

    public UserBo() {

    }

    public UserBo(User user) {
        super(user.getUid(), user.getUsername(), user.getPassword(), user.getEnabled(), user.getRole());
        this.unlockedTime = LocalDateTime.now();
    }
}
