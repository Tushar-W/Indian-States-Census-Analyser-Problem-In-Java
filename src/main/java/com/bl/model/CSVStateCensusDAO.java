package com.bl.model;

import com.bl.model.CSVStateCensus;
import com.bl.model.CSVStateCode;

public class CSVStateCensusDAO {

    public String stateCode;
    public int population;
    public int areaInSqKm;
    public int densityPerSqKm;
    public  String state;

    public CSVStateCensusDAO(CSVStateCensus indiaCensusCSV) {
        state = indiaCensusCSV.state;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        population = indiaCensusCSV.population;
    }

    public CSVStateCensusDAO(CSVStateCode indiaCodeCSV) {
        stateCode = indiaCodeCSV.stateCode;
    }

    public CSVStateCensusDAO() {
    }
}

