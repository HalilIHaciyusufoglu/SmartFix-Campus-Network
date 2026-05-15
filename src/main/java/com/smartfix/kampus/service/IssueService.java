package com.smartfix.kampus.service;

import com.smartfix.kampus.model.Issue;
import com.smartfix.kampus.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    // Tüm arızaları listeleme
    public List<Issue> tumArizalariGetir() {
        return issueRepository.findAll();
    }

    // Yeni arıza kaydetme
    public Issue arizaKaydetVeYapayZekaAnaliziYap(Issue yeniAriza) {
        yeniAriza.setBildirimTarihi(LocalDateTime.now());
        yeniAriza.setDurum("Bekliyor");

        // ARTIK GERÇEK YAPAY ZEKA ÇAĞRISI YAPIYORUZ!
        int aiSkoru = gercekYapayZekaSkoruSor(yeniAriza.getBaslik(), yeniAriza.getAciklama());
        yeniAriza.setAiAciliyetSkoru(aiSkoru);

        return issueRepository.save(yeniAriza);
    }

    // Arıza Silme İşlemi (DELETE)
    @Transactional
    public void arizaSil(Long id) {
        issueRepository.deleteById(id);
    }

    // Arıza Durumu Güncelleme İşlemi (PUT)
    @Transactional
    public void durumGuncelle(Long id, String yeniDurum) {
        Issue issue = issueRepository.findById(id).orElseThrow(() -> new RuntimeException("Arıza bulunamadı!"));
        issue.setDurum(yeniDurum);
        issueRepository.save(issue);
    }

    // 🚀 GERÇEK LLM (Büyük Dil Modeli) API BAĞLANTISI
    private int gercekYapayZekaSkoruSor(String baslik, String aciklama) {
        try {
            // Senin Google Gemini API Anahtarın
            String apiKey = System.getenv("GEMINI_API_KEY");
            String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey;

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Yapay Zekaya vereceğimiz komut (Prompt Engineering)
            String prompt = "Sen bir üniversite kampüsü arıza değerlendirme yapay zekasısın. " +
                    "Aşağıdaki arıza bildirimini bir insan gibi oku, anlamını analiz et ve aciliyetine göre bana SADECE 1 ile 10 arasında tek bir rakam dön. " +
                    "(1 en önemsiz estetik detay, 10 ise can güvenliği riski, yangın, kaza, çökme, uçak düşmesi, ölüm gibi aşırı kritik durumlar). " +
                    "Asla açıklama yapma, kelime kullanma, sadece rakam yaz. " +
                    "Arıza Başlığı: " + baslik + " | Detay: " + aciklama;

            // JSON Paketini güvenli şekilde oluşturma
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> textPart = new HashMap<>();
            textPart.put("text", prompt);
            Map<String, Object> partsMap = new HashMap<>();
            partsMap.put("parts", new Object[]{textPart});
            Map<String, Object> bodyMap = new HashMap<>();
            bodyMap.put("contents", new Object[]{partsMap});

            String requestBody = mapper.writeValueAsString(bodyMap);

            // API'ye İnternet Üzerinden İsteği Gönder!
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
            String responseBody = restTemplate.postForObject(url, entity, String.class);

            // Gelen cevabın (JSON) içinden yapay zekanın söylediği rakamı çekip alma
            JsonNode root = mapper.readTree(responseBody);
            String aiCevabi = root.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText().trim();

            // Sadece rakam döneceğinden emin olmak için parse ediyoruz
            return Integer.parseInt(aiCevabi);

        } catch (Exception e) {
            System.out.println("Yapay zeka API hatası (İnternet yok veya Key yanlış): " + e.getMessage());
            // Eğer internetin koparsa falan proje patlamasın diye ortalama bir puan olan 5 veriyoruz
            return 5;
        }
    }
}