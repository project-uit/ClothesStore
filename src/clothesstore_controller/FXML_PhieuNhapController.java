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
import java.io.IOException;
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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import clothesstore_model.NhaCungCap;
import clothesstore_model.PhieuNhap;
import javafx.scene.control.DatePicker;
import static jdk.nashorn.tools.ShellFunctions.input;

/**
 * FXML Controller class
 *
 * @author 15520
 */
public class FXML_PhieuNhapController implements Initializable {

    @FXML
    private AnchorPane QLPNpane;
    private JFXTextField txtfimaphieunhap;
    @FXML
    private JFXDatePicker datengaynhap;
    @FXML
    private JFXComboBox cbnhacungcap;
    @FXML
    private JFXButton btnthemphieu;
    @FXML
    private TableColumn clmaphieunhap;
    @FXML
    private TableColumn clnhacungcap;
    @FXML
    private TableColumn clngaynhap;
    @FXML
    private TableColumn cltongtien;
    
    public static Stage stageQuanLyNCC;
    public static Stage stageQuanLyCTPN;
    public static int mapn;
    private ChangeListener<NhaCungCap> listenerNCC;
        
    private PhieuNhap phieunhap;
    @FXML
    private TableView<PhieuNhap> tableviewphieunhap;
    @FXML
    private JFXButton btnluuphieunhap;
    @FXML
    private JFXButton btnhuyphieunhap;
    @FXML
    private AnchorPane paneINFO;
    @FXML
    private JFXButton btnnhacungcap;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InitTableViewPhieuNhap();
        InitCmbNCC();
        datengaynhap.setValue(LocalDate.now());
        
    }    

    @FXML
    private void Handler_btnnhacungcap(ActionEvent event) {
        cbnhacungcap.getSelectionModel().selectedItemProperty().removeListener(listenerNCC);
        try { 
            Parent root = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_NhaCungCap.fxml"));            
            stageQuanLyNCC = new Stage();
            stageQuanLyNCC.initModality(Modality.APPLICATION_MODAL);
            //stageQuanLyNCC.initStyle(StageStyle.UNDECORATED);
            stageQuanLyNCC.setScene(new Scene(root));  
            stageQuanLyNCC.setOnCloseRequest((WindowEvent event1) -> {
                NhaCungCap ncc = new NhaCungCap();
                ObservableList <NhaCungCap> list = ncc.getTableNhaCungCap();
                cbnhacungcap.getItems().clear();
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
                
                cbnhacungcap.getSelectionModel().selectedItemProperty().addListener(listenerNCC = new ChangeListener<NhaCungCap>() {
                    @Override
                    public void changed(ObservableValue<? extends NhaCungCap> observable, NhaCungCap oldValue, NhaCungCap newValue) {
                        System.out.println(newValue.getManhacungcap());
                        manhacungcap = newValue.getManhacungcap();
                    }
                });
                        
                cbnhacungcap.getSelectionModel().selectFirst();
            });
            stageQuanLyNCC.show();
        } catch(Exception e) {
           e.printStackTrace();
          }
    }
    public int getmaphieunhap;
    private int manhacungcap;
    private String tennhacungcap;
    private void InitCmbNCC(){
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
        cbnhacungcap.getSelectionModel().selectedItemProperty().addListener(listenerNCC = new ChangeListener<NhaCungCap>() {
            @Override
            public void changed(ObservableValue<? extends NhaCungCap> observable, NhaCungCap oldValue, NhaCungCap newValue) {
                System.out.println(newValue.getManhacungcap());
                manhacungcap = newValue.getManhacungcap();
                tennhacungcap = newValue.getTencungcap();
            }
        });
    }

    @FXML
    private void handler_Themphieunhap(ActionEvent event) {
        NhaCungCap ncc = new NhaCungCap();
        ObservableList <NhaCungCap> list = ncc.getTableNhaCungCap();
        int nhacc = manhacungcap;
        LocalDate _ngaynhap = null;
        java.sql.Date ngaynhap = null;
        try{
            _ngaynhap = datengaynhap.getValue();
            ngaynhap = java.sql.Date.valueOf(_ngaynhap );
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        int tongtien=0;
        System.out.println(""+ _ngaynhap);
        if( nhacc==0|| _ngaynhap==null){
            
            ButtonType cancel = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Vui lòng điền đầy đủ thông tin",
                    cancel);

            alert.setTitle("Nhắc nhở");
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait(); 
        }
        else{
        PhieuNhap pn = new PhieuNhap( nhacc, ngaynhap,tongtien);
        pn.ThemPhieuNhap();
        InitTableViewPhieuNhap();
        Reset();
        }
    }
    
    public void InitTableViewPhieuNhap(){
        PhieuNhap pn = new PhieuNhap();
        ObservableList <PhieuNhap> list = pn.getListPhieuNhap();
        clmaphieunhap.setCellValueFactory(new PropertyValueFactory("maphieunhap"));   
        clngaynhap.setCellValueFactory(new PropertyValueFactory("ngaynhap"));
        clnhacungcap.setCellValueFactory(new PropertyValueFactory("manhacungcap"));
        cltongtien.setCellValueFactory(new PropertyValueFactory("tongtien"));
        tableviewphieunhap.setItems(list);
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
    private void handler_themchitietphieunhap(ActionEvent event) throws IOException {
        PhieuNhap selectedForDeletion = tableviewphieunhap.getSelectionModel().getSelectedItem();
        if(selectedForDeletion==null)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Mời chọn phiếu nhập");
            alert.setTitle("Nhắc nhở");
            alert.setHeaderText(null);
            return;
        }
        
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
        
    }
    @FXML
    private void handler_suaphieunhap(ActionEvent event) {
        PhieuNhap selectedForDeletion = tableviewphieunhap.getSelectionModel().getSelectedItem();
        phieunhap=(PhieuNhap) tableviewphieunhap.getSelectionModel().getSelectedItem();
        datengaynhap.setValue(selectedForDeletion.getNgaynhap().toLocalDate());
        if(selectedForDeletion==null)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Mời chọn phiếu nhập");
            alert.setTitle("Nhắc nhở");
            alert.setHeaderText(null);
            return;
        }
        
        //txtfinguoinhap.setText(selectedForDeletion.getMaNhanVien());
                
        btnluuphieunhap.setDisable(false);
        btnthemphieu.setDisable(true);
        btnhuyphieunhap.setDisable(false);
    }
    @FXML
    private void handler_luuphieunhap(ActionEvent event) {
        
        int nhacc = manhacungcap;
        LocalDate _ngaynhap = null;
        java.sql.Date ngaynhap = null;
        try{
            _ngaynhap = datengaynhap.getValue();
            ngaynhap = java.sql.Date.valueOf(_ngaynhap );
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        
        if(nhacc==0 || ngaynhap==null){
            
            ButtonType cancel = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Vui lòng điền đầy đủ thông tin",
                    cancel);

            alert.setTitle("Nhắc nhở");
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait(); 
        }
        else{
        PhieuNhap pn = new PhieuNhap(phieunhap.getMaphieunhap(), nhacc, ngaynhap);
        pn.CapNhatPhieuNhap();
        InitTableViewPhieuNhap();
        btnluuphieunhap.setDisable(true);
        btnthemphieu.setDisable(false);
        btnhuyphieunhap.setDisable(true);
        }
    }
    private void Reset(){
       
    }
    @FXML
    private void handler_huyphieunhap(ActionEvent event) {
        btnluuphieunhap.setDisable(true);
        btnthemphieu.setDisable(false);
        btnhuyphieunhap.setDisable(true);
    }
    
}
