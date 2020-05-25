package pl.sgnit.ims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sgnit.ims.model.Role;
import pl.sgnit.ims.repository.RoleRepository;
import pl.sgnit.ims.service.RoleService;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    private final RoleRepository roleRepository;
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleRepository roleRepository, RoleService roleService) {
        this.roleRepository = roleRepository;
        this.roleService = roleService;
    }

    @GetMapping(value = {"", "/"})
    public String allRoles(Model model) {
        List<Role> roles = roleRepository.findAll();

        model.addAttribute("roles", roles);
        return "role/all";
    }
}
