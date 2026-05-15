package com.smartfix.kampus.controller;

import com.smartfix.kampus.model.Issue;
import com.smartfix.kampus.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/issues")
@CrossOrigin(origins = "*") // İleride web sayfamızdan rahatça bağlanabilelim diye eklendi
public class IssueController {

    @Autowired
    private IssueService issueService;

    // 1. Yeni Arıza Bildirme Yolu (POST İsteği) - Dosya yükleme destekli
    @PostMapping(value = "/ekle", consumes = {"multipart/form-data"})
    public Issue arizaEkle(
            @RequestParam("baslik") String baslik,
            @RequestParam("aciklama") String aciklama,
            @RequestParam(value = "foto", required = false) MultipartFile foto,
            @RequestParam(value = "ogrenciEmail", required = false, defaultValue = "Bilinmiyor") String ogrenciEmail
    ) {
        Issue yeniAriza = new Issue();
        yeniAriza.setBaslik(baslik);
        yeniAriza.setAciklama(aciklama);
        yeniAriza.setOgrenciEmail(ogrenciEmail);

        // Dosya Yükleme İşlemi (Uploads klasörüne kaydetme)
        if (foto != null && !foto.isEmpty()) {
            try {
                String uploadDir = System.getProperty("user.dir") + "/uploads/";
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs(); // Klasör yoksa otomatik oluştur
                }

                // Benzersiz dosya adı oluşturma
                String originalFilename = foto.getOriginalFilename();
                String fileExtension = "";
                if (originalFilename != null && originalFilename.contains(".")) {
                    fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                } else {
                    fileExtension = ".jpg";
                }
                String newFileName = UUID.randomUUID().toString() + fileExtension;

                Path path = Paths.get(uploadDir + newFileName);
                Files.write(path, foto.getBytes()); // Dosyayı diske yaz

                yeniAriza.setFotografUrl(newFileName);
            } catch (IOException e) {
                System.out.println("Dosya yüklenirken hata oluştu: " + e.getMessage());
                yeniAriza.setFotografUrl("Fotoğraf Yüklenemedi");
            }
        } else {
            yeniAriza.setFotografUrl("Fotoğraf Yok");
        }

        // Gelen arızayı al, Service katmanına yolla, AI skorunu hesaplayıp veritabanına kaydetsin!
        return issueService.arizaKaydetVeYapayZekaAnaliziYap(yeniAriza);
    }

    // 2. Tüm Arızaları Listeleme Yolu (GET İsteği)
    @GetMapping("/liste")
    public List<Issue> tumArizalariGetir() {
        return issueService.tumArizalariGetir();
    }

    // 3. Arıza Silme Yolu (DELETE İsteği)
    @DeleteMapping("/{id}")
    public org.springframework.http.ResponseEntity<Void> arizaSil(@PathVariable Long id) {
        issueService.arizaSil(id);
        return org.springframework.http.ResponseEntity.ok().build();
    }

    // 4. Arıza Durumu Güncelleme Yolu (PUT İsteği)
    @PutMapping("/{id}/durum")
    public org.springframework.http.ResponseEntity<Void> durumGuncelle(@PathVariable Long id, @RequestBody java.util.Map<String, String> payload) {
        String durum = payload.get("durum");
        issueService.durumGuncelle(id, durum);
        return org.springframework.http.ResponseEntity.ok().build();
    }
}