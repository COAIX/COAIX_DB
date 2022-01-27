package com.coaix.databaseprototype.bean.statement;

import com.coaix.databaseprototype.bean.data.ColumnAttributes;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author LiaoWei
 * @date 2021-10-30 13:16
 */
@Data
public class CreateSm implements Serializable {

    public static String CREATE = "CREATE";
    public static String DATABASE = "DATABASE";
    public static String INDEX = "INDEX";
    public static String TABLE = "TABLE";
    public static String ON = "ON";
    public static String[] necessity={"CREATE","DATABASE","TABLE"};

    /*填入项*/
    private String name;

    /*填入项*/
    private boolean complete;

    /*填入项*/
    private String dataOrtable;

    /*填入项 - > 是否为创建索引，默认为false*/
    private boolean isIndex = false;

    /*填入项 - > 表中索引列名*/
    private String indexColumnName;

    /*填入项*/
    private ArrayList<ColumnAttributes> columnAttributes;



}
