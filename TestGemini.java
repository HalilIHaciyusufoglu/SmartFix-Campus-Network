import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class TestGemini {
    public static void main(String[] args) {
        try {
            String apiKey = "AIzaSyDyJOnEVoKxZFb4TPvIqf3oL1UCEobNRrc";
            String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey;

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String prompt = "Sen bir üniversite kampüsü arıza değerlendirme yapay zekasısın. " +
                    "Aşağıdaki arıza bildirimini bir insan gibi oku, anlamını analiz et ve aciliyetine göre bana SADECE 1 ile 10 arasında tek bir rakam dön. " +
                    "(1 en önemsiz estetik detay, 10 ise can güvenliği riski, yangın, kaza, çökme, uçak düşmesi, ölüm gibi aşırı kritik durumlar). " +
                    "Asla açıklama yapma, kelime kullanma, sadece rakam yaz. " +
                    "Arıza Başlığı: Test | Detay: Test";

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> textPart = new HashMap<>();
            textPart.put("text", prompt);
            Map<String, Object> partsMap = new HashMap<>();
            partsMap.put("parts", new Object[]{textPart});
            Map<String, Object> bodyMap = new HashMap<>();
            bodyMap.put("contents", new Object[]{partsMap});

            String requestBody = mapper.writeValueAsString(bodyMap);

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
            String responseBody = restTemplate.postForObject(url, entity, String.class);

            System.out.println("RESPONSE: " + responseBody);

            JsonNode root = mapper.readTree(responseBody);
            String aiCevabi = root.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText().trim();
            System.out.println("PARSED ANSWER: " + aiCevabi);
            int value = Integer.parseInt(aiCevabi);
            System.out.println("INTEGER VALUE: " + value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
