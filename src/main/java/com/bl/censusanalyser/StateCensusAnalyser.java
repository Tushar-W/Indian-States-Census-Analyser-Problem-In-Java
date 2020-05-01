package com.bl.censusanalyser;

import com.bl.censusanalyserexception.CensusAnalyserException;
import com.bl.csvstatecensus.CSVStateCensus;
import com.bl.csvstatecode.CSVStateCode;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class StateCensusAnalyser {
    public int loadIndianStateCensusData(String csvFilePath) {
        int numOfEnteries = 0;
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            CsvToBeanBuilder<CSVStateCensus> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(CSVStateCensus.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<CSVStateCensus> csvToBean = csvToBeanBuilder.build();
            Iterator<CSVStateCensus> censusCSVIterator = csvToBean.iterator();
            Iterable<CSVStateCensus> csvIterable=() ->censusCSVIterator;
            numOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(),false).count();
            return numOfEnteries;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch(RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.FILE_WRONG_HEADER);
        }

    }

    public int loadIndianStateCodeData(String csvFilePath) {
        int numOfEnteries = 0;
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            CsvToBeanBuilder<CSVStateCode> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(CSVStateCode.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<CSVStateCode> csvToBean = csvToBeanBuilder.build();
            Iterator<CSVStateCode> csvStateCodeIterator = csvToBean.iterator();
            Iterable<CSVStateCode> csvIterable=() ->csvStateCodeIterator;
            numOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(),false).count();
            return numOfEnteries;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                                              CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    public void loadIndianStateCensusData(String csvFilePath,Class fileType,String seperator) {
        if (seperator.equals(",")) {
            if (fileType.equals(CSVStateCensus.class))
                loadIndianStateCensusData(csvFilePath);
            else {
                throw new CensusAnalyserException("FILE TYPE IS WRONG",
                        CensusAnalyserException.ExceptionType.FILE_TYPE_NOT_FOUND);
            }
        }else{
            throw new CensusAnalyserException("FILE DELIMITER IS WRONG",
                    CensusAnalyserException.ExceptionType.NO_FILE_DELIMITER_FOUND);
        }
    }

    public void loadIndianStateCodeData(String csvFilePath,Class fileType,String seperator) {
        if (seperator.equals(",")) {
            if (fileType.equals(CSVStateCode.class))
                loadIndianStateCodeData(csvFilePath);
            else {
                throw new CensusAnalyserException("FILE TYPE IS WRONG",
                        CensusAnalyserException.ExceptionType.FILE_TYPE_NOT_FOUND);
            }
        }else{
            throw new CensusAnalyserException("FILE DELIMITER IS WRONG",
                    CensusAnalyserException.ExceptionType.NO_FILE_DELIMITER_FOUND);
        }
    }
}
