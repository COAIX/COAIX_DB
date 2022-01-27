package com.coaix.databaseprototype.bean.statement;

import lombok.Data;

/**
 * @author LiaoWei
 * @date 2021-11-04 16:35
 */
@Data
public class UpdateSm {

    public static String UPDATE = "UPDATE";
    public static String SET = "SET";
    public static String[] necessity={"UPDATE","SET"};
    public static String WHERE = "WHERE";


    /*填入项*/
    private String tableName;

    /*填入项*/
    private boolean complete;

    /*填入项*/
    private String[] changeData;

    /*填入项*/
    private String[] requirement = null;

}
