package com.coaix.databaseprototype.bean.statement;

import lombok.Data;

/**
 * @author LiaoWei
 * @date 2021-11-02 21:25
 */
@Data
public class DropSm {

    public static String DROP = "DROP";
    public static String TABLE = "TABLE";
    public static String IF = "IF";
    public static String EXISTS = "EXISTS";
    public static String[] necessity={"DROP","TABLE"};

    /*填入项*/
    private String tableName;

    /*填入项*/
    private boolean complete;
}
