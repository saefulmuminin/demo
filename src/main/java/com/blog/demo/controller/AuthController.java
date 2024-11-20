package com.blog.demo.controller;

import com.blog.demo.model.User;
import com.blog.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Menampilkan halaman login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Menampilkan halaman register
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
public String registerUser(@ModelAttribute User user, Model model) {
    System.out.println("Attempting to register user: " + user.getUsername());
    
    if (userService.existsByUsername(user.getUsername())) {
        model.addAttribute("error", "Username sudah terdaftar.");
        return "register";
    }

    if (userService.existsByEmail(user.getEmail())) {
        model.addAttribute("error", "Email sudah terdaftar.");
        return "register";
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    System.out.println("Password encoded: " + user.getPassword());

    try {
        // Simpan user baru ke database
        userService.createUser(user);
        return "redirect:/home";
    } catch (Exception e) {
        e.printStackTrace();
        model.addAttribute("error", "Terjadi kesalahan saat mendaftar. Coba lagi.");
        return "register";
    }
}


    // Proses login user
    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            // Autentikasi pengguna
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/"; // Redirect ke halaman utama setelah login sukses
        } catch (Exception e) {
            model.addAttribute("error", "Username atau password salah.");
            return "login";
        }
    }
}
