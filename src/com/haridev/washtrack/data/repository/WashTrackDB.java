package com.haridev.washtrack.data.repository;

import com.haridev.washtrack.data.dto.*;
import com.haridev.washtrack.enums.EmployeeStatus;
import com.haridev.washtrack.enums.PaymentStatus;
import com.haridev.washtrack.enums.Role;
import com.haridev.washtrack.enums.WashStatus;
import com.haridev.washtrack.util.ParseHelper;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WashTrackDB {
    private WashTrackDB(){}

    private static WashTrackDB washTrackDB;

    public static final WashTrackDB getInstance(){
        if(washTrackDB==null) washTrackDB = new WashTrackDB();
        return washTrackDB;
    }


    private final List<Employee> employees = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private final List<Vehicle> vehicles = new ArrayList<>();
    private final List<WashRecord> washRecords = new ArrayList<>();
    private final List<Attendance> attendances = new ArrayList<>();
    private final List<Payment> payments = new ArrayList<>();
    private long employeePK = 0L;
    private long customerPK = 0L;
    private long washRecordsPK = 0L;
    private long attendancePK = 0L;
    private long paymentPK = 0L;

    public WashRecord updateWashRecordStatus(WashRecord record) {
        if(record == null)return null;
        for (int i=0;i<washRecords.size();i++){
            if(record.getWashId().equals(washRecords.get(i).getWashId())){
                washRecords.set(i,record);
                return record;
            }
        }
        return null;
    }


    public List<Employee> getAllEmployees() {
        return employees;
    }
    public List<Customer> getAllCustomers() {
        return customers;
    }
    public List<Employee> getAllManagerAndEmployees(){
        List<Employee> result = new ArrayList<>();
        for (Employee current: employees){
            if(!(current.getRole().equals(Role.OWNER))){
                result.add(current);
            }
        }
        return result;
    }

    public Employee getEmployeeByEmail(String email) {
        if (email == null) return null;
        String key = email.trim().toLowerCase(Locale.ROOT);
        if (key.isEmpty()) return null;
        for (Employee current : employees) {
            if (current.getEmailId() != null
                    && current.getEmailId().trim().toLowerCase(Locale.ROOT).equals(key)) {
                return current;
            }
        }
        return null;
    }

    public Employee getEmployeeById(Long id) {
        if(id == null ) return  null;
        for (Employee employee: employees){
            if(employee.getId().equals(id)){
                return employee;
            }
        }
        return null;
    }

    public Employee addEmployee(Employee employee) {

        if (employee == null) return null;
        if (employee.getEmailId() == null || employee.getEmailId().trim().isEmpty()) return null;

        employeePK++;
        employee.setId(employeePK);
        employees.add(employee);
        return employee;
    }



    public Customer addCustomer(Customer customer) {
        if (customer == null) return null;
        customerPK++;
        customer.setId(customerPK);
        customers.add(customer);
        return customer;
    }

    public Employee authenticateEmployee(String emailId, String password) {
        Employee employee = getEmployeeByEmail(emailId);
        if(employee==null) return null;
        if(password == null || !password.equals(employee.getPassword())) return null;
        return employee;
    }
    public List<Employee> getTodayCheckedInEmployees(){
        List<Employee> result = new ArrayList<>();

        for(Attendance attendance:attendances){
            String recorderDate = ParseHelper.formatDate(attendance.getDate());
            String todayDate = ParseHelper.formatDate(System.currentTimeMillis());
            if(recorderDate.equals(todayDate) && attendance.getCheckinTime()!=null){
                    Employee employee = getEmployeeById(attendance.getEmployeeId());
                    if(employee.getStatus().equals(EmployeeStatus.ACTIVE)&& (employee.getRole().equals(Role.EMPLOYEE))){
                        result.add(employee);
                    }
            }
        }
        return result;
    }

    public List<Employee> getAllNonManagerEmployees() {
        List<Employee> result = new ArrayList<>();
        for (Employee employee:employees){
            if(employee.getRole().equals(Role.EMPLOYEE)){
                result.add(employee);
            }
        }
        return result;
    }



    public Attendance recordAttendanceFor(Attendance attendance) {
        if(attendance==null) return null;
        attendancePK++;
        attendance.setId(attendancePK);
        attendances.add(attendance);
        return attendance;
    }

    public List<Attendance> getAllAttendanceOf(Long employeeId) {
        List<Attendance> result = new ArrayList<>();
        if(employeeId==null) return result;
        for (Attendance attendance:attendances){
            if(attendance.getEmployeeId().equals(employeeId)){
                result.add(attendance);
            }
        }
        return result;
    }

    public Attendance getTodayAttendanceOf(Long id) {
        List<Attendance> attendanceList = getAllAttendanceOf(id);
        String todayDate = ParseHelper.formatDate(System.currentTimeMillis());
        for (Attendance attendance: attendanceList){
            String date = ParseHelper.formatDate(attendance.getDate());
            if(todayDate.equals(date)){
                return attendance;
            }
        }
        return null;
    }

    public Attendance updateAttendance(Attendance attendance) {
        if(attendance == null)return null;
        for (int i=0;i<attendances.size();i++){
            if(attendance.getId().equals(attendances.get(i).getId())){
                attendances.set(i,attendance);
                return attendance;
            }
        }
        return null;
    }

    public List<Attendance> getAllEmployeesAttendance() {
        List<Attendance> result = new ArrayList<>();

        for (Attendance attendance: attendances){
            Long id = attendance.getEmployeeId();
            Employee employee = getEmployeeById(id);
            if(employee.getRole().equals(Role.EMPLOYEE)){
                result.add(attendance);
            }
        }
        return result;
    }


    public List<Attendance> getAllEmployeesManagersAttendance() {
        List<Attendance> result = new ArrayList<>();

        for (Attendance attendance: attendances){
            Long id = attendance.getEmployeeId();
            if(id==null) return null;
            Employee employee = getEmployeeById(id);
            if(!(employee.getRole().equals(Role.OWNER))){
                result.add(attendance);
            }
        }
        return result;
    }

    public List<Attendance> getMyAttendances(Long id) {
        List<Attendance> result = new ArrayList<>();
        for (Attendance attendance: attendances){
            if(attendance.getEmployeeId().equals(id)){
                result.add(attendance);
            }
        }
        return result;
    }


    public Vehicle saveVehicle(Vehicle vehicle) {
        if(vehicle==null) return null;
        String plateNumber = vehicle.getNumberPlate();
        if(plateNumber.isEmpty()) return null;

        vehicles.add(vehicle);
        return vehicle;
    }

    public Payment savePayment(Payment payment) {
        if(payment==null) return null;
        paymentPK++;
        payment.setId(paymentPK);
        payments.add(payment);
        return payment;
    }

    public WashRecord saveWashRecord(WashRecord washRecord) {
        if(washRecord==null) return null;
        washRecordsPK++;
        washRecord.setWashId(washRecordsPK);
        washRecords.add(washRecord);
        return washRecord;
    }

//    public List<WashRecord> getAllWashRecord() {
//        return washRecords;
//    }
    public List<WashRecord> getAllPendingWashRecord() {
        List<WashRecord> result = new ArrayList<>();
        for(WashRecord washRecord:washRecords){
            if(washRecord.getWashStatus().equals(WashStatus.PENDING)){
                result.add(washRecord);
            }
        }
        return result;
    }

    public List<WashRecord> getPendingWashRecordsOf(Long id) {

        List<WashRecord> result = new ArrayList<>();
        for(WashRecord washRecord:washRecords){
            if(washRecord.getWashStatus().equals(WashStatus.PENDING) && washRecord.getAssignedTo().equals(id)){
                result.add(washRecord);
            }
        }
        return result;
    }

    public List<WashRecord> getPaymentPendingWashRecords() {
        List<WashRecord> result = new ArrayList<>();
        for(WashRecord washRecord:washRecords){
            if(washRecord.getPayment().getStatus().equals(PaymentStatus.PENDING) ){
                result.add(washRecord);
            }
        }
        return result;
    }

    public WashRecord getWashRecordById(Long id) {
        for (WashRecord washRecord:washRecords){
            if(washRecord.getWashId().equals(id)){
                return washRecord;
            }
        }
        return null;
    }


    public List<WashRecord> getAllWashRecord() {
        return washRecords;
    }

    public Customer getCustomerBy(Long id) {
        for (Customer customer:customers){
            if(customer.getId().equals(id)) return customer;
        }
        return null;
    }






}
