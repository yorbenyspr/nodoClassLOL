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

/**
 *
 * @author dotUser
 */
public final class Utils {
    private Utils(){}
    
    public static synchronized JSONObject getConfiguration()
    {
            JSONObject obj = new JSONObject("{}");;
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
    
    
}