package com.bl.csvbuilderfactory;
import com.bl.csvbuilderinterface.ICSVBuilder;
import com.bl.opencsvbuilder.OpenCSVBuilder;

public class CSVBuilderFactory {
    public static ICSVBuilder createCSVBuilder() {
        return new OpenCSVBuilder();
    }
}
