package com.coaix.databaseprototype.tools;

import com.coaix.databaseprototype.bean.data.ColumnAttributes;
import com.coaix.databaseprototype.bean.data.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author LiaoWei
 * @date 2021-11-03 17:16
 */
public class TablePrintTools {
        private int PADDING_SIZE = 2;
        private String NEW_LINE = "\n";
        private String TABLE_JOINT_SYMBOL = "+";
        private String TABLE_V_SPLIT_SYMBOL = "|";
        private String TABLE_H_SPLIT_SYMBOL = "-";

        public String generateTable(List<String> headersList, List<List<String>> rowsList,int... overRiddenHeaderHeight)
        {
            StringBuilder stringBuilder = new StringBuilder();

            int rowHeight = overRiddenHeaderHeight.length > 0 ? overRiddenHeaderHeight[0] : 1;

            Map<Integer,Integer> columnMaxWidthMapping = getMaximumWidhtofTable(headersList, rowsList);

            stringBuilder.append(NEW_LINE);
            stringBuilder.append(NEW_LINE);
            createRowLine(stringBuilder, headersList.size(), columnMaxWidthMapping);
            stringBuilder.append(NEW_LINE);


            for (int headerIndex = 0; headerIndex < headersList.size(); headerIndex++) {
                fillCell(stringBuilder, headersList.get(headerIndex), headerIndex, columnMaxWidthMapping);
            }

            stringBuilder.append(NEW_LINE);

            createRowLine(stringBuilder, headersList.size(), columnMaxWidthMapping);


            for (List<String> row : rowsList) {

                for (int i = 0; i < rowHeight; i++) {
                    stringBuilder.append(NEW_LINE);
                }

                for (int cellIndex = 0; cellIndex < row.size(); cellIndex++) {
                    fillCell(stringBuilder, row.get(cellIndex), cellIndex, columnMaxWidthMapping);
                }

            }

            stringBuilder.append(NEW_LINE);
            createRowLine(stringBuilder, headersList.size(), columnMaxWidthMapping);
            stringBuilder.append(NEW_LINE);
            stringBuilder.append(NEW_LINE);

            return stringBuilder.toString();
        }

        private void fillSpace(StringBuilder stringBuilder, int length)
        {
            for (int i = 0; i < length; i++) {
                stringBuilder.append(" ");
            }
        }

        private void createRowLine(StringBuilder stringBuilder,int headersListSize, Map<Integer,Integer> columnMaxWidthMapping)
        {
            for (int i = 0; i < headersListSize; i++) {
                if(i == 0)
                {
                    stringBuilder.append(TABLE_JOINT_SYMBOL);
                }

                for (int j = 0; j < columnMaxWidthMapping.get(i) + PADDING_SIZE * 2 ; j++) {
                    stringBuilder.append(TABLE_H_SPLIT_SYMBOL);
                }
                stringBuilder.append(TABLE_JOINT_SYMBOL);
            }
        }


        private Map<Integer,Integer> getMaximumWidhtofTable(List<String> headersList, List<List<String>> rowsList)
        {
            Map<Integer,Integer> columnMaxWidthMapping = new HashMap<>();

            for (int columnIndex = 0; columnIndex < headersList.size(); columnIndex++) {
                columnMaxWidthMapping.put(columnIndex, 0);
            }

            for (int columnIndex = 0; columnIndex < headersList.size(); columnIndex++) {

                if(headersList.get(columnIndex).length() > columnMaxWidthMapping.get(columnIndex))
                {
                    columnMaxWidthMapping.put(columnIndex, headersList.get(columnIndex).length());
                }
            }


            for (List<String> row : rowsList) {

                for (int columnIndex = 0; columnIndex < row.size(); columnIndex++) {

                    if(row.get(columnIndex).length() > columnMaxWidthMapping.get(columnIndex))
                    {
                        columnMaxWidthMapping.put(columnIndex, row.get(columnIndex).length());
                    }
                }
            }

            for (int columnIndex = 0; columnIndex < headersList.size(); columnIndex++) {

                if(columnMaxWidthMapping.get(columnIndex) % 2 != 0)
                {
                    columnMaxWidthMapping.put(columnIndex, columnMaxWidthMapping.get(columnIndex) + 1);
                }
            }


            return columnMaxWidthMapping;
        }

        private int getOptimumCellPadding(int cellIndex,int datalength,Map<Integer,Integer> columnMaxWidthMapping,int cellPaddingSize)
        {
            if(datalength % 2 != 0)
            {
                datalength++;
            }

            if(datalength < columnMaxWidthMapping.get(cellIndex))
            {
                cellPaddingSize = cellPaddingSize + (columnMaxWidthMapping.get(cellIndex) - datalength) / 2;
            }

            return cellPaddingSize;
        }

        private void fillCell(StringBuilder stringBuilder,String cell,int cellIndex,Map<Integer,Integer> columnMaxWidthMapping)
        {

            int cellPaddingSize = getOptimumCellPadding(cellIndex, cell.length(), columnMaxWidthMapping, PADDING_SIZE);

            if(cellIndex == 0)
            {
                stringBuilder.append(TABLE_V_SPLIT_SYMBOL);
            }

            fillSpace(stringBuilder, cellPaddingSize);
            stringBuilder.append(cell);
            if(cell.length() % 2 != 0)
            {
                stringBuilder.append(" ");
            }

            fillSpace(stringBuilder, cellPaddingSize);

            stringBuilder.append(TABLE_V_SPLIT_SYMBOL);

        }


        public void tablePrint(ArrayList<ColumnAttributes> columnAttributesArrayList, ArrayList<Data> dataArrayList){

            TablePrintTools tablePrintTools = new TablePrintTools();

            ArrayList<String> columnName = new ArrayList<>();

            ArrayList<List<String>> dataAl= new ArrayList<>();

            columnAttributesArrayList.forEach(
                    s -> columnName.add(s.getColumnName())
            );

            dataArrayList.forEach(
                    s -> dataAl.add(s.getDataRowArray())
            );

            String s = tablePrintTools.generateTable(columnName,dataAl);

            System.out.println(s);
        }

        @Test
        public void test2(){
            String tableDictionaryPath = FileTools.RESOURCES+"BOOKS\\"+FileTools.DICTIONARY+"EMPLOYEE";

            String tableDatabasePath = FileTools.RESOURCES+"BOOKS\\"+FileTools.DATABASEDATA+"EMPLOYEE";

            ArrayList<Data> dataArrayList = FileTools.fileReader(new ArrayList<Data>(), tableDatabasePath);

            ArrayList<ColumnAttributes> columnAttributesArrayList = FileTools.fileReader(new ArrayList<ColumnAttributes>(), tableDictionaryPath);

            TablePrintTools tablePrintTools = new TablePrintTools();

            ArrayList<String> strings = new ArrayList<>();

            ArrayList<List<String>> arrayLists= new ArrayList<>();

            columnAttributesArrayList.forEach(
                    s -> strings.add(s.getColumnName())
            );

            dataArrayList.forEach(
                    s -> arrayLists.add(s.getDataRowArray())
            );

            String s = tablePrintTools.generateTable(strings,arrayLists);

            System.out.println(s);
        }

}
