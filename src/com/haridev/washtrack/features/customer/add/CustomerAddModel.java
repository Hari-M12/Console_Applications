package com.haridev.washtrack.features.customer.add;

import com.haridev.washtrack.data.dto.Customer;
import com.haridev.washtrack.data.repository.WashTrackDB;
import com.haridev.washtrack.util.ParseHelper;

import java.util.regex.Pattern;

class CustomerAddModel {

    private static final Pattern MOBILE_PATTERN = Pattern.compile("^[6-9]\\d{9}$");

    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_NAME_LENGTH = 50;
    private static final int MIN_AGE_YEARS = 18;

    private final CustomerAddView customerAddView;

    CustomerAddModel(CustomerAddView customerAddView) {
        this.customerAddView = customerAddView;
    }

    String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Name cannot be empty";
        }
        String trimmed = name.trim();
        if (trimmed.length() < MIN_NAME_LENGTH || trimmed.length() > MAX_NAME_LENGTH) {
            return "Name must be between " + MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH + " characters";
        }
        return null;
    }

    String validateAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            return "Address cannot be empty.";
        }
        if (address.length() < 5) {
            return "Address must be at least 5 characters long.";
        }
        return null;
    }

    String validateMobile(String mobile) {
        if (mobile == null || mobile.trim().isEmpty()) {
            return "Mobile number cannot be empty";
        }
        if (!MOBILE_PATTERN.matcher(mobile.trim()).matches()) {
            return "Enter a valid 10 digit mobile number";
        }
        return null;
    }

    Long parseDateOfBirth(String dobText) {
        Long dobMillis = ParseHelper.parseDate(dobText);
        if (dobMillis == null)
            return null;
        if (dobMillis >= System.currentTimeMillis())
            return null;
        if (ParseHelper.calculateAgeYears(dobMillis) < MIN_AGE_YEARS)
            return null;
        return dobMillis;
    }

    public void registerCustomer(String name, String mobile, Long dob, String address) {
        Customer customer = new Customer();
        customer.setName(name.trim());
        customer.setPhone(mobile.trim());
        customer.setDob(dob);

        Customer saved = WashTrackDB.getInstance().addCustomer(customer);
        if (saved == null) {
            customerAddView.showErrorMessage("Could not add customer. Please try again.");
            return;
        }
        customerAddView.onCustomerAdded(saved);
    }

}
