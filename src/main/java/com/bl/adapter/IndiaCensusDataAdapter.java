package com.bl.adapter;

import com.bl.exception.CensusAnalyserException;
import com.bl.model.CSVStateCensus;
import com.bl.dao.CSVStateCensusDAO;
import com.bl.model.CSVStateCode;
import com.bl.opencsv.CSVBuilderFactory;
import com.bl.opencsv.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IndiaCensusDataAdapter extends CensusAdapter {
    Map<String,CSVStateCensusDAO> csvMap = new HashMap<>();

    @Override
    public Map<String, CSVStateCensusDAO> loadCensusData(String... csvFilePath) {
        Map<String, CSVStateCensusDAO> censusStateMap = super.loadCensusData(CSVStateCensus.class, csvFilePath[0]);
        this.loadIndianStateCodeData(csvMap,csvFilePath[1]);
        return censusStateMap;
    }

    public int loadIndianStateCodeData(Map<String, CSVStateCensusDAO> csvMap, String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStateCode> csvStateCodeIterator = csvBuilder.getCSVFileIterator(reader, CSVStateCode.class);
            Iterable<CSVStateCode> stateCodeIterable = () -> csvStateCodeIterator;
            StreamSupport.stream(stateCodeIterable.spliterator(), false)
                    .filter(stateSCV -> this.csvMap.get(stateSCV.state) != null)
                    .forEach(stateSCV -> this.csvMap.get(stateSCV.state).stateCode = stateSCV.stateCode);
            return this.csvMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch(RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.FILE_WRONG_HEADER);
        }
    }
}
