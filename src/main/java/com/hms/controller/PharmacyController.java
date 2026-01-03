package com.hms.controller;

import com.hms.entity.Medicine;
import com.hms.entity.PharmacySale;
import com.hms.service.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pharmacy")
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyService pharmacyService;

    // Medicine CRUD
    @PostMapping("/medicine")
    public Medicine addMedicine(@RequestBody Medicine medicine) {
        return pharmacyService.addMedicine(medicine);
    }

    @PutMapping("/medicine/{id}")
    public Medicine updateMedicine(@PathVariable Long id,
                                   @RequestBody Medicine medicine) {
        return pharmacyService.updateMedicine(id, medicine);
    }

    @DeleteMapping("/medicine/{id}")
    public void deleteMedicine(@PathVariable Long id) {
        pharmacyService.deleteMedicine(id);
    }

    @GetMapping("/medicine")
    public List<Medicine> getAllMedicines() {
        return pharmacyService.getAllMedicines();
    }

    // Stock
    @PutMapping("/medicine/{id}/stock")
    public Medicine addStock(@PathVariable Long id,
                             @RequestParam Integer quantity) {
        return pharmacyService.addStock(id, quantity);
    }

    // Sale
    @PostMapping("/sell")
    public PharmacySale sellMedicine(@RequestParam Long medicineId,
                                     @RequestParam Long patientId,
                                     @RequestParam Integer quantity) {
        return pharmacyService.sellMedicine(medicineId, patientId, quantity);
    }
}
