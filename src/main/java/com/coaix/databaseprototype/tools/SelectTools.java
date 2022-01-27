package com.coaix.databaseprototype.tools;

import com.coaix.databaseprototype.bean.statement.SelectSm;
import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * @author LiaoWei
 * @date 2021-11-03 16:27
 */
@Data
public class SelectTools {


    public static SelectSm returnSm(String[] split){
        SelectSm selectSm = new SelectSm();
        /*检查语句*/
        /*如果语句不缺成分或无多余*/
        if ( StringTools.checkAllString(SelectSm.necessity,split) ) {
            selectSm.setComplete(true);
            /*将成分填入*/

            /*处理投影*/
            selectSm.setColumnName( split[StringTools.checkIndex(split, SelectSm.SELECT) + 1].split(","));
            //                      0     1      2     3        4       5                  6
//                    select name,boyname from boys,beauty where beauty.boyfriend_id=boys.id;
            /*处理表名*/
            selectSm.setTableName(split[StringTools.checkIndex(split, SelectSm.FROM) + 1].split(","));

            /*处理条件*/
            String[] split1 = split[StringTools.checkIndex(split, SelectSm.WHERE) + 1].split("=");
            if(StringTools.checkIndex(split,SelectSm.WHERE)!=-1) {
                if (split1.length == 2) {
                    selectSm.setConditionColumn1(split1[0]);
                    selectSm.setConditionColumn2(split1[1]);
                } else {
                    selectSm.setConditionColumn1(split1[0]);
                }
            }
            String[] split2 = split[StringTools.checkIndex(split, SelectSm.AND) + 1].split("=");
            if(StringTools.checkIndex(split2,SelectSm.AND)!=-1) {
                selectSm.setANDSIGNAL(true);
                if (split2.length == 2) {
                    selectSm.setANDConditionColumn1(split2[0]);
                    selectSm.setANDConditionColumn2(split2[1]);
                } else {
                    selectSm.setANDConditionColumn1(split2[0]);
                }
            }

            /*处理类型*/
            if( StringTools.checkIndex(split,SelectSm.ALL_SIGN)!=-1 ){
                //如果存在查找全部列
                selectSm.setSelectType(SelectSm.ALL_SIGN);
            }else{
                if(StringTools.checkIndex(split,SelectSm.WHERE)!=-1) {
                    if (StringTools.havePoint(split1[1])) {
                        //如果条件2带"."则为连接条件
                        selectSm.setSelectType(SelectSm.JOINSELECT_SIGN);
                    } else {
                        //常量查询
                        selectSm.setSelectType(SelectSm.FIELDSELECT_SIGN);
                    }
                }else{

                }
            }

        }else{
            selectSm.setComplete(false);
        }
        return selectSm;
    }



    @Test
    public void test1(){
        String str = "select name,boyname from boys,beauty where beauty.boyfriend_id=3;";
        System.out.println(SelectTools.returnSm(str.toUpperCase().split(" ")));
    }
    @Test
    public void test2(){
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(3);
        integers.add(2);
        integers.add(5);
        integers.add(6);
        System.out.println(integers);
        integers.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });
        System.out.println(integers);
    }

}
