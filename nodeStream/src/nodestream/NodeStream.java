/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodestream;

import java.io.Console;
import java.util.Scanner;
import nodestream.utils.ConfigWriter;
import nodestream.utils.Informations;
import nodestream.utils.SystemInfo;

/**
 *
 * @author yorbe
 */
public class NodeStream {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String fM=SystemInfo.freeMemory();
        String tM=SystemInfo.totalMemory();
        String uM=SystemInfo.memoryUsage();
        String cpuU=SystemInfo.cpuUsage();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleccione una opción:");
        System.out.println("1-Create Stream.");
        System.out.println("2-Delete Stram.");
        String auxIn = "";
        while(!auxIn.equals("Q"))
        {
           auxIn = scanner.nextLine();
            try {
                switch(auxIn)
                {
                    case "1":
                    {
                        ConfigWriter.singleton().createStream();
                        Runtime.getRuntime().exec(Informations.pathNginxBinary+" -s reload");
                        break;
                    }
                    case "2":
                    {
                        ConfigWriter.singleton().deleteStream("live5");
                        Runtime.getRuntime().exec(Informations.pathNginxBinary+" -s reload");
                        break;
                    }
                    default:break;
                }
                
            } catch (Exception e) {
            }
           
        }
    }
    
}
