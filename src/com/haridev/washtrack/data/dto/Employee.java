package com.haridev.washtrack.data.dto;

import com.haridev.washtrack.enums.EmployeeStatus;
import com.haridev.washtrack.enums.Role;

public class Employee {
    private Long id;
    private String name;
    private String password;
    private Long dob;
    private String emailId;
    private String mobileNo;
    private Role role;
    private Double salary;
    private String address;
    private EmployeeStatus status;
    private Long joinedDate;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getDob() {
        return dob;
    }

    public void setDob(Long dob) {
        this.dob = dob;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(Long joinedDate) {
        this.joinedDate = joinedDate;
    }
}
