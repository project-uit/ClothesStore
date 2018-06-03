/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 *
 * @author quochung
 */
public class DBConnection {

    private String DBName, User, Password, Port;

    public DBConnection() {
    }

    public Connection getConnecttion() {
        
        try {
            FileReader reader = new FileReader("DBConnection.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            List<String> rs = new ArrayList();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                rs.add(line);
            }
            reader.close();

            DBName = new EncodeDecode().decodeString(rs.get(0));
            User = new EncodeDecode().decodeString(rs.get(1));
            Password = new EncodeDecode().decodeString(rs.get(2));
            Port = new EncodeDecode().decodeString(rs.get(3));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String url = "jdbc:mysql://localhost:" + Port + "/" + DBName;
            String user = User;
            String password = Password;
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            return con;
        } catch (ClassNotFoundException e) {
            System.out.println("Loading driver is failed");
        } catch (SQLException ex) {
            TrayNotification tray = new TrayNotification("Thông báo",
                    "Kết nối thất bại", NotificationType.ERROR);
            tray.showAndDismiss(Duration.seconds(2));
            System.out.println("Error " + ex.getMessage());
        }
        return null;
    }
}
