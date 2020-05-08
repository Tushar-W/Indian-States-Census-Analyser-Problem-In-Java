package com.bl.opencsv;
import com.bl.opencsv.ICSVBuilder;
import com.bl.opencsv.OpenCSVBuilder;

public class CSVBuilderFactory {
    public static ICSVBuilder createCSVBuilder() {
        return new OpenCSVBuilder();
    }
}
