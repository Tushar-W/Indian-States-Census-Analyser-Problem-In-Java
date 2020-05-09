package com.bl.censusanalyser;

import com.bl.exception.CensusAnalyserException;
import com.bl.model.CSVStateCensus;
import com.bl.model.CSVStateCensusDAO;
import com.bl.model.CSVStateCode;
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

public class CensusDataLoader {
    Map<String,CSVStateCensusDAO> csvMap = new HashMap<>();

    public Map<String, CSVStateCensusDAO> loadCensusData(StateCensusAnalyser.Country country, String... csvFilePath) {
        if (country.equals(StateCensusAnalyser.Country.INDIA))
            return this.loadCensusData(CSVStateCensus.class,csvFilePath);
        else if (country.equals(StateCensusAnalyser.Country.US))
            return this.loadCensusData(USCensusCSV.class,csvFilePath);
        else throw new CensusAnalyserException("INCORRECT COUNTRY",CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
    }

    public<E> Map<String, CSVStateCensusDAO> loadCensusData(Class<E> csvCensusClass, String... csvFilePath) {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]))){
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
            if (csvFilePath.length == 1) return csvMap;
            this.loadIndianStateCodeData(csvMap,csvFilePath[1]);
            return csvMap;
        }catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch(RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.FILE_WRONG_HEADER);
        }
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
