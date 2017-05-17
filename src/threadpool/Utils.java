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
public class Utils {
    
    public static void sleep(long timeMils)
    {
        try
        {
            Thread.sleep(timeMils);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
}
