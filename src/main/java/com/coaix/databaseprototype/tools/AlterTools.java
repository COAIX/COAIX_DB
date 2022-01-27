package com.coaix.databaseprototype.tools;

import com.coaix.databaseprototype.bean.statement.AlterSm;
import com.coaix.databaseprototype.bean.statement.CreateSm;
import org.junit.Test;

/**
 * @author LiaoWei
 * @date 2021-11-02 11:32
 */
public class AlterTools {
    public static AlterSm returnSm(String[] split){
        AlterSm alterSm = new AlterSm();
        /*检查语句完整性*/
            /*语句完整*/
                /*设置complete为true*/
                /*将语句信息填入到AlterSm实例对象中*/
                /*判断是ADD还是DROP*/
                    /*ADD*/
                        /*设置addOrdrop为ADD*/
                    /*DROP*/
                        /*设置addOrdrop为DROP*/
            /*语句不完整*/
                /*设置complete为false*/
        if(StringTools.alterStringCheck(AlterSm.necessity,split)){
            alterSm.setComplete(true);
            alterSm.setColumnName(split[
                        StringTools.checkIndex(split,AlterSm.COLUMN) + 1
                    ]);
            alterSm.setTableName(split[
                        StringTools.checkIndex(split,AlterSm.TABLE) + 1
                    ]);
            /*ADD*/
            if(StringTools.checkIndex(split,"ADD")!= -1 ){
                alterSm.setAddOrdrop(AlterSm.ADD);
                /*判断是否缺少属性类型字段*/
                if(StringTools.checkIndex(split,alterSm.getColumnName()) == split.length - 1){
                    System.out.println("---------缺少属性类型--------");
                    alterSm.setComplete(false);
                    return alterSm;
                }else {
                    alterSm.setType(split[
                            StringTools.checkIndex(split, alterSm.getColumnName()) + 1
                            ]);
                }
            }else{/*DROP*/
                alterSm.setAddOrdrop(AlterSm.DROP);
            }
        }else{
            alterSm.setComplete(false);
        }
        return alterSm;
    }

    @Test
    public void test1(){
        String stateMent="ALTER TABLE author ADD COLUMN annual double(11)";
        String stateMent2="ALTER TABLE author DROP COLUMN annual";
        Object typeSm = SmTools.getTypeSm(stateMent);
        Object typeSm2 = SmTools.getTypeSm(stateMent2);
        System.out.println(typeSm);
        System.out.println(typeSm2);
    }
}
