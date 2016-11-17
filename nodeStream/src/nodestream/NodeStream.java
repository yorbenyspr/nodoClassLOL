/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodestream;


import java.util.Scanner;
import nodestream.utils.ArgumentReader;
/**
 *
 * @author yorbe
 */
public class NodeStream {

    /**
     * @param args the command line arguments
     * create-stream $streamName pull-from $pullStream
     * delete-stream  $streamName
     * nginx-config-file $confFile nginx-exec-file $execFile interval $interval
     */
    public static void main(String[] args) {
        ArgumentReader.readArguments(args);
        Scanner scanner = new Scanner(System.in);
        String auxIn = "";
        while(!auxIn.equals("Q"))
        {
           
           auxIn = scanner.nextLine();
           String[] commands= auxIn.split(" ");
           if(commands.length<2)
               continue;
           auxIn=commands[0];
           ArgumentReader.readArguments(commands);
           
        }
    }
    
}
