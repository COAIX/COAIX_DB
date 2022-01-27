package com.coaix.databaseprototype.bean.statement;

import lombok.Data;

import java.io.Serializable;

/**
 * @author LiaoWei
 * @date 2021-10-30 13:17
 */
@Data
public class DeleteSm implements Serializable {

    public static String DELETE = "DELETE";
    public static String FROM = "FROM";
    public static String[] necessity={"DELETE","FROM"};
    public static String WHERE = "WHERE";

    /*填入项*/
    private String tableName;

    /*填入项*/
    private String[] requirement;

    /*填入项*/
    private boolean complete;

}
