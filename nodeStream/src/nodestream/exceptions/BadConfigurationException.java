/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodestream.exceptions;

/**
 *
 * @author Yorbenys
 * This Exception is throwed when Nginx configuration File has a bag structure.
 */
public class BadConfigurationException extends Exception {

    @Override
    public String getMessage() {
        return "Bad configuration file"; 
    }
    
}
