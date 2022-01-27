package com.coaix.databaseprototype.bean.thread;

import com.coaix.databaseprototype.bean.index.BPlusTree;
import com.coaix.databaseprototype.tools.FileTools;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * @author LiaoWei
 * @date 2021-11-04 11:38
 */
public class FutureDemo {

    static ExecutorService executorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);

    public static void main(String[] args) {
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    BPlusTree<ArrayList<Integer>, String> objectVBPlusTree = FileTools.fileReader(new BPlusTree<ArrayList<Integer>, String>(), "C:\\Users\\Administrator\\Desktop\\数据库概论\\Database\\src\\main\\resources\\BOOKS\\INDEX\\EMPLOYEE_LASTNAME");
                    ArrayList arrayList = objectVBPlusTree.find("TEST8");
                    System.out.println(arrayList==null);
                    if( arrayList != null ) {
                        arrayList.forEach(System.out::println);
                    }else{
                        System.out.println("不存在");
                    }

                } catch (InterruptedException e) {
                    System.out.println("任务被中断。");
                }
                return  "OK";
            }
        });
        try {
            String result = future.get(2, TimeUnit.SECONDS);
        } catch (InterruptedException |ExecutionException | TimeoutException e) {
            future.cancel(true);
            System.out.println("任务超时。");
        }finally {
            System.out.println("清理资源。");
        }
    }
}