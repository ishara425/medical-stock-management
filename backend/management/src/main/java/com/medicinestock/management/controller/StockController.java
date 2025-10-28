package com.medicinestock.management.controller;

import com.medicinestock.management.model.Stock;
import com.medicinestock.management.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stock")
@CrossOrigin(origins = "*")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public List<Stock> getAllStock() {
        return stockService.getAllStock();
    }

    @PostMapping("/receive")
    public Stock receiveStock(@RequestParam Long medicineId, @RequestBody Stock stock) {
        return stockService.addStock(medicineId, stock);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Long id) {
        Stock stock = stockService.getStockById(id);
        if (stock == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(stock);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(
            @PathVariable Long id,
            @RequestParam Long medicineId,
            @RequestBody Stock stock) {
        Stock updatedStock = stockService.updateStock(id, medicineId, stock);
        if (updatedStock == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updatedStock);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/summary")
    public Map<String, Object> getStockSummary() {
        return stockService.getStockSummary();
    }

    @GetMapping("/expiring")
    public List<Stock> getExpiringStock(@RequestParam(defaultValue = "30") int days) {
        return stockService.getExpiringStock(days);
    }
}
