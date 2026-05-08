package com.haridev.washtrack.features.customer.details;

import com.haridev.washtrack.data.dto.Customer;
import com.haridev.washtrack.util.ConsoleInput;
import com.haridev.washtrack.util.ParseHelper;

import java.util.List;
import java.util.Scanner;

public class CustomerDetailsView {

    private final CustomerDetailsModel customerDetailsModel;
    private final Scanner scanner;

    public CustomerDetailsView(){
        this.customerDetailsModel = new CustomerDetailsModel(this);
        this.scanner = ConsoleInput.getScanner();
    }

    public void init(){
        System.out.println();
        System.out.println("Customer Details");
        Customer selectedCustomer = pickCustomer();
        if(selectedCustomer == null) {
            showErrorMessage("No Customers found");
            System.out.println("Enter to return");
            scanner.nextLine();
            return;
        }

        System.out.println("-------------------------------------------------------------");
        System.out.printf("%-15s : %s%n", "Customer ID", selectedCustomer.getId());
        System.out.printf("%-15s : %s%n", "Name", safe(selectedCustomer.getName()));
        System.out.printf("%-15s : %s%n", "Phone", safe(selectedCustomer.getPhone()));
        System.out.printf("%-15s : %s%n", "DOB", selectedCustomer.getDob() == null ? "-" : ParseHelper.formatDate(selectedCustomer.getDob()));
        System.out.printf("%-15s : %s%n", "Address", safe(selectedCustomer.getAddress()));
        System.out.println("-------------------------------------------------------------");

        System.out.print("Press Enter to return...");
        scanner.nextLine();
    }

    Customer pickCustomer(){
        List<Customer> totalCustomers = customerDetailsModel.getAllCustomer();
        if (totalCustomers == null || totalCustomers.isEmpty()) {
            return null;
        }
        System.out.println("#   Customer Id   Name                  Phone            DOB          Address");
        System.out.println("----------------------------------------------------------------------------");
        for (int i = 0; i < totalCustomers.size(); i++) {
            Customer c = totalCustomers.get(i);
            String row = String.format(
                    "%-3d %-12s %-20s %-15s %-12s %-20s",
                    (i + 1),
                    c.getId(),
                    truncate(safe(c.getName()), 20),
                    truncate(safe(c.getPhone()), 15),
                    c.getDob() == null ? "-" : ParseHelper.formatDate(c.getDob()),
                    truncate(safe(c.getAddress()), 20)
            );
            System.out.println(row);
        }
        System.out.println("do you want to select any customer to view specifically?   (Y/N): ");
        if (ParseHelper.isYes(scanner.nextLine())) {
            while (true) {
                System.out.println("Select customer you want to view:");
                String choice = scanner.nextLine().trim();

                Long id = ParseHelper.parseNonNegativeLong(choice);

                Customer selected = customerDetailsModel.getCustomer(id);
                if(selected != null) return selected;
                showErrorMessage("customer not found salect an valid option");
            }
        } else {
            return null;
        }


    }

    private String safe(String value) {
        return value == null ? "-" : value;
    }
    private String truncate(String value, int max) {
        if (value.length() <= max) return value;
        return value.substring(0, max - 1) + "~";
    }


    public void showErrorMessage(String message) {
        System.out.println(message);
    }
}
