package com.coaix.databaseprototype.tools;
import com.coaix.databaseprototype.bean.data.ColumnAttributes;
import com.coaix.databaseprototype.bean.data.DataDictionary;
import com.coaix.databaseprototype.bean.statement.CreateSm;
import com.coaix.databaseprototype.bean.statement.InsertSm;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author LiaoWei
 * @date 2021-10-30 14:19
 */
public class StringTools {
    /**
     * 检查一个数组是否包含另一个数组的全部元素
     * @author LiaoWei
     * @date 2021/10/30 14:25
     * @param module
     * @param target
     * @return boolean
     */
    public static boolean checkAllString(String[] module,String[] target){
        int flag=0;
        for (String s : target) {
            for (String s1 : module) {
                if(s.equals(s1)){
                    flag++;
                }
            }
        }
        if(flag==module.length){
            return true;
        }
        else {
            return false;
        }
    }
    /**
     * CREATE语句专用
     * @author LiaoWei
     * @date 2021/10/30 19:42
     * @param module
     * @param target
     * @return boolean
     */
    public static boolean createStringCheck(String[] module,String[] target){
        int flag=0;
        for (String s : target) {
            for (String s1 : module) {
                if(s.equals(s1)){
                    flag++;
                }
            }
        }
        if(flag==2){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * ALTER语句专用
     * @author LiaoWei
     * @date 2021/11/2 11:27
     * @param module
     * @param target
     * @return boolean
     */
    public static boolean alterStringCheck(String[] module,String[] target){
        int flag=0;
        for (String s : target) {
            for (String s1 : module) {
                if(s.equals(s1)){
                    flag++;
                }
            }
        }
        if(flag==4){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 返回字符在数组中出现的第一个位置，如果不存在于数组中则返回-1
     * @author LiaoWei
     * @date 2021/10/30 14:37
     * @param module
     * @param target
     * @return int
     */
    public static int checkIndex(String[] module,String target){
        int i=0;
        for (String s : module) {
            if (s.equals(target)){
                return i;
            }
            else{
                i++;
            }
        }
        return -1;
    }

    /**
     * 目前为止用在delete和update中，用来将判断条件用‘=’分割开，返回‘=’号两边的元素组成的数组
     * @author LiaoWei
     * @date 2021/10/30 14:54
     * @param old
     * @return java.lang.String[]
     */
    public static String[] equalSignDivision(String old){
        return old.split("=");
    }

    /**
     * 传入列名或者插入值字符串，返回列名字符串数组或者插入数值字符串数组
     * @author LiaoWei
     * @date 2021/10/30 15:31
     * @param old "(1,LiaoWei,100)"
     * @return java.lang.String[]
     */
    public static String[] getColumnName(String old){
        String[] replace = old.replace("(", "").replace(")","").split(",");
        return replace;
    }

    public static String[] getInsertValue(String old){
        return StringTools.getColumnName(old);
    }

//    CREATE TABLE EMPLOYEE (ID INT(11) NOTNULL AUTOINCREMENT,LASTNAME VARCHAR(255) DEFAULT NULL,GENDER CHAR(1) DEFAULT NULL)
//    "(ID" "INT(11)" "NOTNULL" "AUTOINCREMENT,LASTNAME" "VARCHAR(255)" "DEFAULTNULL,GENDER" "CHAR(1)" "DEFAULT" "NULL)"

    public static ArrayList<ColumnAttributes> createTableAttributes(String[] splitold){
        int attributesSplitStart = StringTools.checkIndex(splitold, CreateSm.TABLE)+2;
        StringBuilder attributesSM = new StringBuilder();
        for (int i = attributesSplitStart; i < splitold.length; i++) {
            attributesSM.append(splitold[i]+" ");
        }
        attributesSM.deleteCharAt(attributesSM.length() - 1);

        String[] firstArray = StringTools.getColumnName(attributesSM.toString());
        /*定义列属性集合，将每一个属性加入到集合中*/
        ArrayList<ColumnAttributes> attributes = new ArrayList<>();
        for (String s : firstArray) {
            String[] split = s.split(" ");
            ColumnAttributes columnAttributes = new ColumnAttributes();
            columnAttributes.setColumnName(split[0]);
            columnAttributes.setType(split[1]);
            /*将约束都加入到列表中*/
            ArrayList<String> bind=new ArrayList<>();
            for (int i = 2; i < split.length; i++) {
                bind.add(split[i]);
            }
            columnAttributes.setBind(bind);
            /*单个列属性整合完毕，加入到列属性集合中*/
            attributes.add(columnAttributes);
        }
        return attributes;
    }


    @Test
    public void test1(){
//        String[] columnName = StringTools.getColumnName("(1,LiaoWei,100)");
//        System.out.println(Arrays.toString(columnName));
        String str="CREATE TABLE EMPLOYEE (ID INT(11) NOTNULL AUTOINCREMENT,LASTNAME VARCHAR(255) DEFAULTNULL,GENDER CHAR(1) DEFAULTNULL)";

        Object typeSm = SmTools.getTypeSm(str);

        System.out.println(typeSm.getClass().equals(CreateSm.class));

        System.out.println(typeSm.getClass().equals(InsertSm.class));

        CreateSm createSm = (CreateSm)typeSm;

        System.out.println(createSm);
    }

}
