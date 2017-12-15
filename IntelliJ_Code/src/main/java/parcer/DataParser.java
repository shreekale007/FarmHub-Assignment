package com.farm.hub.parcer;

import com.farm.hub.constants.Constants;
import com.farm.hub.callbackhandler.IErrorHandler;
import com.farm.hub.models.RegionUrlsModel;
import com.farm.hub.models.TextFileDataModel;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to parse the web page source and find URLs associated in it. Also used to parse url's text file data.
 */
public class DataParser implements IDataParser {

    /**
     * Reference to provide callbacks of IErrorHandler.
     */
    private IErrorHandler mErrorHandlerListener;

    /**
     * Constructor to init DataParser instance with listeners.
     *
     * @param errorHandlerListener listener to provide error occurred callback
     */
    public DataParser(IErrorHandler errorHandlerListener) {
        mErrorHandlerListener = errorHandlerListener;
    }

    @Override
    public List<RegionUrlsModel> parseWebPageData(Document webPageSource) {
        Elements tableElements = webPageSource.select("table");
        List<RegionUrlsModel> regionUrlsModelList = new ArrayList<>();

        //Iterate through the tables found on the web page to get the desired table.
        for (int tableIndex = 0; tableIndex < tableElements.size(); tableIndex++) {
            Elements tableRowElements = tableElements.get(tableIndex).select("tr");
            String strTableRows = tableRowElements.html().toString();
            //Skip the iteration for the table having rank wise data
            if (strTableRows.contains("Tmax/ranked/") && strTableRows.contains("Tmin/ranked/") &&
                    !strTableRows.contains("/date/")) {
                continue;
            }

            //Separate out the region code, weather params and urls from the page source for the table having year wise data
            for (int tableRowIndex = Constants.MIN_NUMBER_OF_REGION; tableRowIndex < Constants.MAX_NUMBER_OF_REGION;
                 tableRowIndex++) {
                RegionUrlsModel regionUrlsModel = new RegionUrlsModel();

                //Get the region code for urls and set it in model
                String regionCode = tableRowElements.get(tableRowIndex).getElementsByTag("strong").text();
                regionUrlsModel.setRegionCode(regionCode);

                //Get the urls for region code and set it in model
                Elements weatherParamUrlElements = tableRowElements.get(tableRowIndex).getElementsByTag("a");
                List<String> weatherParamUrlList = new ArrayList<>();
                for (int weatherParamIndex = Constants.MIN_NUMBER_OF_WEATHER_PARAMS; weatherParamIndex <
                        Constants.MAX_NUMBER_OF_WEATHER_PARAMS; weatherParamIndex++) {
                    String weatherParamUrl = weatherParamUrlElements.get(weatherParamIndex).attr("href");
                    weatherParamUrlList.add(weatherParamUrl);
                }
                regionUrlsModel.setWeatherParamUrl(weatherParamUrlList);

                regionUrlsModelList.add(regionUrlsModel);
            }
        }
        return regionUrlsModelList;
    }

    @Override
    public TextFileDataModel parseTextFileData(Document textFileSource, String regionCode, String weatherParamUrl) {
        String strTextFileData = textFileSource.getElementsByTag("body").text();
        strTextFileData = strTextFileData.split("/2017. ")[1].replace("---", "N/A");
        String[] arrCellInFile = strTextFileData.split(" ");

        //Init the local variables used for parsing of text file data and separate each cell data.
        String weatherParamHeader = weatherParamUrl.split("datasets/")[1].split("/")[0];
        int rowCellNumbers = 18;
        int lineIndexMultiplier = 1;
        int multiplierOffset = 1;
        TextFileDataModel textFileDataModel = new TextFileDataModel();
        List<List<String>> mAllCellDataList = new ArrayList<>();
        List<String> eachRowCellDataList = new ArrayList<>();
        eachRowCellDataList.add(Constants.REGION_CODE_HEADER);
        eachRowCellDataList.add(Constants.WEATHER_PARAM_HEADER);

        //Separate out the header titles from the server text file for the CSV file to be written
        for (int headerTitleIndex = 0; headerTitleIndex < rowCellNumbers; headerTitleIndex++) {
            eachRowCellDataList.add(arrCellInFile[headerTitleIndex]);
        }
        mAllCellDataList.add(eachRowCellDataList);
        eachRowCellDataList = new ArrayList<>();
        eachRowCellDataList.add(regionCode);
        eachRowCellDataList.add(weatherParamHeader);

        //Separate out the rows and their cells from the server text file for the CSV file to be written
        for (int rowParamIndex = (rowCellNumbers * lineIndexMultiplier); rowParamIndex < (rowCellNumbers * (lineIndexMultiplier + multiplierOffset)); rowParamIndex++) {
            eachRowCellDataList.add(arrCellInFile[rowParamIndex]);
            //Increment the row index to parse next cells. This will happen only during last iteration of current row.
            if (rowParamIndex == ((rowCellNumbers * (lineIndexMultiplier + multiplierOffset)) - 1)) {
                lineIndexMultiplier++;
                mAllCellDataList.add(eachRowCellDataList);
                eachRowCellDataList = new ArrayList<>();
                eachRowCellDataList.add(regionCode);
                eachRowCellDataList.add(weatherParamHeader);
            }
            //Break the loop once you are done with iterating last row or it will go on infinite
            if ((arrCellInFile.length - rowCellNumbers) == rowParamIndex - 1) {
                break;
            }
        }
        textFileDataModel.setCellDataList(mAllCellDataList);

        return textFileDataModel;
    }
}
