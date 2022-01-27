package com.coaix.databaseprototype.service.impl;

import com.coaix.databaseprototype.bean.statement.DropSm;
import com.coaix.databaseprototype.bean.statement.UseSm;
import com.coaix.databaseprototype.service.Service;
import com.coaix.databaseprototype.tools.DropTools;
import com.coaix.databaseprototype.tools.FileTools;
import com.coaix.databaseprototype.tools.IndexTools;

import java.io.File;

/**
 * @author LiaoWei
 * @date 2021-11-02 21:26
 */
public class DropService implements Service {

    private DropSm dropSm;

    private UseSm useSm;

    private DropTools dropTools;

    public DropService(DropSm dropSm,UseSm useSm){
        this.dropSm=dropSm;
        this.useSm=useSm;
    }

    @Override
    public void runService() {
        /*判断是否选择了库*/
        if ( useSm.isComplete() ) {
            /*选择了库*/
            /*检查表是否存在*/
            String tableDictionaryPath = FileTools.RESOURCES+useSm.getName()+"\\"+FileTools.DICTIONARY+dropSm.getTableName();
            String tableDatabasePath = FileTools.RESOURCES+useSm.getName()+"\\"+FileTools.DATABASEDATA+dropSm.getTableName();
            File tableDictionaryFile = new File(tableDictionaryPath);
            File tableDatabaseFile = new File(tableDatabasePath);
            if( tableDictionaryFile.exists() && tableDatabaseFile.exists()) {
                /*存在*/
                /*将数据字典文件和数据文件删除*/
                tableDatabaseFile.delete();
                tableDictionaryFile.delete();
                /*删除索引文件*/
                IndexTools.deleteIndex(useSm,dropSm.getTableName());
            }else {
                /*不存在*/
                /*提示表不存在，return*/
                System.out.println("-----------表不存在-----------");
            }
        }else {
            /*没有选择库，提示为选择库，return*/
            System.out.println("-----------库选择错误-----------");
        }
    }
}
