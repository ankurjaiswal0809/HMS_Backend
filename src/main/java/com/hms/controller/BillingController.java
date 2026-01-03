package com.hms.controller;

import com.hms.entity.Bill;
import com.hms.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/billing")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;

    @PostMapping
    public Bill generateBill(@RequestParam Long patientId,
                             @RequestParam Double amount,
                             @RequestParam String description) {
        return billingService.generateBill(patientId, amount, description);
    }

    @GetMapping
    public List<Bill> getAllBills() {
        return billingService.getAllBills();
    }
}
