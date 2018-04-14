/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import clothesstore_model.NhaCungCap;
import clothesstore_model.PhieuNhap;

/**
 * FXML Controller class
 *
 * @author 15520
 */
public class FXML_PhieuNhapController implements Initializable {

    @FXML
    private AnchorPane QLPNpane;
    @FXML
    private AnchorPane paneINFO;
    @FXML
    private JFXTextField txtfimaphieunhap;
    @FXML
    private JFXTextField txtfinguoinhap;
    @FXML
    private JFXDatePicker datengaynhap;
    @FXML
    private JFXComboBox cbnhacungcap;
    @FXML
    private JFXButton btnnhacungcap;
    @FXML
    private JFXButton btnthemphieu;
    @FXML
    private TableColumn clmaphieunhap;
    @FXML
    private TableColumn cltennhanvien;
    @FXML
    private TableColumn clnhacungcap;
    @FXML
    private TableColumn clngaynhap;
    @FXML
    private TableColumn cltongtien;
    
    public static Stage stageQuanLyNCC;
    public static Stage stageQuanLyCTPN;
    public static int mapn;
    @FXML
    private TableView<PhieuNhap> tableviewphieunhap;
    @FXML
    private JFXButton btnluuphieunhap;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InitTableViewPhieuNhap();
        InitCmb();
    }    

    @FXML
    private void Handler_btnnhacungcap(ActionEvent event) {
        try { 
            Parent root = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_NhaCungCap.fxml"));            
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));  
            stage.show();
            stageQuanLyNCC = stage;
            
        } catch(Exception e) {
           e.printStackTrace();
          }
    }
    public int getmaphieunhap;
    private int manhacungcap;
    private void InitCmb(){
        NhaCungCap ncc = new NhaCungCap();
        ObservableList <NhaCungCap> list = ncc.getTableNhaCungCap();
        cbnhacungcap.setItems(list);      
        cbnhacungcap.setConverter(new StringConverter<NhaCungCap>() {
            @Override
            public String toString(NhaCungCap object) {
                return object.getTencungcap();
            }
            @Override
            public NhaCungCap fromString(String string) {
                return null;
            }
        });  
        cbnhacungcap.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<NhaCungCap>() {
            @Override
            public void changed(ObservableValue<? extends NhaCungCap> observable, NhaCungCap oldValue, NhaCungCap newValue) {
                manhacungcap=newValue.getManhacungcap();
            }

            
        });
       
    }
    
    @FXML
    private void handler_Themphieunhap(ActionEvent event) {
        NhaCungCap ncc = new NhaCungCap();
        ObservableList <NhaCungCap> list = ncc.getTableNhaCungCap();
        int maphieu = Integer.parseInt(txtfimaphieunhap.getText().toString());
        String nguoinhap = txtfinguoinhap.getText();
        int nhacc = manhacungcap;
        LocalDate _ngaynhap = datengaynhap.getValue();
        java.sql.Date ngaynhap = java.sql.Date.valueOf(_ngaynhap );
        int tongtien=0;
        PhieuNhap pn = new PhieuNhap(maphieu,tongtien, nguoinhap, nhacc, ngaynhap);
        pn.ThemPhieuNhap();
        InitTableViewPhieuNhap();
        
    }
    
    public void InitTableViewPhieuNhap(){
        PhieuNhap pn = new PhieuNhap();
        ObservableList <PhieuNhap> list = pn.getListPhieuNhap();
        clmaphieunhap.setCellValueFactory(new PropertyValueFactory("maphieunhap"));   
        cltennhanvien.setCellValueFactory(new PropertyValueFactory("tennhanvien"));
        clngaynhap.setCellValueFactory(new PropertyValueFactory("ngaynhap"));
        clnhacungcap.setCellValueFactory(new PropertyValueFactory("manhacungcap"));
        cltongtien.setCellValueFactory(new PropertyValueFactory("tongtien"));
        tableviewphieunhap.setItems(list);
        
         
    }

    @FXML
    private void handler_load(MouseEvent event) {
        InitCmb();
    }

    @FXML
    private void handler_xoaphieunhap(ActionEvent event) {
        PhieuNhap selectedForDeletion = tableviewphieunhap.getSelectionModel().getSelectedItem();
        if(selectedForDeletion==null)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Mời chọn phiếu nhập");
            alert.setTitle("Nhắc nhở");
            alert.setHeaderText(null);
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Nhắc nhở");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có muốn xóa phiếu nhập mã "+ selectedForDeletion.getMaphieunhap()+" ?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get()==ButtonType.OK)
        {
            PhieuNhap pn = new PhieuNhap();
            pn.XoaPhieuNhap(selectedForDeletion.getMaphieunhap());
            InitTableViewPhieuNhap();
            System.out.println("Xoa Thanh Cong");
        }else 
                System.out.println("Xoa That Bai");
        
    }

    @FXML
    private void handler_themchitietphieunhap(ActionEvent event) {
        try { 
            PhieuNhap getSelectedRow = tableviewphieunhap.getSelectionModel().getSelectedItem();
            mapn=getSelectedRow.getMaphieunhap();
            Parent root = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_ChiTietPhieuNhap.fxml"));            
            Stage stage = new Stage();
            
            stage.setScene(new Scene(root));  
            stage.show();
            stageQuanLyCTPN = stage;
            
            stage.setOnCloseRequest(new EventHandler<WindowEvent> () {
                @Override
                public void handle(WindowEvent event) {
                InitTableViewPhieuNhap();
                }
            });
        } catch(Exception e) {
           e.printStackTrace();
          }
    }
    

    @FXML
    private void handler_suaphieunhap(ActionEvent event) {
        PhieuNhap getSelectedRow =  tableviewphieunhap.getSelectionModel().getSelectedItem();
        txtfimaphieunhap.setText(Integer.toString(getSelectedRow.getMaphieunhap()));
        btnluuphieunhap.setDisable(false);
        btnthemphieu.setDisable(true);
    }


    @FXML
    private void handler_luuphieunhap(ActionEvent event) {
        int maphieu = Integer.parseInt(txtfimaphieunhap.getText().toString());
        String nguoinhap = txtfinguoinhap.getText();
        int nhacc = manhacungcap;
        LocalDate _ngaynhap = datengaynhap.getValue();
        java.sql.Date ngaynhap = java.sql.Date.valueOf(_ngaynhap );
        PhieuNhap pn = new PhieuNhap(maphieu, nguoinhap, nhacc, ngaynhap);
        pn.CapNhatPhieuNhap(maphieu);
        InitTableViewPhieuNhap();
        btnluuphieunhap.setDisable(true);
        btnthemphieu.setDisable(false);
    }
    
}
