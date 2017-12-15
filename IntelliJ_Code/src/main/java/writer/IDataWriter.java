package com.farm.hub.writer;

import com.farm.hub.models.TextFileDataModel;

import java.util.List;

/**
 * Interface to provide writing methods to write text file data to the local CSV file.
 */
public interface IDataWriter {
    /**
     * Method to write the text files data to the local CSV file.
     *
     * @return true, if writing file is success full. False otherwise.
     */
    boolean writeDataToFile(List<TextFileDataModel> textFileDataModels);
}
