package com.coaix.databaseprototype;
import com.coaix.databaseprototype.bean.data.ColumnAttributes;
import com.coaix.databaseprototype.bean.data.Data;
import com.coaix.databaseprototype.bean.index.BPlusTree;
import com.coaix.databaseprototype.bean.statement.*;
import com.coaix.databaseprototype.root.bean.Databaseinformation;
import com.coaix.databaseprototype.service.impl.*;
import com.coaix.databaseprototype.tools.FileTools;
import com.coaix.databaseprototype.tools.SmTools;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * @author LiaoWei
 * @date 2021-10-31 16:02
 */
public class Test {
    @org.junit.Test
    public void tes1(){
        String str = "create database books";
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
        String str="CREATE TABLE EMPLOYEE (ID INT(11) NOTNULL AUTOINCREMENT,LASTNAME VARCHAR(255) DEFAULTNULL,GENDER CHAR(1) DEFAULTNULL)";
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
        String insertStatement = "insert into employee (id,lastname,gender) values (7,test783,女)";
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
        String insertStatement = "insert into employee2 (id,lastname,gender) values (1,COAIXTest,男)";
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
        String stateMent="ALTER TABLE employee drop COLUMN annual double(11)";
        String use="use books";
        AlterSm alterSm = (AlterSm)SmTools.getTypeSm(stateMent);
        UseSm useSm = (UseSm)SmTools.getTypeSm(use);
        AlterService alterService = new AlterService(alterSm, useSm);
        alterService.runService();
    }

    //删除Service测试
    @org.junit.Test
    public void deleteServiceTest(){
        String deleteStatement =  "delete from employee where LASTNAME=TEST12";
        String databaseStatement = "use books";
        DeleteSm deleteSm = (DeleteSm) SmTools.getTypeSm(deleteStatement);
        UseSm useSm = (UseSm) SmTools.getTypeSm(databaseStatement);
        DeleteService deleteService = new DeleteService(deleteSm, useSm);
        deleteService.runService();

    }

    @org.junit.Test
    public void dropServiceTest(){
        String s1 = "use employee2";
        String s2 = "drop table employee";
        UseSm useSm = (UseSm) SmTools.getTypeSm(s1);
        DropSm dropSm = (DropSm) SmTools.getTypeSm(s2);
        DropService dropService = new DropService(dropSm, useSm);
        dropService.runService();
    }

    @org.junit.Test
    public void selectServiceTest(){
        String s1 = "use books";
        String s2 = "select * from employee";
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
        ArrayList arrayList = objectVBPlusTree.find("TEST12");
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

    @org.junit.Test
    public void testFindFileExplore(){
        ArrayList<Databaseinformation> databaseinfromation = FileTools.getDatabaseinfromation();
        databaseinfromation.forEach(new Consumer<Databaseinformation>() {
            @Override
            public void accept(Databaseinformation databaseinformation) {
                System.out.println(databaseinformation);
            }
        });
    }

}
