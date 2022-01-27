package com.coaix.databaseprototype.bean.statement;

import lombok.Data;

/**
 * @author LiaoWei
 * @date 2021-11-02 11:20
 */
@Data
public class AlterSm {
    public static String ALTER = "ALTER";
    public static String TABLE = "TABLE";
    public static String ADD = "ADD";
    public static String COLUMN = "COLUMN";
    public static String DROP = "DROP";
    public static String[] necessity={"ALTER","TABLE","ADD","DROP","COLUMN"};

    /*填入项*/
    private String tableName;

    /*填入项*/
    private boolean complete;

    /*填入项*/
    private String columnName;

    /*填入项*/
    private String type;

    /*填入项*/
    private String addOrdrop;
}
