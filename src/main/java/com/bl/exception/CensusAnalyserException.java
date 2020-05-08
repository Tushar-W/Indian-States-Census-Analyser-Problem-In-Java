package com.bl.exception;

public class CensusAnalyserException extends RuntimeException {
    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,FILE_TYPE_NOT_FOUND,NO_FILE_DELIMITER_FOUND, NO_CENSUS_DATA, FILE_WRONG_HEADER
    }

    public ExceptionType type;

    public CensusAnalyserException(String message, String name) {
        super(message);
        this.type = ExceptionType.valueOf(name);
    }

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
