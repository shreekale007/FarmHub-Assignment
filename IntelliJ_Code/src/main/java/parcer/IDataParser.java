package com.farm.hub.parcer;

import com.farm.hub.models.RegionUrlsModel;
import com.farm.hub.models.TextFileDataModel;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.TreeMap;

/**
 * Interface to provide parsing methods to web page source and parse url's text file data.
 */
public interface IDataParser {
    /**
     * Method to parse the web page source and find URLs associated in it.
     *
     * @return list of RegionUrlsModels
     */
    List<RegionUrlsModel> parseWebPageData(Document webPageSource);

    /**
     * Method to parse the web page source and find URLs associated in it.
     *
     * @return list of RegionUrlsModels
     */
    TextFileDataModel parseTextFileData(Document textFileSource, String regionCode, String weatherParamUrl);
}
