package com.bl.csvbuilderexception;

public class CSVBuilderException extends  RuntimeException{
    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,FILE_TYPE_NOT_FOUND,NO_FILE_DELIMITER_FOUND,FILE_WRONG_HEADER
    }

    public CSVBuilderException.ExceptionType type;

    public CSVBuilderException(String message, CSVBuilderException.ExceptionType type) {
        super(message);
        this.type = type;
    }
}
