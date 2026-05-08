package com.haridev.washtrack.features.report;

import com.haridev.washtrack.util.ConsoleInput;
import com.haridev.washtrack.util.ParseHelper;

import java.util.Scanner;

public class ReportDashboardView {
    private final ReportDashboardModel reportDashboardModel;
    private final Scanner scanner;

    public ReportDashboardView(){
        this.reportDashboardModel = new ReportDashboardModel(this);
        this.scanner = ConsoleInput.getScanner();
    }

    public void init(){
        showMenu();
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n--- Report Dashboard ---");
            System.out.println("1. Daily Wash Summary");
            System.out.println("2. Payment Pending Report");
            System.out.println("3. Monthly Revenue Trend");
            System.out.println("4. Employee Performance Report");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            Integer choice = ParseHelper.parseNonNegativeInt(scanner.nextLine());
            if (choice == null) {
                System.out.println("Invalid choice. Try again.");
                continue;
            }

            switch (choice) {
                case 1: reportDashboardModel.generateDailyWashSummary(); break;
                case 2: reportDashboardModel.generatePaymentPendingReport(); break;
                case 3: reportDashboardModel.generateMonthlyRevenueTrend(); break;
                case 4: reportDashboardModel.generateEmployeePerformanceReport(); break;
                case 5: return;
                default: System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
