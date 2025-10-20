package com.medicinestock.management.repository;

import com.medicinestock.management.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findByMedicineId(Long medicineId);
}
