package com.coaix.databaseprototype.service.impl;
import com.coaix.databaseprototype.bean.data.ColumnAttributes;
import com.coaix.databaseprototype.bean.data.Data;
import com.coaix.databaseprototype.bean.statement.AlterSm;
import com.coaix.databaseprototype.bean.statement.UseSm;
import com.coaix.databaseprototype.service.Service;
import com.coaix.databaseprototype.tools.AlterTools;
import com.coaix.databaseprototype.tools.FileTools;
import java.io.File;
import java.util.ArrayList;

/**
 * @author LiaoWei
 * @date 2021-11-02 12:08
 */
public class AlterService implements Service {

    private AlterSm alterSm;

    private UseSm useSm;

    private AlterTools alterTools;

    public AlterService(AlterSm alterSm,UseSm useSm){
        this.alterSm=alterSm;
        this.useSm=useSm;
    }

    @Override
    public void runService() {
        /*判断是否选择了库*/
        if(useSm.isComplete()) {
            /*选择了库*/
            /*从库文件中读出数据字典*/
            String dictionaryPath= FileTools.RESOURCES+useSm.getName()+"\\"+FileTools.DICTIONARY+alterSm.getTableName();
            /*判断文件是否存在*/
            File file = new File(dictionaryPath);
            if(file.exists()) {
                ArrayList<ColumnAttributes> columnNameArrayList = FileTools.fileReader(new ArrayList<>(), dictionaryPath);
                /*判断是ADD还是DROP*/
                if (alterSm.getAddOrdrop().equals(AlterSm.ADD)) {
                    /*ADD*/
                    /*判断是否已经存在属性*/
                    if(checkExist(columnNameArrayList, alterSm) == -1) {
                        /*尾插入数据字典文件中，写入文件*/
                        ColumnAttributes columnAttributes = new ColumnAttributes();
                        columnAttributes.setType(alterSm.getType());
                        columnAttributes.setColumnName(alterSm.getColumnName());
                        columnNameArrayList.add(columnAttributes);
                        FileTools.fileWriter(columnNameArrayList, dictionaryPath);
                        /*将新列的数据值统统置为null*/
                        String dataBasePath = FileTools.RESOURCES+useSm.getName()+"\\"+FileTools.DATABASEDATA+alterSm.getTableName();

                        ArrayList<Data> dataArrayList = FileTools.fileReader(new ArrayList<Data>(), dataBasePath);
                        newColumnSetNull(dataBasePath, dataArrayList);

                    }else{
                        System.out.println("----------属性名已存在-----------");
                    }
                } else {
                    /*DROP*/
                    /*判断数据字典中是否存在相应属性*/
                    int checkIndex = checkExist(columnNameArrayList, alterSm);
                    if (checkIndex != -1) {
                        /*存在*/
                        /*删除对应属性，写回文件*/
                        columnNameArrayList.remove(checkIndex);
                        FileTools.fileWriter(columnNameArrayList, dictionaryPath);
                        /*删除数据文件中该字段名的数据*/
                        String databasePath = FileTools.RESOURCES+useSm.getName()+"\\"+FileTools.DATABASEDATA+alterSm.getTableName();
                        /*判断文件长度*/
                        if(new File(databasePath).length() == 0) {
                            /*长度为0，无操作，return*/
                            return;
                        }else {
                            /*长度不为0，执行删除数据操作*/
                            ArrayList<Data> dataArrayList = FileTools.fileReader(new ArrayList<Data>(), databasePath);
                            deleteDataColumn(checkIndex, databasePath, dataArrayList);
                        }
                    } else {
                        /*不存在*/
                        /*输出属性不存在，return*/
                        System.out.println("---------属性不存在---------");
                    }
                }
            }else{
                System.out.println("----------表不存在-----------");
            }
        }else {
            /*没选择*/
            /*输出未选择库，return*/
            System.out.println("---------库选择错误----------");
        }

    }

    /**
     * 将新列的数据值统统置为null
     * @author LiaoWei
     * @date 2021/11/2 21:18
     * @param dataBasePath
     * @param dataArrayList
     */
    private void newColumnSetNull(String dataBasePath, ArrayList<Data> dataArrayList) {
        for (Data data : dataArrayList) {
            data.getDataRowArray().add("null");
        }
        FileTools.fileWriter(dataArrayList,dataBasePath);
    }

    private void deleteDataColumn(int checkIndex, String databasePath, ArrayList<Data> dataArrayList) {
        for (int i = 0; i < dataArrayList.size(); i++) {
            dataArrayList.get(i).getDataRowArray().remove(checkIndex);
        }
        FileTools.fileWriter(dataArrayList,databasePath);
    }

    /**
     * 判断数据字典中是否存在相应属性，返回相应下标，如果不存在相应属性，返回-1
     * @author LiaoWei
     * @date 2021/11/2 16:32
     * @param columnNameArrayList
     * @param alterSm
     * @return boolean
     */
    private int checkExist(ArrayList<ColumnAttributes> columnNameArrayList,AlterSm alterSm) {
        for (int i = 0; i < columnNameArrayList.size(); i++) {
            if ( columnNameArrayList.get(i).getColumnName().equals(alterSm.getColumnName()) ) {
                return i;
            }
        }
        return -1;
    }
}
