package com.bl.model;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {
    @CsvBindByName(column = "State Id")
    public String StateID;

    @CsvBindByName(column = "State")
    public String State;

    @CsvBindByName(column = "Population Density")
    public double PopulationDensity;

    @CsvBindByName(column = "Population")
    public int Population;

    @CsvBindByName(column = "Total area")
    public double Area;


    @Override
    public String toString() {
        return "USCensusCSV{" +
                "StateID='" + StateID + '\'' +
                ", State='" + State + '\'' +
                ", PopulationDensity='" + PopulationDensity + '\'' +
                ", Population='" + Population + '\'' +
                ", Area='" + Area + '\'' +
                '}';
    }
}
