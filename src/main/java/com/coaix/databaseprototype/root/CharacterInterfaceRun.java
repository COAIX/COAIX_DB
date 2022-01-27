package com.coaix.databaseprototype.root;

import com.coaix.databaseprototype.bean.data.DataDictionary;
import com.coaix.databaseprototype.bean.statement.*;
import com.coaix.databaseprototype.service.impl.*;
import com.coaix.databaseprototype.tools.*;

import java.util.Objects;

/**
 * 数据库原型字符界面运行主程序
 * @author LiaoWei
 * @date 2021-10-30 15:54
 */
public class CharacterInterfaceRun {

    public static void main(String[] args) {
        UseSm useSm = new UseSm();
        System.out.println("-----------DATABASE-----------");
        /*运行Service之前要判定语句成分是否缺少*/
        while (true) {
            String string = KeyInputTools.readString();
            Object typeSm = SmTools.getTypeSm(string);
            if ( typeSm.getClass().equals( CreateSm.class ) ){

                System.out.println("CreateSm"+typeSm);
                new CreateService((CreateSm)typeSm,useSm).runService();

            }else if ( typeSm.getClass().equals( InsertSm.class ) ){

                System.out.println("InsertSm"+typeSm);
                new InsertService((InsertSm)typeSm,useSm).runService();

            }else if ( typeSm.getClass().equals( AlterSm.class ) ){

                System.out.println("AlterSm"+typeSm);
                new AlterService((AlterSm) typeSm,useSm).runService();

            }else if ( typeSm.getClass().equals( DeleteSm.class ) ){

                System.out.println("DeleteSm"+typeSm);
                new DeleteService((DeleteSm) typeSm,useSm).runService();

            }else if ( typeSm.getClass().equals( DropSm.class ) ){

                System.out.println("DropSm"+typeSm);
                new DropService((DropSm) typeSm,useSm).runService();

            }else if ( typeSm.getClass().equals( SelectSm.class ) ){

                System.out.println("SelectSm"+typeSm);
                new SelectService((SelectSm) typeSm,useSm).runService();

            }else if ( typeSm.getClass().equals( UseSm.class ) ){

                System.out.println("UseSm"+typeSm);
                useSm = (UseSm) typeSm;

            }else if ( typeSm.getClass().equals( ErrorSm.class ) ){

                System.out.println("---------语法错误--------");

            }else{

                System.out.println("---------语法错误--------");

            }

        }
    }



}
