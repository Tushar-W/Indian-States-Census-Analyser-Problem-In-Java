package com.bl.model;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {
    @CsvBindByName(column = "StateId")
    public String StateID;

    @CsvBindByName(column = "State")
    public String State;

    @CsvBindByName(column = "PopulationDensity")
    public double PopulationDensity;

    @CsvBindByName(column = "Population")
    public int Population;

    @CsvBindByName(column = "TotalArea")
    public double Area;


    @Override
    public String toString() {
        return "USCensusCSV{" +
                "StateID='" + StateID + '\'' +
                ", State='" + State + '\'' +
                ", PopulationDensity=" + PopulationDensity +
                ", Population=" + Population +
                ", Area=" + Area +
                '}';
    }
}
