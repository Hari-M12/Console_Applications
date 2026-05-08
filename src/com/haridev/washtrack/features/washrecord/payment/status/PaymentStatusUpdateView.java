package com.haridev.washtrack.features.washrecord.payment.status;

import com.haridev.washtrack.data.dto.Payment;
import com.haridev.washtrack.data.dto.Vehicle;
import com.haridev.washtrack.data.dto.WashRecord;
import com.haridev.washtrack.enums.*;
import com.haridev.washtrack.util.ConsoleInput;
import com.haridev.washtrack.util.ParseHelper;

import java.util.List;
import java.util.Scanner;

public class PaymentStatusUpdateView {
    private final PaymentStatusUpdateModel paymentStatusUpdateModel;
    private final Scanner scanner;

    public PaymentStatusUpdateView() {
        this.paymentStatusUpdateModel = new PaymentStatusUpdateModel(this);
        this.scanner = ConsoleInput.getScanner();

    }

    public void init() {
        System.out.println();
        System.out.println("Payment Update");

        WashRecord selectWashRecord = selectWashRecord();
        if (selectWashRecord.getPayment() == null) {
            showErrorMessage("payment is null");
            return;
        }
        PaymentStatus currentStatus = selectWashRecord.getPayment().getStatus();
        System.out.println("current payment status = " + currentStatus);
        PaymentStatus selectedWashStatus = promptPaymentStatus();

        paymentStatusUpdateModel.updateWashRecordPaymentStatus(selectWashRecord.getWashId(), currentStatus,
                selectedWashStatus);
    }

    private WashRecord selectWashRecord() {
        List<WashRecord> washRecords = paymentStatusUpdateModel.getPaymentPendingWashRecords();

        if (washRecords.isEmpty()) {
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
            System.out.println("Select any payment you want to update by ID:");
            String choice = scanner.nextLine().trim();

            Long id = ParseHelper.parseNonNegativeLong(choice);

            WashRecord selected = paymentStatusUpdateModel.getWashRecordWithId(id);
            if (selected != null)
                return selected;
            showErrorMessage("record not found! select a valid option");
        }
    }

    private PaymentStatus promptPaymentStatus() {
        while (true) {
            System.out.println("\nChoose Payment Status:");
            System.out.println("1. Pending");
            System.out.println("2. Completed");
            System.out.print("Enter choice: ");

            PaymentStatus paymentStatus = paymentStatusUpdateModel.parsePaymentStatus(scanner.nextLine().trim());
            if (paymentStatus != null)
                return paymentStatus;
            showErrorMessage("Choose a valid option");
        }
    }

    void showErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }

    void onUpdateSuccess(PaymentStatus old, PaymentStatus updated) {
        System.out.println("Update Success");
        System.out.println("Status updated from " + old + " to " + updated);
    }

    public void onSuccess(String message) {
        System.out.println(message);
    }
}
