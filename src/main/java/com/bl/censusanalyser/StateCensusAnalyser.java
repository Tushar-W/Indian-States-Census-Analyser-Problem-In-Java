package com.bl.censusanalyser;

import com.bl.censusanalyserexception.CensusAnalyserException;
import com.bl.csvbuilderexception.CSVBuilderException;
import com.bl.csvbuilderfactory.CSVBuilderFactory;
import com.bl.csvbuilderinterface.ICSVBuilder;
import com.bl.csvstatecensus.CSVStateCensus;
import com.bl.csvstatecode.CSVStateCode;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class StateCensusAnalyser {
    public int loadIndianStateCensusData(String csvFilePath) {
        int numOfEnteries = 0;
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<CSVStateCensus> censusCSVList = csvBuilder.getCSVFileList(reader,CSVStateCensus.class);
            return censusCSVList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.FILE_WRONG_HEADER);
        }

    }

    public int loadIndianStateCodeData(String csvFilePath) {
        int numOfEnteries = 0;
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<CSVStateCode> codeCSVList = csvBuilder.getCSVFileList(reader,CSVStateCode.class);
            return codeCSVList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                                              CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.FILE_WRONG_HEADER);
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

    public String getStateWiseSortedCensusData(String indiaStateCodeCsvFilePath) {
        return null;
    }

}