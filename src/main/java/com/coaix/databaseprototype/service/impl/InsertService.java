package com.coaix.databaseprototype.service.impl;

import com.coaix.databaseprototype.bean.data.ColumnAttributes;
import com.coaix.databaseprototype.bean.data.Data;
import com.coaix.databaseprototype.bean.statement.InsertSm;
import com.coaix.databaseprototype.bean.statement.UseSm;
import com.coaix.databaseprototype.service.Service;
import com.coaix.databaseprototype.tools.FileTools;
import com.coaix.databaseprototype.tools.IndexTools;
import com.coaix.databaseprototype.tools.InsertTools;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author LiaoWei
 * @date 2021-11-01 20:17
 */
public class InsertService implements Service {

    private InsertSm insertSm;

    private UseSm useSm;

    private InsertTools insertTools;

    public InsertService(InsertSm insertSm,UseSm useSm){
        this.insertSm=insertSm;
        this.useSm=useSm;
    }

    @Override
    public void runService() {
        /*判断是否选择了库*/
        if(useSm.isComplete()) {
            /*选择了库*/
            /*判断需要插入数据的表是否存在*/
            String tablePath=FileTools.RESOURCES+useSm.getName()+"\\"+FileTools.DICTIONARY+insertSm.getTableName();
            File tableFile = new File(tablePath);
            /*存在*/
            if(tableFile.exists()) {
                /*判断插入数据数据项是否和数据字典中的属性一致*/
                ArrayList<ColumnAttributes> attributes = FileTools.fileReader(new ArrayList<ColumnAttributes>(), tablePath);
                String[] insertSmColumnName = insertSm.getColumnName();
                if (check(attributes, insertSmColumnName)) {
                    /*一致，填入相应库的dataBaseData中的表名文件中，将表中文件读出来，然后尾插*/
                    String tableDataFilePath=FileTools.RESOURCES+useSm.getName()+"\\"+FileTools.DATABASEDATA+insertSm.getTableName();
                    File tableDatafile = new File(tableDataFilePath);
                        /*判断文件长度*/
                    if(tableDatafile.length()==0){
                        /*如果文件长度为0，则直接新建一个ArrayList<Data>,将数据填入Arraylist<Data>中，填入文件*/
                        ArrayList<Data> dataArrayList = new ArrayList<>();
                        tailInsertData(tableDataFilePath, dataArrayList);
                    }else{
                        /*如果文件长度不为0，则读出文件为一个ArrayList<Data>对象，新建一个Data对象，将值填入Data对象中，再尾插入ArrayList<Data>对象中，再覆盖写入原本文件*/
                        ArrayList<Data> originData = FileTools.fileReader(new ArrayList<Data>(), tableDataFilePath);
                        tailInsertData(tableDataFilePath, originData);
                        IndexTools.refreshIndex(useSm,insertSm.getTableName());
                    }
                }
                /*不一致，提示语句错误，return*/
                else{
                    System.out.println("-----------属性名错误-----------");
                }
            }
            /*不存在*/
            else {
                /*提示表不存在，return*/
                System.out.println("------------表不存在-----------");
            }
        }
        else{
            /*没选择库*/
                /*提示未选择操作库，退出*/
            System.out.println("------------库选择错误-----------");
        }
    }

    /**
     * 尾插数据
     * @author LiaoWei
     * @date 2021/11/2 10:03
     * @param tableDataFilePath 数据文件所在位置
     * @param dataArrayList 数据文件源数据文件对象
     */
    private void tailInsertData(String tableDataFilePath, ArrayList<Data> dataArrayList) {
        Data data = new Data();
        ArrayList<String> dataRowArray = new ArrayList<>(Arrays.asList(insertSm.getInsertValue()));
        data.setDataRowArray(dataRowArray);
        dataArrayList.add(data);
        FileTools.fileWriter(dataArrayList, tableDataFilePath);
    }

    /**
     * 检查insert语句和数据字典文件中的属性映射关系是否正确
     * @author LiaoWei
     * @date 2021/11/2 10:04
     * @param attributes 数据字典中属性ArrayList<ColumnAttributes>
     * @param insertSmColumnName insert语句中属性String[]数组
     * @return boolean 映射关系正确返回true，错误返回false
     */
    private boolean check(ArrayList<ColumnAttributes> attributes, String[] insertSmColumnName) {
        boolean check=false;
        for (int i = 0; i < insertSmColumnName.length; i++) {
            if(insertSmColumnName[i].equals(attributes.get(i).getColumnName())){
                check=true;
            }else{
                return false;
            }
        }
        return check;
    }
}
