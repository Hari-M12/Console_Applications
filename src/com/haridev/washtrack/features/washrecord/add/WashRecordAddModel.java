package com.haridev.washtrack.features.washrecord.add;

import com.haridev.washtrack.data.dto.Employee;
import com.haridev.washtrack.data.dto.Payment;
import com.haridev.washtrack.data.dto.Vehicle;
import com.haridev.washtrack.data.dto.WashRecord;
import com.haridev.washtrack.data.repository.WashTrackDB;
import com.haridev.washtrack.enums.*;
import com.haridev.washtrack.util.ParseHelper;
import com.haridev.washtrack.util.PaymentCalculator;

import java.util.List;
import java.util.regex.Pattern;

class WashRecordAddModel {

    private static final Pattern VEHICLE_NUMBER_PATTERN = Pattern.compile("^[A-Z]{2}\\d{2}[A-Z]{1,2}\\d{4}$");
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^[6-9]\\d{9}$");
    private static final int MIN_TEXT_LENGTH = 3;
    private static final int MAX_TEXT_LENGTH = 50;
    private static final int MIN_AGE_YEARS = 18;
    private final WashRecordAddView washRecordAddView;

    WashRecordAddModel(WashRecordAddView washRecordAddView){
        this.washRecordAddView = washRecordAddView;
    }

    void createWashRecord(Vehicle vehicle, WashType washType, WashStatus washStatus, Payment payment, Long washDate, String customerPhone, Long assignedTo) {
        WashRecord washRecord = new WashRecord();

        washRecord.setVehicle(WashTrackDB.getInstance().saveVehicle(vehicle));
        washRecord.setWashType(washType);
        washRecord.setWashStatus(washStatus);
        payment.setAmount(PaymentCalculator.calculate(washType,vehicle.getType()));
        washRecord.setPayment(WashTrackDB.getInstance().savePayment(payment));
        washRecord.setWashDate(washDate);
        washRecord.setCustomerPhone(customerPhone);
        washRecord.setAssignedTo(assignedTo);

        WashRecord saved = WashTrackDB.getInstance().saveWashRecord(washRecord);
        if(saved == null){
            washRecordAddView.showError("error occured during saving washRecord");
            return;
        }else {
            washRecordAddView.onSuccess("Successfully stored washRecord");
        }

    }

    Vehicle createVehicle(String brand, String model, String color, VehicleType type, String numberPlate) {
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(brand.trim());
        vehicle.setModel(model.trim());
        vehicle.setColor(color.trim());
        vehicle.setType(type);
        vehicle.setNumberPlate(numberPlate.trim());
        return vehicle;
    }

    Payment createPayment(PaymentType paymentType, PaymentStatus paymentStatus, Long date) {
        Payment payment = new Payment();
        payment.setType(paymentType);
        payment.setStatus(paymentStatus);
        payment.setPaymentDate(date);
        return payment;
    }

    String validateText(String text) {
        if (text == null || text.trim().isEmpty()) {
            return "Name cannot be empty";
        }
        String trimmed = text.trim();
        if (trimmed.length() < MIN_TEXT_LENGTH || trimmed.length() > MAX_TEXT_LENGTH) {
            return "Text must be between " + MIN_TEXT_LENGTH + " and " + MAX_TEXT_LENGTH + " characters";
        }
        return null;
    }


    String validateVehicleNumber(String input) {
        if(input.isEmpty() || input.equals(null)) return "Vehicle number cannot be empty";
        if(!VEHICLE_NUMBER_PATTERN.matcher(input).matches())
            return "Invalid vehicle number format. Expected: LL NN LL NNNN (e.g., TN07AB1234)";
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
        if (dobMillis == null) return null;
        if (dobMillis >= System.currentTimeMillis()) return null;
        if (ParseHelper.calculateAgeYears(dobMillis) < MIN_AGE_YEARS) return null;
        return dobMillis;
    }

    VehicleType parseChoice(String input) {
        if(input.equals("1") || input.equalsIgnoreCase("Two Wheeler")) return VehicleType.TWO_WHEELER;
        if(input.equals("2") || input.equalsIgnoreCase("Four Wheeler")) return VehicleType.TWO_WHEELER;
        if(input.equals("3") || input.equalsIgnoreCase("Three Wheeler")) return VehicleType.TWO_WHEELER;
        if(input.equals("4") || input.equalsIgnoreCase("Heavy Vehicle")) return VehicleType.TWO_WHEELER;
        return null;
    }

    WashType parseWashChoice(String input) {
        if(input.equals("1") || input.equalsIgnoreCase("Body Wash")) return WashType.BODY_WASH;
        if(input.equals("2") || input.equalsIgnoreCase("Full Wash")) return WashType.COMPLETE_WASH;
        return null;
    }
    WashStatus parseWashStatusChoice(String input) {
        if(input.equals("1") || input.equalsIgnoreCase("Pending")) return WashStatus.PENDING;
        if(input.equals("2") || input.equalsIgnoreCase("Completed")) return WashStatus.COMPLETED;
        return null;
    }

    PaymentType parsePaymentType(String input) {
        if (input.equals("1") || input.equalsIgnoreCase("UPI")) return PaymentType.UPI;
        if (input.equals("2") || input.equalsIgnoreCase("Card")) return PaymentType.CARD;
        if (input.equals("3") || input.equalsIgnoreCase("Cash")) return PaymentType.CASH;
        return null;
    }

    PaymentStatus parsePaymentStatus(String input) {
        if (input.equals("1") || input.equalsIgnoreCase("Pending")) return PaymentStatus.PENDING;
        if (input.equals("2") || input.equalsIgnoreCase("Completed")) return PaymentStatus.COMPLETED;
        return null;
    }


    List<Employee> getTodayCheckedInEmployees() {
        return WashTrackDB.getInstance().getTodayCheckedInEmployees();
    }


}
