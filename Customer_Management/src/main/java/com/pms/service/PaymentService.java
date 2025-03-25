package com.pms.service;

import com.pms.entity.Payment;
import com.pms.entity.Policy;
import com.pms.entity.Customer;
import com.pms.repository.PaymentRepository;
import com.pms.repository.CustomerRepository;
import com.pms.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PolicyRepository policyRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Transactional
    public Payment createPayment(String paymentId,String customerId, Long policyId, Double amount, Payment.PaymentType paymentType) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        Optional<Policy> policyOpt = policyRepository.findById(policyId);

        if (customerOpt.isPresent() && policyOpt.isPresent()) {
            Payment payment = new Payment();
            payment.setPaymentId(paymentId);
            payment.setAmount(amount);
            payment.setPaymentDate(LocalDate.now());
            payment.setPaymentType(paymentType);
            payment.setCustomer(customerOpt.get());
            payment.setPolicy(policyOpt.get());

            return paymentRepository.save(payment);
        } else {
            throw new RuntimeException("Customer or Policy not found");
        }
    }

    public void deletePayment(String paymentId) {
        paymentRepository.deleteById(paymentId);
    }

    public List<Payment> viewCustPayments(String customerId) {
        List<Payment> payments = paymentRepository.findByCustomerId(customerId);

        for (Payment payment : payments) {
            if (payment.getPolicy() == null) {  
                Optional<Policy> policyOpt = policyRepository.findById(
                    payment.getCustomer().getPolicies().stream()
                    .filter(policy -> policy.getPayments().contains(payment))
                    .map(Policy::getPolicyId)
                    .findFirst()
                    .orElse(null)
                );
                policyOpt.ifPresent(payment::setPolicy);
            }
        }

        return payments;
    }



}
