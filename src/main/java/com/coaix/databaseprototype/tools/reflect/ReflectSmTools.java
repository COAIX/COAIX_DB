package com.coaix.databaseprototype.tools.reflect;
import cn.hutool.core.util.StrUtil;
import com.coaix.databaseprototype.bean.statement.*;
import org.junit.Test;
import java.lang.reflect.Method;

/**
 * @author LiaoWei
 * @date 2021-11-04 15:44
 */
public class ReflectSmTools {
    /**
     * 传入sql语句，解析返回对应的Sm类
     * @author LiaoWei
     * @date 2021/10/30 14:45
     * @param statement
     * @return java.lang.Object
     */
    public static Object getTypeSm(String statement)throws Exception{

        String[] split = statement.toUpperCase().split(" ");
        String typeHead = split[0];
        String toolsClassName = getToolsClassName(typeHead);
        try {
            Class<?> threadClazz = Class.forName(toolsClassName);
            Method method = threadClazz.getMethod("returnSm", String[].class);
            return method.invoke(null, (Object) split);
        } catch (ClassNotFoundException e) {
            System.out.println("----Reflect Error Can't Find This Class--");
//            System.out.println("----------语法错误----------");
            return new ErrorSm();
        }

    }

    private static String getToolsClassName(String typeSm){
        String packagePath = "com.coaix.databaseprototype.tools.";
        String typeHead = StrUtil.sub(typeSm, 0, 1).toUpperCase();
        String typeBody = StrUtil.sub(typeSm,1,typeSm.length() ).toLowerCase();
        return packagePath+typeHead+typeBody+"Tools";
    }

    @Test
    public void testReflect(){
        Object typeSm = null;
        try {
            typeSm = ReflectSmTools.getTypeSm("UPDATE BEAUTY SET PHONE=13888 WHERE ID=2");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(typeSm);
    }
}
