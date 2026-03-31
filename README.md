# 🏢 SmartFix Kampüs: AI Destekli Arıza Önceliklendirme Ağı

![Status](https://img.shields.io/badge/Durum-Aktif_Geliştirme_Aşamasında-success?style=for-the-badge)

Bu proje, üniversite kampüslerindeki fiziksel arızaların (kırık bank, patlak boru, bozuk priz vb.) bildirim sürecini dijitalleştiren ve bu bildirimleri görüntü işleme/yapay zeka destekli bir algoritma ile aciliyet skoruna göre sıraya koyan akıllı bir web (lojistik) platformudur.

### 🚀 Temel Modüller ve Algoritma Mantığı
* **Görsel Analiz ve Aciliyet Skorlama:** Sisteme yüklenen arıza fotoğraflarının yapay zeka ile analiz edilerek (örneğin; su kaçağı = yüksek öncelik, boya dökülmesi = düşük öncelik) teknik ekibin iş yükünün algoritmik olarak sıralanması.
* **Dinamik İş Kuyruğu (Priority Queue):** Gelen taleplerin lokasyon ve aciliyet durumuna göre teknik personel dashboard'unda (kontrol panelinde) dinamik olarak listelenmesi.
* **Rol Bazlı Yönetim Sistemi:** Öğrenci/Personel (bildirim yapan) ve Teknik Ekip (çözen) olmak üzere farklı yetki seviyelerine sahip arayüz mimarisi.

### 🛠️ Kullanılan Teknolojiler
* **Web Teknolojileri:** (Kullanacağın dili buraya yazarsın örn: HTML, CSS, JavaScript, PHP/Node.js)
* **Yapay Zeka Entegrasyonu:** Python (Arka planda görsel analiz için)
* **Veritabanı:** İlişkisel Veritabanı Yönetimi (SQL)
