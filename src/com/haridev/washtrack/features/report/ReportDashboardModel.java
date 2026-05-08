package com.haridev.washtrack.features.report;

import com.haridev.washtrack.data.dto.Payment;
import com.haridev.washtrack.data.dto.WashRecord;
import com.haridev.washtrack.data.repository.WashTrackDB;
import com.haridev.washtrack.enums.PaymentStatus;
import com.haridev.washtrack.enums.WashType;
import com.haridev.washtrack.util.ParseHelper;

import java.util.HashMap;
import java.util.Map;

class ReportDashboardModel {
    private final ReportDashboardView reportDashboardView;
    public ReportDashboardModel(ReportDashboardView reportDashboardView){
        this.reportDashboardView = reportDashboardView;
    }

    void generateDailyWashSummary() {
        String today = ParseHelper.formatDate(System.currentTimeMillis());
        int bodyWashCount = 0, completeWashCount = 0;
        double totalRevenue = 0;

        for (WashRecord record : WashTrackDB.getInstance().getAllWashRecord()) {
            if (ParseHelper.formatDate(record.getWashDate()).equals(today)) {
                if (record.getWashType() == WashType.BODY_WASH) bodyWashCount++;
                else if (record.getWashType() == WashType.COMPLETE_WASH) completeWashCount++;
                if (record.getPayment() != null) {
                    totalRevenue += record.getPayment().getAmount();
                }
            }
        }

        System.out.println("\nDaily Wash Summary (" + today + ")");
        System.out.println("Body Washes: " + bodyWashCount);
        System.out.println("Complete Washes: " + completeWashCount);
        System.out.println("Total Revenue: ₹" + totalRevenue);
    }

    void generatePaymentPendingReport() {
        System.out.println("\nPayment Pending Report");
        System.out.printf("%-12s %-12s %-12s %-12s %-12s%n",
                "ID", "CustomerPhone", "WashType", "Amount", "AssignedTo");
        System.out.println("-------------------------------------------------------------");

        for (WashRecord record : WashTrackDB.getInstance().getAllWashRecord()) {
            Payment p = record.getPayment();
            if (p != null && p.getStatus() == PaymentStatus.PENDING) {
                System.out.printf("%-12s %-12s %-12s %-12.2f %-12s%n",
                        record.getWashId(),
                        record.getCustomerPhone(),
                        record.getWashType(),
                        p.getAmount(),
                        record.getAssignedTo() != null ? record.getAssignedTo() : "-");
            }
        }
    }

    void generateMonthlyRevenueTrend() {
        Map<String, Double> monthlyRevenue = new HashMap<>();

        for (WashRecord record : WashTrackDB.getInstance().getAllWashRecord()) {
            String month = ParseHelper.formatMonth(record.getWashDate());
            Payment p = record.getPayment();
            if (p != null && p.getStatus() == PaymentStatus.COMPLETED) {
                monthlyRevenue.put(month, monthlyRevenue.getOrDefault(month, 0.0) + p.getAmount());
            }
        }

        System.out.println("\nMonthly Revenue Trend:");
        for (Map.Entry<String, Double> entry : monthlyRevenue.entrySet()) {
            System.out.println(entry.getKey() + " : ₹" + entry.getValue());
        }
    }

    void generateEmployeePerformanceReport() {
        Map<Long, Integer> employeeWashCount = new HashMap<>();

        for (WashRecord record : WashTrackDB.getInstance().getAllWashRecord()) {
            Long empId = record.getAssignedTo();
            if (empId != null) {
                employeeWashCount.put(empId, employeeWashCount.getOrDefault(empId, 0) + 1);
            }
        }

        System.out.println("\nEmployee Performance Report:");
        System.out.printf("%-12s %-12s%n", "EmployeeId", "WashCount");
        System.out.println("--------------------------------");

        for (Map.Entry<Long, Integer> entry : employeeWashCount.entrySet()) {
            System.out.printf("%-12s %-12s%n", entry.getKey(), entry.getValue());
        }
    }
}
