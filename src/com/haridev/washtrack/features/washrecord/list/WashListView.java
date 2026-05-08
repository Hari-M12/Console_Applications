package com.haridev.washtrack.features.washrecord.list;

import com.haridev.washtrack.data.dto.Payment;
import com.haridev.washtrack.data.dto.Vehicle;
import com.haridev.washtrack.data.dto.WashRecord;
import com.haridev.washtrack.util.ConsoleInput;

import java.util.List;
import java.util.Scanner;

public class WashListView {

    private final WashListModel washListModel;
    private final Scanner scanner;
    public WashListView(){
        this.washListModel = new WashListModel(this);
        this.scanner = ConsoleInput.getScanner();
    }

    public void init(){
        System.out.println();
        System.out.println("List Wash Record");
        List<WashRecord> washRecords = washListModel.getWashRecords();

        if (washRecords.isEmpty()) {
            System.out.println("No wash records found.");
            return;
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

        System.out.println("Enter to return");
        scanner.nextLine();
    }
}
