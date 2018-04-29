/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import static clothesstore_controller.FXML_ClothesStoreController.rootP;
import clothesstore_model.TaiKhoan;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.FlowPane;

/**
 * FXML Controller class
 *
 * @author user
 */
public class FXML_DangKyController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXTextField txtUser;
 
    @FXML
    private JFXPasswordField txtPass;
    
    @FXML
    private Hyperlink linkback;
    
    @FXML
    private JFXButton btnDangKy;
    
    @FXML
    private void BackDangNhap(ActionEvent event) throws IOException{
       FlowPane DangNhap = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_DangNhap.fxml"));   
       rootP.setLeftAnchor(DangNhap, 0.0);
       rootP.setRightAnchor(DangNhap, 0.0);
       rootP.setTopAnchor(DangNhap, 0.0);
       rootP.setBottomAnchor(DangNhap, 0.0);
       FXML_ClothesStoreController.rootP.getChildren().setAll(DangNhap); 
    }
    
    @FXML
    private void DangKy(ActionEvent event) throws IOException{
       String user = txtUser.getText();
       String password = txtPass.getText().toString();
        
       TaiKhoan tk = new TaiKhoan(user, password, 1, 123); // 0->admin, 1->user
       if(tk.ThemTaiKhoan()){
           System.out.println("Đăng ký thành công");
       }
       else
            System.out.println("Đăng ký thất bại");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
