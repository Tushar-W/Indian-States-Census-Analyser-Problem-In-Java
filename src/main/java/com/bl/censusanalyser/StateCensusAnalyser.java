package com.bl.censusanalyser;

import com.bl.csvstatecensus.CSVStateCensus;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

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
            while(censusCSVIterator.hasNext()) {
                namOfEateries++;
                CSVStateCensus censusData = censusCSVIterator.next();
            }
            return namOfEateries;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return namOfEateries;
    }
}
