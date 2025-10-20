package com.medicinestock.management.controller;

import com.medicinestock.management.model.Distribution;
import com.medicinestock.management.model.Medicine;
import com.medicinestock.management.model.User;
import com.medicinestock.management.repository.MedicineRepository;
import com.medicinestock.management.repository.UserRepository;
import com.medicinestock.management.service.DistributionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/distributions")
@CrossOrigin(origins = "http://localhost:5173")
public class DistributionController {

    private final DistributionService distributionService;
    private final UserRepository userRepository;
    private final MedicineRepository medicineRepository;

    public DistributionController(DistributionService distributionService,
                                  UserRepository userRepository,
                                  MedicineRepository medicineRepository) {
        this.distributionService = distributionService;
        this.userRepository = userRepository;
        this.medicineRepository = medicineRepository;
    }

    // ✅ Fetch all officers (users with role USER)
    @GetMapping("/officers")
    public List<User> getOfficers() {
        return userRepository.findByRole("USER");
    }

    // ✅ Fetch all medicines
    @GetMapping("/medicines")
    public List<Medicine> getMedicines() {
        return medicineRepository.findAll();
    }

    // ✅ Distribute medicine
    @PostMapping
    public Distribution distribute(@RequestParam Long officerId,
                                   @RequestParam Long medicineId,
                                   @RequestParam int quantity) {
        return distributionService.distributeMedicine(officerId, medicineId, quantity);
    }

    // ✅ View all distribution history
    @GetMapping
    public List<Distribution> getAllDistributions() {
        return distributionService.getAllDistributions();
    }
}
