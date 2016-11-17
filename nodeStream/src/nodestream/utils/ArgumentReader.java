/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodestream.utils;
import java.util.HashMap;
import org.json.*;
/**
 *
 * @author yorbe
 */
public final class ArgumentReader {
    
    public static synchronized void readArguments(String[] arguments)
    {
        JSONObject configuration = Utils.getConfiguration();
        HashMap<String,String> confArguments= new HashMap<>();
        for(int i=0; i<arguments.length;i+=2)
        {
            if(arguments.length%2==0)
            {
                if(arguments[i].equals("nginx-config-file"))
                    confArguments.put("nginxConfigurationPath", arguments[i+1]);
                else if(arguments[i].equals("nginx-exec-file"))
                    confArguments.put("nginxBinaryPath", arguments[i+1]);
                else if(arguments[i].equals("interval"))
                    confArguments.put("interval", arguments[i+1]);
                else if(arguments[i].equals("create-stream"))
                {
                    if(!arguments[i+2].equals("pull-from"))
                        continue;
                    try {
                            ConfigWriter.singleton().createStream(configuration.getString("nginxConfigurationPath"),arguments[i+1],arguments[i+3]);
                            Runtime.getRuntime().exec(configuration.getString("nginxBinaryPath")+" -s reload");
                    } catch (Exception e) {
                    }
                    i+=2;
                    
                }
                else if(arguments[i].equals("delete-stream"))
                {
                    try {
                        ConfigWriter.singleton().deleteStream(arguments[i+1],configuration.getString("nginxConfigurationPath"));
                        Runtime.getRuntime().exec(configuration.getString("nginxBinaryPath")+" -s reload");
                    } catch (Exception e) {
                    }
                }
                
                
            }
                
        }
        if(!confArguments.isEmpty())
            Utils.createConfigFile(confArguments);
        
        
    }
    
}
