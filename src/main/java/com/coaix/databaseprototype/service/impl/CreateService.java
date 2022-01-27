package com.coaix.databaseprototype.service.impl;

import com.coaix.databaseprototype.bean.data.ColumnAttributes;
import com.coaix.databaseprototype.bean.data.Data;
import com.coaix.databaseprototype.bean.index.BPlusTree;
import com.coaix.databaseprototype.bean.statement.CreateSm;
import com.coaix.databaseprototype.bean.statement.UseSm;
import com.coaix.databaseprototype.service.Service;
import com.coaix.databaseprototype.tools.CreateTools;
import com.coaix.databaseprototype.tools.FileTools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author LiaoWei
 * @date 2021-10-30 19:00
 */
public class CreateService implements Service {

    private CreateTools creatTools;

    private CreateSm createSm;

    /*获取库名*/
    private UseSm useSm;

    /*创建库的时候用的构造方法 UseSm直接New一个*/
    /*选择了数据库和表=>此处是创建数据库*/
    public CreateService(CreateSm createSm,UseSm useSm){
        this.createSm=createSm;
        this.useSm=useSm;
    }

    @Override
    public void runService() {
        /*判断是否选择了要操作的库 或者 没有选择操作的库,为创造库语句*/
        if(useSm.isComplete() || createSm.getDataOrtable().equals(CreateSm.DATABASE)) {
            System.out.println("----------CreateService is begin----------");
            /*如果不是创建索引*/
            if(!createSm.isIndex()) {
                if (createSm.getDataOrtable().equals(CreateSm.TABLE)) {
                    /*创造表*/
                    String tablePath = FileTools.RESOURCES + useSm.getName() + "\\" + FileTools.DICTIONARY + createSm.getName();
                    String tableDataPath = FileTools.RESOURCES + useSm.getName() + "\\" + FileTools.DATABASEDATA + createSm.getName();
                    File file = new File(tablePath);
                    /*判断表是否存在*/
                    if (file.exists()) {
                        /*表存在，提示表已经存在，return*/
                        System.out.println("----------" + createSm.getName() + "表已经存在----------");
                        return;
                    } else {
                        /*表不存在，将数据字典写入相应的库的数据字典文件文件夹下*/
                        /*写入步骤*/
                        ArrayList<ColumnAttributes> columnAttributes = createSm.getColumnAttributes();
                        FileTools.fileWriter(columnAttributes, tablePath);
                        /*表不存在，新建Data数据文件，新建一个空ArrayList<Data>写入*/
                        /*写入步骤*/
                        FileTools.fileWriter(new ArrayList<Data>(), tableDataPath);
                    }
                } else {// createSm.getDataOrtable().equals(CreateSm.DATABASE)
                    /*创造库*/
                    File file = new File(FileTools.RESOURCES + createSm.getName());
                    /*判断库是否存在*/
                    if (file.exists()) {
                        /*库存在，提示库存在，return*/
                        System.out.println("----------" + createSm.getName() + "库已经存在----------");
                    } else {
                        /*库不存在，在resource文件夹下创建名为库名的文件夹并在文件夹下下创建数据字典文件夹和数据文件夹*/
                        file.mkdirs();
                        File file1 = new File(FileTools.RESOURCES + createSm.getName() + "\\" + FileTools.DICTIONARY);
                        File file2 = new File(FileTools.RESOURCES + createSm.getName() + "\\" + FileTools.DATABASEDATA);
                        file1.mkdirs();
                        file2.mkdirs();
                    }
                }
            }else{
                /*创建索引*/
                /*判断表和数据文件是否存在*/
                String tablePath = FileTools.RESOURCES + useSm.getName() + "\\" + FileTools.DICTIONARY + createSm.getName();
                String tableDataPath = FileTools.RESOURCES + useSm.getName() + "\\" + FileTools.DATABASEDATA + createSm.getName();
                File tableFile = new File(tablePath);
                File tableDataFile = new File(tableDataPath);
                if ( tableDataFile.exists() && tableFile.exists() ){
                    /*判断是否存在该列属性*/
                    int checkIndex = checkExist(FileTools.fileReader(new ArrayList<ColumnAttributes>(), tablePath), createSm);
                    if( checkIndex != -1 ) {
                        /*存在该属性*/
                        /*判断数据文件里面是否为空数据*/
                        ArrayList<Data> dataArrayList = FileTools.fileReader(new ArrayList<Data>(), tableDataPath);
                        if (dataArrayList.size() == 0) {
                            /*是空数据*/
                            return;
                        } else {
                            /*不是空数据*/
                            /*将数据循环读出来，新建B+树，填入B+树中*/
                            createIndex(checkIndex, dataArrayList);
                        }
                    }else{
                        System.out.println("----------不存在该属性---------");
                        return;
                    }
                }
            }
        }
        else{
            System.out.println("------------------语法错误------------------");
            return;
        }
    }

    private void createIndex(int checkIndex, ArrayList<Data> dataArrayList) {
        BPlusTree<ArrayList<Integer>, String> bPlusTree = new BPlusTree<>();
        HashMap<String, ArrayList<Integer>> WTF = new HashMap<>();
        System.out.println("dataArrayList.size()"+dataArrayList.size());
        for (int i = 0; i < dataArrayList.size(); i++) {
            /*判断是否已经存在相应的节点*/
            String findKey = dataArrayList.get(i).getDataRowArray().get(checkIndex);
            ArrayList<Integer> findValue = WTF.get(findKey);
            if( findValue != null ) {
                /*已经存在,找出相应节点，将索引 add进ArrayList，然后删除旧节点，再添加新节点*/
                findValue.add(i);
                WTF.remove(findKey);
                WTF.put(findKey,findValue);
            }else {
                /*不存在*/
                /*Value：索引位置，也就是ArrayList数组下标*/
                /*Key：键 -> 属性值 */
                ArrayList<Integer> value = new ArrayList<>();
                value.add(i);
                WTF.put(findKey,value);
            }
        }
        WTF.forEach(
                ( s,integers ) -> bPlusTree.insert(integers,s)
        );
        /*将B+树写入外存*/
        /*命名规则 - > 表名_属性名 */
        String indexFileExplorePath = FileTools.RESOURCES + useSm.getName() + "\\" + FileTools.INDEX;
        String indexFilePath = FileTools.RESOURCES + useSm.getName() + "\\" + FileTools.INDEX + createSm.getName() + "_" + createSm.getIndexColumnName();
        File indexFileExplore = new File(indexFileExplorePath);
        File indexFile = new File(indexFilePath);
        if(indexFile.exists()){
            indexFile.delete();
        }
        try {
            indexFileExplore.mkdirs();
            indexFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileTools.fileWriter(bPlusTree, indexFilePath);
    }

    /**
     * 判断数据字典中是否存在相应属性，返回相应下标，如果不存在相应属性，返回-1
     * @author LiaoWei
     * @date 2021/11/2 16:32
     * @param columnNameArrayList
     * @return boolean
     */

    private int checkExist(ArrayList<ColumnAttributes> columnNameArrayList, CreateSm createSm) {
        for (int i = 0; i < columnNameArrayList.size(); i++) {
            if ( columnNameArrayList.get(i).getColumnName().equals(createSm.getIndexColumnName()) ) {
                return i;
            }
        }
        return -1;
    }


}


