package com.bl.censusanalyserexception;

import com.bl.csvbuilderexception.CSVBuilderException;

public class CensusAnalyserException extends RuntimeException {

    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,FILE_TYPE_NOT_FOUND,NO_FILE_DELIMITER_FOUND,FILE_WRONG_HEADER
    }

    public ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
