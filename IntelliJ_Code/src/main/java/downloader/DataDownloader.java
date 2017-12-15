package com.farm.hub.downloader;

import com.farm.hub.callbackhandler.ITaskCompleteListener;
import com.farm.hub.bussinesslogic.TaskType;
import com.farm.hub.constants.Constants;
import com.farm.hub.callbackhandler.IErrorHandler;
import com.farm.hub.models.RegionUrlsModel;
import com.farm.hub.models.TextFileDataModel;
import com.farm.hub.parcer.DataParser;
import com.farm.hub.parcer.IDataParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to start the parsing of web page, find associated urls and parse url's text file data.
 */
public class DataDownloader implements IDataDownloader, IErrorHandler {

    /**
     * Reference to hold instance of list of TextFileDataModel.
     */
    private List<TextFileDataModel> mTextFileDataModelList;
    /**
     * Reference to provide callbacks of ITaskCompleteListener.
     */
    private ITaskCompleteListener mTaskCompleteListener;
    /**
     * Reference to provide callbacks of IErrorHandler.
     */
    private IErrorHandler mErrorHandlerListener;

    /**
     * Constructor to init DataDownloader instance with listeners.
     *
     * @param taskCompleteListener listener to provide task completion callback
     * @param errorHandlerListener listener to provide error occurred callback
     */
    public DataDownloader(ITaskCompleteListener taskCompleteListener, IErrorHandler errorHandlerListener) {
        mTaskCompleteListener = taskCompleteListener;
        mErrorHandlerListener = errorHandlerListener;
    }

    @Override
    public void onErrorOccurred(String errorMessage) {

    }

    @Override
    public void downloadData() {
        try {
            //Get the page source for the web page using Jsoup.
            Document webPageDoc = Jsoup.connect(Constants.WEATHER_DATA_URL).get();
            IDataParser dataParser = new DataParser(this);
            List<RegionUrlsModel> regionUrlsModelList = dataParser.parseWebPageData(webPageDoc);

            //Get the page source for the each text file using Jsoup.
            mTextFileDataModelList = new ArrayList<>();
            for (RegionUrlsModel regionUrlsModel : regionUrlsModelList) {
                for (String regionParamUrl : regionUrlsModel.getWeatherParamUrl()) {
                    Document textFileDoc = Jsoup.connect(regionParamUrl).get();
                    mTextFileDataModelList.add(dataParser.parseTextFileData(textFileDoc, regionUrlsModel.
                            getRegionCode(), regionParamUrl));
                }
            }

            //Provide the task completion callback to subscriber.
            mTaskCompleteListener.onTaskCompletion(TaskType.DOWNLOAD_DATA,
                    "Downloading of text file data has been completed successfully. \n" +
                            "Starting writing of data to local file.");

        } catch (IOException e) {
            //Provide the error occurred callback to subscriber.
            mErrorHandlerListener.onErrorOccurred("Download of weather data failed due to: \n" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<TextFileDataModel> getDownloadedData() {
        return mTextFileDataModelList;
    }
}
