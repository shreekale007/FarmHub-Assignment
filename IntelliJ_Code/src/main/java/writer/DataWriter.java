package com.farm.hub.writer;

import com.farm.hub.callbackhandler.ITaskCompleteListener;
import com.farm.hub.bussinesslogic.TaskType;
import com.farm.hub.constants.Constants;
import com.farm.hub.callbackhandler.IErrorHandler;
import com.farm.hub.models.TextFileDataModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Class used to write the weather param text file data to the local CSV file.
 */
public class DataWriter implements IDataWriter {

    /**
     * Reference to provide callbacks of ITaskCompleteListener.
     */
    private ITaskCompleteListener mTaskCompleteListener;
    /**
     * Reference to provide callbacks of IErrorHandler.
     */
    private IErrorHandler mErrorHandlerListener;

    /**
     * Constructor to init DataWriter instance with listeners.
     *
     * @param taskCompleteListener listener to provide write completion callback
     * @param errorHandlerListener listener to provide error occurred callback during file write
     */
    public DataWriter(ITaskCompleteListener taskCompleteListener, IErrorHandler errorHandlerListener) {
        mTaskCompleteListener = taskCompleteListener;
        mErrorHandlerListener = errorHandlerListener;
    }

    @Override
    public boolean writeDataToFile(List<TextFileDataModel> textFileDataModels) {
        FileWriter fileWriter = null;
        String workingDir = "";
        try {
            //Logic to write the already separated cells from the server text file to the local CSV file.
            workingDir = System.getProperty(Constants.USER_DIR);
            fileWriter = new FileWriter(workingDir + File.separator + Constants.FILE_NAME);
            for (TextFileDataModel textFileDataModel : textFileDataModels) {
                for (List<String> eachRowCellDataList : textFileDataModel.getCellDataList()) {
                    for (String eachCellDataList : eachRowCellDataList) {
                        fileWriter.append(eachCellDataList);
                        fileWriter.append(Constants.COMMA_DELIMITER);
                    }
                    fileWriter.append(Constants.NEW_LINE_SEPARATOR);
                }
                fileWriter.append(Constants.NEW_LINE_SEPARATOR);
            }
        } catch (IOException e) {
            mErrorHandlerListener.onErrorOccurred("Error in CsvFileWriter due to: \n" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
                mTaskCompleteListener.onTaskCompletion(TaskType.WRITE_TO_FILE,
                        "Writing text file data to CSV file has been completed successfully. \n" +
                                "Check the output file " + Constants.FILE_NAME + " at location: " + workingDir);
            } catch (IOException e) {
                mErrorHandlerListener.onErrorOccurred("Error while flushing/closing fileWriter due to: " +
                        "\n" + e.getMessage());
                e.printStackTrace();
            }
        }

        return true;
    }
}
