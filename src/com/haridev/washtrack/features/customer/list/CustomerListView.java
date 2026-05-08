package com.haridev.washtrack.features.customer.list;

import com.haridev.washtrack.data.dto.Customer;
import com.haridev.washtrack.util.ConsoleInput;
import com.haridev.washtrack.util.ParseHelper;

import java.util.List;
import java.util.Scanner;

public class CustomerListView {

    private final CustomerListModel customerListModel;
    private final Scanner scanner;

    public CustomerListView() {
        this.customerListModel = new CustomerListModel(this);
        this.scanner = ConsoleInput.getScanner();
    }

    public void init() {

        System.out.println();
        System.out.println("All Customers");
        List<Customer> customers = customerListModel.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No employees yet.");
        } else {
            System.out.println("#   Customer Id   Name                  Phone            DOB          Address");
            System.out.println("----------------------------------------------------------------------------");

            for (int i = 0; i < customers.size(); i++) {
                Customer c = customers.get(i);

                String row = String.format(
                        "%-3d %-12s %-20s %-15s %-12s %-20s",
                        (i + 1),
                        c.getId(),
                        truncate(safe(c.getName()), 20),
                        truncate(safe(c.getPhone()), 15),
                        c.getDob() == null ? "-" : ParseHelper.formatDate(c.getDob()),
                        truncate(safe(c.getAddress()), 20));

                System.out.println(row);
            }
        }
        System.out.print("Press Enter to return: ");
        scanner.nextLine();
    }

    private String safe(String value) {
        return value == null ? "-" : value;
    }

    private String truncate(String value, int max) {
        if (value.length() <= max)
            return value;
        return value.substring(0, max - 1) + "~";
    }
}
