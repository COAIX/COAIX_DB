package com.coaix.databaseprototype.tools;


import com.coaix.databaseprototype.bean.statement.UpdateSm;

/**
 * @author LiaoWei
 * @date 2021-11-04 16:38
 */
public class UpdateTools {

    public static UpdateSm returnSm(String[] split){

        UpdateSm updateSm = new UpdateSm();
        /*检查语句完整性*/
        if(StringTools.checkAllString(UpdateSm.necessity,split)) {
            /*语句完整*/
            /*设置complete为true*/
            updateSm.setComplete(true);
            /*将语句信息填入UpdateSm实例对象中*/
            /*判断是否有where字样*/
            updateSm.setTableName( split[
                        StringTools.checkIndex(split,UpdateSm.UPDATE) + 1
                    ] );
            updateSm.setChangeData(
                    StringTools.equalSignDivision(
                        split[
                            StringTools.checkIndex(split,UpdateSm.SET) + 1
                        ]
                    )
            );
            if( StringTools.checkIndex(split,UpdateSm.WHERE) != -1 ) {
                /*有where - > 具有判断条件*/
                updateSm.setRequirement(
                        StringTools.equalSignDivision(
                                split[
                                        StringTools.checkIndex(split,UpdateSm.WHERE) + 1
                                        ]
                        )
                );
            }else {
                /*没有*/
                updateSm.setRequirement(null);
            }
        }else {
            /*语句不完整*/
            /*设置complete为false*/
            updateSm.setComplete(false);
        }
        return updateSm;
    }

}
