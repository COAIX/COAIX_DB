package com.coaix.databaseprototype.service.impl;

import com.coaix.databaseprototype.bean.data.ColumnAttributes;
import com.coaix.databaseprototype.bean.data.Data;
import com.coaix.databaseprototype.bean.index.BPlusTree;
import com.coaix.databaseprototype.bean.statement.AlterSm;
import com.coaix.databaseprototype.bean.statement.DeleteSm;
import com.coaix.databaseprototype.bean.statement.UseSm;
import com.coaix.databaseprototype.service.Service;
import com.coaix.databaseprototype.tools.DeleteTools;
import com.coaix.databaseprototype.tools.FileTools;
import com.coaix.databaseprototype.tools.IndexTools;

import java.io.File;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * @author LiaoWei
 * @date 2021-11-02 17:47
 */
public class DeleteService implements Service {

    private DeleteSm deleteSm;

    private UseSm useSm;

    private DeleteTools alterTools;

    public DeleteService(DeleteSm deleteSm,UseSm useSm){
        this.deleteSm=deleteSm;
        this.useSm=useSm;
    }

    @Override
    public void runService() {
        /*判断是否选择了库*/
        /*选择了库*/
        if(useSm.isComplete()) {
            /*检查表是否存在*/
            String tableDictionaryPath = FileTools.RESOURCES+useSm.getName()+"\\"+FileTools.DICTIONARY+deleteSm.getTableName();
            File tableDictionary = new File(tableDictionaryPath);
            /*表存在*/
            if(tableDictionary.exists()) {
                /*提取表数据字典中的属性*/
                ArrayList<ColumnAttributes> attributes = FileTools.fileReader(new ArrayList<ColumnAttributes>(), tableDictionaryPath);
                /*将属性和requirement(0)进行对比，判定属性名是否存在*/
                int checkIndex = checkExist(attributes, deleteSm);
                /*存在，将数据文件读出*/
                if(checkIndex != -1) {
                    /*判断数据文件长度*/
                    String tableDataPath =  FileTools.RESOURCES+useSm.getName()+"\\"+FileTools.DATABASEDATA+deleteSm.getTableName();
                    File tableDataFile = new File(tableDataPath);
                    if( tableDataFile.length() == 0 ) {
                        /*长度为0，直接return*/
                    }else {
                        /*长度不为0*/
                        /*将文件读出成ArrayList<Data>,将ArrayList进行循环，删除和条件符合的数据*/
                        ArrayList<Data> dataArrayList = FileTools.fileReader(new ArrayList<Data>(), tableDataPath);
                        ArrayList<Data> newDataArrayList = removeData(checkIndex, dataArrayList,deleteSm,useSm);
                        /*将修改后的ArralyList<Data>写入到文件中*/
                        FileTools.fileWriter(newDataArrayList,tableDataPath);
                        /*维护索引*/
                        IndexTools.refreshIndex(useSm,deleteSm.getTableName());
                    }
                }else {
                    /*不存在*/
                    /*输出属性名不存在，return*/
                    System.out.println("----------属性名不存在----------");
                }
            }
            else{
                /*表不存在*/
                /*输出表不存在，return*/
                System.out.println("----------表不存在----------");
                return;
            }
        }else {
            /*没有选择库*/
            /*输出未选择库,return*/
            System.out.println("----------库选择错误----------");
        }
    }

    /**
     * 将数据文件中的文件施加判定移除
     * @author LiaoWei
     * @date 2021/11/2 20:18
     * @param checkIndex
     * @param dataArrayList
     */
    private ArrayList<Data> removeData(int checkIndex, ArrayList<Data> dataArrayList,DeleteSm deleteSm,UseSm useSm) {
//        System.out.println("old deleteArrayListLength"+dataArrayList.size());
        ArrayList<Data> deleteArrayList = new ArrayList<>();
        String indexFilePath = FileTools.RESOURCES + useSm.getName() + "\\" + FileTools.INDEX + deleteSm.getTableName() + "_" + deleteSm.getRequirement()[0];
        File indexFile = new File(indexFilePath);
        if(indexFile.exists()){
            BPlusTree<ArrayList<Integer>, String> bPlusTree = FileTools.fileReader(new BPlusTree<>(), indexFilePath);
            ArrayList<Integer> deleteList = bPlusTree.find(deleteSm.getRequirement()[1]);

            deleteList.forEach(
                    (Integer integer) -> deleteArrayList.add( dataArrayList.get(integer) )
            );
            System.out.println("-----------READ_INDEX----------");

        }else {

            for (Data data : dataArrayList) {
                if (data.getDataRowArray().get(checkIndex).equals(deleteSm.getRequirement()[1])) {
                    deleteArrayList.add(data);
                }
            }


        }


        dataArrayList.removeAll(deleteArrayList);
//        System.out.println("old deleteArrayListLength"+dataArrayList.size());

        return dataArrayList;
    }

    /**
     * 判断数据字典中是否存在相应属性，返回相应下标，如果不存在相应属性，返回-1
     * @author LiaoWei
     * @date 2021/11/2 16:32
     * @param columnNameArrayList
     * @param deleteSm
     * @return boolean
     */
    private int checkExist(ArrayList<ColumnAttributes> columnNameArrayList, DeleteSm deleteSm) {
        for (int i = 0; i < columnNameArrayList.size(); i++) {
            if ( columnNameArrayList.get(i).getColumnName().equals(deleteSm.getRequirement()[0] )) {
                return i;
            }
        }
        return -1;
    }
}
