package com.haridev.washtrack.data.dto;

import com.haridev.washtrack.enums.PaymentType;
import com.haridev.washtrack.enums.PaymentStatus;

public class Payment {
    private Long id;
    private Double amount;
    private PaymentType type;
    private PaymentStatus status;
    private Long paymentDate;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Long getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Long paymentDate) {
        this.paymentDate = paymentDate;
    }
}
