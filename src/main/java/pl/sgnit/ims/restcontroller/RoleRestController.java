package pl.sgnit.ims.restcontroller;

import org.springframework.web.bind.annotation.*;
import pl.sgnit.ims.model.Role;
import pl.sgnit.ims.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleRestController {

    private final RoleService roleService;

    public RoleRestController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/radd")
    public String addRole(@RequestBody Role role) {
        roleService.save(role);
        return "{\"state\": \"OK\"}";
    }

    @GetMapping("/rroleList")
    public List<Role> roleList() {
        return roleService.findAll();
    }
}
