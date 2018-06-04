/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import static clothesstore_controller.FXML_DangNhapController.stageCaiDat;
import clothesstore_model.DBConnection;
import clothesstore_model.EncodeDecode;
import clothesstore_model.ScriptRunner;
import static clothesstore_view.ClothesStore.stageDangNhap;
import com.jfoenix.controls.JFXButton;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author dieunguyen
 */
public class FXML_ConnectDBController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField txtDBName, txtUser, txtPassword, txtPort;

    @FXML
    private JFXButton btnConnect;

    @FXML
    private Hyperlink lbDefault, lbSetPath;
    @FXML
    private Label lbPath;

    private String DBName, User, Password, Port;

    @FXML
    void Handler_btnConnect(ActionEvent event) {
        ButtonType yes = new ButtonType("Kết nối", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Huỷ", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Kết nối lại dữ liệu sẽ làm mất hết dữ liệu cũ, bạn có chắc chắn muốn tiếp tục ?",
                yes,
                cancel);

        alert.setTitle("Nhắc nhở");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == yes) {
            DBName = txtDBName.getText();
            User = txtUser.getText();
            Password = txtPassword.getText();
            Port = txtPort.getText();

            try {
                FileOutputStream outputStream = new FileOutputStream("DBConnection.txt");
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

                bufferedWriter.write(new EncodeDecode().encodeString(DBName));
                bufferedWriter.newLine();
                bufferedWriter.write(new EncodeDecode().encodeString(User));
                bufferedWriter.newLine();
                bufferedWriter.write(new EncodeDecode().encodeString(Password));
                bufferedWriter.newLine();
                bufferedWriter.write(new EncodeDecode().encodeString(Port));

                bufferedWriter.close();

                new DBConnection().Create();
                runScriptSQL();

                stageCaiDat.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void Handler_btnBackup() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stageDangNhap);

        if (selectedDirectory != null) {
            DBConnection db = new DBConnection();
            if (db.getConnecttion() != null) {
                db.Backup(selectedDirectory, lbPath.getText());
            }
        }
    }

    @FXML
    private void Handler_btnRestore() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            new DBConnection().Restore(selectedFile, lbPath.getText());
        } else {
            System.out.println("File selection cancelled.");
        }
    }

    @FXML
    void Handler_lbDefault(ActionEvent event) {
        txtDBName.setText("clothesshop");
        txtUser.setText("root");
        txtPassword.setText("");
        txtPort.setText("3306");
    }

    @FXML
    void Handler_lbSetPath(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            lbPath.setText(selectedFile.getAbsolutePath());
        } else {
            System.out.println("File selection cancelled.");
        }
    }

    private void runScriptSQL() {
        Connection con = new DBConnection().getConnecttion();
        ScriptRunner runner = new ScriptRunner(con, false, false);
        try {
            runner.runScript(new BufferedReader(new FileReader("clothesshop.sql")));
            TrayNotification tray = new TrayNotification("Thông báo",
                    "Cài đặt kết nối csdl thành công", NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.seconds(2));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXML_ConnectDBController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | SQLException ex) {
            TrayNotification tray = new TrayNotification("Thông báo",
                    "Kết nối thất bại", NotificationType.ERROR);
            tray.showAndDismiss(Duration.seconds(2));
            Logger.getLogger(FXML_ConnectDBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if (System.getProperty("os.name").startsWith("Windows")) {
            String path = "";
            lbPath.setText(path);
        } else if (System.getProperty("os.name").startsWith("Mac")) {
            String path = "/Applications/MySQLWorkbench.app/Contents/MacOS/";
            lbPath.setText(path);
        }
    }

}
