package com.haridev.washtrack.features.washrecord.list;

import com.haridev.washtrack.data.dto.WashRecord;
import com.haridev.washtrack.data.repository.WashTrackDB;

import java.util.List;

class WashListModel {
    private WashListView washListView;

    WashListModel(WashListView washListView){
        this.washListView = washListView;
    }

    List<WashRecord> getWashRecords() {
        return WashTrackDB.getInstance().getAllWashRecord();
    }
}
