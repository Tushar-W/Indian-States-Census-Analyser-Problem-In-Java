package com.bl.opencsvbuilder;

import com.bl.csvbuilderexception.CSVBuilderException;
import com.bl.csvbuilderinterface.ICSVBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class OpenCSVBuilder<E> implements ICSVBuilder {
   /* public <E> Iterator<E> getCSVFileIterator (Reader reader, Class<E> csvClass) {
        CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
        csvToBeanBuilder.withType(csvClass);
        csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
        CsvToBean<E> csvToBean = csvToBeanBuilder.build();
        return ((CsvToBean) csvToBean).iterator();
    }*/
   @Override
   public List<E> getCSVFileList(Reader reader, Class csvClass) {
       return this.getCsvToBean(reader, csvClass).parse();
   }

    @Override
    public Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) {
        return this.getCsvToBean(reader, csvClass).iterator();

    }

    private CsvToBean getCsvToBean(Reader reader, Class csvClass) {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            return csvToBeanBuilder.build();
        } catch (IllegalStateException e) {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.FILE_WRONG_HEADER);
        }
    }

}
