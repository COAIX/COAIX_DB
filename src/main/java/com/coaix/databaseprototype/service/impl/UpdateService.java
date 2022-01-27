package com.coaix.databaseprototype.service.impl;

import com.coaix.databaseprototype.bean.data.ColumnAttributes;
import com.coaix.databaseprototype.bean.data.Data;
import com.coaix.databaseprototype.bean.statement.UpdateSm;
import com.coaix.databaseprototype.bean.statement.UseSm;
import com.coaix.databaseprototype.service.Service;
import com.coaix.databaseprototype.tools.FileTools;
import com.coaix.databaseprototype.tools.IndexTools;
import com.coaix.databaseprototype.tools.UpdateTools;
import java.io.File;
import java.util.ArrayList;

/**
 * @author LiaoWei
 * @date 2021-11-04 16:56
 */
public class UpdateService implements Service {

    private UpdateSm updateSm;

    private UseSm useSm;

    private UpdateTools updateTools;

    public UpdateService(UpdateSm updateSm,UseSm useSm){
        this.updateSm=updateSm;
        this.useSm=useSm;
    }


    @Override
    public void runService() {
        /*判断是否选择了库*/
            /*选择了库*/
        if ( useSm.isComplete() ) {
            /*检查表是否存在*/
            String tableDictionaryPath = FileTools.RESOURCES+useSm.getName()+"\\"+FileTools.DICTIONARY+updateSm.getTableName();
            String tableDatabasePath = FileTools.RESOURCES+useSm.getName()+"\\"+FileTools.DATABASEDATA+updateSm.getTableName();
            File tableDictionaryFile = new File(tableDictionaryPath);
            File tableDatabaseFile = new File(tableDatabasePath);
            /*存在*/
            if(tableDictionaryFile.exists() && tableDatabaseFile.exists()) {
                /*将表数据文件读出来，ArrayList<ColumnAttributes>，如果ArrayList<ColumnAttributes>长度为0直接退出*/
                ArrayList<ColumnAttributes> columnAttributesArrayList = FileTools.fileReader(new ArrayList<ColumnAttributes>(), tableDictionaryPath);
                ArrayList<Data> dataArrayList = FileTools.fileReader(new ArrayList<Data>(), tableDatabasePath);
                if ( columnAttributesArrayList.size() == 0  ){
                    return;
                }
                /*判断更改数据列是否存在*/
                int checkExist = checkExist(columnAttributesArrayList, updateSm.getChangeData()[0]);
                if ( checkExist != -1 ) {
                    /*更改数据列存在*/
                    /*判断是否有更新条件*/
                    ArrayList<Data> newData = new ArrayList<>();
                    if (updateSm.getRequirement() != null) {
                        /*有更新条件*/
                        /*判定更新条件列是否存在*/
                        int checkExistRequire = checkExist(columnAttributesArrayList, updateSm.getRequirement()[0]);
                        if ( checkExistRequire != -1 ) {
                            /*更新条件列存在*/
                            /*遍历数据文件，当满足条件的才更新成新值*/
                            dataArrayList.forEach(
                                    (Data data) -> {
                                    if ( data.getDataRowArray().get(checkExistRequire).equals(updateSm.getRequirement()[1]) ){
                                        data.getDataRowArray().remove(checkExist);
                                        data.getDataRowArray().add(checkExist,updateSm.getChangeData()[1]);
                                    }
                                    newData.add(data);
                                }
                            );
                        }else {
                            /*不存在*/
                            /*输出条件属性出错，return*/
                            System.out.println("---"+updateSm.getTableName()+"---"+updateSm.getRequirement()[0]+"---NOT_EXIST---");
                            return;
                        }
                    }else {
                        /*没有更新条件*/
                        /*便利数据文件，将每一行更新*/
                        dataArrayList.forEach(
                                (Data data) -> {
                                data.getDataRowArray().remove(checkExist);
                                data.getDataRowArray().add(checkExist,updateSm.getChangeData()[1]);
                                newData.add(data);
                            }
                        );
                    }
                    FileTools.fileWriter( newData , tableDatabasePath );
                    IndexTools.refreshIndex(useSm,updateSm.getTableName());

                }else {
                    /*更改数据列存在不存在*/
                    /*输出更改数据列不存在，return*/
                    System.out.println("-----------更改数据列不存在----------");
                    return;
                }
            }else {
                /*不存在*/
                /*提示表不存在*/
                System.out.println("-----------表不存在----------");
            }

        }else {
            /*没有选择库*/
            /*提示库选择错误，return*/
            System.out.println("--------------库选择错误--------------");
            return;
        }

    }

    /**
     * 判断是否存在该列属性，存在返回列下标，不存在返回-1
     * @author LiaoWei
     * @date 2021/11/4 20:12
     * @param columnNameArrayList ArryaList<ColumnAttributes> 属性ArrayList集合
     * @param columnName 属性名
     * @return int
     */
    private int checkExist(ArrayList<ColumnAttributes> columnNameArrayList, String columnName) {
        for (int i = 0; i < columnNameArrayList.size(); i++) {
            if ( columnNameArrayList.get(i).getColumnName().equals(columnName) ) {
                return i;
            }
        }
        return -1;
    }

}
