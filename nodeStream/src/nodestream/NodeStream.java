/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodestream;


import java.util.Scanner;
import nodestream.utils.ConfigWriter;
import nodestream.utils.Utils;
import org.json.JSONObject;
/**
 *
 * @author yorbe
 */
public class NodeStream {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleccione una opción:");
        System.out.println("1-Create Stream.");
        System.out.println("2-Delete Stram.");
        String auxIn = "";
        while(!auxIn.equals("Q"))
        {
           JSONObject configuration = Utils.getConfiguration();

           auxIn = scanner.nextLine();
           boolean validConfig=false;
           try{
               if(!configuration.getString("nginxConfigurationPath").equals("")&&!configuration.getString("nginxBinaryPath").equals(""))
                    validConfig=true;
           }
           catch(Exception e){}
           if(validConfig)
            try {
                switch(auxIn)
                {
                    case "1":
                    {
                        
                        ConfigWriter.singleton().createStream(configuration.getString("nginxConfigurationPath"));
                        Runtime.getRuntime().exec(configuration.getString("nginxBinaryPath")+" -s reload");
                        break;
                    }
                    case "2":
                    {
                        ConfigWriter.singleton().deleteStream("live5",configuration.getString("nginxConfigurationPath"));
                        Runtime.getRuntime().exec(configuration.getString("nginxBinaryPath")+" -s reload");
                        break;
                    }
                    default:break;
                }
                
            } catch (Exception e) {
            }
           
        }
    }
    
}
