package com.haridev.washtrack.data.dto;

public class Attendance {
    private Long id;
    private Long employeeId;
    private Long date;
    private Long checkinTime;
    private Long checkoutTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Long getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(Long checkinTime) {
        this.checkinTime = checkinTime;
    }

    public Long getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(Long checkoutTime) {
        this.checkoutTime = checkoutTime;
    }
}
