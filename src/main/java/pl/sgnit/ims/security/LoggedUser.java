package pl.sgnit.ims.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class LoggedUser extends User {

    private pl.sgnit.ims.model.User user;

    public LoggedUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
                      pl.sgnit.ims.model.User user) {
        super(username, password, authorities);
        this.user = user;
    }

    public pl.sgnit.ims.model.User getUser() {
        return user;
    }
}
