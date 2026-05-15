# 🏢 SmartFix Kampüs: AI Destekli Arıza Önceliklendirme Ağı

![Durum](https://img.shields.io/badge/DURUM-TAMAMLANDI-007acc?style=for-the-badge)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)

Bu proje, üniversite kampüslerindeki fiziksel arızaların (elektrik panosu yangınları, su borusu patlamaları, asansör arızaları, kırık donanımlar vb.) bildirim sürecini tamamen dijitalleştiren ve bu bildirimleri gerçek zamanlı yapay zeka entegrasyonu ile analiz ederek aciliyet skoruna göre otomatik olarak sıraya koyan **Full-Stack akıllı bir kampüs yönetim (lojistik) platformudur.**

---

## 🚀 Temel Modüller ve Algoritma Mantığı

* **🤖 Yapay Zeka ile Aciliyet Skorlaması (Google Gemini API):** Öğrenciler tarafından girilen arıza başlığı ve detay açıklaması, arka planda Google Gemini LLM (Büyük Dil Modeli) API'sine güvenli bir şekilde gönderilir. Yapay zeka, can güvenliği risklerini ve kampüs lojistiğini analiz ederek 1 ile 10 arasında dinamik bir aciliyet skoru üretir.
* **⚡ Gelişmiş Yönetici Paneli (Full CRUD):** Yetkili teknik ekip, yapay zekanın en acil olarak işaretlediği (8-10 arası kritik skorlar) ihbarları neon kırmızı aydınlatmalı uyarılarla en üstte görür. Panel üzerinden arıza durumları (Bekliyor, İşleme Alındı, Çözüldü) güncellenebilir veya troll/asılsız ihbarlar veritabanından kalıcı olarak silinebilir.
* **📸 Canlı Fotoğraf Önizleme (Lightbox):** İhbarlara eklenen fotoğraflar, yönetici panelinde dosya ismi olarak kalmaz; üzerine tıklandığında ekranın ortasında pürüzsüz bir animasyonla açılan modern ve cam efekti verilmiş (Glassmorphic Modal) bir pencerede gerçek zamanlı önizlenir.
* **🔐 Rol Bazlı Güvenli Giriş ve Database Seeding:** Öğrenciler kampüs e-postaları (`.edu.tr`) ile hızlı ve pratik bir şekilde giriş yaparken, adminler veritabanı korumalı kimlik doğrulamasıyla panele erişir. Sistem ilk kez ayağa kalktığında, veritabanında yönetici yoksa *Database Seeding* mekanizması devreye girerek default admin hesabını otomatik olarak PostgreSQL'e güvenli bir şekilde yazar.

---

## 🛠️ Kullanılan Teknolojiler

* **Arayüz (Frontend):** HTML5, CSS3 (Premium Deep Cyber Dark Tema, Özel `cubic-bezier` geçiş animasyonları, sürükle-bırak dosya yükleme alanı alanı), JavaScript (ES6+, Fetch API ve asenkron DOM yönetimi).
* **Arka Plan (Backend):** Java 17+, Spring Boot (Spring Web, Spring Data JPA, Hibernate ORM, RestTemplate, Jackson ObjectMapper).
* **Yapay Zeka Entegrasyonu:** Google Gemini 1.5 Flash API *(Sistem güvenliği için API anahtarı kod içerisine gömülmemiş, Çevre Değişkenleri (Environment Variables) üzerinden lokal sistemden okunacak şekilde mimari edilmiştir).*
* **Veritabanı:** PostgreSQL (İlişkisel Veritabanı Yönetimi).

---

## 💻 Kurulum ve Lokal Çalıştırma

1. Projeyi bilgisayarınıza klonlayın veya indirin.
2. Bilgisayarınızın sistem ayarlarına veya kullandığınız IDE'nin (IntelliJ IDEA) *Run/Debug Configurations* ortam değişkenlerine şu çevre değişkenini ekleyin:
   ```text
   GEMINI_API_KEY=your_gemini_api_key_here
