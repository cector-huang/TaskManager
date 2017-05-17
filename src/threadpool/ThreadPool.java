/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package threadpool;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author cector
 */
public class ThreadPool {
    
    Logger LOGGER;

    public ThreadPool() {
        this.LOGGER = Logger.getLogger(ThreadPool.class);
        LOGGER.info("begin to create instance!");
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //启动数据库监控线程
        init();
        Runnable dbmonitor = new DBMonitor();
        Thread monitor = new Thread(dbmonitor, "DBMonitor");
        monitor.setDaemon(false);//非守护线程
        monitor.start();//开始监控数据库，如果数据库中有任何变化就添加到任务队列
    }
    
    private static void init()
    {
        String url = ThreadPool.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        System.out.println(url);
        String path = "";
        if(url.endsWith(".jar"))
        {
            path = url.substring(0, url.lastIndexOf("/")+1);
        }
        if(path != "" && !path.isEmpty())
        {
            path += "config/log4j.properties";
            System.out.println(path);
            PropertyConfigurator.configure(path);
        }
    }
    
}
