package com.haridev.washtrack.features.customer.details;

import com.haridev.washtrack.data.dto.Customer;
import com.haridev.washtrack.data.repository.WashTrackDB;

import java.util.List;

class CustomerDetailsModel {
    private final CustomerDetailsView customerDetailsView;

    CustomerDetailsModel(CustomerDetailsView customerDetailsView){
        this.customerDetailsView = customerDetailsView;
    }

    List<Customer> getAllCustomer() {
        List<Customer> totalCustomers = WashTrackDB.getInstance().getAllCustomers();
        if(totalCustomers == null){
            customerDetailsView.showErrorMessage("No customer found in db");
            return null;
        }
        return totalCustomers;
    }

    Customer getCustomer(Long id) {
        if(id == null) return null;
        Customer fetchedCustomer = WashTrackDB.getInstance().getCustomerBy(id);
        if(fetchedCustomer == null){
            customerDetailsView.showErrorMessage("No customer found in db");
            return null;
        }
        return fetchedCustomer;

    }
}
