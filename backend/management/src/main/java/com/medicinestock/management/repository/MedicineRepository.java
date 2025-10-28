package com.medicinestock.management.repository;

import com.medicinestock.management.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    // Find medicines expiring before a specific date
    @Query("SELECT m FROM Medicine m WHERE m.expirationDate <= :date")
    List<Medicine> findExpiringBefore(@Param("date") LocalDate date);

    // Find expired medicines
    @Query("SELECT m FROM Medicine m WHERE m.expirationDate < CURRENT_DATE")
    List<Medicine> findExpiredMedicines();

    // Find low stock medicines (threshold passed from service)
    @Query("SELECT m FROM Medicine m WHERE m.stock < :threshold")
    List<Medicine> findLowStock(@Param("threshold") int threshold);
}
