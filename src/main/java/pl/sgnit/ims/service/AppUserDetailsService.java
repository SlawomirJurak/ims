package pl.sgnit.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.sgnit.ims.model.User;
import pl.sgnit.ims.repository.UserRepository;
import pl.sgnit.ims.security.LoggedUser;

import java.util.HashSet;
import java.util.Set;

public class AppUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserNameAndEnabledIsTrue(userName);
        if (user == null) {
            throw new UsernameNotFoundException(userName);
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        user.getRoles().forEach(role ->
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getName())));
        return new LoggedUser(user.getUserName(), user.getPassword(), grantedAuthorities, user);
    }
}
