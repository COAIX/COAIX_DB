package com.coaix.databaseprototype.service.impl;

import com.coaix.databaseprototype.bean.data.ColumnAttributes;
import com.coaix.databaseprototype.bean.data.Data;
import com.coaix.databaseprototype.bean.statement.SelectSm;
import com.coaix.databaseprototype.bean.statement.UseSm;
import com.coaix.databaseprototype.service.Service;
import com.coaix.databaseprototype.tools.FileTools;
import com.coaix.databaseprototype.tools.SelectTools;
import com.coaix.databaseprototype.tools.TablePrintTools;

import java.io.File;
import java.util.ArrayList;

/**
 * @author LiaoWei
 * @date 2021-11-03 16:36
 */
public class SelectService implements Service {

    private SelectSm selectSm;

    private UseSm useSm;

    private SelectTools selectTools;

    public SelectService(SelectSm selectSm,UseSm useSm){
        this.selectSm=selectSm;
        this.useSm=useSm;
    }

    @Override
    public void runService() {
        /*判断是否选择了库*/
        /*选择了库*/
        if( useSm.isComplete() ) {
            /*检查表是否存在*/
            String tableDictionaryPath = FileTools.RESOURCES+useSm.getName()+"\\"+FileTools.DICTIONARY+selectSm.getTableName();
            String tableDatabasePath = FileTools.RESOURCES+useSm.getName()+"\\"+FileTools.DATABASEDATA+selectSm.getTableName();
            File tableDictionaryFile = new File(tableDictionaryPath);
            File tableDatabaseFile = new File(tableDatabasePath);
            /*表存在*/
            if( tableDictionaryFile.exists() && tableDatabaseFile.exists()) {
//                /*输出表的数据字典*/
//                printDictionary(tableDictionaryPath);
//                /*输出表的数据*/
//                printData(tableDatabasePath);
                /*表格输出*/
                TablePrintTools tablePrintTools = new TablePrintTools();
                tablePrintTools.tablePrint(
                        FileTools.fileReader(new ArrayList<>(), tableDictionaryPath),
                        FileTools.fileReader(new ArrayList<>(), tableDatabasePath)
                );

            }else {
                /*表不存在*/
                /*提示表不存在*/
                System.out.println("-----------表不存在-----------");
            }

        }else {
            /*没有选择库*/
            /*提示库选择错误，return*/
            System.out.println("-----------库选择错误-----------");
        }
    }

    @Deprecated
    private void printData(String tableDatabasePath) {
        ArrayList<Data> dataArrayList = FileTools.fileReader(new ArrayList<Data>(), tableDatabasePath);
        for (Data data : dataArrayList) {
            data.getDataRowArray().forEach(
                    s -> System.out.print(s+"\t\t\t")
            );
            System.out.println();
        }
    }

    @Deprecated
    private void printDictionary(String tableDictionaryPath) {
        ArrayList<ColumnAttributes> columnAttributesArrayList = FileTools.fileReader(new ArrayList<ColumnAttributes>(), tableDictionaryPath);
        columnAttributesArrayList.forEach(
                s -> System.out.print(s.getColumnName()+"\t\t\t")
        );
        System.out.println();
    }

}
