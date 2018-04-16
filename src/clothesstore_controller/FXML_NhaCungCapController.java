/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import clothesstore_model.NhaCungCap;
import static clothesstore_controller.FXML_PhieuNhapController.stageQuanLyNCC;
import javafx.scene.control.Alert;


/**
 * FXML Controller class
 *
 * @author 15520
 */
public class FXML_NhaCungCapController implements Initializable {

    @FXML
    private TableView tblQLyNhaCungCap;
    @FXML
    private TableColumn manhacungcap;
    @FXML
    private TableColumn tencungcap;
    @FXML
    private TableColumn diachi;
    @FXML
    private TableColumn email;
    @FXML
    private TableColumn ghichu;
    private JFXButton btnXoa;
    private JFXButton btnSua;
    @FXML
    private JFXButton btnthem;
    private NhaCungCap ncctemp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InitTableView();
    }    
    public void InitTableView(){
        NhaCungCap ncc = new NhaCungCap();
        ObservableList <NhaCungCap> list = ncc.getTableNhaCungCap();
        manhacungcap.setCellValueFactory(new PropertyValueFactory<NhaCungCap, String>("manhacungcap"));   
        tencungcap.setCellValueFactory(new PropertyValueFactory<NhaCungCap, String>("tencungcap"));
        diachi.setCellValueFactory(new PropertyValueFactory<NhaCungCap, String>("diachi"));
        email.setCellValueFactory(new PropertyValueFactory<NhaCungCap, String>("email"));
        ghichu.setCellValueFactory(new PropertyValueFactory<NhaCungCap, String>("ghichu"));
            
        tblQLyNhaCungCap.setItems(list);
        
        tblQLyNhaCungCap.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                if (tblQLyNhaCungCap.getSelectionModel().getSelectedItem() != null) {
                    ncctemp = (NhaCungCap) tblQLyNhaCungCap.getSelectionModel().getSelectedItem();           
                } 
            }
        });
    }

    
    

    @FXML
    private void Handler_btnThem(ActionEvent event) {     
        Dialog<NhaCungCap> dialog = new Dialog<>();
        dialog.setTitle("Thêm nhà cung cấp");

        // Set the button types.
        ButtonType btnSave = new ButtonType("Lưu", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancel = new ButtonType("Huỷ", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(btnSave, btnCancel);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 70, 10, 70));

        TextField txtmanhacungcap = new TextField();
        txtmanhacungcap.setPromptText("Mã nhà cung cấp");
        
        TextField txttennhacungcap = new TextField();
        txttennhacungcap.setPromptText("Tên nhà cung cấp");
        
        TextField txtdiachi = new TextField();
        txtdiachi.setPromptText("Địa Chỉ");
        
        TextField txtemail = new TextField();
        txtemail.setPromptText("Email");
        
        TextField txtghichu = new TextField();
        txtghichu.setPromptText("Ghi chú");

        gridPane.add(new Label("Mã nhà cung cấp"), 0, 0);
        gridPane.add(txtmanhacungcap, 0, 1);
        gridPane.add(new Label("Tên nhà cung cấp"), 0, 2);
        gridPane.add(txttennhacungcap, 0, 3);
        gridPane.add(new Label("Địa Chỉ"), 0, 4);
        gridPane.add(txtdiachi, 0, 5);
        gridPane.add(new Label("Email"), 0, 6);
        gridPane.add(txtemail, 0, 7);
        gridPane.add(new Label("Ghi chú"), 0, 8);
        gridPane.add(txtghichu, 0, 9);

        dialog.getDialogPane().setContent(gridPane);

        // Request focus on the username field by default.
        Platform.runLater(() -> txttennhacungcap.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnSave) {
                return new NhaCungCap(Integer.parseInt(txtmanhacungcap.getText().toString()), txttennhacungcap.getText(),txtdiachi.getText(),txtemail.getText(),txtghichu.getText());
            }
            return null;
        });

        Optional<NhaCungCap> result = dialog.showAndWait();

        result.ifPresent(ncc -> {
            if(ncc.ThemNhaCungCap()){
                System.out.println("Them thanh cong");
                InitTableView();
            }
            else{    
                System.out.println("Them that bai");
            }
        });
    }  

    @FXML
    private void handler_sua(ActionEvent event) {
        Dialog<NhaCungCap> dialog = new Dialog<>();
        dialog.setTitle("Sửa nhà cung cấp");

        // Set the button types.
        ButtonType btnSave = new ButtonType("Lưu", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnCancel = new ButtonType("Huỷ", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(btnSave, btnCancel);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 70, 10, 70));

        TextField txtmanhacungcap = new TextField();
        txtmanhacungcap.setPromptText("Mã nhà cung cấp");
        txtmanhacungcap.setText(Integer.toString(ncctemp.getManhacungcap()));
        
        TextField txttennhacungcap = new TextField();
        txttennhacungcap.setPromptText("Tên nhà cung cấp");
        txttennhacungcap.setText(ncctemp.getTencungcap());
        
        TextField txtdiachi = new TextField();
        txtdiachi.setPromptText("Địa Chỉ");
        txtdiachi.setText(ncctemp.getDiachi());
        
        TextField txtemail = new TextField();
        txtemail.setPromptText("Email");
        txtemail.setText(ncctemp.getEmail());
        
        TextField txtghichu = new TextField();
        txtghichu.setPromptText("Ghi chú");
        txtghichu.setText(ncctemp.getGhichu());

        gridPane.add(new Label("Mã nhà cung cấp"), 0, 0);
        gridPane.add(txtmanhacungcap, 0, 1);
        gridPane.add(new Label("Tên nhà cung cấp"), 0, 2);
        gridPane.add(txttennhacungcap, 0, 3);
        gridPane.add(new Label("Địa Chỉ"), 0, 4);
        gridPane.add(txtdiachi, 0, 5);
        gridPane.add(new Label("Email"), 0, 6);
        gridPane.add(txtemail, 0, 7);
        gridPane.add(new Label("Ghi chú"), 0, 8);
        gridPane.add(txtghichu, 0, 9);

        dialog.getDialogPane().setContent(gridPane);

        // Request focus on the username field by default.
        Platform.runLater(() -> txttennhacungcap.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnSave) {
                return new NhaCungCap(Integer.parseInt(txtmanhacungcap.getText().toString()), txttennhacungcap.getText(),txtdiachi.getText(),txtemail.getText(),txtghichu.getText());
            }
            return null;
        });

        Optional<NhaCungCap> result = dialog.showAndWait();

        result.ifPresent(ncc -> {
            if(ncc.CapNhatNhaCungCap()){
                System.out.println("Them thanh cong");
                InitTableView();
            }
            else{    
                System.out.println("Them that bai");
            }
        });
        
        
    }

    @FXML
    private void handler_xoa(ActionEvent event) {
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
            NhaCungCap ncc = new NhaCungCap();
            
            if (ncc.XoaNhaCungCap(ncctemp.getTencungcap())){
                System.out.println("Xoa thanh cong");
                InitTableView();
            }     
            else    
                System.out.println("Xoa that bai");
        }            
    }
    
    
    
}
