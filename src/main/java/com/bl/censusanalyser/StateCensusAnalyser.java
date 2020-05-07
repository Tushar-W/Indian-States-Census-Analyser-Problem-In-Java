package com.bl.censusanalyser;

import com.bl.censusanalyserexception.CensusAnalyserException;
import com.bl.csvbuilderfactory.CSVBuilderFactory;
import com.bl.csvbuilderinterface.ICSVBuilder;
import com.bl.csvstatecensus.CSVStateCensus;
import com.bl.csvstatecode.CSVStateCode;
import com.google.gson.Gson;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StateCensusAnalyser<E> {

    List<E> csvStateFile = null;

    Map<String,E> csvStateMap = new HashMap<>();

    public int loadIndianStateCensusData(String csvFilePath) {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))){
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStateCensus> csvCensusIterator = csvBuilder.getCSVFileIterator(reader,CSVStateCensus.class);
            while (csvCensusIterator.hasNext()) {
                CSVStateCensus value = csvCensusIterator.next();
                this.csvStateMap.put(value.state, (E) value);
                csvStateFile = csvStateMap.values().stream().collect(Collectors.toList());
            }
            return csvStateMap.size();
        }catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                                              CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch(RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.FILE_WRONG_HEADER);
        }
    }

    public int loadIndianStateCodeData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStateCode> csvStateCodeIterator = csvBuilder.getCSVFileIterator(reader,CSVStateCode.class);
            while (csvStateCodeIterator.hasNext()) {
                CSVStateCode value = csvStateCodeIterator.next();
                this.csvStateMap.put(value.stateCode, (E) value);
                csvStateFile = csvStateMap.values().stream().collect(Collectors.toList());
            }
            return csvStateMap.size();
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
        if (csvStateFile == null || csvStateFile.size() == 0){
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CSVStateCensus> censusComparator = Comparator.comparing(census -> census.state);
        this.sort((Comparator<E>) censusComparator,csvStateFile);
        String sortedStateCensusJson = new Gson().toJson(csvStateFile);
        return sortedStateCensusJson;
    }

    public String getStateWiseSortedCodeData() {
        if (csvStateFile == null || csvStateFile.size() == 0){
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CSVStateCode> codeComparator = Comparator.comparing(census -> census.stateCode);
        this.sort((Comparator<E>) codeComparator,csvStateFile);
        String sortedStateCodeJson = new Gson().toJson(csvStateFile);
        return sortedStateCodeJson;
    }


    private<E> void sort(Comparator<E> censusComparator,List<E> csvFileList) {
        for (int i = 0; i < csvFileList.size() - 1; i++) {
            for (int j = 0; j < csvFileList.size() - 1 - i; j++) {
                E census1 = csvFileList.get(j);
                E census2 = csvFileList.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    csvFileList.set(j, census2);
                    csvFileList.set(j + 1, census1);
                }
            }
        }
    }
}