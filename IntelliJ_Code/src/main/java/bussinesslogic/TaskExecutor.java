package com.farm.hub.bussinesslogic;

import com.farm.hub.callbackhandler.ITaskCompleteListener;
import com.farm.hub.downloader.DataDownloader;
import com.farm.hub.downloader.IDataDownloader;
import com.farm.hub.callbackhandler.IErrorHandler;
import com.farm.hub.writer.DataWriter;
import com.farm.hub.writer.IDataWriter;

import java.io.IOException;

/**
 * Main class to start the flow of writing text file data to local CSV file.
 */
public class TaskExecutor implements ITaskCompleteListener, IErrorHandler {

    /**
     * Reference to hold DataDownloader instance.
     */
    private IDataDownloader mDataDownloader;

    /**
     * Main method to start the execution of code.
     */
    public static void main(String[] args) {
        TaskExecutor executor = new TaskExecutor();
        try {
            executor.executeTask();
        } catch (IOException | IllegalArgumentException e) {
            executor.onErrorOccurred(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Method to start the main execution flow of download weather text files
     *
     * @throws IOException during downloading of data.
     */
    private void executeTask() throws IOException {
        mDataDownloader = new DataDownloader(this, this);
        mDataDownloader.downloadData();
    }

    @Override
    public void onTaskCompletion(TaskType taskType, String successMessage) {
        switch (taskType) {
            case DOWNLOAD_DATA:
                System.out.println(successMessage);
                IDataWriter dataWriter = new DataWriter(this, this);
                dataWriter.writeDataToFile(mDataDownloader.getDownloadedData());
                break;
            case WRITE_TO_FILE:
                System.out.println(successMessage);
                break;
            default:
        }
    }

    @Override
    public void onErrorOccurred(String errorMessage) {
        System.out.println(errorMessage);
    }
}
