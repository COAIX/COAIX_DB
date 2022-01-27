package com.coaix.databaseprototype.bean.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.*;


import com.coaix.databaseprototype.bean.index.BPlusTree;
import com.coaix.databaseprototype.tools.FileTools;
import com.sun.corba.se.impl.orbutil.threadpool.TimeoutException;

public class ThreadTest {

       private static class Caller implements Callable<Boolean> {
        @Override
        public Boolean call() {
            try {
                BPlusTree<ArrayList<Integer>, String> objectVBPlusTree = FileTools.fileReader(new BPlusTree<ArrayList<Integer>, String>(), "C:\\Users\\Administrator\\Desktop\\数据库概论\\Database\\src\\main\\resources\\BOOKS\\INDEX\\EMPLOYEE_LASTNAME");
                ArrayList arrayList = objectVBPlusTree.find("TEST8");
                System.out.println(arrayList==null);
                if( arrayList != null ) {
                    arrayList.forEach(System.out::println);
                }else{
                    System.out.println("不存在");
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    private static class Runner implements Runnable {
        @Override
        public void run() {
            ExecutorService excutor = Executors.newSingleThreadExecutor();
            Future<Boolean> future = excutor.submit(new Caller());
            try {
                future.get(1, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ScheduledExecutorService service
                = Executors.newScheduledThreadPool(1);
        service.submit(new Runner());
    }
}