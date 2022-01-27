package com.coaix.databaseprototype.root;

import cn.hutool.core.util.StrUtil;
import com.coaix.databaseprototype.bean.statement.*;
import com.coaix.databaseprototype.tools.KeyInputTools;
import com.coaix.databaseprototype.tools.reflect.ReflectSmTools;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author LiaoWei
 * @date 2021-11-04 14:49
 */
public class FactoryMode {

    public static void main(String[] args) throws Exception{
        UseSm useSm = new UseSm();
        System.out.println("-----------DATABASE-----------");
        /*运行Service之前要判定语句成分是否缺少*/
        while (true) {
            String inputString = KeyInputTools.readString();
            Object typeSm = ReflectSmTools.getTypeSm(inputString);
            String className = FactoryMode.getServiceClassName(inputString);
            /*如果不是选择库*/
            if (!typeSm.getClass().equals(UseSm.class)) {
                try {
                    Class<?> serviceClass = Class.forName(className);
                    Constructor<?>[] declaredConstructors = serviceClass.getDeclaredConstructors();
                    Object o = declaredConstructors[0].newInstance(typeSm, useSm);
                    Method runService = o.getClass().getMethod("runService");
                    runService.invoke(o);
                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
                    System.out.println("---------语法错误--------");
                }
            } else {
                useSm = (UseSm) typeSm;
            }
        }

    }

    public static String getServiceClassName(String stateMent){
        String typeSm = stateMent.split(" ")[0];
        String packagePath = "com.coaix.databaseprototype.service.impl.";
        String typeHead = StrUtil.sub(typeSm, 0, 1).toUpperCase();
        String typeBody = StrUtil.sub(typeSm,1,typeSm.length() ).toLowerCase();
        return packagePath+typeHead+typeBody+"Service";
    }

    @Test
    public void test1(){
        String str = "SELECT";
        String serviceClassName = getServiceClassName(str);
        System.out.println(serviceClassName);
    }

}
