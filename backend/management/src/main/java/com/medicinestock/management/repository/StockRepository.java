package com.medicinestock.management.repository;

import com.medicinestock.management.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    // Find stock that expires before a certain date
    List<Stock> findByExpiryDateBefore(LocalDate date);

    // Find stock with quantity <= reorder level
    List<Stock> findByQuantityLessThanEqual(int threshold);

    // Optional: find all stock for a specific medicine
    List<Stock> findByMedicineId(Long medicineId);
}
