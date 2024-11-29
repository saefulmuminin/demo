package com.blog.demo.service;

import com.blog.demo.model.User;
import com.blog.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public CustomUserDetailsService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User tidak ditemukan dengan email: " + email));

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }

    public User registerUser(String username, String email, String password, String passwordConfirm) throws Exception {
        if (userRepository.existsByEmail(email)) {
            throw new Exception("Email sudah terdaftar");
        }

        if (userRepository.existsByUsername(username)) {
            throw new Exception("Username sudah terdaftar");
        }

        if (!password.equals(passwordConfirm)) {
            throw new Exception("Password dan konfirmasi password tidak cocok");
        }

        if (password.length() < 8) {
            throw new Exception("Password terlalu pendek, minimal 8 karakter");
        }

        String encryptedPassword = passwordEncoder.encode(password);
        User newUser = new User(username, email, encryptedPassword, null);

        return userRepository.save(newUser);
    }

    public boolean existsByEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existsByEmail'");
    }
}
