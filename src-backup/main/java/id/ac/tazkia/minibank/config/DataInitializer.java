package id.ac.tazkia.minibank.config;

import id.ac.tazkia.minibank.entity.Role;
import id.ac.tazkia.minibank.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;
    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        createIfNotExists("ROLE_ADMIN");
        createIfNotExists("ROLE_SUPERVISOR");
        createIfNotExists("ROLE_TELLER");
        createIfNotExists("ROLE_CS");
    }

    private void createIfNotExists(String name) {
        roleRepository.findByName(name).orElseGet(() -> roleRepository.save(new Role(name)));
    }
}
