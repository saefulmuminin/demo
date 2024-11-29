package com.blog.demo.controller.auth;

import com.blog.demo.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Menampilkan halaman login
    @GetMapping("/login")
    public String showLoginPage() {
        return "auth/login"; // Menampilkan view login
    }

    // Menampilkan halaman registrasi
    @GetMapping("/register")
    public String showRegistrationPage() {
        return "auth/register"; // Menampilkan view registrasi
    }

    // Proses login pengguna
    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password, Model model) {
        try {
            // Otentikasi pengguna
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            // Set otentikasi di SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Redirect ke dashboard setelah login sukses
            return "redirect:/dashboard"; // Sesuaikan dengan URL dashboard yang sesuai
        } catch (Exception e) {
            // Jika terjadi error saat login, tampilkan pesan kesalahan
            model.addAttribute("error", "Email atau password salah");
            return "auth/login"; // Kembali ke halaman login jika ada error
        }
    }

    // Proses registrasi pengguna
    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String email,
                               @RequestParam String password, @RequestParam String passwordConfirm, Model model) {
    
        // Validasi password dan konfirmasi password
        if (!password.equals(passwordConfirm)) {
            model.addAttribute("error", "Password dan konfirmasi password tidak cocok");
            return "auth/register"; // Kembali ke halaman registrasi jika password tidak cocok
        }

        // Validasi apakah email sudah terdaftar
        if (userDetailsService.existsByEmail(email)) {
            model.addAttribute("error", "Email sudah terdaftar");
            return "auth/register"; // Kembali ke halaman registrasi jika email sudah terdaftar
        }

        try {
            // Register user, tanpa menyimpan hasilnya
            userDetailsService.registerUser(username, email, password, passwordConfirm); // Sesuaikan dengan service Anda
    
            // Success message
            model.addAttribute("success", "Akun berhasil dibuat! Silakan login.");
            return "auth/login"; // Setelah registrasi berhasil, arahkan ke halaman login
        } catch (Exception e) {
            // Jika terjadi kesalahan, tampilkan pesan kesalahan
            model.addAttribute("error", "Terjadi kesalahan: " + e.getMessage());
            return "auth/register"; // Kembali ke halaman registrasi jika ada error
        }
    }
}
