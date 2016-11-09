/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodestream.utils;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import sun.misc.GC;
/**
 *
 * @author yorbe
 */
public final class SystemInfo {
    private SystemInfo(){};
    
    public static String memoryUsage()
    {
        OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        long usage=operatingSystemMXBean.getTotalPhysicalMemorySize()-operatingSystemMXBean.getFreePhysicalMemorySize();
        return Long.toString(usage/1024);
    }
    public static String totalMemory()
    {
        OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return Long.toString(operatingSystemMXBean.getTotalPhysicalMemorySize()/1024);
        
    }
    public static String freeMemory()
    {
        OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return Long.toString(operatingSystemMXBean.getFreePhysicalMemorySize()/1024);
    }
    public static String cpuUsage()
    {
        OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean(); 
        double usage= operatingSystemMXBean.getSystemCpuLoad();
        return Double.toString(usage);
    }
}
