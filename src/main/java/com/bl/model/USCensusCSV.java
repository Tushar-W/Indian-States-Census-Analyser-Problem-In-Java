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

    public USCensusCSV(String state, String stateCode, int population, double populationDensity, double totalArea) {
        this.State = state;
        this.StateID = stateCode;
        this.Population = population;
        this.PopulationDensity = populationDensity;
        this.Area = totalArea;
    }

    public USCensusCSV() {
    }

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
