package com.haridev.washtrack.features.washrecord.payment.status;

import com.haridev.washtrack.data.dto.Employee;
import com.haridev.washtrack.data.dto.WashRecord;
import com.haridev.washtrack.data.repository.WashTrackDB;
import com.haridev.washtrack.enums.*;


import java.util.List;

class PaymentStatusUpdateModel {


    private final PaymentStatusUpdateView paymentStatusUpdateView;

    PaymentStatusUpdateModel(PaymentStatusUpdateView paymentStatusUpdateView){
        this.paymentStatusUpdateView = paymentStatusUpdateView;
    }

    void updateWashRecordPaymentStatus(Long id,PaymentStatus oldPaymentStatus,PaymentStatus selectedPaymentStatus) {
        WashRecord fetched = WashTrackDB.getInstance().getWashRecordById(id);

        fetched.getPayment().setStatus(selectedPaymentStatus);
        WashRecord updated = WashTrackDB.getInstance().updateWashRecordStatus(fetched);
        if(updated == null) {
            paymentStatusUpdateView.showErrorMessage("Updation failed");
        }else {
            paymentStatusUpdateView.onUpdateSuccess(oldPaymentStatus,selectedPaymentStatus);
        }

    }


    PaymentStatus parsePaymentStatus(String input) {
        if (input.equals("1") || input.equalsIgnoreCase("Pending")) return PaymentStatus.PENDING;
        if (input.equals("2") || input.equalsIgnoreCase("Completed")) return PaymentStatus.COMPLETED;
        return null;
    }


    List<Employee> getTodayCheckedInEmployees() {
        return WashTrackDB.getInstance().getTodayCheckedInEmployees();
    }


    List<WashRecord> getPaymentPendingWashRecords() {

        return WashTrackDB.getInstance().getPaymentPendingWashRecords();
    }

    WashRecord getWashRecordWithId(Long id) {
        if(id==null) return null;
        WashRecord fetched = WashTrackDB.getInstance().getWashRecordById(id);
        if(fetched == null){
            paymentStatusUpdateView.showErrorMessage("WashRecord Not Found");
            return null;
        }
        return fetched;
    }
}
