package com.bl.dao;

import com.bl.model.CSVStateCensus;
import com.bl.model.CSVStateCode;
import com.bl.model.USCensusCSV;

public class CSVStateCensusDAO {

    public String stateCode;
    public int population;
    public double totalArea;
    public double populationDensity;
    public  String state;

    public CSVStateCensusDAO(CSVStateCensus indiaCensusCSV) {
        state = indiaCensusCSV.state;
        populationDensity = indiaCensusCSV.densityPerSqKm;
        totalArea = indiaCensusCSV.areaInSqKm;
        population = indiaCensusCSV.population;
    }

    public CSVStateCensusDAO(CSVStateCode indiaCodeCSV) {
        stateCode = indiaCodeCSV.stateCode;
    }

    public CSVStateCensusDAO(USCensusCSV usCensusCSV) {
        state = usCensusCSV.State;
        stateCode = usCensusCSV.StateID;
        populationDensity = usCensusCSV.PopulationDensity;
        totalArea = usCensusCSV.Area;
        population = usCensusCSV.Population;
    }
}

