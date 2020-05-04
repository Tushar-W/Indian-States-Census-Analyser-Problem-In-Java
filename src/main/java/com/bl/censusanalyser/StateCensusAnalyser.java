package com.bl.censusanalyser;

import com.bl.censusanalyserexception.CensusAnalyserException;
import com.bl.csvstatecensus.CSVStateCensus;
import com.bl.csvstatecode.CSVStateCode;
import com.bl.opencsvbuilder.OpenCSVBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class StateCensusAnalyser {
    public int loadIndianStateCensusData(String csvFilePath) {
        int numOfEnteries = 0;
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            Iterator<CSVStateCensus> censusCSVIterator = new OpenCSVBuilder().getCSVFileIterator(reader,CSVStateCensus.class);
            return getCount(censusCSVIterator);
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
            Iterator<CSVStateCode> csvStateCodeIterator = new OpenCSVBuilder().getCSVFileIterator(reader,CSVStateCode.class);
            return getCount(csvStateCodeIterator);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                                              CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch(RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.FILE_WRONG_HEADER);
        }
    }

    public void loadIndianStateData(String csvFilePath,String seperator) {
       if (seperator.equals(",")) {
           String ext1 = FilenameUtils.getExtension(csvFilePath);
           if("csv".equals(ext1))
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

    public <E> int getCount(Iterator<E> CSVIterator){
        Iterable<E> csvIterable=() ->CSVIterator;
        return (int) StreamSupport.stream(csvIterable.spliterator(),false).count();
    }

}