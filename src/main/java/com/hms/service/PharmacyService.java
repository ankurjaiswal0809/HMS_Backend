package com.hms.service;

import com.hms.entity.Medicine;
import com.hms.entity.PharmacySale;

import java.util.List;

public interface PharmacyService {

    // Medicine CRUD
    Medicine addMedicine(Medicine medicine);

    Medicine updateMedicine(Long medicineId, Medicine medicine);

    void deleteMedicine(Long medicineId);

    List<Medicine> getAllMedicines();

    // Stock
    Medicine addStock(Long medicineId, Integer quantity);

    // Sale
    PharmacySale sellMedicine(Long medicineId,
                              Long patientId,
                              Integer quantity);
}
