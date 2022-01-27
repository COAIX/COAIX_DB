package com.coaix.databaseprototype.tools;


import com.coaix.databaseprototype.bean.statement.UseSm;
import org.apache.log4j.Logger;
import org.junit.Test;
import sun.net.smtp.SmtpClient;

import java.io.File;

/**
 * @author LiaoWei
 * @date 2021-10-31 10:32
 */
public class UseTools {
    public static UseSm returnSm(String[] split) {
        UseSm useSm = new UseSm();
        /*检查语句*/
            /*如果语句不缺失成分或无多余*/
        if (StringTools.checkAllString(UseSm.necessity, split)) {
            useSm.setComplete(true);
            useSm.setName(
                    split[
                            StringTools.checkIndex(split, UseSm.USE) + 1
                            ]
            );
            String dataBasePath = FileTools.RESOURCES+useSm.getName();
            File dataBase = new File(dataBasePath);
            if(!dataBase.exists()) {
                useSm.setComplete(false);
            }
        }
            /*如果语句缺失成分或有多余*/
        else {
            useSm.setComplete(false);
        }
        return useSm;
    }

    @Test
    public void test1(){
        Object use_employee = SmTools.getTypeSm("use employee");
        System.out.println(use_employee.getClass().equals(UseSm.class));
        System.out.println(use_employee);
    }
}
