package com.pms.controller;

import com.pms.entity.Customer;
import com.pms.entity.Payment;
import com.pms.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @PostMapping("/viewCustPayments")
    public List viewCustPayments(@RequestBody Customer cust) {
       return paymentService.viewCustPayments(cust.getId());
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        Payment createdPayment = paymentService.createPayment(
                payment.getPaymentId(),
                payment.getCustomer().getId(),
                payment.getPolicy().getPolicyId(),
                payment.getAmount(),
                payment.getPaymentType()
        );
        return ResponseEntity.ok(createdPayment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable String id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
