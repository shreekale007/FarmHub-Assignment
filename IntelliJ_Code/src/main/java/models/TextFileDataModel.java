package com.farm.hub.models;

import java.util.List;

/**
 * Model class to hold the each cell data of text file. This will be used by writer to write data to CSV file.
 */
public class TextFileDataModel {
    /**
     * Reference to hold list of list of cell data.
     */
    List<List<String>> mCellDataList;

    public TextFileDataModel() {
    }

    public TextFileDataModel(List<List<String>> cellDataList) {
        this.mCellDataList = cellDataList;
    }

    public List<List<String>> getCellDataList() {
        return mCellDataList;
    }

    public void setCellDataList(List<List<String>> cellDataList) {
        this.mCellDataList = cellDataList;
    }

}
