package com.bl.opencsv;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface ICSVBuilder <E>{
    public Iterator<E> getCSVFileIterator(Reader reader, Class csvStatesClass);

    public  List<E> getCSVFileList(Reader reader, Class indiaCensusCSVClass);
}

