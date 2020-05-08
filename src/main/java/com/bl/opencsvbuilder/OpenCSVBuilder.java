package com.bl.opencsvbuilder;

import com.bl.csvbuilderinterface.ICSVBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class OpenCSVBuilder<E> implements ICSVBuilder<E> {

    @Override
    public Iterator<E> getCSVFileIterator(Reader reader, Class csvClass){
        return this.getCsvToBean(reader, csvClass).iterator();
    }

   @Override
   public List getCSVFileList(Reader reader, Class csvClass) {
       return this.getCsvToBean(reader, csvClass).parse();
   }

   private CsvToBean<E> getCsvToBean(Reader reader, Class csvClass) {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            return csvToBeanBuilder.build();
    }

}
