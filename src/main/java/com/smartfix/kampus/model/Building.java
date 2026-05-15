package com.smartfix.kampus.model;

import jakarta.persistence.*;

@Entity
@Table(name = "buildings")
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String binaAdi;
    private Double enlem; // Isı haritası için
    private Double boylam; // Isı haritası için

    // Getter ve Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBinaAdi() { return binaAdi; }
    public void setBinaAdi(String binaAdi) { this.binaAdi = binaAdi; }
    public Double getEnlem() { return enlem; }
    public void setEnlem(Double enlem) { this.enlem = enlem; }
    public Double getBoylam() { return boylam; }
    public void setBoylam(Double boylam) { this.boylam = boylam; }
}