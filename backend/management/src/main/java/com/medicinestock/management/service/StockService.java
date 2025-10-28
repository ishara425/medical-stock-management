package com.medicinestock.management.service;

import com.medicinestock.management.model.Medicine;
import com.medicinestock.management.model.Stock;
import com.medicinestock.management.repository.MedicineRepository;
import com.medicinestock.management.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final MedicineRepository medicineRepository;

    public StockService(StockRepository stockRepository, MedicineRepository medicineRepository) {
        this.stockRepository = stockRepository;
        this.medicineRepository = medicineRepository;
    }

    public List<Stock> getAllStock() {
        return stockRepository.findAll();
    }

    public Stock getStockById(Long id) {
        return stockRepository.findById(id).orElse(null);
    }

    public Stock addStock(Long medicineId, Stock stockDetails) {
        Medicine medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));
        stockDetails.setMedicine(medicine);
        return stockRepository.save(stockDetails);
    }

    public Stock updateStock(Long id, Long medicineId, Stock stockDetails) {
        Stock stock = stockRepository.findById(id).orElse(null);
        if (stock != null) {
            Medicine medicine = medicineRepository.findById(medicineId)
                    .orElseThrow(() -> new RuntimeException("Medicine not found"));
            stock.setMedicine(medicine);
            stock.setQuantity(stockDetails.getQuantity());
            stock.setBatchNumber(stockDetails.getBatchNumber());
            stock.setExpiryDate(stockDetails.getExpiryDate());
            stock.setReceivedDate(stockDetails.getReceivedDate());
            stock.setSupplier(stockDetails.getSupplier());
            stock.setUnitPrice(stockDetails.getUnitPrice());
            stock.setReorderLevel(stockDetails.getReorderLevel());
            return stockRepository.save(stock);
        }
        return null;
    }

    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }

    public List<Stock> getExpiringStock(int days) {
        return stockRepository.findByExpiryDateBefore(LocalDate.now().plusDays(days));
    }

    public Map<String, Object> getStockSummary() {
        List<Stock> allStock = stockRepository.findAll();
        int totalItems = allStock.stream().mapToInt(Stock::getQuantity).sum();
        double totalValue = allStock.stream().mapToDouble(s -> s.getQuantity() * s.getUnitPrice()).sum();
        List<Stock> lowStock = stockRepository.findByQuantityLessThanEqual(5);
        return Map.of(
                "totalItems", totalItems,
                "totalValue", totalValue,
                "lowStockAlerts", lowStock
        );
    }
}
