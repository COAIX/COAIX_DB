package com.coaix.databaseprototype.bean.statement;

import lombok.Data;

import java.io.Serializable;

/**
 * @author LiaoWei
 * @date 2021-10-30 13:17
 */
@Data
public class InsertSm implements Serializable {

    public static String INSERT = "INSERT";
    public static String INTO = "INTO";
    public static String VALUES = "VALUES";
    public static String[] necessity={"INSERT","INTO","VALUES"};

    /*填入项*/
    private String tableName;

    /*填入项*/
    private boolean complete;

    /*填入项*/
    private String[] columnName;

    /*填入项*/
    private String[] insertValue;
}
