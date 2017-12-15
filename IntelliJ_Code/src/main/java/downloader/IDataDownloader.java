package com.farm.hub.downloader;

import com.farm.hub.models.TextFileDataModel;

import java.util.List;

/**
 * Interface to provide methods to download test file data from web page source.
 */
public interface IDataDownloader {

    /**
     * Method to download test file data from web page source.
     */
    void downloadData();

    /**
     * Getter to get downloaded test file data from web page source.
     *
     * @return list of TextFileDataModels
     */
    List<TextFileDataModel> getDownloadedData();
}
