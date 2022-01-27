package com.coaix.databaseprototype.webbean;

import lombok.Data;

/**
 * @author LiaoWei
 * @date 2021-11-04 23:16
 */
@Data
public class testName {

    public String name;

    public String no;

    public testName(String name, String no) {
        this.name = name;
        this.no = no;
    }
}
