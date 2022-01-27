package com.coaix.databaseprototype.root.bean;

import lombok.Data;

import java.util.ArrayList;

/**
 * @author LiaoWei
 * @date 2021-11-05 11:29
 */
@Data
public class Databaseinformation {
    public String databaseName;

    public ArrayList<String> tableNameList;

    public Databaseinformation(String databaseName, ArrayList<String> tableNameList) {
        System.out.println(databaseName+"_"+tableNameList);
        this.databaseName = databaseName;
        this.tableNameList = tableNameList;
    }
}
