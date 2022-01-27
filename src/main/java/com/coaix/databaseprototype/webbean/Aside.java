package com.coaix.databaseprototype.webbean;

import lombok.Data;

import java.util.ArrayList;

/**
 * @author LiaoWei
 * @date 2021-11-04 22:39
 */
@Data
public class Aside {
    public String databaseName;

    public ArrayList<String> tableNameList;
}
