/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nodestream.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.*;
import java.util.HashMap;
/**
 *
 * @author Yorbenys
 */
public final class Utils {
    private Utils(){}
    /**
    *
    * @author Yorbenys
    * Get the configuration for the nodeStrem app
    */    
    public static synchronized JSONObject getConfiguration()
    {
            JSONObject obj = new JSONObject("{}");
            try{
            BufferedReader br = new BufferedReader(new FileReader("nodeConfig"));
            StringBuilder sb= new StringBuilder();
            String line = br.readLine();
            while(line != null)
            {
                sb.append(line);
                sb.append(System.lineSeparator());
                line= br.readLine();
            }
            br.close();
            obj= new JSONObject(sb.toString());
            }
            catch(Exception e)
            {
                
                obj.put("nginxConfigurationPath", "");
                obj.put("nginxBinaryPath", "");
                obj.put("interval", -1);
                FileWriter fWriter= new FileWriter("nodeConfig");
                StringBuilder sb= new StringBuilder(obj.toString());
                fWriter.append(sb);
                fWriter.flush();
                fWriter.close();
            }
            finally
            {
                return obj;
            }
          
    }
    /**
    *
    * @author Yorbenys
    * Create a configuration file for the nodeStrem app
    * @param values The key-value
    */
    public static synchronized void createConfigFile(HashMap<String,String> values)
    {
        JSONObject obj = getConfiguration();
        for(String key : values.keySet())
        {
            if(!key.equals("interval"))
                obj.put(key, values.get(key));
            else
                obj.put(key, Integer.parseInt(values.get(key)));
            
        }
              
        try {
            FileWriter fWriter= new FileWriter("nodeConfig");
            StringBuilder sb= new StringBuilder(obj.toString());
            fWriter.append(sb);
            fWriter.flush();
            fWriter.close();
        } catch (Exception e) {
        }
        
    }
    
    
}