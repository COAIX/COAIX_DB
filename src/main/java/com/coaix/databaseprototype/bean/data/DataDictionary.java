package com.coaix.databaseprototype.bean.data;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 数据字典
 * @author LiaoWei
 * @date 2021-10-30 16:51
 */
@Data
public class DataDictionary implements Serializable {
    /*表属性*/
    private ArrayList<ColumnAttributes> columnArray;
}
