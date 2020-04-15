package pl.sgnit.ims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sgnit.ims.model.Role;
import pl.sgnit.ims.repository.RoleRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping(value = {"", "/"})
    public String allRoles(Model model) {
        List<Role> roles = roleRepository.findAll();

        model.addAttribute("roles", roles);
        return "role/all";
    }

    @GetMapping("/add")
    public String initAdd(Model model) {
        Role role = new Role();

        model.addAttribute("role", role);
        return "role/addEdit";
    }

    @PostMapping("/add")
    public String add(@Valid Role role, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "role/addEdit";
        }
        roleRepository.save(role);
        return "redirect:";
    }
}
