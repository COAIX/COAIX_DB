package com.coaix.databaseprototype.bean.data;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * @author LiaoWei
 * @date 2021-10-30 17:02
 */
@lombok.Data
public class Data implements Serializable {
    private ArrayList<String> dataRowArray;
}
