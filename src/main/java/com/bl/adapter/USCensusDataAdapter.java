package com.bl.adapter;

import com.bl.dao.CSVStateCensusDAO;
import com.bl.model.USCensusCSV;

import java.util.Map;

public class USCensusDataAdapter extends CensusAdapter {
    @Override
    public Map<String, CSVStateCensusDAO> loadCensusData(String... csvFilePath) {
        return super.loadCensusData(USCensusCSV.class, csvFilePath[0]);
    }
}
