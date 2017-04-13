/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodestream.utils;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import org.hyperic.sigar.*;
import org.json.*;
/**
 *
 * @author Yorbenys
 */
public final class SystemInfo {
    private SystemInfo(){};
    /**
    *
    * @author Yorbenys
    * Get the memory usage in the System.
    */
    public static String memoryUsage()
    {
        OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        long usage=operatingSystemMXBean.getTotalPhysicalMemorySize()-operatingSystemMXBean.getFreePhysicalMemorySize();
        return Long.toString(usage/1024);
    }
    /**
    *
    * @author Yorbenys
    * Get the total memory in the System.
    */
    public static String totalMemory()
    {
        OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return Long.toString(operatingSystemMXBean.getTotalPhysicalMemorySize()/1024);
        
    }
    /**
    *
    * @author Yorbenys
    * Get the free memory in the System.
    */
    public static String freeMemory()
    {
        OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return Long.toString(operatingSystemMXBean.getFreePhysicalMemorySize()/1024);
    }
    /**
    *
    * @author Yorbenys
    * Get the cpu info in the System.
    */
    public static JSONObject cpuInfo()
    {
        JSONObject cpuU = new JSONObject();
        try {
            Sigar s= new Sigar();
            CpuPerc percs[]= s.getCpuPercList();
            cpuU.put("totalUsage",s.getCpuPerc().getCombined()*100);//Global information
            cpuU.put("cores", percs.length);
            JSONObject perCores = new JSONObject();
            for (int i = 0; i < percs.length; i++) {
                perCores.put("core-"+String.valueOf(i),percs[i].getCombined()*100);//Per core
            }
            cpuU.put("perCores", perCores);
            
        } catch (Exception e) {
        }
        return cpuU;
    }
    /**
    *
    * @author Yorbenys
    * Get the system info.
    */
    public static JSONObject getSystemInfo()
    {
        JSONObject info = new JSONObject();
        info.put("memoryUsage", memoryUsage());
        info.put("FreeMemory", freeMemory());
        info.put("TotalMemory", totalMemory());
        info.put("cpuInfo", cpuInfo());
        return info;
    }
}
