package com.coaix.databaseprototype.bean.thread;

import com.coaix.databaseprototype.bean.index.BPlusTree;
import com.coaix.databaseprototype.tools.FileTools;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiaoWei
 * @date 2021-11-04 11:27
 */
public class DemonThread {
    static class Task implements Runnable{
        private String name;
        private int time;
        public Task(String s,int t) {
            name=s;
            time=t;
        }
        public int getTime(){
            return time;
        }
        @Override
        public void run() {
            for(int i=0;i<time;++i){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(name+" is interrupted when calculating, will stop...");
                    return;  //注意这里如果不return的话，线程还会继续执行，所以任务超时后在这里处理结果然后返回
                }
                /*----------------*/
                BPlusTree<ArrayList<Integer>, String> objectVBPlusTree = FileTools.fileReader(new BPlusTree<ArrayList<Integer>, String>(), "C:\\Users\\Administrator\\Desktop\\数据库概论\\Database\\src\\main\\resources\\BOOKS\\INDEX\\EMPLOYEE_LASTNAME");
                ArrayList arrayList = objectVBPlusTree.find("TEST8");
                System.out.println(arrayList==null);
                if( arrayList != null ) {
                    arrayList.forEach(System.out::println);
                }else{
                    System.out.println("不存在");
                }
                /*----------------*/

                System.out.println("task "+name+" "+(i+1)+" round");
            }
            System.out.println("task "+name+" finished successfully");
        }
    }
    static class Daemon implements Runnable{
        List<Runnable> tasks=new ArrayList<Runnable>();
        private Thread thread;
        private int time;
        public Daemon(Thread r,int t) {
            thread=r;
            time=t;
        }
        public void addTask(Runnable r){
            tasks.add(r);
        }
        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(time*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                thread.interrupt();
            }
        }

    }
    public static void main(String[] args) {
        Task task1=new Task("one", 2);
        Thread t1=new Thread(task1);
        Daemon daemon=new Daemon(t1, 3);
        Thread daemoThread=new Thread(daemon);
        daemoThread.setDaemon(true);
        daemoThread.start();
        t1.start();
    }
}