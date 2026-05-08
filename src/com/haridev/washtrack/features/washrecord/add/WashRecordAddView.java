package com.haridev.washtrack.features.washrecord.add;

import com.haridev.washtrack.data.dto.Employee;
import com.haridev.washtrack.data.dto.Payment;
import com.haridev.washtrack.data.dto.Vehicle;
import com.haridev.washtrack.data.repository.WashTrackDB;
import com.haridev.washtrack.enums.*;
import com.haridev.washtrack.util.ConsoleInput;
import com.haridev.washtrack.util.ParseHelper;

import java.util.List;
import java.util.Scanner;

public class WashRecordAddView {
    private final WashRecordAddModel washRecordAddModel;
    private final Scanner scanner;

    public WashRecordAddView(){
        this.washRecordAddModel = new WashRecordAddModel(this);
        this.scanner = ConsoleInput.getScanner();
    }

    public void init(){
        System.out.println();
        System.out.println("Add Wash Record");

        Vehicle vehicle = promptVehicle();
        WashType washType = promptWashType();
        WashStatus washStatus = promptWashStatus();
        Payment payment = promptPayment(washStatus);
        Long washDate = System.currentTimeMillis();
        String customerPhone = promptMobile();
        Long assignedTo = pickEmployee();
        if(assignedTo == null ) return;

        washRecordAddModel.createWashRecord(vehicle,washType,washStatus,payment,washDate,customerPhone,assignedTo);

        System.out.println("enter to return");
        scanner.nextLine();
    }

    private Long pickEmployee() {

        List<Employee> employeeList = washRecordAddModel.getTodayCheckedInEmployees();
        if (employeeList == null || employeeList.isEmpty()) {
            System.out.println("No employees have checked in today.so no one is available to assign the task");
            return null;
        }

        while (true) {
            System.out.println("\nSelect an employee:");
            for (int i = 0; i < employeeList.size(); i++) {
                Employee e = employeeList.get(i);
                System.out.println((i + 1) + ". " + e.getName() + " (" + e.getId()
                        + ", " + (e.getRole() == null ? "-" : e.getRole().name()) + ")");
            }
            System.out.print("Choose an option: ");

            Integer index = ParseHelper.parseNonNegativeInt(scanner.nextLine());
            if (index != null && index >= 1 && index <= employeeList.size()) {
                return employeeList.get(index - 1).getId();
            }
            System.out.println("Select a valid option.");
        }

    }

    private Payment promptPayment(WashStatus washStatus) {
        System.out.println();
        System.out.println("Enter Payment details");


        System.out.println("is the customer paid?  (Y/N): ");
        PaymentStatus paymentStatus;
        PaymentType paymentType = null;
        if(ParseHelper.isYes(scanner.nextLine())){
             paymentType = promptPaymentType();
            paymentStatus = PaymentStatus.COMPLETED;
        }else{
            paymentStatus = PaymentStatus.PENDING;
        }
//        PaymentStatus paymentStatus = promptPaymentStatus();
        Long date = System.currentTimeMillis();
        return washRecordAddModel.createPayment(paymentType,paymentStatus,date);
    }


    private Vehicle promptVehicle(){
        System.out.println();

        String brand = promptText("brand");
        String model = promptText("model");
        String color = promptText("color");
        VehicleType type = promptType();
        String numberPlate = promptNumberPlate();

        return washRecordAddModel.createVehicle(brand, model, color, type, numberPlate);

    }



    private VehicleType promptType() {
        while (true){
            System.out.println("\nChoose Vehicle Type:");
            System.out.println("1. Two Wheeler");
            System.out.println("2. Four Wheeler");
            System.out.println("3. Three Wheeler");
            System.out.println("4. Heavy Vehicle");
            System.out.print("Enter choice: ");

            VehicleType vehicleType = washRecordAddModel.parseChoice(scanner.nextLine().trim());
            if(vehicleType!=null) return vehicleType;
            showErrorMessage("vehicle type is null! Choose a valid option");
        }
    }

    private WashType promptWashType() {
        while (true){
            System.out.println("\nChoose Wash Type:");
            System.out.println("1. Body Wash");
            System.out.println("2. Complete Wash");
            System.out.print("Enter choice: ");

            WashType washType = washRecordAddModel.parseWashChoice(scanner.nextLine().trim());
            if(washType!=null) return washType;
            showErrorMessage("Choose a valid option");
        }
    }

    private WashStatus promptWashStatus() {
        while (true){
            System.out.println("\nChoose Wash Status:");
            System.out.println("1. Pending");
            System.out.println("2. Completed");
            System.out.print("Enter choice: ");

            WashStatus washStatus = washRecordAddModel.parseWashStatusChoice(scanner.nextLine().trim());
            if(washStatus!=null) return washStatus;
            showErrorMessage("Choose a valid option");
        }
    }




    private String promptText(String text) {
        while (true) {
            if(text.equalsIgnoreCase("brand"))  System.out.print("Enter your vehicle brand : ");
            else if(text.equalsIgnoreCase("model"))  System.out.print("Enter your vehicle model: ");
            else if(text.equalsIgnoreCase("color"))  System.out.print("Enter your vehicle color: ");

            String input = scanner.nextLine();
            String error = washRecordAddModel.validateText(input);
            if (error == null) return input.trim();
            showErrorMessage(error);
        }
    }

    private String promptNumberPlate() {
        while (true){
            System.out.println("enter vehicle number in LL NN LL NNNN format");
            String input = washRecordAddModel.validateVehicleNumber(scanner.nextLine().trim());
            if(input!=null) return input;
            showErrorMessage(input);
        }
    }

    private String promptMobile() {
        while (true) {
            System.out.print("Enter customer 10 digit mobile number: ");
            String input = scanner.nextLine();
            String error = washRecordAddModel.validateMobile(input);
            if (error == null) return input.trim();
            showErrorMessage(error);
        }
    }

    private PaymentType promptPaymentType() {
        while (true) {
            System.out.println("\nChoose Payment Type:");
            System.out.println("1. UPI");
            System.out.println("2. Card");
            System.out.println("3. Cash");
            System.out.print("Enter choice: ");

            PaymentType paymentType = washRecordAddModel.parsePaymentType(scanner.nextLine().trim());
            if (paymentType != null) return paymentType;
            showErrorMessage("Choose a valid option");
        }
    }

    private PaymentStatus promptPaymentStatus() {
        while (true) {
            System.out.println("\nChoose Payment Status:");
            System.out.println("1. Pending");
            System.out.println("2. Completed");
            System.out.print("Enter choice: ");

            PaymentStatus paymentStatus = washRecordAddModel.parsePaymentStatus(scanner.nextLine().trim());
            if (paymentStatus != null) return paymentStatus;
            showErrorMessage("Choose a valid option");
        }
    }


    void showErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }

    public void showError(String message) {
        System.out.println(message);
    }

    public void onSuccess(String message) {
        System.out.println(message);
    }
}
