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
public class Task implements Runnable
    {
        private static volatile int i = 1;
        
        //执行任务
        
        @Override
        public void run()
        {
            String str = Thread.currentThread().getName();
            System.out.println(str + ":任务" + (i++) + "完成");
        }
    }
