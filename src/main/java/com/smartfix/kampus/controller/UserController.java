package com.smartfix.kampus.controller;

import com.smartfix.kampus.model.User;
import com.smartfix.kampus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String sifre = credentials.get("sifre");

        Map<String, String> response = new HashMap<>();

        // 1. Veritabanından bu e-postaya sahip bir kullanıcı (Admin vb.) var mı diye kontrol et
        User dbUser = userRepository.findByEmail(email);

        if (dbUser != null) {
            // Sistemde böyle biri var. Şifresi doğru mu?
            if (dbUser.getSifre().equals(sifre)) {
                // Şifre doğru, veritabanındaki gerçek rolünü JSON ile frontend'e gönder
                response.put("rol", dbUser.getRol());
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "Şifre hatalı! Lütfen tekrar deneyin.");
                return ResponseEntity.status(401).body(response);
            }
        }
        
        // 2. Veritabanında YOKSA ama e-posta .edu.tr ile bitiyorsa (Şifresiz Öğrenci Girişi)
        if (email != null && email.endsWith(".edu.tr")) {
            response.put("rol", "ogrenci");
            return ResponseEntity.ok(response);
        }

        // 3. Hiçbiri değilse yetkisiz giriş!
        response.put("error", "Güvenlik İhlali: Sadece yetkili personel veya .edu.tr uzantılı öğrenciler girebilir.");
        return ResponseEntity.status(401).body(response);
    }
}