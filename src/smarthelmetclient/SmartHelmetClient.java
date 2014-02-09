/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthelmetclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Sasi Praveen
 */
public class SmartHelmetClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        String serverAddress = JOptionPane.showInputDialog(
                "Enter IP Address of Server:");
        while (true) {
            try{
            File file = new File("info.dat");
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader("info.dat"));
                uid = br.readLine();
                br.close();
            }
            if (uid == null || uid.equals("null")) {
                RegistrationForm reg = new RegistrationForm();
                reg.setServerAddress(serverAddress);
                Utilfunctions.setLocation(reg);
                reg.setVisible(true);
                while((uid = reg.getUid())== null);
                //Write to file 
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(uid);
                bw.close();
            } else {
                Socket send = new Socket(serverAddress, 6050);
                PrintWriter out = new PrintWriter(send.getOutputStream(), true);
                out.println("UID~"+uid);//let server know your wake
                send.close();
                Socket recieve = new Socket(serverAddress, 6051);
                while (true) {
                    BufferedReader input = new BufferedReader(new InputStreamReader(recieve.getInputStream()));
                    String msg = null;
                    while (msg == null) { // wait for message from server
                        msg = input.readLine();
                    }
                    String URL = "http://maps.google.com/maps?z=16&t=m&q=loc:" + msg;
                    Map map = new Map();
                    map.show(URL);
                    msg = "";
                }
            }

            }catch(Exception ex){
                Logger.getLogger(SmartHelmetClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    private static String uid;
    
  
}
