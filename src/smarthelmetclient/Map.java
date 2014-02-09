/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthelmetclient;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author Sasi Praveen
 */
public class Map {
    
    private Desktop desktop;
    public void show(String URL) throws URISyntaxException, IOException{
        desktop = Desktop.getDesktop();
        URI uri = new URI(URL);
        desktop.browse(uri);
        
    } 
    
}
