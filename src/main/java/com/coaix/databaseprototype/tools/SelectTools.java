package com.coaix.databaseprototype.tools;

import com.coaix.databaseprototype.bean.statement.SelectSm;
import lombok.Data;

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
            /*将语句完整性设置为true*/
            selectSm.setComplete(true);
            /*将语句成分填入*/
            selectSm.setTableName(split[
                        StringTools.checkIndex(split,SelectSm.FROM) + 1
                    ]);
        }else {
            /*如果语句成分缺失或有多余*/
            /*将语句完整性设置为false*/
            selectSm.setComplete(false);
        }
        return selectSm;
    }
}
