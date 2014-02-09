
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthelmetclient;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Sasi Praveen
 */
public class Utilfunctions {

    public static void setLocation(JFrame jf) {
        toolkit = Toolkit.getDefaultToolkit();
        dim = toolkit.getScreenSize();

        width = dim.width;
        height = dim.height;

        Dimension dm = jf.getContentPane().getSize();

        int x = (width - dm.width) / 2;
        int y = (height - dm.height) / 2;

        jf.setLocation(x, y);

    }

    public static void setIconImage(JFrame jf) {
        File directory = new File(".");
        try {
            String path = directory.getCanonicalPath();
            String s = File.separator;
            jf.setIconImage(Toolkit.getDefaultToolkit().getImage(path + s + "src" + s + "images" + s + "logo.gif"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unexpected Error. Exiting application");
        }
    }
    
    public static void setClosePrompt(JFrame jf) {
        jf.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int opt = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit SchedulePro?", "SchedulePro - Exit", JOptionPane.YES_NO_OPTION);
                if (opt == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }
    
   
    private static Toolkit toolkit;
    private static Dimension dim;
    private static int width;
    private static int height;
}
