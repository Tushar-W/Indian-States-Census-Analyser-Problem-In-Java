package com.bl.censusanalyser;

import com.bl.exception.CensusAnalyserException;
import com.bl.model.CSVStateCensus;
import com.bl.model.CSVStateCode;
import com.bl.model.USCensusCSV;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static com.bl.censusanalyser.ConstantsFieldsOfTest.*;

public class StateCensusAnalyserTest {

    //TC1.1
    @Test
    public void givenIndianStateCensusCSVFile_ShouldReturnsCorrectRecords() {
        try {
            stateCensusAnalyser = new StateCensusAnalyser(StateCensusAnalyser.Country.INDIA);
            int noOfRecords = stateCensusAnalyser.loadStateCensusData(StateCensusAnalyser.Country.INDIA,
                                                                      INDIAN_STATES_CENSUS_CSV_FILE_PATH,
                                                                      INDIAN_STATES_CODE_CSV_FILE_PATH);
            Assert.assertEquals(29, noOfRecords);
        }catch (CensusAnalyserException e){ }
    }

    //TC1.2
    @Test
    public void givenIndiaStateCensusData_WithWrongFile_ShouldThrowException() {
        try {
            stateCensusAnalyser = new StateCensusAnalyser(StateCensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            int noOfRecords = stateCensusAnalyser.loadStateCensusData(StateCensusAnalyser.Country.INDIA,
                                                                      WRONG_CSV_FILE_PATH,WRONG_CSV_FILE_PATH);
        }catch(CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    //TC1.3
    @Test
    public void givenIndiaStateCensusCSVFile_WhenWrongType_ShouldThrowException() {
        try {
            stateCensusAnalyser = new StateCensusAnalyser(StateCensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            stateCensusAnalyser.loadIndianStateData(",",
                                                    INDIAN_STATES_CENSUS_CSV_FILE_WRONG_TYPE,
                                                    INDIAN_STATES_CODE_CSV_FILE_WITH_WRONG_HEADER);
        }catch(CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.FILE_TYPE_NOT_FOUND,e.type);
        }
    }

    //TC1.4
    @Test
    public void givenIndiaStateCensusCSVFile_WhenWrongDelimiter_ShouldThrowException() {
        try {
            stateCensusAnalyser = new StateCensusAnalyser(StateCensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            stateCensusAnalyser.loadIndianStateData(";",INDIAN_STATES_CENSUS_CSV_FILE_WRONG_TYPE);
        }catch (CensusAnalyserException e){
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_FILE_DELIMITER_FOUND,e.type);
        }
    }

    //TC1.5
    @Test
    public void givenIndiaStateCensusCSVFile_WhenWrongHeader_ShouldThrowException() {
        try {
            stateCensusAnalyser = new StateCensusAnalyser(StateCensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            stateCensusAnalyser.loadStateCensusData(StateCensusAnalyser.Country.INDIA,
                                                    INDIAN_STATES_CENSUS_CSV_FILE_WITH_WRONG_HEADER,
                                                    INDIAN_STATES_CODE_CSV_FILE_WITH_WRONG_HEADER);
        }catch (CensusAnalyserException e){
            Assert.assertEquals(CensusAnalyserException.ExceptionType.FILE_WRONG_HEADER,e.type);
        }
    }

    //TC2.1
    @Test
    public void givenIndianStateCodeCSVFile_ShouldReturnsCorrectRecords() {
        try {
            stateCensusAnalyser = new StateCensusAnalyser(StateCensusAnalyser.Country.INDIA);
            int noOfRecords = stateCensusAnalyser.loadStateCensusData(StateCensusAnalyser.Country.INDIA,
                                                                      INDIAN_STATES_CENSUS_CSV_FILE_PATH,
                                                                      INDIAN_STATES_CODE_CSV_FILE_PATH);
            Assert.assertEquals(29, noOfRecords);
        }catch (CensusAnalyserException e){ }
    }

    //TC2.2
    @Test
    public void givenIndiaStateCodeData_WithWrongFile_ShouldThrowException() {
        try {
            stateCensusAnalyser = new StateCensusAnalyser(StateCensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            int noOfRecords = stateCensusAnalyser.loadStateCensusData(StateCensusAnalyser.Country.INDIA,
                                                                      WRONG_CSV_FILE_PATH,
                                                                      WRONG_CSV_FILE_PATH);
        }catch(CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    //TC2.3
    @Test
    public void givenIndiaStateCodeCSVFile_WhenWrongType_ShouldThrowException() {
        try {
            stateCensusAnalyser = new StateCensusAnalyser(StateCensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            stateCensusAnalyser.loadIndianStateData(",",ConstantsFieldsOfTest.INDIAN_STATES_CENSUS_CSV_FILE_WRONG_TYPE);
        }catch(CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.FILE_TYPE_NOT_FOUND,e.type);
        }
    }

    //TC2.4
    @Test
    public void givenIndiaStateCodeCSVFile_WhenWrongDelimiter_ShouldThrowException() {
        try {
            stateCensusAnalyser = new StateCensusAnalyser(StateCensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            stateCensusAnalyser.loadIndianStateData(";",INDIAN_STATES_CENSUS_CSV_FILE_WRONG_TYPE,
                                                        INDIAN_STATES_CODE_CSV_FILE_WITH_WRONG_HEADER);
        }catch (CensusAnalyserException e){
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_FILE_DELIMITER_FOUND,e.type);
        }
    }

    //TC2.5
    @Test
    public void givenIndiaStateCodeCSVFile_WhenWrongHeader_ShouldThrowException() {
        try {
            stateCensusAnalyser = new StateCensusAnalyser(StateCensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            stateCensusAnalyser.loadStateCensusData(StateCensusAnalyser.Country.INDIA,
                                                    INDIAN_STATES_CENSUS_CSV_FILE_WITH_WRONG_HEADER,
                                                    INDIAN_STATES_CODE_CSV_FILE_WITH_WRONG_HEADER);
        }catch (CensusAnalyserException e){
            Assert.assertEquals(CensusAnalyserException.ExceptionType.FILE_WRONG_HEADER,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        try {
            stateCensusAnalyser = new StateCensusAnalyser(StateCensusAnalyser.Country.INDIA);
            stateCensusAnalyser.loadStateCensusData(StateCensusAnalyser.Country.INDIA,INDIAN_STATES_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedData("state");
            CSVStateCensus[] censusCSV = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        }catch(CensusAnalyserException e){ }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnEndState() {
        try {
            stateCensusAnalyser = new StateCensusAnalyser(StateCensusAnalyser.Country.INDIA);
            stateCensusAnalyser.loadStateCensusData(StateCensusAnalyser.Country.INDIA,
                                                    INDIAN_STATES_CENSUS_CSV_FILE_PATH,
                                                    INDIAN_STATES_CODE_CSV_FILE_PATH);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedData("state");
            CSVStateCensus[] censusCSV = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertEquals("West Bengal", censusCSV[28].state);
        }catch(CensusAnalyserException e){ }
    }

    @Test
    public void givenIndianStateCodeData_WhenSortedOnState_ShouldReturnSortedResult() {
        try {
            stateCensusAnalyser = new StateCensusAnalyser(StateCensusAnalyser.Country.INDIA);
            stateCensusAnalyser.loadStateCensusData(StateCensusAnalyser.Country.INDIA,
                                                    INDIAN_STATES_CENSUS_CSV_FILE_PATH,
                                                    INDIAN_STATES_CODE_CSV_FILE_PATH);
            String sortedCodeData = stateCensusAnalyser.getStateWiseSortedData("stateCode");
            CSVStateCode[] csvStateCode = new Gson().fromJson(sortedCodeData, CSVStateCode[].class);
            Assert.assertEquals("AD", csvStateCode[0].stateCode);
        }catch(CensusAnalyserException e){ }
    }

    @Test
    public void givenIndianStateCodeData_WhenSortedOnState_ShouldReturnSortedEndStateCode() {
        try {
            stateCensusAnalyser = new StateCensusAnalyser(StateCensusAnalyser.Country.INDIA);
            stateCensusAnalyser.loadStateCensusData(StateCensusAnalyser.Country.INDIA,
                                                    INDIAN_STATES_CENSUS_CSV_FILE_PATH,
                                                    INDIAN_STATES_CODE_CSV_FILE_PATH);
            String sortedCodeData = stateCensusAnalyser.getStateWiseSortedData("stateCode");
            CSVStateCode[] csvStateCode = new Gson().fromJson(sortedCodeData, CSVStateCode[].class);
            Assert.assertEquals("WB", csvStateCode[36].stateCode);
        }catch(CensusAnalyserException e){ }
    }

    @Test
    public void givenStateCensusPopulationData_WhenSortedOnStates_ShouldReturnSortedResult() {
        try {
            stateCensusAnalyser = new StateCensusAnalyser(StateCensusAnalyser.Country.INDIA);
            stateCensusAnalyser.loadStateCensusData(StateCensusAnalyser.Country.INDIA,
                                                    INDIAN_STATES_CENSUS_CSV_FILE_PATH,
                                                    INDIAN_STATES_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedData("population");
            CSVStateCensus[] csvStateCensus = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertEquals(199812341, csvStateCensus[28].population);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenStateCensusDensityData_WhenSortedOnStates_ShouldReturnSortedResult() {
        try {
            stateCensusAnalyser = new StateCensusAnalyser(StateCensusAnalyser.Country.INDIA);
            stateCensusAnalyser.loadStateCensusData(StateCensusAnalyser.Country.INDIA,
                                                    INDIAN_STATES_CENSUS_CSV_FILE_PATH,
                                                    INDIAN_STATES_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedData("getStateWiseSortedData");
            CSVStateCensus[] csvStateCensus = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertEquals(303, csvStateCensus[0].densityPerSqKm);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenStateCensusAreaData_WhenSortedOnStates_ShouldReturnSortedResult() {
        try {
            stateCensusAnalyser = new StateCensusAnalyser(StateCensusAnalyser.Country.INDIA);
            stateCensusAnalyser.loadStateCensusData(StateCensusAnalyser.Country.INDIA,INDIAN_STATES_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedData("totalArea");
            CSVStateCensus[] csvStateCensus = new Gson().fromJson(sortedCensusData, CSVStateCensus[].class);
            Assert.assertEquals(3702, csvStateCensus[0].areaInSqKm);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenUSStateCensusCSVFile_ShouldReturnsCorrectRecords() {
        stateCensusAnalyser = new StateCensusAnalyser(StateCensusAnalyser.Country.US);
        int noOfRecords = stateCensusAnalyser.loadStateCensusData(StateCensusAnalyser.Country.US,US_STATES_CENSUS_CSV_FILE_PATH);
        Assert.assertEquals(51, noOfRecords);
    }

    @Test
    public void givenUSStateCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() {
        try {
            stateCensusAnalyser = new StateCensusAnalyser(StateCensusAnalyser.Country.US);
            stateCensusAnalyser.loadStateCensusData(StateCensusAnalyser.Country.US,US_STATES_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedData("population");
            USCensusCSV csvStateCensus[] = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Wyoming", csvStateCensus[0].State);
        } catch(CensusAnalyserException e) { e.printStackTrace(); }
    }

    @Test
    public void givenUSStateCensusData_WhenSortedOnPopulation_ShouldReturnSortedEndState() {
        try {
            stateCensusAnalyser = new StateCensusAnalyser(StateCensusAnalyser.Country.US);
            stateCensusAnalyser.loadStateCensusData(StateCensusAnalyser.Country.US,US_STATES_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedData("population");
            USCensusCSV csvStateCensus[] = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("California", csvStateCensus[50].State);
        } catch(CensusAnalyserException e) { e.printStackTrace(); }
    }

    @Test
    public void givenUSStateCensusData_WhenSortedOnStates_ShouldReturnSortedResult() {
        try {
            stateCensusAnalyser = new StateCensusAnalyser(StateCensusAnalyser.Country.US);
            stateCensusAnalyser.loadStateCensusData(StateCensusAnalyser.Country.US,US_STATES_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedData("state");
            USCensusCSV csvStateCensus[] = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Alabama", csvStateCensus[0].State);
        } catch(CensusAnalyserException e) { e.printStackTrace(); }
    }

    @Test
    public void givenUSStateCensusData_WhenSortedOnPopulationDensity_ShouldReturnSortedResult() {
        try {
            stateCensusAnalyser = new StateCensusAnalyser(StateCensusAnalyser.Country.US);
            stateCensusAnalyser.loadStateCensusData(StateCensusAnalyser.Country.US,US_STATES_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedData("populationDensity");
            USCensusCSV csvStateCensus[] = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Alaska", csvStateCensus[0].State);
        } catch(CensusAnalyserException e) { e.printStackTrace(); }
    }

    @Test
    public void givenUSStateCensusData_WhenSortedOnArea_ShouldReturnSortedResult() {
        try {
            stateCensusAnalyser = new StateCensusAnalyser(StateCensusAnalyser.Country.US);
            stateCensusAnalyser.loadStateCensusData(StateCensusAnalyser.Country.US,US_STATES_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = stateCensusAnalyser.getStateWiseSortedData("totalArea");
            USCensusCSV csvStateCensus[] = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("District of Columbia", csvStateCensus[0].State);
        } catch(CensusAnalyserException e) { e.printStackTrace(); }
    }

}