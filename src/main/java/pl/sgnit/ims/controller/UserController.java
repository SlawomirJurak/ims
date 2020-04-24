package pl.sgnit.ims.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sgnit.ims.model.Role;
import pl.sgnit.ims.model.User;
import pl.sgnit.ims.service.RoleService;
import pl.sgnit.ims.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @ModelAttribute("allRoles")
    public List<Role> allRoles() {
        return roleService.findAll();
    }

    @GetMapping(value = {"", "/"})
    public String allUsers(Model model) {
        List<User> users = userService.findAll();

        model.addAttribute("users", users);
        return "user/all";
    }
}
