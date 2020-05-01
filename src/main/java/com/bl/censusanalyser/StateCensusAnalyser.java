package com.bl.censusanalyser;

import com.bl.censusanalyserexception.CensusAnalyserException;
import com.bl.csvstatecensus.CSVStateCensus;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class StateCensusAnalyser {
    int namOfEateries = 0;
    public int loadIndianStateCensusData(String csvFilePath) {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            CsvToBeanBuilder<CSVStateCensus> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(CSVStateCensus.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<CSVStateCensus> csvToBean = csvToBeanBuilder.build();
            Iterator<CSVStateCensus> censusCSVIterator = csvToBean.iterator();
            Iterable<CSVStateCensus> csvIterable=() ->censusCSVIterator;
            namOfEateries = (int) StreamSupport.stream(csvIterable.spliterator(),false).count();
            return namOfEateries;
        } catch (IOException e) {
            e.printStackTrace();
        }catch(Exception e) {
            throw new CensusAnalyserException(e.getMessage(),
                                               CensusAnalyserException.ExceptionType.FILE_WRONG_HEADER);
        }
        return namOfEateries;
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
}
