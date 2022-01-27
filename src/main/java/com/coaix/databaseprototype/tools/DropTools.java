package com.coaix.databaseprototype.tools;


import com.coaix.databaseprototype.bean.statement.DropSm;
import org.junit.Test;

/**
 * @author LiaoWei
 * @date 2021-11-02 21:25
 */
public class DropTools {

    public static DropSm returnSm(String[] split){
        DropSm dropSm = new DropSm();
        /*检查语句*/
        /*如果语句成分完整*/
        if ( StringTools.checkAllString(DropSm.necessity,split) ) {
            /*设置DropSm语句完整性为true*/
            dropSm.setComplete(true);
            /*将表名填入*/
            if ( StringTools.checkIndex(split,DropSm.EXISTS) != -1 ) {
                /*如果存在EXISTS字段，则将EXISTS字段后面的字符串填入*/
                dropSm.setTableName( split[
                            StringTools.checkIndex(split,DropSm.EXISTS) + 1
                        ] );
            }else {
                /*如果不存在EXISTS字段，则将TABLE字段后面的字符串填入*/
                dropSm.setTableName( split[
                            StringTools.checkIndex(split,DropSm.TABLE) + 1
                        ] );
            }
        }else {
            /*如果语句成分不完整*/
            /*将DropSm语句完整性设置为false*/
            dropSm.setComplete(false);
        }
        /*返回DropSm*/
        return dropSm;
    }

    @Test
    public void test1(){
        String stateMent = "drop table if exists book_author";
        String stateMent2 = "drop table book_author";
        DropSm dropSm = (DropSm) SmTools.getTypeSm(stateMent);
        DropSm dropSm2 = (DropSm) SmTools.getTypeSm(stateMent2);
        System.out.println(dropSm);
        System.out.println(dropSm2);

    }

}
