/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodestream.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import nodestream.exceptions.BadConfigurationException;
/**
 *
 * @author Yorbenys
 * Manipulate the Ngins configuration file 
 */
public class ConfigWriter {
    private static ConfigWriter instance=null;
    private Logger logger = null;    
    private ConfigWriter()
    {
        logger = Logger.getLogger("NodeStream");
        try {  

        // This block configure the logger with handler and formatter
        FileHandler fh = new FileHandler("NodeStream.log");  
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();  
        fh.setFormatter(formatter);

    } catch (SecurityException | IOException e) {  
    }  
    }
    
    //Singleton
    public static synchronized ConfigWriter singleton()
    {
        if(instance==null)
            instance= new ConfigWriter();
        return instance;
    }
    /**
    *
    * @author Yorbenys
    * Write information into Nginx configuration file.
    * @param sb the string to write in the Nginx configuration file
    * @param pathNginxConfigFile the path to Nginx configuration file
    */
    private void writeFile(StringBuilder sb,String pathNginxConfigFile) throws IOException
    {
            FileWriter fWriter= new FileWriter(pathNginxConfigFile);
            fWriter.append(sb);
            fWriter.flush();
            fWriter.close();
            logger.info("Writing into Nginx configuration file"); 
    }
    
    /**
    *
    * @author Yorbenys
    * Get the position where will be inserted the new String
    * @param sb the string 
    */
    private int positionToWrite(StringBuilder sb) 
    {
        int offset=sb.indexOf("application ");
        if(offset==-1)
        {
            offset=sb.indexOf("rtmp");
            if(offset>-1)
            {
                StringBuilder rtmpPart= new StringBuilder();
                boolean buildStream=true;
                while(offset<sb.length())
                {
                   rtmpPart.append(sb.charAt(offset));
                   if(rtmpPart.indexOf("}")>-1 && rtmpPart.indexOf("server")==-1)
                       return -1;
                   else if(rtmpPart.indexOf("}")>-1 && rtmpPart.indexOf("server")>-1)
                       return offset;
                   else if(rtmpPart.indexOf("}")>-1)
                       return -1;
                   offset++;
                }
                return -1;
            }
            else
                return offset;
        }
        return offset;
    }
    /**
    *
    * @author Yorbenys
    * Write into Nginx configuration file a new stream.
    * @param pathNginxConfigFile the path to Nginx configuration file
    * @param streamName stream id
    * @param pullFrom the strem source 
    */
    public void createStream(String pathNginxConfigFile,String streamName, String pullFrom) throws FileNotFoundException, IOException, BadConfigurationException
    {
        synchronized(ConfigWriter.class)
        {
            //It Represents the new stream
            String newStreamString= "\n\tapplication "+streamName+"{\n" +
                                    "                        live on;\n" +
                                    "                        exec_options on;\n" +
                                    "                        exec_pull ffmpeg -i "+ pullFrom + " -threads 1 -c:v libx264 -profile:v baseline -b:v 350K -s 640x360 -f flv -c:a aac -ac 1 -strict -2 -b:a 56k rtmp://localhost/$app/$name name=live;\n" +
                                    "                }\n	";
            BufferedReader br = new BufferedReader(new FileReader(pathNginxConfigFile));
            StringBuilder sb= new StringBuilder();
            String line = br.readLine();
            while(line != null)
            {
                sb.append(line);
                sb.append(System.lineSeparator());
                line= br.readLine();
            }
            int offset=positionToWrite(sb);
            if(offset==-1)
                throw  new BadConfigurationException();
            sb.insert(offset, newStreamString);
            br.close();
            writeFile(sb,pathNginxConfigFile);
            
            
        }
    }
    /**
    *
    * @author Yorbenys
    * Delete from Nginx configuration file a stream
    * @param id the stream id
    * @param pathNginxConfigFile the path to Nginx configuration file 
    */
    public void deleteStream(String id,String pathNginxConfigFile) throws FileNotFoundException, IOException
    {
        synchronized(ConfigWriter.class)
        {
            //Read the file config and delete new stream thread
            BufferedReader br = new BufferedReader(new FileReader(pathNginxConfigFile));
            boolean appendLine=true;
            String auxId=id;
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                if(line.contains(id))
                {
                    if(id.equals("}"))
                    {
                        id=auxId;
                        line = br.readLine();//Jump to next line
                        appendLine=true;
                    }
                    else
                    {
                        id="}";
                        appendLine=false;
                    }
                    
                }
                if(appendLine)
                {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                }
                line = br.readLine();
               }
            br.close();
            writeFile(sb,pathNginxConfigFile);
            
            } finally {
                        br.close();
                      }
        }
    }
    
}
