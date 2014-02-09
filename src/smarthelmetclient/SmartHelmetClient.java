/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smarthelmetclient;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JOptionPane;

/**
 *
 * @author Sasi Praveen
 */
public class SmartHelmetClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String serverAddress = JOptionPane.showInputDialog(
                "Enter IP Address of Server:");
        //adding system tray
        if (SystemTray.isSupported()) {
            tray = SystemTray.getSystemTray();
            Toolkit kit = Toolkit.getDefaultToolkit();
            Image image = kit.getImage("logo_small.gif");
            //Image image = kit.getImage(path + s + "src" + s + "images" + s + "logo_small.gif");
            PopupMenu popup = new PopupMenu();
            MenuItem ExitItem = new MenuItem("Exit");
            ExitItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int opt = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit 'Smart Helmet'? \nMany people could die you know!!!", "SmartHelmet - Exit", JOptionPane.YES_NO_OPTION);
                    if (opt == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            });

            popup.add(ExitItem);
            trayIcon = new TrayIcon(image, "Smart Helmet", popup);
            try {
                tray.add(trayIcon);
            } catch (AWTException ex) {
                System.err.println(ex);
            }
        }
        while (true) {
            try {
                File file = new File("info.dat");
                if (file.exists()) {
                    BufferedReader br = new BufferedReader(new FileReader("info.dat"));
                    uid = br.readLine();
                    br.close();
                }
                if (uid == null || uid.equals("null")) {
                    RegistrationForm reg = new RegistrationForm();
                    reg.setServerAddress(serverAddress);
                    Utilfunctions.setIconImage(reg);
                    Utilfunctions.setLocation(reg);
                    reg.setVisible(true);
                    while ((uid = reg.getUid()) == null);
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
                    out.println("UID~" + uid);//let server know your wake
                    send.close();
                    Socket recieve = new Socket(serverAddress, 6051);
                    while (true) {
                        BufferedReader input = new BufferedReader(new InputStreamReader(recieve.getInputStream()));
                        String msg = null;
                        while (msg == null) { // wait for message from server
                            msg = input.readLine();
                        }
                        playSound("siren.wav");

                        String URL = "http://maps.google.com/maps?z=16&t=m&q=loc:" + msg;
                        Map map = new Map();
                        map.show(URL);
                        msg = "";
                    }
                }

            } catch (Exception ex) {
                Logger.getLogger(SmartHelmetClient.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    private static String uid;
    private static SystemTray tray;
    private static TrayIcon trayIcon;

    private static synchronized void playSound(final String url) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    File f = new File(url);
                    AudioInputStream audio = AudioSystem.getAudioInputStream(f);
                    AudioFormat format;
                    format = audio.getFormat();
                    SourceDataLine auline;
                    DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                    auline = (SourceDataLine) AudioSystem.getLine(info);
                    auline.open(format);
                    auline.start();
                    int nBytesRead = 0;
                    byte[] abData = new byte[524288];
                    while (nBytesRead != -1) {
                        nBytesRead = audio.read(abData, 0, abData.length);
                        if (nBytesRead >= 0) {
                            auline.write(abData, 0, nBytesRead);
                        }
                    }
                } catch (Exception E) {
                    System.out.println(E.getMessage());

                }
            }
        }).start();
    }
}
