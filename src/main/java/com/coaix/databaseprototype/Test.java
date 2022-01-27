package com.coaix.databaseprototype;
import com.coaix.databaseprototype.bean.data.ColumnAttributes;
import com.coaix.databaseprototype.bean.data.Data;
import com.coaix.databaseprototype.bean.index.BPlusTree;
import com.coaix.databaseprototype.bean.statement.*;
import com.coaix.databaseprototype.service.impl.*;
import com.coaix.databaseprototype.tools.FileTools;
import com.coaix.databaseprototype.tools.SmTools;
import java.util.ArrayList;

/**
 * @author LiaoWei
 * @date 2021-10-31 16:02
 */
public class Test {
    @org.junit.Test
    public void tes1(){
        String str = "create database view";
        CreateSm typeSm = (CreateSm) SmTools.getTypeSm(str);
//        String str2 = "use employee2";
//        UseSm typeSm2 = (UseSm)SmTools.getTypeSm(str2);
        CreateService service = new CreateService(typeSm, new UseSm());
//        CreateService service = new CreateService(typeSm);
        service.runService();
    }

    @org.junit.Test
    public void test12(){
        String database="use books";
        String str="CREATE TABLE USER (username INT(11) NOTNULL AUTOINCREMENT,userid VARCHAR(255) DEFAULTNULL)";
        CreateSm typeSm = (CreateSm)SmTools.getTypeSm(str);
        UseSm typeSm1 = (UseSm)SmTools.getTypeSm(database);
        System.out.println(typeSm.getClass());
        System.out.println(typeSm);
        CreateService createService = new CreateService(typeSm,typeSm1);
        createService.runService();
    }

    @org.junit.Test
    public void test13(){
        String str = "C:\\Users\\Administrator\\Desktop\\数据库概论\\Database\\src\\main\\resources\\BOOKS\\dataDictionary\\EMPLOYEE";
        ArrayList<ColumnAttributes> arrayListClass = FileTools.fileReader(new ArrayList<ColumnAttributes>(), str);
        arrayListClass.forEach(System.out::println);
    }

    /*测试InsertService*/
    @org.junit.Test
    public void stest21(){
        UseSm useSm = new UseSm();
        InsertSm insertSm = new InsertSm();
        String checkDatabase = "use books";
//        String insertStatement = "insert into employee (id,lastname,gender) values (78,test783,女)";
//        String insertStatement = "insert into user (username,userid) values (test1,8)";
        String insertStatement = "insert into user (username,userid) values (coail,3)";
//        String insertStatement = "insert into user (username,userid) values (test1,8)";
        useSm = (UseSm)SmTools.getTypeSm(checkDatabase);
        insertSm = (InsertSm)SmTools.getTypeSm(insertStatement);
        InsertService insertService = new InsertService(insertSm, useSm);
        insertService.runService();
//        ArrayList<ColumnAttributes>s columnAttributes = FileTools.fileReader(new ArrayList<ColumnAttributes>(), FileTools.RESOURCES + "BOOKS\\" + FileTools.DICTIONARY + "EMPLOYEE");
//        columnAttributes.forEach(System.out::println);
    }

    //读取BOOKS数据库EMPLOYEE数据文件
    @org.junit.Test
    public void test22(){
        ArrayList<Data> dataArrayList = FileTools.fileReader(new ArrayList<Data>(), FileTools.RESOURCES + "BOOKS\\" + FileTools.DATABASEDATA + "EMPLOYEE");
        dataArrayList.forEach(System.out::println);
    }

    @org.junit.Test
    public void testCheck(){
        InsertSm insertSm = new InsertSm();
        String insertStatement = "insert into employee (id,lastname,gender,annual) values (1,COAIXTest,男,11)";
        insertSm = (InsertSm)SmTools.getTypeSm(insertStatement);
        String tablePath=FileTools.RESOURCES+"BOOKS"+"\\"+FileTools.DICTIONARY+"EMPLOYEE";
        ArrayList<ColumnAttributes> attributes = FileTools.fileReader(new ArrayList<ColumnAttributes>(), tablePath);
        String[] insertSmColumnName = insertSm.getColumnName();
        System.out.println(check(attributes,insertSmColumnName));
    }

    //读取Employee表数据字典
    @org.junit.Test
    public void test31(){
        String tablePath=FileTools.RESOURCES+"BOOKS"+"\\"+FileTools.DICTIONARY+"EMPLOYEE";
        ArrayList<ColumnAttributes> attributes = FileTools.fileReader( new ArrayList<ColumnAttributes>(), tablePath );
        attributes.forEach( System.out::println );
    }

    @org.junit.Test
    public void alterServiceTest(){
        String stateMent="ALTER TABLE employee DROP COLUMN annual double(11)";
//        String stateMent="ALTER TABLE employee add COLUMN annual double(11)";
        String use="use books";
        AlterSm alterSm = (AlterSm)SmTools.getTypeSm(stateMent);
        UseSm useSm = (UseSm)SmTools.getTypeSm(use);
        AlterService alterService = new AlterService(alterSm, useSm);
        alterService.runService();
    }

    //删除Service测试
    @org.junit.Test
    public void deleteServiceTest(){
        String deleteStatement =  "delete from employee where GENDER=TEST783";
        String databaseStatement = "use books";
        DeleteSm deleteSm = (DeleteSm) SmTools.getTypeSm(deleteStatement);
        UseSm useSm = (UseSm) SmTools.getTypeSm(databaseStatement);
        DeleteService deleteService = new DeleteService(deleteSm, useSm);
        deleteService.runService();

    }

    @org.junit.Test
    public void dropServiceTest(){
        String s1 = "use books";
        String s2 = "drop table user";
        UseSm useSm = (UseSm) SmTools.getTypeSm(s1);
        DropSm dropSm = (DropSm) SmTools.getTypeSm(s2);
        DropService dropService = new DropService(dropSm, useSm);
        dropService.runService();
    }

    @org.junit.Test
    public void selectServiceTest(){
        String s1 = "use books";
        // SelectSm(tableName=[EMPLOYEE], complete=true, columnName=[*], selectType=*, ConditionColumn1=null, ConditionColumn2=null)
//        String s2 = "select * from employee";
//        String s2 = "select * from user";
//        String s2 = "select * from employee,user where employee.lastname=user.username";
//        String s2 = "select * from employee,user where employee.username=test1";
//        String s2 = "select * from employee where id=8";
//        String s2 = "select id,lastname from employee,user where employee.lastname=user.username and employee.lastname=test783";
        String s2 = "select * from employee,user where employee.lastname=user.username";
//        String s2 = "select * from worker,employee,user";
        UseSm useSm = (UseSm) SmTools.getTypeSm(s1);
        Object selectSm =  SmTools.getTypeSm(s2);
        System.out.println( selectSm.getClass().equals(SelectSm.class) );

        SelectService selectService = new SelectService((SelectSm) selectSm, useSm);
        selectService.runService();
    }

    @org.junit.Test
    public void testCreatIndex(){
        String s1 = "use books";
        String s3 = "create index idx_c4 on employee (LASTNAME)";
        UseSm useSm = (UseSm) SmTools.getTypeSm(s1);
        CreateSm createSm = (CreateSm) SmTools.getTypeSm(s3);
        CreateService createService = new CreateService(createSm, useSm);
        createService.runService();
    }

    @org.junit.Test
    public void readIndex(){
        BPlusTree<ArrayList<Integer>, String> objectVBPlusTree = FileTools.fileReader(new BPlusTree<ArrayList<Integer>, String>(), "C:\\Users\\Administrator\\Desktop\\数据库概论\\Database\\src\\main\\resources\\BOOKS\\INDEX\\EMPLOYEE_LASTNAME");
        ArrayList arrayList = objectVBPlusTree.find("22");
        System.out.println(arrayList==null);
        if( arrayList != null ) {
            arrayList.forEach(System.out::println);
        }else{
            System.out.println("不存在");
        }

    }

    private boolean check(ArrayList<ColumnAttributes> attributes, String[] insertSmColumnName) {
        boolean check=false;
        for ( int i = 0; i < insertSmColumnName.length; i++ ) {
            if(insertSmColumnName[i].equals(attributes.get(i).getColumnName())){
                check=true;
            }else{
                return false;
            }
        }
        return check;
    }

}
