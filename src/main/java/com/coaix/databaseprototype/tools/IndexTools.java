package com.coaix.databaseprototype.tools;

import com.coaix.databaseprototype.bean.statement.CreateSm;
import com.coaix.databaseprototype.bean.statement.UseSm;
import com.coaix.databaseprototype.service.impl.CreateService;
import lombok.Data;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LiaoWei
 * @date 2021-11-03 22:32
 */
public class IndexTools {
    @Data
    static class TableNode{
        private String tableName;

        private String indexName;

        TableNode(String tableName,String indexName){
            this.indexName=indexName;
            this.tableName=tableName;
        }
    }

    /**
     * 根据数据库语句和表名刷新索引文件
     * @author LiaoWei
     * @date 2021/11/4 8:19
     * @param useSm
     * @param tableName
     */
    public static void refreshIndex(UseSm useSm,String tableName){
        List<String> filesNameList = FileTools.getFilesNameList(FileTools.RESOURCES + useSm.getName() + "\\" + FileTools.INDEX);
        ArrayList<TableNode> tableNodeArrayList = new ArrayList<>();

        filesNameList.forEach(
                (s) -> {
                    if (s.split("_")[0].equals(tableName)) {
                        TableNode tableNode = new TableNode(tableName, s.split("_")[1]);
                        tableNodeArrayList.add(tableNode);
                    }
                }
        );

        System.out.println("------------tableNodeArrayList_NEEDS_MAINTENANCE------------");
        tableNodeArrayList.forEach(System.out::println);

        tableNodeArrayList.forEach(
                ( tableNode ) ->
                {
                    CreateSm createSm = new CreateSm();
                    createSm.setName(tableNode.getTableName());
                    createSm.setIndex(true);
                    createSm.setIndexColumnName(tableNode.getIndexName());
                    createSm.setComplete(true);
                    new CreateService(createSm,useSm).runService();
                }
        );
        System.out.println("-----------PROTECT_INDEX_"+useSm.getName()+"_"+tableName+"-----------");
    }

    /**
     * 检查是否存在当前列的索引
     * @author LiaoWei
     * @date 2021/11/4 8:18
     * @param dataBaseName  库名
     * @param tableName 表名
     * @param columnName  列名
     * @return boolean
     */
    public static boolean checkIfExistIndex(String dataBaseName,String tableName,String columnName){
        String indexFilePath = FileTools.RESOURCES + dataBaseName + "\\" + FileTools.INDEX + tableName + "_" + columnName;
        File indexFile = new File(indexFilePath);
        if(indexFile.exists()){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 删除索引文件
     * @author LiaoWei
     * @date 2021/11/4 12:48
     * @param useSm
     * @param tableName
     */
    public static void deleteIndex(UseSm useSm,String tableName){
        List<String> filesNameList = FileTools.getFilesNameList(FileTools.RESOURCES + useSm.getName() + "\\" + FileTools.INDEX);
        ArrayList<TableNode> tableNodeArrayList = new ArrayList<>();

        filesNameList.forEach(
                (s) -> {
                    if (s.split("_")[0].equals(tableName)) {
                        TableNode tableNode = new TableNode(tableName, s.split("_")[1]);
                        tableNodeArrayList.add(tableNode);
                    }
                }
        );

        System.out.println("------------tableNodeArrayList_NEEDS_DELETE------------");
        tableNodeArrayList.forEach(System.out::println);

        tableNodeArrayList.forEach(
                ( tableNode ) -> new File(
                        FileTools.RESOURCES +
                                useSm.getName() +
                                "\\" +
                                FileTools.INDEX +
                                tableNode.getTableName() +
                                "_" +
                                tableNode.getIndexName()).delete()

        );
        System.out.println("-----------DELETE_INDEX_"+useSm.getName()+"_"+tableName+"-----------");
    }

}
