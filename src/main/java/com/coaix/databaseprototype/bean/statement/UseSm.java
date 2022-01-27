package com.coaix.databaseprototype.bean.statement;

import com.coaix.databaseprototype.bean.data.ColumnAttributes;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author LiaoWei
 * @date 2021-10-31 10:31
 */
@Data
public class UseSm implements Serializable {
    public static String USE = "USE";
    public static String[] necessity={"USE"};

    /*填入项*/
    private String name;

    /*填入项*/
    private boolean complete= false;
}
