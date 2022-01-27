package com.coaix.databaseprototype.service.impl;
import com.coaix.databaseprototype.bean.data.ColumnAttributes;
import com.coaix.databaseprototype.bean.data.Data;
import com.coaix.databaseprototype.bean.statement.SelectSm;
import com.coaix.databaseprototype.bean.statement.UseSm;
import com.coaix.databaseprototype.service.Service;
import com.coaix.databaseprototype.tools.FileTools;
import com.coaix.databaseprototype.tools.StringTools;
import com.coaix.databaseprototype.tools.TablePrintTools;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author LiaoWei
 * @date 2021-11-03 16:36
 */
public class SelectService implements Service {

    private SelectSm selectSm;

    private UseSm useSm;

    TablePrintTools tablePrintTools = new TablePrintTools();

    public SelectService(SelectSm selectSm, UseSm useSm){
        this.selectSm=selectSm;
        this.useSm=useSm;
    }

    @Override
    public void runService() {
        System.out.println(selectSm);
        /*判断是否选择了库*/
        /*选择了库*/
        if( useSm.isComplete() ) {
            //获取信息
            String[] tableName = selectSm.getTableName();
            String condition1 = selectSm.getConditionColumn1();
            String condition2 = selectSm.getConditionColumn2();
            String[] columnName = selectSm.getColumnName();
            String selectType = selectSm.getSelectType();
            ArrayList<ColumnAttributes> mixAttributes = new ArrayList<>();
            ArrayList<Data> mixData = new ArrayList<>();
            if(tableName.length==2){
                //如果查询的表是两个表
                //构造笛卡尔积
                ArrayList<ColumnAttributes> attributes1 =
                        FileTools.fileReader(new ArrayList<>(), FileTools.RESOURCES + useSm.getName() + "\\" + FileTools.DICTIONARY + tableName[0]);
                ArrayList<ColumnAttributes> attributes2 =
                        FileTools.fileReader(new ArrayList<>(), FileTools.RESOURCES + useSm.getName() + "\\" + FileTools.DICTIONARY + tableName[1]);
                mixAttributes.addAll(attributes1);
                mixAttributes.addAll(attributes2);
                ArrayList<Data> data1 = FileTools.fileReader(new ArrayList<>(), FileTools.RESOURCES + useSm.getName() + "\\" + FileTools.DATABASEDATA + tableName[0]);
                ArrayList<Data> data2 = FileTools.fileReader(new ArrayList<>(), FileTools.RESOURCES + useSm.getName() + "\\" + FileTools.DATABASEDATA + tableName[1]);
                ArrayList<Data> finalMixData = mixData;
                data1.forEach(d1 -> data2.forEach(d2 -> {
                    Data data = new Data();
                    ArrayList<String> dataRowArray1 = d1.getDataRowArray();
                    ArrayList<String> dataRowArray2 = d2.getDataRowArray();
                    ArrayList<String> mixrow = new ArrayList<>();
                    mixrow.addAll(dataRowArray1);
                    mixrow.addAll(dataRowArray2);
                    data.setDataRowArray(mixrow);
                    finalMixData.add(data);
                }));
                mixData=finalMixData;
                    //如果有判断条件，则根据判断条件对表进行处理
                    if(condition1!=null){
                        if(StringTools.havePoint(condition2)) {
                            //如果是连接查询
//                            System.out.println(SelectSm.JOINSELECT_SIGN);
                            String pointValue1 = StringTools.getPointValue(condition1);
                            String pointValue2 = StringTools.getPointValue(condition2);
                            int index1 = StringTools.checkExist(mixAttributes, pointValue1);
                            int index2 = StringTools.checkExist(mixAttributes, pointValue2);
                            mixData.removeIf(
                                    data -> !data.getDataRowArray().get(index1).equals(data.getDataRowArray().get(index2))
                            );
                        }
                        //如果是常量查询
                        else{
//                            System.out.println(SelectSm.FIELDSELECT_SIGN);
                            String pointValue1 = StringTools.getPointValue(condition1);
                            int index1 = StringTools.checkExist(mixAttributes, pointValue1);
                            mixData.removeIf(
                                    data -> !data.getDataRowArray().get(index1).equals(condition2)
                            );
                        }
                    }
                }else{
                //单个表
                    //如果没有判断条件
                mixAttributes=FileTools.fileReader(new ArrayList<>(), FileTools.RESOURCES + useSm.getName() + "\\" + FileTools.DICTIONARY + tableName[0]);
                mixData=FileTools.fileReader(new ArrayList<>(), FileTools.RESOURCES + useSm.getName() + "\\" + FileTools.DATABASEDATA + tableName[0]);
                if(condition1==null) {
//                    System.out.println("no where");
                        //不做处理
                    }else{
                    //如果有判断条件
//                    System.out.println("have where");
                    int index1 = StringTools.checkExist(mixAttributes, condition1);
                    mixData.removeIf(data -> !data.getDataRowArray().get(index1).equals(condition2));
                    }
                }

                if(selectType.equals(SelectSm.ALL_SIGN)){
                    //如果是全查
//                    System.out.println(SelectSm.ALL_SIGN);
                    //不做处理
                }else{
                    //如果不是全查
//                    System.out.println("!"+SelectSm.ALL_SIGN);
                    //删除不需要的列
                    ArrayList<Integer> deleteIndexList = new ArrayList<>();
                    ArrayList<ColumnAttributes> finalMixAttributes = mixAttributes;
                    mixAttributes.forEach(columnAttributes -> {
                        int checkIndex = StringTools.checkIndex(columnName, columnAttributes.getColumnName());
                        if (checkIndex ==-1){
                            deleteIndexList.add(StringTools.checkExist(finalMixAttributes,columnAttributes.getColumnName()));
                        }
                    });
                    deleteIndexList.sort(Comparator.comparingInt(o -> o));
//                    System.out.println("deleteIndexList"+deleteIndexList);
                    for (int i = deleteIndexList.size() - 1; i >= 0; i--) {
                        int integer = deleteIndexList.get(i);
                        mixAttributes.remove(integer);
                    }
                    for (Data data : mixData) {
                        for (int i = deleteIndexList.size() - 1; i >= 0; i--) {
                            int integer = deleteIndexList.get(i);
                            data.getDataRowArray().remove(integer);
                        }
                    }
                }
                tablePrintTools.tablePrint(
                        mixAttributes,
                        mixData
                );
        }else {
            /*没有选择库*/
            /*提示库选择错误，return*/
            System.out.println("-----------库选择错误-----------");
        }
    }
}
