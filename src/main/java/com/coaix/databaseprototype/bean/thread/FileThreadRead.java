package com.coaix.databaseprototype.bean.thread;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * @author LiaoWei
 * @date 2021-11-01 15:09
 */
public class FileThreadRead {

    public static String RESOURCES = "C:\\Users\\Administrator\\Desktop\\数据库概论\\Database\\src\\main\\resources\\";

    public static String DICTIONARY = "dataDictionary\\";

    public static String DATABASEDATA = "dataBaseData\\";

    public static String INDEX = "INDEX\\";

    public synchronized static <T> T fileReader(T t,String fileName) {

        synchronized (FileThreadWrite.class) {

            ObjectInputStream is = null;
            T object = null;
            try {
//            C:\Users\Administrator\Desktop\数据库概论\Database\src\main\resources\test.txt
                // 定位与建立管道
                is = new ObjectInputStream(new FileInputStream(fileName));
                // 操作管道
                object = (T) is.readObject();// 读到的是Obj类对象，需要强制转换
//            System.out.println(object);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return object;
        }

    }
}
