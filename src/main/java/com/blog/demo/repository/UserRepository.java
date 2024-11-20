package com.blog.demo.repository;

import com.blog.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Temukan pengguna berdasarkan username.
     * @param username nama pengguna yang akan dicari.
     * @return Optional berisi User jika ditemukan.
     */
    Optional<User> findByUsername(String username);

    /**
     * Temukan pengguna berdasarkan email.
     * @param email email pengguna yang akan dicari.
     * @return Optional berisi User jika ditemukan.
     */
    Optional<User> findByEmail(String email);

    /**
     * Cek apakah pengguna dengan email tertentu sudah ada.
     * @param email email yang akan diperiksa.
     * @return true jika pengguna dengan email tersebut sudah ada.
     */
    boolean existsByEmail(String email);

    /**
     * Cek apakah pengguna dengan username tertentu sudah ada.
     * @param username username yang akan diperiksa.
     * @return true jika pengguna dengan username tersebut sudah ada.
     */
    boolean existsByUsername(String username);
}
