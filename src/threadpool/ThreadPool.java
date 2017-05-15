/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package threadpool;

/**
 *
 * @author cector
 */
public class ThreadPool {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //创建具有3个线程线程池
        MultiThread t = MultiThread.getThreadPool(100);
        t.execute(new Runnable[] { new Task(), new Task(), new Task()});
        t.execute(new Runnable[] { new Task(), new Task(), new Task()});
        System.out.println(t);
        t.destory();//销毁线程，不然这个进程不会结束
        System.out.println(t);
    }
    
    
    static class Task implements Runnable
    {
        private static volatile int i = 1;
        
        //执行任务
        
        @Override
        public void run()
        {
            System.out.println("任务" + (i++) + "完成");
        }
    }
    
}
