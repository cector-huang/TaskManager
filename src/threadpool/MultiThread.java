/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package threadpool;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author cector
 * 线程池管理器：创建线程、执行任务、销毁线程，获取线程的基本信息
 */
public final class MultiThread {
    //线程池中的线程默认个数
    private static int work_num = 5;
    //工作线程
    private WorkThread[] workThread;
    //未处理的线程的个数
    private static volatile int finished_num = 0;
    //用于缓冲的任务队列，List非线程安全
    private final List<Runnable> taskQueue = new LinkedList<Runnable>();
    //线程池管理器的单例
    private static MultiThread multiThread;
    
    //单例模式的构造函数私有
    private MultiThread()
    {
        this(5);
    }
    
    //创建线程池,传入的参数代表线程池中线程的个数
    private MultiThread(int work_num)
    {
        MultiThread.work_num = work_num;
        //线程池中创建初始线程
        workThread = new WorkThread[work_num];
        for(int i = 0; i < work_num; ++i)
        {
            System.out.println(i);
            workThread[i] = new WorkThread();
            workThread[i].start();//开启线程池中的线程
        }
    }
    
    //单例模式中，获取一个具有指定个数的线程池
    public static MultiThread getThreadPool(int work_num)
    {
        if(work_num <= 0)
        {
            work_num = MultiThread.work_num;
        }
        //查看实例是否已经生成
        if(multiThread == null)
        {
            multiThread = new MultiThread(work_num);
        }
        return multiThread;
    }
    
    //获取默认线程个数的线程池
    public static MultiThread getThreadPool()
    {
        return getThreadPool(MultiThread.work_num);
    }
    
    //执行单个任务，其实就是把任务添加到任务队列中
    public void execute(Runnable task)
    {
        //同步代码块
        synchronized(taskQueue)
        {
            taskQueue.add(task);
            taskQueue.notify();
        }
    }
    //执行多个任务
    public void execute(Runnable[] task)
    {
        synchronized(taskQueue)
        {
            for(Runnable t: task)
            {
                taskQueue.add(t);
            }
            taskQueue.notify();
        }
    }
    
    //执行多个任务
    public void execute(List<Runnable> task)
    {
        synchronized(taskQueue)
        {
            for(Runnable t: task)
            {
                taskQueue.add(t);
            }
            taskQueue.notify();
        }
    }
    
    //销毁线程
    public void destory()
    {
        //如果线程没有执行完，就先暂停一段时间
        while(!taskQueue.isEmpty())
        {
            try{
                Thread.sleep(10);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        //将所有的任务线程终止
        for(int i =0; i< work_num; ++i)
        {
            workThread[i].stopWorker();
            workThread[i] = null;
        }
        multiThread = null;
        taskQueue.clear();//清空任务队列
    }
    //返回任务线程的个数
    public int getWorkerThreadNumber()
    {
        return work_num;
    }
    
    //返回已完成的任务个数
    public int getFinishedThreadNumber()
    {
        return finished_num;//这个已完成时从任务队列出来，并不代表已经完成
    }
    
    //返回当前任务队列中任务的长度
    public int getWaitThreadNumber()
    {
        return taskQueue.size();
    }
    
    //覆盖toString方法
    @Override
    public String toString()
    {
        return "WorkerThread: " + work_num + "Finished_num: " + finished_num + "waitTask: " + getWaitThreadNumber();
    }
    
    //内部类，工作线程
    private class WorkThread extends Thread{
        //该标记用于工作线程是否有效
        private boolean isRunning = true;
        
        //工作线程，如果任务队列不为空，就会从中取出任务，若任务队列为空，就会一直等待，就是说工作线程是持续运转的
        @Override
        public void run()
        {
            Runnable r = null;
            while(isRunning)
            {
                synchronized(taskQueue)//所有的锁都是以任务队列为基准的
                {
                    while(isRunning && taskQueue.isEmpty())
                    {
                        try{
                            taskQueue.wait(20);
                        }
                        catch(InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    if(!taskQueue.isEmpty())
                    {
                        r = taskQueue.remove(0);//从任务队列中取出一个任务
                    }
                }
                if(r != null)
                {
                    r.run();//执行任务
                }
                finished_num++;
                r = null;
            }
        }
        
        //当任务线程执行完工作后，自动结束
        public void stopWorker()
        {
            isRunning = false;
        }
    }
    
    
    
}
