package com.medicinestock.management.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String dosage;
    private String manufacturer;
    private String category;
    private int stock;
    private LocalDate expirationDate;
    private String instructions; // optional (e.g., “Take with food”)

    public Medicine() {}

    public Medicine(String name, String dosage, String manufacturer, String category, int stock, LocalDate expirationDate, String instructions) {
        this.name = name;
        this.dosage = dosage;
        this.manufacturer = manufacturer;
        this.category = category;
        this.stock = stock;
        this.expirationDate = expirationDate;
        this.instructions = instructions;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }

    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public LocalDate getExpirationDate() { return expirationDate; }
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
}
