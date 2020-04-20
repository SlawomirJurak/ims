package pl.sgnit.ims.service;

import org.springframework.stereotype.Service;
import pl.sgnit.ims.model.Role;
import pl.sgnit.ims.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    public void save(Role role) {
        roleRepository.save(role);
    }
}
