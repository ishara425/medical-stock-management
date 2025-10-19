package com.medicinestock.management.service;

import com.medicinestock.management.model.Medicine;
import com.medicinestock.management.repository.MedicineRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MedicineService {

    private final MedicineRepository medicineRepository;

    public MedicineService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    // --- CRUD Methods (same as before) ---
    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    public Medicine saveMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    public Medicine updateMedicine(Long id, Medicine updatedMedicine) {
        return medicineRepository.findById(id).map(medicine -> {
            medicine.setName(updatedMedicine.getName());
            medicine.setDosage(updatedMedicine.getDosage());
            medicine.setManufacturer(updatedMedicine.getManufacturer());
            medicine.setCategory(updatedMedicine.getCategory());
            medicine.setStock(updatedMedicine.getStock());
            medicine.setExpirationDate(updatedMedicine.getExpirationDate());
            medicine.setInstructions(updatedMedicine.getInstructions());
            return medicineRepository.save(medicine);
        }).orElseThrow(() -> new RuntimeException("Medicine not found"));
    }

    public void deleteMedicine(Long id) {
        medicineRepository.deleteById(id);
    }

    // --- Summary & Filters ---
    public List<Medicine> getExpiringSoon(int days) {
        LocalDate today = LocalDate.now();
        LocalDate soon = today.plusDays(days);
        return medicineRepository.findExpiringBefore(soon);
    }

    public List<Medicine> getExpiredMedicines() {
        return medicineRepository.findExpiredMedicines();
    }

    public List<Medicine> getLowStock(int threshold) {
        return medicineRepository.findLowStock(threshold);
    }

    public Map<String, Long> getSummary(int days, int threshold) {
        long total = medicineRepository.count();
        long expiringSoon = getExpiringSoon(days).size();
        long expired = getExpiredMedicines().size();
        long lowStock = getLowStock(threshold).size();

        Map<String, Long> summary = new HashMap<>();
        summary.put("total", total);
        summary.put("expiringSoon", expiringSoon);
        summary.put("expired", expired);
        summary.put("lowStock", lowStock);

        return summary;
    }
}
