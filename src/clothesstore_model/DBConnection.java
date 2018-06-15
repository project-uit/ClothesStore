/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 *
 * @author quochung
 */
public class DBConnection {

    private String DBName, User, Password, Port;

    public DBConnection() {
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
    }

    public Connection getConnecttion() {
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
                    "Kết nối thất bại: " + ex.getMessage(), NotificationType.ERROR);
            tray.showAndDismiss(Duration.seconds(2));
            System.out.println("Error " + ex.getMessage());
        }
        return null;
    }

    public void Create() {
        String dbName = DBName;
        String dbUser = User;
        String dbPass = Password;
        try {
            Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost/?user=" + dbUser + "&password=" + dbPass + "");
            Statement smt = Conn.createStatement();
            smt.executeUpdate("drop database if exists " + dbName + "");
            smt.executeUpdate("CREATE DATABASE " + dbName + "");
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Backup(File selectedDirectory, String PathMysqldump) {
        try {
            String dbName = DBName;
            String dbUser = User;
            String dbPass = Password;

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String time = dateTimeFormatter.format(LocalDate.now()).replace("/", "-");

            String savePath = selectedDirectory + "/" + time + "_clothesshop.sql";

            String executeCmd = "";
            executeCmd = PathMysqldump + "/mysqldump -u" + dbUser + " -p" + dbPass + " " + dbName + " -r " + savePath;

            /*NOTE: Executing the command here*/
            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();

            /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
            if (processComplete == 0) {
                TrayNotification tray = new TrayNotification("Thông báo",
                        "Tạo backup thành công\n->" + selectedDirectory, NotificationType.SUCCESS);
                tray.showAndDismiss(Duration.seconds(2));
            } else {
                System.out.println("Backup Failure");
            }

        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Restore(File selectedFile, String PathMysql) {
        //this.Create();
        ProcessBuilder builder = new ProcessBuilder();
        try {
            if (System.getProperty("os.name").startsWith("Windows")) {
                String quotedPath = "\"" + PathMysql + "/mysql" + "\"";
                builder = new ProcessBuilder("cmd.exe", "/c", quotedPath + " -u"+User+" -p"+Password+" -h localhost "+DBName+" < " + selectedFile.getAbsolutePath());
            } else if (System.getProperty("os.name").startsWith("Mac")) {
                builder = new ProcessBuilder("sh", "-c", PathMysql + "/mysql -u"+User+" -p"+Password+" -h localhost "+DBName+" < " + selectedFile.getAbsolutePath());
            }

            builder.redirectErrorStream(true);
            Process pr = builder.start();

            // Get mysql process's standard output/error.
            InputStream is = pr.getInputStream();

            // Now read from it and write it to standard output.
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String log = "";
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                log += line +"\n";
                line = reader.readLine();
            }
            Notifications.create()
              .title("Log")
              .text(log)
              .showInformation();
        } catch (IOException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Restore111111(File selectedFile) {
        try {
            this.Create();
            /*NOTE: Creating Database Constraints*/
            String dbName = DBName;
            String dbUser = User;
            String dbPass = Password;

            String savePath = selectedFile.getAbsolutePath();

            /*NOTE: Used to create a cmd command*/
            String executeCmd = "";
            if (System.getProperty("os.name").startsWith("Windows")) {
                executeCmd = "mysql -u" + dbUser + " -p" + dbPass + " -h localhost " + dbName + " < " + savePath;
            } else if (System.getProperty("os.name").startsWith("Mac")) {
                executeCmd = "/Applications/MySQLWorkbench.app/Contents/MacOS/mysql -u" + dbUser + " -p" + dbPass + " -h localhost " + dbName + " < " + savePath;
            }

            System.out.println(executeCmd);

            /*NOTE: Executing the command here*/
            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();
            /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
            if (processComplete == 0) {
                TrayNotification tray = new TrayNotification("Thông báo",
                        "Restore dữ liệu thành công", NotificationType.SUCCESS);
                tray.showAndDismiss(Duration.seconds(2));
            } else {
                System.out.println("Restore Failure");
            }

        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(DBConnection.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}
