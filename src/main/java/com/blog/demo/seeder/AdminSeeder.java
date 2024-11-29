package com.blog.demo.seeder;

import com.blog.demo.model.User;
import com.blog.demo.model.User.Role;
import com.blog.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userService.existsByUsername("admin")) {
            System.out.println("Akun admin sudah ada.");
            return;
        }

        User admin = new User(null, null, null, null);
        admin.setUsername("admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(Role.ADMIN);

        userService.createUser(admin);
        System.out.println("Akun admin berhasil dibuat.");
    }
}
