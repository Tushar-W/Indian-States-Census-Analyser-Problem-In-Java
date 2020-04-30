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
            Iterator<CSVStateCensus> censusCSVIterator = this.getCSVFileIterator(reader,CSVStateCensus.class);
            Iterable<CSVStateCensus> csvIterable=() ->censusCSVIterator;
            numOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(),false).count();
            return numOfEnteries;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                                              CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    public int loadIndianStateCodeData(String csvFilePath) {
        int numOfEnteries = 0;
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            Iterator<CSVStateCode> csvStateCodeIterator = this.getCSVFileIterator(reader,CSVStateCode.class);
            Iterable<CSVStateCode> csvIterable=() ->csvStateCodeIterator;
            numOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(),false).count();
            return numOfEnteries;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                                              CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    private <E> Iterator<E> getCSVFileIterator (Reader reader,Class<E> cvsClass) {
        CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
        csvToBeanBuilder.withType(cvsClass);
        csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
        CsvToBean<E> csvToBean = csvToBeanBuilder.build();
        return csvToBean.iterator();
    }

    public void loadIndianStateCensusData(String csvFilePath,Class fileType) {
        if (fileType.equals(CSVStateCensus.class))
            loadIndianStateCensusData(csvFilePath);
        else {
            throw new CensusAnalyserException("WRONG FILE TYPE", CensusAnalyserException.ExceptionType.CENSUS_TYPE_PROBLEM);
        }
    }
}
