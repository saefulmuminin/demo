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
        // Cek jika akun admin sudah ada
        if (userService.existsByUsername("admin")) {
            System.out.println("Akun admin sudah ada.");
            return;
        }

        // Membuat akun admin baru
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@domain.com");
        admin.setPassword(passwordEncoder.encode("adminpassword"));  // Password yang dienkripsi
        admin.setRole(Role.ADMIN);  // Set role sebagai ADMIN

        // Simpan ke dalam database
        userService.createUser(admin);

        System.out.println("Akun admin berhasil dibuat.");
    }
}
