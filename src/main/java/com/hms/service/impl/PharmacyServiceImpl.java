package com.hms.service.impl;

import com.hms.entity.Medicine;
import com.hms.entity.PharmacySale;
import com.hms.repository.MedicineRepository;
import com.hms.repository.PatientRepository;
import com.hms.repository.PharmacySaleRepository;
import com.hms.service.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PharmacyServiceImpl implements PharmacyService {

    private final MedicineRepository medicineRepository;
    private final PharmacySaleRepository saleRepository;
    private final PatientRepository patientRepository;

    @Override
    public Medicine addMedicine(Medicine medicine) {
        medicine.setStock(medicine.getStock() == null ? 0 : medicine.getStock());
        return medicineRepository.save(medicine);
    }

    @Override
    public Medicine updateMedicine(Long medicineId, Medicine medicine) {
        Medicine existing = medicineRepository.findById(medicineId).orElseThrow();
        existing.setName(medicine.getName());
        existing.setBatchNo(medicine.getBatchNo());
        existing.setExpiryDate(medicine.getExpiryDate());
        existing.setPrice(medicine.getPrice());
        return medicineRepository.save(existing);
    }

    @Override
    public void deleteMedicine(Long medicineId) {
        medicineRepository.deleteById(medicineId);
    }

    @Override
    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    @Override
    public Medicine addStock(Long medicineId, Integer quantity) {
        Medicine medicine = medicineRepository.findById(medicineId).orElseThrow();
        medicine.setStock(medicine.getStock() + quantity);
        return medicineRepository.save(medicine);
    }

    @Override
    public PharmacySale sellMedicine(Long medicineId,
                                     Long patientId,
                                     Integer quantity) {

        Medicine medicine = medicineRepository.findById(medicineId).orElseThrow();

        if (medicine.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        medicine.setStock(medicine.getStock() - quantity);
        medicineRepository.save(medicine);

        PharmacySale sale = new PharmacySale();
        sale.setMedicine(medicine);
        sale.setPatient(patientRepository.findById(patientId).orElseThrow());
        sale.setQuantity(quantity);
        sale.setTotalPrice(quantity * medicine.getPrice());
        sale.setSaleDate(LocalDateTime.now());

        return saleRepository.save(sale);
    }
}
