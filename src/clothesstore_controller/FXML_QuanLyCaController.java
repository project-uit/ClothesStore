/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import clothesstore_model.TaiKhoan;
import clothesstore_model.ThoiGianLamViec;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author dieunguyen
 */
public class FXML_QuanLyCaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML 
    private JFXButton btnSua, btnXoa;
    
    @FXML 
    private TableView tblQuanLyCa;
    
    @FXML 
    private TableColumn tenca, giolam;
    
    private ThoiGianLamViec tglvtemp;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InitTableView();
    }    
        
    public void InitTableView(){
        ThoiGianLamViec tglv = new ThoiGianLamViec();
        ObservableList <ThoiGianLamViec> list = tglv.getTableThoiGianLamViec();
        tenca.setCellValueFactory(new PropertyValueFactory<TaiKhoan, String>("tenca"));     
        giolam.setCellValueFactory(new PropertyValueFactory<TaiKhoan, String>("giolam"));    
        tblQuanLyCa.setItems(list);
        
        tblQuanLyCa.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                if (tblQuanLyCa.getSelectionModel().getSelectedItem() != null) {
                    btnSua.setDisable(false);
                    btnXoa.setDisable(false);
                    tglvtemp = (ThoiGianLamViec) tblQuanLyCa.getSelectionModel().getSelectedItem();           
                }
                else{
                    btnSua.setDisable(true);
                    btnXoa.setDisable(true);
                }   
            }
        });
    }

    @FXML
    private void Handler_btnSua(ActionEvent event) {     
        Dialog<ThoiGianLamViec> dialog = new Dialog<>();
        dialog.setTitle("Sửa ca");

        // Set the button types.
        ButtonType btnSave = new ButtonType("Lưu", ButtonData.OK_DONE);
        ButtonType btnCancel = new ButtonType("Huỷ", ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(btnSave, btnCancel);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 70, 10, 70));

        TextField txttenca = new TextField();
        txttenca.setPromptText("Tên ca");
        txttenca.setText(tglvtemp.getTenca());
        
        TextField txtgiolam = new TextField();
        txtgiolam.setPromptText("Giờ làm việc");
        txtgiolam.setText(tglvtemp.getGiolam());

        gridPane.add(new Label("Tên ca"), 0, 0);
        gridPane.add(txttenca, 0, 1);
        gridPane.add(new Label("Giờ làm việc"), 0, 2);
        gridPane.add(txtgiolam, 0, 3);

        dialog.getDialogPane().setContent(gridPane);

        // Request focus on the username field by default.
        Platform.runLater(() -> txttenca.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnSave) {
                return new ThoiGianLamViec(txttenca.getText(), txtgiolam.getText());
            }
            return null;
        });

        Optional<ThoiGianLamViec> result = dialog.showAndWait();

        result.ifPresent(tglv -> {
            if(tglv.CapNhatCa(tglvtemp.getTenca())){
                System.out.println("Sua ca thanh cong");
                InitTableView();
            }
            else 
                System.out.println("Sua ca that bai");
        });
    }
    
   @FXML
    private void Handler_btnThem(ActionEvent event) {     
        Dialog<ThoiGianLamViec> dialog = new Dialog<>();
        dialog.setTitle("Thêm ca");

        // Set the button types.
        ButtonType btnSave = new ButtonType("Lưu", ButtonData.OK_DONE);
        ButtonType btnCancel = new ButtonType("Huỷ", ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(btnSave, btnCancel);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 70, 10, 70));

        TextField txttenca = new TextField();
        txttenca.setPromptText("Tên ca");
        
        TextField txtgiolam = new TextField();
        txtgiolam.setPromptText("Giờ làm việc");

        gridPane.add(new Label("Tên ca"), 0, 0);
        gridPane.add(txttenca, 0, 1);
        gridPane.add(new Label("Giờ làm việc"), 0, 2);
        gridPane.add(txtgiolam, 0, 3);

        dialog.getDialogPane().setContent(gridPane);

        // Request focus on the username field by default.
        Platform.runLater(() -> txttenca.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnSave) {
                return new ThoiGianLamViec(txttenca.getText(), txtgiolam.getText());
            }
            return null;
        });

        Optional<ThoiGianLamViec> result = dialog.showAndWait();

        result.ifPresent(tglv -> {
            if(tglv.ThemCa()){
                System.out.println("Them ca thanh cong");
                InitTableView();
            }
            else{    
                System.out.println("Them ca that bai");
            }
        });
    }  
    
    @FXML
    private void Handler_btnXoa(ActionEvent event) {
        ButtonType yes = new ButtonType("Xoá", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Huỷ", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Bạn có chắc chắn muốn xoá",
                yes,
                cancel);

        alert.setTitle("Nhắc nhở");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == yes) {
            ThoiGianLamViec tglv = new ThoiGianLamViec();
            
            if (tglv.XoaCa(tglvtemp.getTenca())){
                System.out.println("Xoa thanh cong");
                InitTableView();
            }     
            else    
                System.out.println("Xoa that bai");
        }            
    }
}