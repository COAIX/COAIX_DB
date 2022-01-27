package com.coaix.databaseprototype.tools;

import com.coaix.databaseprototype.bean.statement.InsertSm;
import org.junit.Test;

/**
 * @author LiaoWei
 * @date 2021-10-30 13:26
 */
public class InsertTools {


    public static InsertSm returnSm(String[] split){
        InsertSm insertSm= new InsertSm();
        /*检查语句*/
        /*如果语句不缺失成分或无多余*/
        if (StringTools.checkAllString(InsertSm.necessity,split)){
            insertSm.setComplete(true);
            /*将自定义的数据名字填入*/
                /*将表列名填入*/
                insertSm.setColumnName(StringTools.getColumnName(
                        split[
                                StringTools.checkIndex(split,InsertSm.INTO)+2
                                ]
                ));

                /*将插入值填入*/
                insertSm.setInsertValue(StringTools.getInsertValue(
                        split[
                                StringTools.checkIndex(split,InsertSm.VALUES)+1
                                ]
                ));

                /*将表名填入*/
                insertSm.setTableName(
                        split[
                                StringTools.checkIndex(split,InsertSm.INTO)+1
                                ]
                );
            return insertSm;
        }
        /*如果语句缺失成分或有多余*/
        else{
            insertSm.setComplete(false);
            return insertSm;
        }
    }

    @Test
    public void test12(){
        String sm="insert into beauty (name,sex,phone) values (关晓彤,女,110)";
        Object typeSm = SmTools.getTypeSm(sm);
        System.out.println(typeSm);
        System.out.println(typeSm.getClass().equals(InsertSm.class));

        //结果:
        //InsertSm(tableName=BEAUTY, complete=true, columnName=[NAME, SEX, PHONE], insertValue=[关晓彤, 女, 110])
        //true
    }


}
