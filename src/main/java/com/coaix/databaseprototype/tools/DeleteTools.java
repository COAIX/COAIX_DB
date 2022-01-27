package com.coaix.databaseprototype.tools;

import com.coaix.databaseprototype.bean.statement.DeleteSm;
import org.junit.Test;

/**
 * @author LiaoWei
 * @date 2021-10-30 13:39
 */
public class DeleteTools {

    public static DeleteSm returnSm(String[] split){
        DeleteSm deleteSm = new DeleteSm();
        /*检查语句*/
            /*如果语句不缺失成分或无多余*/
        if (StringTools.checkAllString(DeleteSm.necessity,split)){
            deleteSm.setComplete(true);
            /*将自定义的数据名字填入*/
                /*如果有判断条件则将判断条件填入*/
            if (StringTools.checkIndex(split,DeleteSm.WHERE)!=-1) {
                deleteSm.setRequirement(StringTools.equalSignDivision(
                        split[
                                StringTools.checkIndex(split,DeleteSm.WHERE)+1
                                ])
                );
            }
            /*将表名填入*/
            deleteSm.setTableName(
                    split[
                            StringTools.checkIndex(split,DeleteSm.FROM)+1
                            ]
            );

            return deleteSm;
        }
            /*如果语句缺失成分或有多余*/
        else{
            deleteSm.setComplete(false);
            return deleteSm;
        }
    }

    @Test
    public void steat(){
//        String[] a = {"", "2", "3"};
//        String[] b = {"1","2","3","3"};
//        System.out.println(StringTools.checkAllString(a,b));
        String sm="delete from books where name=1";

        Object typeSm = SmTools.getTypeSm(sm);

        System.out.println(typeSm);

    }

}
