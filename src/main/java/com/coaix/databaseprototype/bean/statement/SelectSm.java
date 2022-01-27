package com.coaix.databaseprototype.bean.statement;

import lombok.Data;

/**
 * @author LiaoWei
 * @date 2021-11-03 16:21
 */
@Data
public class SelectSm {

    public static String SELECT = "SELECT";
    public static String FROM = "FROM";
    public static String ALL_SIGN= "*";
    public static String[] necessity={"SELECT","FROM"};

    /*填入项*/
    private String tableName;

    /*填入项*/
    private boolean complete;

    /*填入项*/
    private String[] columnName = null;

}
