package com.coaix.databaseprototype.root.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author LiaoWei
 * @date 2021-11-05 12:28
 */
@Data
public class TableData {
    public ArrayList<String> columnNameList;
    public ArrayList<HashMap<String,String>> data;

    public TableData(ArrayList<String> columnNameList, ArrayList<HashMap<String, String>> data) {
        this.columnNameList = columnNameList;
        this.data = data;
    }
}
