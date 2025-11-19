package id.ac.tazkia.minibank.controller;

import id.ac.tazkia.minibank.entity.Role;
import id.ac.tazkia.minibank.entity.UserAccount;
import id.ac.tazkia.minibank.repository.RoleRepository;
import id.ac.tazkia.minibank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value="error", required=false) String error, Model model) {
        if (error != null) model.addAttribute("errorMessage", "Username atau password salah");
        return "login";
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new UserAccount());
        List<String> roles = Arrays.asList("ROLE_CS", "ROLE_TELLER", "ROLE_SUPERVISOR", "ROLE_ADMIN");
        model.addAttribute("roleOptions", roles);
        return "signup";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserAccount user, @RequestParam("roleSelected") String roleSelected, Model model) {
        if (userRepository.existsByUsername(user.getUsername())) {
            model.addAttribute("errorMessage", "Username sudah dipakai");
            return "signup";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName(roleSelected).orElseGet(() -> roleRepository.save(new Role(roleSelected)));
        user.getRoles().add(role);
        userRepository.save(user);
        return "redirect:/login";
    }
}
