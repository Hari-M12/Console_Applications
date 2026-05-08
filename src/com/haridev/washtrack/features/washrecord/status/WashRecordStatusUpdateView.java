package com.haridev.washtrack.features.washrecord.status;

import com.haridev.washtrack.data.dto.Employee;
import com.haridev.washtrack.data.dto.Payment;
import com.haridev.washtrack.data.dto.Vehicle;
import com.haridev.washtrack.data.dto.WashRecord;
import com.haridev.washtrack.enums.*;
import com.haridev.washtrack.util.ConsoleInput;
import com.haridev.washtrack.util.ParseHelper;

import java.util.List;
import java.util.Scanner;

public class WashRecordStatusUpdateView {
    private final WashRecordStatusUpdateModel washRecordStatusUpdateModel;
    private final Scanner scanner;
    private final Employee employee;

    public WashRecordStatusUpdateView(Employee employee) {
        this.washRecordStatusUpdateModel = new WashRecordStatusUpdateModel(this);
        this.scanner = ConsoleInput.getScanner();
        this.employee = employee;
    }

    public void init() {
        System.out.println();
        System.out.println("Update Wash Record");

        WashRecord selectWashRecord = selectWashRecord(employee);
        if(selectWashRecord == null) {
            return;
        }
        System.out.println("current wash status = " + selectWashRecord.getWashStatus());
        WashStatus selectedWashStatus = promptWashStatus();

        washRecordStatusUpdateModel.updateWashRecordStatus(selectWashRecord.getWashId(),
                selectWashRecord.getWashStatus(), selectedWashStatus);

        System.out.println("Enter to return");
        scanner.nextLine();
    }

    private WashRecord selectWashRecord(Employee employee) {
        List<WashRecord> washRecords = washRecordStatusUpdateModel.getPendingWashRecords(employee);

        if (washRecords == null || washRecords.isEmpty()) {
            System.out.println("No wash records found.");
            return null;
        }

        System.out.printf("%-6s %-10s %-10s %-10s %-12s %-12s %-12s %-12s %-12s %-12s%n",
                "ID", "Brand", "Model", "Color", "VehicleType", "WashType",
                "Amount", "PaymentStatus", "AssignedTo", "WashStatus");

        System.out.println("------------------------------------------------------------------------------------------------");

        for (WashRecord record : washRecords) {
            Vehicle v = record.getVehicle();
            Payment p = record.getPayment();

            System.out.printf("%-6s %-10s %-10s %-10s %-12s %-12s %-12.2f %-12s %-12s %-12s%n",
                    record.getWashId(),
                    v.getBrand(),
                    v.getModel(),
                    v.getColor(),
                    v.getType(),
                    record.getWashType(),
                    (p != null ? p.getAmount() : 0.0),
                    (p != null ? p.getStatus() : "-"),
                    record.getAssignedTo() != null ? record.getAssignedTo() : "-",
                    record.getWashStatus() != null ? record.getWashStatus() : "-");
        }


        while (true) {
            System.out.println("Select any washRecord you want to update by ID:");
            String choice = scanner.nextLine().trim();

            Long id = ParseHelper.parseNonNegativeLong(choice);

            WashRecord selected = washRecordStatusUpdateModel.getWashRecordWithId(id);
            if (selected != null)
                return selected;
            showErrorMessage("wash record not found! enter a valid option");
        }
    }

    // private VehicleType promptType() {
    // while (true) {
    // System.out.println("\nChoose Vehicle Type:");
    // System.out.println("1. Two Wheeler");
    // System.out.println("2. Four Wheeler");
    // System.out.println("3. Three Wheeler");
    // System.out.println("4. Heavy Vehicle");
    // System.out.print("Enter choice: ");

    // VehicleType vehicleType =
    // washRecordStatusUpdateModel.parseChoice(scanner.nextLine().trim());
    // if (vehicleType != null)
    // return vehicleType;
    // showErrorMessage("vehicle type is null! Choose a valid option");
    // }
    // }

    // private WashType promptWashType() {
    // while (true) {
    // System.out.println("\nChoose Wash Type:");
    // System.out.println("1. Body Wash");
    // System.out.println("2. Complete Wash");
    // System.out.print("Enter choice: ");

    // WashType washType =
    // washRecordStatusUpdateModel.parseWashChoice(scanner.nextLine().trim());
    // if (washType != null)
    // return washType;
    // showErrorMessage("Choose a valid option");
    // }
    // }

    private WashStatus promptWashStatus() {
        while (true) {
            System.out.println("\nChoose Wash Type:");
            System.out.println("1. Pending");
            System.out.println("2. Completed");
            System.out.print("Enter choice: ");

            WashStatus washStatus = washRecordStatusUpdateModel.parseWashStatusChoice(scanner.nextLine().trim());
            if (washStatus != null)
                return washStatus;
            showErrorMessage("Choose a valid option");
        }
    }

    void showErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }

    void onUpdateSuccess(WashStatus old, WashStatus updated) {
        System.out.println("Update Success");
        System.out.println("Status updated from " + old + " to " + updated);
    }

    public void onSuccess(String message) {
        System.out.println(message);
    }
}
