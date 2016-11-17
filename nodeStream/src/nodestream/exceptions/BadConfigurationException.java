/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodestream.exceptions;

/**
 *
 * @author yorbe
 * Esta excepción es lanzada cuando el archivo de configuración de Nginx posee una estructura inválida.
 */
public class BadConfigurationException extends Exception {

    @Override
    public String getMessage() {
        return "Bad configuration file"; 
    }
    
}
