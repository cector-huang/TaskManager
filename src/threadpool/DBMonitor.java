/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadpool;

import org.apache.log4j.Logger;

/**
 *
 * @author cector
 */
public class DBMonitor implements Runnable{
    Logger LOGGER = Logger.getLogger(DBMonitor.class);
    private static int i;
    
    DBMonitor()
    {
        i = 1;
    }
    
    @Override
    public void run()
    {
        init();
        while(true)
        {
            Utils.sleep(10000);
            //每过10s钟处理一次数据库
            processWork();
                    
        }
    }
    
    private void processWork()
    {
        //查询数据库记录，如果查询到最新数据就把数据库中的新任务添加到任务队列
        String str = "这是第" + (i++) + "次查询并处理数据库";
        LOGGER.info(str);
        System.out.println(str);
        MultiThread.getThreadPool(100).execute(new Runnable[] { new Task(), new Task(), new Task(), new Task()});
    }
    
    private void init()
    {
        System.out.println("Enter DBMonitor!");
    }
}
