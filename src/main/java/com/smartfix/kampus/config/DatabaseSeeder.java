package com.smartfix.kampus.config;

import com.smartfix.kampus.model.User;
import com.smartfix.kampus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Uygulama ayağa kalktığında admin kullanıcısı var mı diye kontrol et
        User admin = userRepository.findByEmail("admin@inonu.edu.tr");

        if (admin == null) {
            // Eğer yoksa yeni admin kullanıcısını oluştur ve kaydet
            User yeniAdmin = new User();
            yeniAdmin.setAdSoyad("Sistem Yöneticisi");
            yeniAdmin.setEmail("admin@inonu.edu.tr");
            yeniAdmin.setSifre("admin123");
            yeniAdmin.setRol("admin");
            
            userRepository.save(yeniAdmin);
            System.out.println("✅ Veritabanı Seeding: Yönetici hesabı başarıyla oluşturuldu.");
        } else {
            System.out.println("ℹ️ Veritabanı Seeding: Yönetici hesabı zaten mevcut.");
        }
    }
}
