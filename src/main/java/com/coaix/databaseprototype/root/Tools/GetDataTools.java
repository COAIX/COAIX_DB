package com.coaix.databaseprototype.root.Tools;

import com.coaix.databaseprototype.bean.data.ColumnAttributes;
import com.coaix.databaseprototype.bean.data.Data;
import com.coaix.databaseprototype.root.bean.TableData;
import com.coaix.databaseprototype.tools.FileTools;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 * @author LiaoWei
 * @date 2021-11-05 12:32
 */
public class GetDataTools {

    public static TableData getTableData(String databaseName,String tableName){
        ArrayList<String> columnNameList = new ArrayList<>();
        ArrayList<HashMap<String, String>> data = new ArrayList<>();
        String tablePath = FileTools.RESOURCES + databaseName + "\\" + FileTools.DICTIONARY + tableName;
        String dataPath = FileTools.RESOURCES + databaseName + "\\" + FileTools.DATABASEDATA + tableName;

        ArrayList<ColumnAttributes> columnAttributesArrayList = FileTools.fileReader(new ArrayList<ColumnAttributes>(), tablePath);
        ArrayList<Data> dataArrayList = FileTools.fileReader(new ArrayList<Data>(), dataPath);

        columnAttributesArrayList.forEach(new Consumer<ColumnAttributes>() {
            @Override
            public void accept(ColumnAttributes columnAttributes) {
                columnNameList.add(columnAttributes.getColumnName());
            }
        });

        for (int i = 0; i < dataArrayList.size(); i++) {

            HashMap<String, String> hashMap = new HashMap<>();
            for (int i1 = 0; i1 < dataArrayList.get(i).getDataRowArray().size(); i1++) {
                String columnName = columnAttributesArrayList.get(i1).getColumnName();
                String columnValue = dataArrayList.get(i).getDataRowArray().get(i1);
                hashMap.put(columnName, columnValue);
            }
            data.add(hashMap);
        }

        return new TableData(columnNameList, data);
    }

    @Test
    public void testGetTableData(){
        TableData tableData = GetDataTools.getTableData("BOOKS", "EMPLOYEE");
        System.out.println(tableData.getColumnNameList());
        System.out.println(tableData.getData());
    }

}
