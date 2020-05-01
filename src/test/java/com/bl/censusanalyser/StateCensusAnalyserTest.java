package com.bl.censusanalyser;

import com.bl.censusanalyserexception.CensusAnalyserException;
import com.bl.csvstatecensus.CSVStateCensus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StateCensusAnalyserTest {
    private static StateCensusAnalyser stateCensusAnalyser;
    private static final String INDIAN_STATES_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIAN_STATES_CENSUS_CSV_FILE_WITH_WRONG_HEADER = "src/test/resources/IndiaStateCensusDataWithWrongHeader.csv";


    @Before
    public void initializeOject() {
        stateCensusAnalyser = new StateCensusAnalyser();
    }
    
    //TC1.1
    @Test
    public void givenIndianStateCensusCSVFile_ShouldReturnsCorrectRecords() {
        int noOfRecords = stateCensusAnalyser.loadIndianStateCensusData(INDIAN_STATES_CENSUS_CSV_FILE_PATH);
        Assert.assertEquals(29,noOfRecords);
    }

    //TC1.2
    @Test
    public void givenIndiaStateCensusData_WithWrongFile_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            int noOfRecords = stateCensusAnalyser.loadIndianStateCensusData(WRONG_CSV_FILE_PATH);
        }catch(CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    //TC1.3
    @Test
    public void givenIndiaStateCensusCSVFile_WhenWrongType_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            stateCensusAnalyser.loadIndianStateCensusData(INDIAN_STATES_CENSUS_CSV_FILE_PATH, String.class,",");
        }catch(CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.FILE_TYPE_NOT_FOUND,e.type);
        }
    }

    //TC1.4
    @Test
    public void givenIndiaStateCensusCSVFile_WhenWrongDelimiter_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            stateCensusAnalyser.loadIndianStateCensusData(INDIAN_STATES_CENSUS_CSV_FILE_PATH,CSVStateCensus.class,".");
        }catch (CensusAnalyserException e){
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_FILE_DELIMITER_FOUND,e.type);
        }
    }

}
