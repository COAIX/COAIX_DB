package com.coaix.databaseprototype.tools;

import com.coaix.databaseprototype.bean.data.Data;
import com.coaix.databaseprototype.bean.statement.UseSm;
import com.coaix.databaseprototype.root.bean.Databaseinformation;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author LiaoWei
 * @date 2021-10-30 16:03
 */
public class FileTools{

    public static String RESOURCES = "C:\\Users\\Administrator\\Desktop\\数据库概论\\DatabaseWeb\\src\\main\\resources\\";

    public static String USESMPATH = "C:\\Users\\Administrator\\Desktop\\数据库概论\\DatabaseWeb\\src\\main\\resources\\static\\STOREUSM";

    public static String DICTIONARY = "dataDictionary\\";

    public static String DATABASEDATA = "dataBaseData\\";

    public static String INDEX = "INDEX\\";
    /**
     * (!覆盖写入)将对象T写入路径为filename的文件中(filename)
     * @author LiaoWei
     * @date 2021/10/30 16:45
     * @param t
     * @param fileName
     */
    public static <T> void fileWriter(T t,String fileName){
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

    /**
     * 从文件路径为fileName的文件中读出对象
     * @author LiaoWei
     * @date 2021/10/30 16:48
     * @param t
     * @param fileName
     * @return T
     */
    public static <T> T fileReader(T t,String fileName){
        ObjectInputStream is = null;
        T object = null;
        try {
//            C:\Users\Administrator\Desktop\数据库概论\Database\src\main\resources\test.txt
            // 定位与建立管道
            is = new ObjectInputStream(new FileInputStream(fileName));
            // 操作管道
            object = (T)is.readObject();// 读到的是Obj类对象，需要强制转换
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

    @Test
    public void test1(){
//        CreateSm createSm = FileTools.fileReader(new CreateSm(), FileTools.RESOURCES+"test.txt");
//        System.out.println(createSm);
//        DeleteSm deleteSm = new DeleteSm();
//        deleteSm.setTableName("BOOKS");
//        FileTools.fileWriter(deleteSm,FileTools.RESOURCES+FileTools.DICTIONARYURL+"employee\\test2.txt");
//        DeleteSm deleteSm1 = FileTools.fileReader(new DeleteSm(), "test2.txt");
//        System.out.println(deleteSm1);

//        File file = new File(FileTools.RESOURCES+FileTools.DICTIONARYURL+"employee");
//        file.mkdirs();
//                System.out.println(file.exists());

        File file = new File(FileTools.RESOURCES + "test1.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileTools.fileWriter(new ArrayList<Data>(),FileTools.RESOURCES + "test1.txt");

        ArrayList<Data> dates = FileTools.fileReader(new ArrayList<Data>(), FileTools.RESOURCES + "test1.txt");
        dates.forEach(System.out::println);

    }

    /**
     * 递归删除
     * 删除某个目录及目录下的所有子目录和文件
     * @param file 文件或目录
     * @return 删除结果
     */
    public static boolean delFiles(File file){
        boolean result = false;
        //目录
        if(file.isDirectory()){
            File[] childrenFiles = file.listFiles();
            for (File childFile:childrenFiles){
                result = delFiles(childFile);
                if(!result){
                    return result;
                }
            }
        }
        //删除 文件、空目录
        result = file.delete();
        return result;
    }

    /**
     * 获取一个文件夹下的所有文件名
     * @author LiaoWei
     * @date 2021/11/3 22:38
     * @param path 文件路径
     * @return java.util.List<java.lang.String>
     */
    public static List<String> getFilesNameList(String path) {
        List<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                files.add(tempList[i].getName());
                //文件名，不包含路径
                //String fileName = tempList[i].getName();
            }
            if (tempList[i].isDirectory()) { //如果是文件目录
                //这里就不递归了
            }
        }
        return files;
    }

    /**
     * 获取指定路径下所有文件夹名字
     * @author LiaoWei
     * @date 2021/11/5 11:22
     * @param path
     * @return java.util.List<java.lang.String>
     */
    public static List<String> getFileDirectoryName(String path){
        List<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
//                files.add(tempList[i].getName());
                //文件名，不包含路径
                //String fileName = tempList[i].getName();
            }
            if (tempList[i].isDirectory()) { //如果是文件目录
                //这里就不递归了
                if ( !("static".equals(tempList[i].getName()) || "templates".equals(tempList[i].getName())) ) {
                    files.add(tempList[i].getName());
                }
            }
        }
        return files;
    }

    public static ArrayList<Databaseinformation> getDatabaseinfromation(){

        ArrayList<Databaseinformation> databaseinformations = new ArrayList<>();

        ArrayList<String> fileDirectoryName = (ArrayList<String>) FileTools.getFileDirectoryName(FileTools.RESOURCES);

        fileDirectoryName.forEach(
                (String databaseName) ->
                {
                    List<String> filesNameList = FileTools.getFilesNameList(FileTools.RESOURCES + databaseName + "\\" + FileTools.DICTIONARY);
                    databaseinformations.add(new Databaseinformation(databaseName, (ArrayList<String>) filesNameList));
                }
        );
        return databaseinformations;
    }


    @Test
    public void test31(){
        List<String> fileDirectoryName = FileTools.getFileDirectoryName(FileTools.RESOURCES);
        System.out.println(fileDirectoryName);
        ArrayList<Databaseinformation> databaseinfromation = FileTools.getDatabaseinfromation();
        databaseinfromation.forEach(System.out::println);
    }

    @Test
    public void test33(){
        UseSm useSm = new UseSm();
        useSm.setName("BOOKS");
        useSm.setComplete(true);
        FileTools.fileWriter(useSm,FileTools.USESMPATH);
    }
}
