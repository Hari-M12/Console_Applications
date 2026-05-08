package com.haridev.washtrack.features.customer.list;

import com.haridev.washtrack.data.dto.Customer;
import com.haridev.washtrack.data.repository.WashTrackDB;

import java.util.List;

class CustomerListModel {
    private final CustomerListView customerListView;

    CustomerListModel(CustomerListView customerListView) {
        this.customerListView = customerListView;
    }

    List<Customer> getAllCustomers() {
        return WashTrackDB.getInstance().getAllCustomers();
    }
}
