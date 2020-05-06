package com.bl.censusanalyser;

import com.bl.censusanalyserexception.CensusAnalyserException;
import com.bl.csvbuilderexception.CSVBuilderException;
import com.bl.csvbuilderfactory.CSVBuilderFactory;
import com.bl.csvbuilderinterface.ICSVBuilder;
import com.bl.csvstatecensus.CSVStateCensus;
import com.bl.csvstatecode.CSVStateCode;
import com.google.gson.Gson;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class StateCensusAnalyser {

    List<CSVStateCensus> censusCSVList = null;

    public int loadIndianStateCensusData(String csvFilePath) {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            censusCSVList = csvBuilder.getCSVFileList(reader,CSVStateCensus.class);
            return censusCSVList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                                              CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch(RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.FILE_WRONG_HEADER);
        }

    }

    public int loadIndianStateCodeData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<CSVStateCode> codeCSVList = csvBuilder.getCSVFileList(reader,CSVStateCode.class);
            return codeCSVList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                                              CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch(RuntimeException e) {
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
        int noOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(),false).count();
        return noOfEnteries;
    }

    public String getStateWiseSortedCensusData() {
        if (censusCSVList == null || censusCSVList.size() == 0){
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CSVStateCensus> censusComparator = Comparator.comparing(census -> census.state);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusCSVList);
        return sortedStateCensusJson;
    }

    private void sort(Comparator<CSVStateCensus> censusComparator) {
        for (int i = 0; i < censusCSVList.size() - 1; i++) {
            for (int j = 0; j < censusCSVList.size() - 1 - i; j++) {
                CSVStateCensus census1 = censusCSVList.get(j);
                CSVStateCensus census2 = censusCSVList.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    censusCSVList.set(j, census2);
                    censusCSVList.set(j + 1, census1);
                }
            }
        }
    }
}