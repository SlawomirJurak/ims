package pl.sgnit.ims.restcontroller;

import org.springframework.web.bind.annotation.*;
import pl.sgnit.ims.model.User;
import pl.sgnit.ims.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/radd")
    public String addUser(@RequestBody User user) {

        return userService.saveUser(user);
    }

    @PostMapping("/redit")
    public String editUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @GetMapping("/rall")
    public List<User> userList() {
        return userService.findAll();
    }

    @GetMapping("/rget/{id}")
    public User getUser(@PathVariable Long id) {
        User user = userService.findById(id).get();
        user.setPassword("");
        return user;
    }

    @DeleteMapping("/rremove/{id}")
    public String removeUser(@PathVariable Long id) {
        userService.removeUser(id);
        return "{\"state\": \"OK\"}";
    }

    @PostMapping("/changePassword/{id}/{pass}")
    public String changePassword(@PathVariable Long id, @PathVariable(name = "pass") String newPassword) {
        userService.changePassword(id, newPassword);
        return "{\"state\": \"OK\"}";
    }
}
