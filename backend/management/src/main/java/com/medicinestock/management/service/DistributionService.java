package com.medicinestock.management.service;

import com.medicinestock.management.model.Distribution;
import com.medicinestock.management.model.Medicine;
import com.medicinestock.management.model.User;
import com.medicinestock.management.repository.DistributionRepository;
import com.medicinestock.management.repository.MedicineRepository;
import com.medicinestock.management.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DistributionService {

    private final DistributionRepository distributionRepository;
    private final UserRepository userRepository;
    private final MedicineRepository medicineRepository;

    public DistributionService(DistributionRepository distributionRepository,
                               UserRepository userRepository,
                               MedicineRepository medicineRepository) {
        this.distributionRepository = distributionRepository;
        this.userRepository = userRepository;
        this.medicineRepository = medicineRepository;
    }

    public List<Distribution> getAllDistributions() {
        return distributionRepository.findAll();
    }

    public Distribution distributeMedicine(Long officerId, Long medicineId, int quantity) {
        User officer = userRepository.findById(officerId)
                .orElseThrow(() -> new RuntimeException("Officer not found"));
        Medicine medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));

        if (medicine.getStock() < quantity) {
            throw new RuntimeException("Not enough stock available");
        }

        // Update medicine stock
        medicine.setStock(medicine.getStock() - quantity);
        medicineRepository.save(medicine);

        Distribution distribution = Distribution.builder()
                .officer(officer)
                .medicine(medicine)
                .quantity(quantity)
                .date(LocalDate.now())
                .status("Completed")
                .build();

        return distributionRepository.save(distribution);
    }
}
