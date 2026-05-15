package com.smartfix.kampus.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "issues")
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String baslik;
    private String aciklama;
    private String fotografUrl;
    private String ogrenciEmail;

    private Integer aiAciliyetSkoru;
    private String durum = "Bekliyor";
    private LocalDateTime bildirimTarihi = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User bildirenKisi;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building bina;

    // Getter ve Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBaslik() { return baslik; }
    public void setBaslik(String baslik) { this.baslik = baslik; }
    public String getAciklama() { return aciklama; }
    public void setAciklama(String aciklama) { this.aciklama = aciklama; }
    public String getFotografUrl() { return fotografUrl; }
    public void setFotografUrl(String fotografUrl) { this.fotografUrl = fotografUrl; }
    public String getOgrenciEmail() { return ogrenciEmail; }
    public void setOgrenciEmail(String ogrenciEmail) { this.ogrenciEmail = ogrenciEmail; }
    public Integer getAiAciliyetSkoru() { return aiAciliyetSkoru; }
    public void setAiAciliyetSkoru(Integer aiAciliyetSkoru) { this.aiAciliyetSkoru = aiAciliyetSkoru; }
    public String getDurum() { return durum; }
    public void setDurum(String durum) { this.durum = durum; }
    public LocalDateTime getBildirimTarihi() { return bildirimTarihi; }
    public void setBildirimTarihi(LocalDateTime bildirimTarihi) { this.bildirimTarihi = bildirimTarihi; }
    public User getBildirenKisi() { return bildirenKisi; }
    public void setBildirenKisi(User bildirenKisi) { this.bildirenKisi = bildirenKisi; }
    public Building getBina() { return bina; }
    public void setBina(Building bina) { this.bina = bina; }
}