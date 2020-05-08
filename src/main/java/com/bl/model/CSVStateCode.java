package com.bl.model;

import com.opencsv.bean.CsvBindByName;

public class CSVStateCode {
        @CsvBindByName(column = "State Name", required = true)
        public String state;

        @CsvBindByName(column = "StateCode", required = true)
        public String stateCode;
}
