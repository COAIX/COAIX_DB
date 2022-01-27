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
    public static String WHERE = "WHERE";
    public static String ALL_SIGN= "*";
    public static String AND="AND";
    public static String[] necessity={"SELECT","FROM"};
    public static String JOINSELECT_SIGN="JOINSELECT";
    public static String FIELDSELECT_SIGN="FIELDSELECT_SIGN";
    private boolean ANDSIGNAL= false;

    /*填入项*/
    private String[] tableName;

    /*填入项*/
    private boolean complete;

    /*填入项*/
    private String[] columnName = null;

    /*填入项*/
    public String selectType="null";

    private String ConditionColumn1=null;
    /*填入项*/
    private String ConditionColumn2=null;

    private String ANDConditionColumn1=null;
    /*填入项*/
    private String ANDConditionColumn2=null;


}
