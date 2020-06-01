package pl.sgnit.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sgnit.ims.model.Role;
import pl.sgnit.ims.model.User;
import pl.sgnit.ims.repository.RoleRepository;
import pl.sgnit.ims.repository.UserRepository;
import pl.sgnit.ims.util.*;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final String START_PASSWORD = "123456789";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PasswordChecker passwordChecker;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, PasswordChecker passwordChecker) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordChecker = passwordChecker;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    public String saveUser(User user) {
        if (userExists(user.getUserName())) {
            return "Użytkownik o nazwie " + user.getUserName() + " już istnieje";
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return "Istnieje już użytkownik o adresie email - " + user.getEmail();
        }

        Role role;

        setUserRole(user);
        user.setPassword(passwordEncoder.encode(START_PASSWORD));
        userRepository.save(user);
        return "OK";
    }

    public String updateUser(User user) {
        User tmpUser = userRepository.findByUserNameAndIdNot(user.getUserName(), user.getId());
        if (tmpUser != null) {
            return "Użytkownik o nazwie " + user.getUserName() + " już istnieje";
        }
        tmpUser = userRepository.findByEmailAndIdNot(user.getEmail(), user.getId());
        if (tmpUser != null) {
            return "Istnieje już użytkownik o adresie email - " + user.getEmail();
        }
        tmpUser = userRepository.findById(user.getId()).get();
        user.setPassword(tmpUser.getPassword());
        setUserRole(user);
        userRepository.save(user);
        return "OK";
    }

    public void changePassword(Long id, String newPassword) {
        User user = userRepository.findById(id).get();

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public boolean userExists(String userName) {
        User user = userRepository.findByUserName(userName);

        return user != null;
    }

    public boolean hasUserStartPassword(User user) {
        return passwordEncoder.matches(START_PASSWORD, user.getPassword());
    }

    public String setPassword(Long userId, NewPassword newPassword) {
        if (!newPassword.getNewPassword1().equals(newPassword.getNewPassword2())) {
            return "Wpisane hasła są różne";
        }

        String passwordMessage = passwordChecker.checkPassword(newPassword.getNewPassword1());

        if (!"OK".equals(passwordMessage)) {
            return passwordMessage;
        }

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            user.get().setPassword(passwordEncoder.encode(newPassword.getNewPassword1()));
            userRepository.save(user.get());
            return "OK";
        }
        return "Błąd podczas zmiany hasła";
    }

    private void setUserRole(User user) {
        Role role;

        user.getRoles().clear();
        if (user.getAdministrator()) {
            role = roleRepository.findByName("administrator");
        } else {
            role = roleRepository.findByName("all_modules");
        }
        user.getRoles().add(role);
    }
}
