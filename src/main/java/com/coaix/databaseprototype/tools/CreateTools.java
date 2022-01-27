package com.coaix.databaseprototype.tools;

import com.coaix.databaseprototype.bean.statement.CreateSm;
import org.junit.Test;

/**
 * @author LiaoWei
 * @date 2021-10-30 13:39
 */
public class CreateTools {

    public static CreateSm returnSm(String[] split){
        CreateSm createSm = new CreateSm();
        /*检查语句*/
            /*如果语句不缺失成分或无多余*/
        if (StringTools.checkAllString(CreateSm.necessity,split) || StringTools.createStringCheck(CreateSm.necessity,split) || StringTools.checkIndex(split,CreateSm.INDEX) != -1){
            /*是否为创建索引*/
            if( StringTools.checkIndex(split,CreateSm.INDEX) != -1 ){
                /*是创建索引*/
                createSm.setComplete(true);
                /*设置为是创建索引*/
                createSm.setIndex(true);
                /*设置索引所在表*/
                createSm.setName(split[
                            StringTools.checkIndex(split,CreateSm.ON) + 1
                        ]);
                /*设置索引所在表的属性名*/
                createSm.setIndexColumnName(split[
                            StringTools.checkIndex(split,CreateSm.ON) + 2
                        ].replace("(","").replace(")",""));
                return createSm;
            }else {
                /*不是创建索引*/

                /*如果是创造Databases*/
                if (split[StringTools.checkIndex(split, CreateSm.CREATE) + 1].equals(CreateSm.DATABASE)) {
                    createSm.setDataOrtable(CreateSm.DATABASE);
                    createSm.setComplete(true);
                    /*将自定义的数据名字填入*/
                    createSm.setName(split[StringTools.checkIndex(split, CreateSm.DATABASE) + 1]);
                    return createSm;
                }
                /*如果是创造表*/
                else {
                    createSm.setDataOrtable(CreateSm.TABLE);
                    createSm.setComplete(true);
                    createSm.setName(split[
                            StringTools.checkIndex(split, CreateSm.TABLE) + 1
                            ]);
                    createSm.setColumnAttributes(
                            StringTools.createTableAttributes(split)
                    );
                    return createSm;
                }
            }

        }
            /*如果语句缺失成分或有多余*/
        else{
            createSm.setComplete(false);
            return createSm;
        }
    }





    @Test
    public void steat(){
//        String[] a = {"", "2", "3"};
//        String[] b = {"1","2","3","3"};
//        System.out.println(StringTools.checkAllString(a,b));
//        String sm="create database books".toUpperCase();
//        String[] split = sm.split(" ");
//        String s = Arrays.toString(split);
//
//        Object typeSm = SmTools.getTypeSm(sm);
//        System.out.println(typeSm);
//        String str="CREATE TABLE EMPLOYEE (ID INT(11) NOTNULL AUTOINCREMENT,LASTNAME VARCHAR(255) DEFAULTNULL,GENDER CHAR(1) DEFAULTNULL)";
//        CreateSm createSm = CreateTools.returnSm(str.split(" "));
//        System.out.println(createSm);
//
//        ArrayList<ColumnAttributes> attributes = FileTools.fileReader(new ArrayList<ColumnAttributes>(), "C:\\Users\\Administrator\\Desktop\\数据库概论\\Database\\src\\main\\resources\\BOOKS\\dataDictionary\\EMPLOYEE");
//        attributes.forEach(System.out::println);
        String str = "create index index_name on table_name (column_list)";
        CreateSm createSm = CreateTools.returnSm(str.toUpperCase().split(" "));
        System.out.println(createSm);
        //输出
        //ColumnAttributes(columnName=ID, type=INT11, bind=[NOTNULL, AUTOINCREMENT])
        //ColumnAttributes(columnName=LASTNAME, type=VARCHAR255, bind=[DEFAULTNULL])
        //ColumnAttributes(columnName=GENDER, type=CHAR1, bind=[DEFAULTNULL])

    }

}
