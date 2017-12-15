package com.farm.hub.constants;

/**
 * Constants class to hold static constants to be used for parsing web page, text file and writing parsed data to
 * local CSV file.
 */
public class Constants {
    //URL for web page where year wise weather params text files are stored
    public static final String WEATHER_DATA_URL =
            "https://www.metoffice.gov.uk/climate/uk/summaries/datasets#yearOrdered";

    //Used to find current working dir for user
    public static final String USER_DIR = "user.dir";

    //Delimiter used in CSV file
    public static final String COMMA_DELIMITER = ",";
    //Next line used in CSV file
    public static final String NEW_LINE_SEPARATOR = "\n";
    //CSV file name
    public static final String FILE_NAME = "weather.csv";

    //Max number of regions for which weather text files are to be downloaded
    public static final int MAX_NUMBER_OF_REGION = 5;
    public static final int MIN_NUMBER_OF_REGION = 1;
    //Max number of weather params for which text files are to be downloaded
    public static final int MAX_NUMBER_OF_WEATHER_PARAMS = 5;
    public static final int MIN_NUMBER_OF_WEATHER_PARAMS = 0;

    //Extra header titles for CSV file data
    public static final String REGION_CODE_HEADER = "Region_Code";
    public static final String WEATHER_PARAM_HEADER = "Weather_Param";
}
