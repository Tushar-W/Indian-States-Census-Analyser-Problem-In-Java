package com.bl.adapter;

import com.bl.exception.CensusAnalyserException;
import com.bl.model.CSVStateCensus;
import com.bl.dao.CSVStateCensusDAO;
import com.bl.model.USCensusCSV;
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

public abstract class CensusAdapter {
    public abstract Map<String, CSVStateCensusDAO> loadCensusData(String... csvFilePath);

    public<E> Map<String, CSVStateCensusDAO> loadCensusData(Class<E> csvCensusClass, String csvFilePath) {
        Map<String,CSVStateCensusDAO> csvMap = new HashMap<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))){
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> csvCensusIterator = csvBuilder.getCSVFileIterator(reader,csvCensusClass);
            Iterable<E> stateCensusIterable = () -> csvCensusIterator;
            if (csvCensusClass.getName().equals("com.bl.model.CSVStateCensus")) {
                StreamSupport.stream(stateCensusIterable.spliterator(), false)
                        .map(CSVStateCensus.class::cast)
                        .forEach(censusCSV -> csvMap.put(censusCSV.state, new CSVStateCensusDAO(censusCSV)));
            }else if (csvCensusClass.getName().equals("com.bl.model.USCensusCSV")){
                StreamSupport.stream(stateCensusIterable.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(censusCSV -> csvMap.put(censusCSV.State, new CSVStateCensusDAO(censusCSV)));
            }
            return csvMap;
        }catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch(RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.FILE_WRONG_HEADER);
        }
    }
}
