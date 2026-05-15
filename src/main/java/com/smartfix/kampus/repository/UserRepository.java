package com.smartfix.kampus.repository;

import com.smartfix.kampus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Veritabanında e-posta adresine göre kullanıcı arayan sihirli kodumuz:
    User findByEmail(String email);
}