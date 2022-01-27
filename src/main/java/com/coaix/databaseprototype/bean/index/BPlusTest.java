package com.coaix.databaseprototype.bean.index;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author LiaoWei
 * @date 2021-11-03 20:10
 */
public class BPlusTest {
    @Test
    public void test11(){
//        BPlusTree<ArrayList<String>, String> objectVBPlusTree = new BPlusTree<>();
//        ArrayList<String> bPlusTreeValue = new ArrayList<>();
//        bPlusTreeValue.add("string12");
//        objectVBPlusTree.insert(bPlusTreeValue,"string");
//        objectVBPlusTree.insert(bPlusTreeValue,"string2");
//        System.out.println(objectVBPlusTree);
//        ArrayList<String> string = objectVBPlusTree.find("string");
//        System.out.println(string.get(0));
//        ArrayList<String> set = objectVBPlusTree.find("set");
//        System.out.println(set==null);
//
//        BPlusTree<ArrayList<String>, String> objectVBPlusTree2 = new BPlusTree<>();
//        ArrayList<String> bPlusTreeValue2 = new ArrayList<>();
//        bPlusTreeValue2.add("string12");
//        objectVBPlusTree2.insert(bPlusTreeValue2,"string2");
//        objectVBPlusTree = objectVBPlusTree2;
//        ArrayList<String> string2 = objectVBPlusTree.find("string2");
//        string2.forEach(System.out::println);

        BPlusTree<Integer, String> objectVBPlusTree = new BPlusTree<>();
        objectVBPlusTree.insert(9,"TEST9");
        objectVBPlusTree.insert(8,"TEST8");
        objectVBPlusTree.insert(7,"TEST7");
        objectVBPlusTree.insert(6,"TEST6");
        Integer test7 = objectVBPlusTree.find("TEST5");
        System.out.println(test7);


//        HashMap hashMap = new HashMap();
//        hashMap.put(1,2);
//        Object o = hashMap.get(1);
//        Object o1 = hashMap.get(2);
//        System.out.println(o);
//        System.out.println(o1);
//        System.out.println(o1 == null);
    }

}
