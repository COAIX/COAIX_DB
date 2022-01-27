package com.coaix.databaseprototype.bean.data;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 列属性
 * @author LiaoWei
 * @date 2021-10-30 16:58
 */
@Data
public class ColumnAttributes implements Serializable {

    /*列名*/
    private String columnName;

    private String type;
    /*约束*/
    private ArrayList<String> bind;

}
