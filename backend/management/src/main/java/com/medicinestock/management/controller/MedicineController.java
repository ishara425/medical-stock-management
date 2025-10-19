package com.medicinestock.management.controller;

import com.medicinestock.management.model.Medicine;
import com.medicinestock.management.service.MedicineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/medicines")

public class MedicineController {

    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    // CRUD
    @GetMapping
    public List<Medicine> getAllMedicines() {
        return medicineService.getAllMedicines();
    }

    @PostMapping
    public Medicine addMedicine(@RequestBody Medicine medicine) {
        return medicineService.saveMedicine(medicine);
    }

    @PutMapping("/{id}")
    public Medicine updateMedicine(@PathVariable Long id, @RequestBody Medicine medicine) {
        return medicineService.updateMedicine(id, medicine);
    }

    @DeleteMapping("/{id}")
    public void deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
    }

    // --- Summary & Filters ---
    @GetMapping("/summary")
    public Map<String, Long> getSummary(
            @RequestParam(defaultValue = "30") int days,        // expiring soon = 30 days
            @RequestParam(defaultValue = "10") int threshold) { // low stock < 10
        return medicineService.getSummary(days, threshold);
    }

    @GetMapping("/expiring-soon")
    public List<Medicine> getExpiringSoon(@RequestParam(defaultValue = "30") int days) {
        return medicineService.getExpiringSoon(days);
    }

    @GetMapping("/expired")
    public List<Medicine> getExpiredMedicines() {
        return medicineService.getExpiredMedicines();
    }

    @GetMapping("/low-stock")
    public List<Medicine> getLowStock(@RequestParam(defaultValue = "10") int threshold) {
        return medicineService.getLowStock(threshold);
    }
}
