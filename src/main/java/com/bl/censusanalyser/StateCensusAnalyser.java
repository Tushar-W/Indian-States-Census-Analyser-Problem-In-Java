package com.bl.censusanalyser;

import com.bl.adapter.CensusAdapterFactory;
import com.bl.exception.CensusAnalyserException;
import com.bl.dao.CSVStateCensusDAO;
import com.google.gson.Gson;
import org.apache.commons.io.FilenameUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StateCensusAnalyser<E> {

    public enum Country { INDIA, US }
    private  Country country;
    Map<String,CSVStateCensusDAO> csvMap = new HashMap<>();

    public StateCensusAnalyser(Country country) {
        this.country = country;
    }

    public int loadStateCensusData(Country country, String... csvFilePath) {
        csvMap = new CensusAdapterFactory().getCensusData(country, csvFilePath);
        return csvMap.size();
    }

    public void loadIndianStateData(String seperator, String... csvFilePath) {
        if (seperator.equals(",")) {
            String ext1 = FilenameUtils.getExtension(csvFilePath[0]);
            if("csv".equals(ext1))
                loadStateCensusData(Country.INDIA,csvFilePath);
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

    private Comparator sortedColumnWiseData(String columnName)
    {
        Comparator<CSVStateCensusDAO> dataComparator = null;
        switch(columnName) {
            case "state":
                dataComparator = Comparator.comparing(census -> census.state);
                break;
            case "stateCode":
                dataComparator = Comparator.comparing(census -> census.stateCode);
                break;
            case "population":
                dataComparator = Comparator.comparing(census -> census.population);
                break;
            case "populationDensity":
                dataComparator = Comparator.comparing(census -> census.populationDensity);
                break;
            case "totalArea":
                dataComparator = Comparator.comparing(census -> census.totalArea);
                break;
        }
        return dataComparator;
    }

    public String getStateWiseSortedData(String columnName) {
        if (csvMap == null || csvMap.size() == 0){
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CSVStateCensusDAO> dataComparator = sortedColumnWiseData(columnName);
        ArrayList censusDTOS = csvMap.values().stream().
                                    sorted(dataComparator).
                                    map(csvStateCensusDAO -> csvStateCensusDAO.getCensusDTO(country)).
                                    collect(Collectors.toCollection(ArrayList::new));
        String sortedStateDataJson = new Gson().toJson(censusDTOS);
        return sortedStateDataJson;
    }
}