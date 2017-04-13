/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodestream;


import java.io.File;
import java.util.Scanner;
import nodestream.utils.ArgumentReader;
/**
 *
 * @author Yorbenys
 */
public class NodeStream implements Runnable{

    /**
     * @author Yorbenys
     * Entry ponint for the nodeStream app 
     * @param args the command line arguments
     * create-stream $streamName pull-from $pullStream
     * delete-stream  $streamName
     * nginx-config-file $confFile nginx-exec-file $execFile interval $interval
     */
    public static void main(String[] args) {
        Thread pipeThread = new Thread(new NodeStream());
        pipeThread.start();
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
    /**
     * @author Yorbenys
     * Read commands from a named pipe
     */
    @Override
    public void run() {
        try {
            String pipePath="/tmp/nodepipe";
            Runtime.getRuntime().exec("rm "+pipePath).waitFor();
            Runtime.getRuntime().exec("mkfifo "+pipePath).waitFor();
            while (true) {
                Scanner in = new Scanner(new File(pipePath));
                while(in.hasNextLine())
                {
                    ArgumentReader.readArguments(in.nextLine().split(" "));
                }
                in.close();
                
            }
        } catch (Exception e) {
        }
    }
    
}
