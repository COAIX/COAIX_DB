package com.coaix.databaseprototype.root.Tools;

import com.coaix.databaseprototype.bean.statement.ErrorSm;
import com.coaix.databaseprototype.bean.statement.UseSm;
import com.coaix.databaseprototype.root.FactoryMode;
import com.coaix.databaseprototype.tools.FileTools;
import com.coaix.databaseprototype.tools.KeyInputTools;
import com.coaix.databaseprototype.tools.reflect.ReflectSmTools;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author LiaoWei
 * @date 2021-11-05 14:27
 */
public class StatementTools {


    public static String sendStatement(UseSm useSm,String statement){
        System.out.println("-----------DATABASE-----------");
        /*运行Service之前要判定语句成分是否缺少*/
            Object typeSm = null;
            try {
                typeSm = ReflectSmTools.getTypeSm(statement);
            } catch (Exception e) {
                return "false";
            }
            if ( typeSm.getClass().equals(ErrorSm.class) ){
                return "false";
            }
            String className = FactoryMode.getServiceClassName(statement);
            /*如果不是选择库*/
            if (!typeSm.getClass().equals(UseSm.class)) {
                try {
                    Class<?> serviceClass = Class.forName(className);
                    Constructor<?>[] declaredConstructors = serviceClass.getDeclaredConstructors();
                    Object o = null;
                    try {
                        o = declaredConstructors[0].newInstance(typeSm, useSm);
                    } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                        return "false";
                    }
                    Method runService = null;
                    try {
                        runService = o.getClass().getMethod("runService");
                    } catch (NoSuchMethodException e) {
                        return "false";
                    }
                    try {
                        runService.invoke(o);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        return "false";
                    }
                } catch (ClassNotFoundException e) {
                    return "false";
                }
            } else {
                useSm = (UseSm) typeSm;
                FileTools.fileWriter(useSm,FileTools.USESMPATH);
            }

        return "true";
    }
}
