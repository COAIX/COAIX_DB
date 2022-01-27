package com.coaix.databaseprototype.bean.thread;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * @author LiaoWei
 * @date 2021-11-01 15:09
 */
public class FileThreadWrite {


    public static String RESOURCES = "C:\\Users\\Administrator\\Desktop\\数据库概论\\Database\\src\\main\\resources\\";

    public static String DICTIONARY = "dataDictionary\\";

    public static String DATABASEDATA = "dataBaseData\\";

    public static String INDEX = "INDEX\\";

    public synchronized static <T> void fileWriter(T t,String fileName) {

        synchronized (FileThreadRead.class) {

            ObjectOutputStream os = null;
            try {
//            C:\Users\Administrator\Desktop\数据库概论\Database\src\main\resources\test.txt
                // 定位与建立管道
                os = new ObjectOutputStream(new FileOutputStream(fileName));
                // 操作管道
                os.writeObject(t);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
