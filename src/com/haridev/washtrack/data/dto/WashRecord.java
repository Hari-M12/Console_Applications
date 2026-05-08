package com.haridev.washtrack.data.dto;

import com.haridev.washtrack.enums.WashStatus;
import com.haridev.washtrack.enums.WashType;

public class WashRecord {
    private Long washId;
    private Vehicle vehicle;
    private WashType washType;
    private Payment payment;
    private WashStatus washStatus;
    private Long washDate;
    private String customerPhone;
    private Long assignedTo;


    public Long getWashId() {
        return washId;
    }
    public void setWashId(Long washId) {
        this.washId = washId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public WashType getWashType() {
        return washType;
    }
    public void setWashType(WashType washType) {
        this.washType = washType;
    }

    public Payment getWashPrice() {
        return payment;
    }
    public void setWashPrice(Payment payment) {
        this.payment = payment;
    }

    public WashStatus getWashStatus() {
        return washStatus;
    }
    public void setWashStatus(WashStatus washStatus) {
        this.washStatus = washStatus;
    }

    public Long getWashDate() {
        return washDate;
    }
    public void setWashDate(Long washDate) {
        this.washDate = washDate;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Long getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Long assignedTo) {
        this.assignedTo = assignedTo;
    }
}

