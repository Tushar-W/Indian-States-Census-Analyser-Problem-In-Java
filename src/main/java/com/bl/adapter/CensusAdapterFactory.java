package com.bl.adapter;

import com.bl.censusanalyser.StateCensusAnalyser;
import com.bl.exception.CensusAnalyserException;
import com.bl.dao.CSVStateCensusDAO;

import java.util.Map;

public class CensusAdapterFactory {
    public Map<String, CSVStateCensusDAO> getCensusData(StateCensusAnalyser.Country country, String... csvFilePath) {
        if (country.equals(StateCensusAnalyser.Country.INDIA))
            return new IndiaCensusDataAdapter().loadCensusData(csvFilePath);
        if (country.equals(StateCensusAnalyser.Country.US))
            return new USCensusDataAdapter().loadCensusData(csvFilePath);
        throw new CensusAnalyserException("UNKNOWN COUNTRY", CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
    }
}
