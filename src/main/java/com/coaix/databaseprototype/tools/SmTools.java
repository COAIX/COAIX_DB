package com.coaix.databaseprototype.tools;

import com.coaix.databaseprototype.bean.statement.*;

import java.util.Objects;

/**
 * @author LiaoWei
 * @date 2021-10-30 13:29
 */
public class SmTools {

    /**
     * 传入sql语句，解析返回对应的Sm类
     * @author LiaoWei
     * @date 2021/10/30 14:45
     * @param statement
     * @return java.lang.Object
     */
    public static Object getTypeSm(String statement){

        /*将语句用空格分隔开*/
        String[] split = statement.toUpperCase().split(" ");

        /*如果是插入语句
        * 传进去的是按空格分好的数组
        * */
        if (Objects.equals(split[0], InsertSm.INSERT)){
            return InsertTools.returnSm(split);
        }

        /*如果是选择数据库语句*/
        else if (Objects.equals(split[0], UseSm.USE)){
            return UseTools.returnSm(split);
        }

        /*如果是删除语句*/
        else if (Objects.equals(split[0], DeleteSm.DELETE)){
            return DeleteTools.returnSm(split);
        }

        /*如果是创造语句*/
        else if (Objects.equals(split[0], CreateSm.CREATE)){
            return CreateTools.returnSm(split);
        }

        /*如果是删除表语句(DROP)*/
        else if (Objects.equals(split[0], DropSm.DROP)){
            return DropTools.returnSm(split);
        }

        /*如果是编辑属性语句*/
        else if (Objects.equals(split[0], AlterSm.ALTER)){
            return AlterTools.returnSm(split);
        }

        /*如果是查询语句*/
        else if (Objects.equals(split[0], SelectSm.SELECT)){
            return SelectTools.returnSm(split);
        }

        /*如果是错误语句*/
        else{
            return new ErrorSm();
        }
    }

}
