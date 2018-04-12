/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import static clothesstore_controller.FXML_DangNhapController.stageMain;
import clothesstore_model.NhanVien;
import clothesstore_model.TaiKhoan;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author user
 */
public class FXML_QuanLyTaiKhoanController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane QLNVpane, paneINFO, paneTK;
    
    @FXML
    private TableView tblNhanvien;
    
    @FXML
    private JFXButton btnThem, btnSua, btnXoa, btnLuu, btnHuy, btnShow, btnShow1;
    
    @FXML
    private JFXTextField txtTenTaiKhoan, txtTenNhanVien, txtCMND, 
            txtDiaChi, txtLuong;
    @FXML
    private JFXPasswordField txtMatKhau, txtMatKhau1;
             
    @FXML
    private JFXComboBox cmbGioiTinh, cmbTrangThai, cmbFilterTrangThai;
            
    @FXML
    private JFXDatePicker dpNgaySinh;
            
    @FXML
    private TableColumn tennhanvien, diachi, gioitinh, cmnd, ngaysinh, luong, trangthai, tentaikhoan, matkhau, manhanvien;
        
    public static AnchorPane _QLNVpane;
    
    private NhanVien nhanvien;
    private int flag = -1; // 0->Sua, 1->Them
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        _QLNVpane = QLNVpane;
        InitTableView();
        InitCmb();
        
        txtLuong.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, 
            String newValue) {
                if (!newValue.matches("\\d*")) { // set number only
                    txtLuong.setText(newValue.replaceAll("[^\\d]", ""));
                } 
                if (newValue.length() >= 9) ((StringProperty)observable).setValue(oldValue); // limit input
            }   
        });
    }    
    
    private void InitCmb(){
        cmbGioiTinh.getItems().addAll("Nam" , "Nữ");
        cmbGioiTinh.getSelectionModel().selectFirst();
        
        cmbTrangThai.getItems().addAll("Hoạt động", "Không hoạt động");
        cmbTrangThai.getSelectionModel().selectFirst();
        
        cmbFilterTrangThai.getItems().addAll("Tất cả", "Hoạt động", "Không hoạt động");
        cmbFilterTrangThai.getSelectionModel().selectFirst();
        
        cmbFilterTrangThai.valueProperty().addListener(new ChangeListener<String>() {
        @Override public void changed(ObservableValue ov, String t, String t1) {
            NhanVien nv = new NhanVien();
            ObservableList <NhanVien> list = null;
            if (t1.equals("Tất cả"))
                InitTableView();
            else if (t1.equals("Hoạt động")){
                list = nv.TrangThaiFilter(1);
                FilterTrangThai(list);
                System.out.println(t1);
            }
            else{
                list = nv.TrangThaiFilter(0);
                FilterTrangThai(list);
                System.out.println(t1);
            } 
            }    
        });
    }
    
    private void FilterTrangThai(ObservableList list){
        manhanvien.setCellValueFactory(new PropertyValueFactory("manhanvien"));
        tennhanvien.setCellValueFactory(new PropertyValueFactory("tennhanvien"));     
        diachi.setCellValueFactory(new PropertyValueFactory("diachi"));        
        //gioitinh.setCellValueFactory(new PropertyValueFactory("gioitinh")); 
        cmnd.setCellValueFactory(new PropertyValueFactory("cmnd"));     
        ngaysinh.setCellValueFactory(new PropertyValueFactory("ngaysinh"));        
        luong.setCellValueFactory(new PropertyValueFactory("luong"));  
        //trangthai.setCellValueFactory(new PropertyValueFactory("trangthai")); 
        
        gioitinh.setCellValueFactory(new Callback<CellDataFeatures<NhanVien, String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(CellDataFeatures<NhanVien, String> p) {
            String gioitinh="";
            if(p.getValue().getGioitinh() == 1)
                gioitinh = "Nam";
            else    
                gioitinh = "Nữ";
            return new SimpleStringProperty(gioitinh);
        }
        });
        
        trangthai.setCellValueFactory(new Callback<CellDataFeatures<NhanVien, String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(CellDataFeatures<NhanVien, String> p) {
            String trangthai="";
            if(p.getValue().getTrangthai()== 1)
                trangthai = "Hoạt động" ;
            else    
                trangthai = "Không hoạt động";
            return new SimpleStringProperty(trangthai);
        }
        });
        
        tentaikhoan.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NhanVien, TaiKhoan>, ObservableValue<TaiKhoan>>() {
            @Override
            public ObservableValue<TaiKhoan> call(TableColumn.CellDataFeatures<NhanVien, TaiKhoan> param) {
                return  new ReadOnlyObjectWrapper(param.getValue().getTaiKhoan().getTentaikhoan());
            }
        });
        
        matkhau.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NhanVien, TaiKhoan>, ObservableValue<TaiKhoan>>() {
            @Override
            public ObservableValue<TaiKhoan> call(TableColumn.CellDataFeatures<NhanVien, TaiKhoan> param) {
                return  new ReadOnlyObjectWrapper(param.getValue().getTaiKhoan().getMatkhau());
            }
        });
        
        tblNhanvien.setItems(list);
        
        tblNhanvien.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                if (tblNhanvien.getSelectionModel().getSelectedItem() != null) {
                    nhanvien = (NhanVien) tblNhanvien.getSelectionModel().getSelectedItem();
                    btnXoa.setDisable(false);
                    btnSua.setDisable(false);
                }
                else{
                    nhanvien = (NhanVien) tblNhanvien.getSelectionModel().getSelectedItem();
                    btnXoa.setDisable(true);
                    btnSua.setDisable(true); 
                    btnLuu.setDisable(true);
                    btnHuy.setDisable(true);
                }   
            }
        });
    }
    
    private void InitTableView(){
        NhanVien nv = new NhanVien();
        ObservableList <NhanVien> list = nv.getListNhanVien();
        
        manhanvien.setCellValueFactory(new PropertyValueFactory("manhanvien"));
        tennhanvien.setCellValueFactory(new PropertyValueFactory("tennhanvien"));     
        diachi.setCellValueFactory(new PropertyValueFactory("diachi"));        
        cmnd.setCellValueFactory(new PropertyValueFactory("cmnd"));     
        ngaysinh.setCellValueFactory(new PropertyValueFactory("ngaysinh"));        
        luong.setCellValueFactory(new PropertyValueFactory("luong"));  
        
        gioitinh.setCellValueFactory(new Callback<CellDataFeatures<NhanVien, String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(CellDataFeatures<NhanVien, String> p) {
            String gioitinh="";
            if(p.getValue().getGioitinh() == 1)
                gioitinh = "Nam";
            else    
                gioitinh = "Nữ";
            return new SimpleStringProperty(gioitinh);
        }
        });
        
        trangthai.setCellValueFactory(new Callback<CellDataFeatures<NhanVien, String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(CellDataFeatures<NhanVien, String> p) {
            String trangthai="";
            if(p.getValue().getTrangthai()== 1)
                trangthai = "Hoạt động" ;
            else    
                trangthai = "Không hoạt động";
            return new SimpleStringProperty(trangthai);
        }
        });
        
        tentaikhoan.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NhanVien, TaiKhoan>, ObservableValue<TaiKhoan>>() {
            @Override
            public ObservableValue<TaiKhoan> call(TableColumn.CellDataFeatures<NhanVien, TaiKhoan> param) {
                return  new ReadOnlyObjectWrapper(param.getValue().getTaiKhoan().getTentaikhoan());
            }
        });
        
        matkhau.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<NhanVien, TaiKhoan>, ObservableValue<TaiKhoan>>() {
            @Override
            public ObservableValue<TaiKhoan> call(TableColumn.CellDataFeatures<NhanVien, TaiKhoan> param) {
                return  new ReadOnlyObjectWrapper(param.getValue().getTaiKhoan().getMatkhau());
            }
        });
        
        tblNhanvien.setItems(list);
        
        tblNhanvien.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                if (tblNhanvien.getSelectionModel().getSelectedItem() != null) {
                    nhanvien = (NhanVien) tblNhanvien.getSelectionModel().getSelectedItem();
                    btnXoa.setDisable(false);
                    btnSua.setDisable(false);
                }
                else{
                    nhanvien = (NhanVien) tblNhanvien.getSelectionModel().getSelectedItem();
                    btnXoa.setDisable(true);
                    btnSua.setDisable(true); 
                    btnLuu.setDisable(true);
                    btnHuy.setDisable(true);
                }   
            }
        });
    }
    
    @FXML
    private void Handler_btnThem(ActionEvent event) {
        flag = 1;
        paneINFO.setDisable(false);
        paneTK.setDisable(false);
        btnXoa.setDisable(true);
        btnSua.setDisable(true);
        btnLuu.setDisable(false);
        btnHuy.setDisable(false);
        cmbTrangThai.setDisable(true);
        tblNhanvien.setDisable(true);
    }
    
    @FXML
    private void Handler_btnSua(ActionEvent event) {
        flag = 0;
        tblNhanvien.setDisable(true);
        btnLuu.setDisable(false);
        btnHuy.setDisable(false);
        btnThem.setDisable(true);
        paneINFO.setDisable(false);
        paneTK.setDisable(false);   
        cmbTrangThai.setDisable(false);
        txtTenNhanVien.setText(nhanvien.getTennhanvien());
        txtCMND.setText(nhanvien.getCmnd());
        dpNgaySinh.setValue(nhanvien.getNgaysinh().toLocalDate());
        txtDiaChi.setText(nhanvien.getDiachi());
        txtLuong.setText(Integer.toString(nhanvien.getLuong()));
        txtTenTaiKhoan.setText(nhanvien.getTaiKhoan().getTentaikhoan());
        txtMatKhau.setText(nhanvien.getTaiKhoan().getMatkhau());
        txtMatKhau1.setText(nhanvien.getTaiKhoan().getMatkhau());
        if(nhanvien.getGioitinh() == 1)
            cmbGioiTinh.getSelectionModel().selectFirst();
        else 
            cmbGioiTinh.getSelectionModel().selectLast();
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
            TaiKhoan tk = new TaiKhoan();
            if(tk.XoaTaiKhoan(nhanvien.getTaiKhoan().getTentaikhoan())){
                if(nhanvien.XoaNhanVien()){
                    InitTableView();
                    System.out.println("Xoa Thanh Cong");
                } else {
                    System.out.println("Xoa TK thanh cong, xoa nhan vien that bai");
                }
                    
            } else 
                System.out.println("Xoa That Bai");
            
        }            
    }
    
    @FXML
    private void Handler_btnLuu(ActionEvent event) {
        String tentaikhoan = txtTenTaiKhoan.getText();
        String matkhau = txtMatKhau.getText().toString();
        String matkhau1 = txtMatKhau.getText().toString();
        String ten = txtTenNhanVien.getText();
        String diachi = txtDiaChi.getText();
        String cmnd = txtCMND.getText();
        tblNhanvien.getSelectionModel().focus(-1);
        int luong = 0;
        try{
            luong = Integer.parseInt(txtLuong.getText().toString());
        }catch(Exception ex){
            System.out.println(ex);
        }
        int gioitinh = 0;
        if ( cmbGioiTinh.getValue().equals("Nam"))
            gioitinh = 1;
        else 
            gioitinh = 0;
        
        int trangthai = 0;
        if ( cmbTrangThai.getValue().equals("Hoạt động"))
            trangthai = 1;
        else 
            trangthai = 0;
        
        LocalDate _ngaysinh = null;
        java.sql.Date ngaysinh = null;
        try{
            _ngaysinh = dpNgaySinh.getValue();
            ngaysinh = java.sql.Date.valueOf(_ngaysinh );
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        if(tentaikhoan.equals("") || matkhau.equals("") || matkhau1.equals("")
                || ten.equals("") || diachi.equals("")
                || cmnd.equals("") || luong ==0 || _ngaysinh == null){
            
            ButtonType cancel = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Vui lòng điền đầy đủ thông tin",
                    cancel);

            alert.setTitle("Nhắc nhở");
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait(); 

        } else if (!txtMatKhau.getText().toString().equals(txtMatKhau1.getText().toString())){
            ButtonType cancel = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Mật khẩu xác nhận không trùng với mật khẩu",
                    cancel);

            alert.setTitle("Nhắc nhở");
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait(); 
        }
        else {
            if (flag == 1){
                NhanVien nv = new NhanVien(ten, diachi, gioitinh, ngaysinh, cmnd, 1, luong);

                if(nv.ThemNhanVien()){
                    TaiKhoan tk = new TaiKhoan(tentaikhoan, matkhau, 0, nv.getLastId());
                    if (tk.ThemTaiKhoan()){
                        InitTableView();
                        System.out.println("Them thanh cong");  
                        btnLuu.setDisable(true);
                        btnHuy.setDisable(true);
                        paneINFO.setDisable(true);
                        paneTK.setDisable(true);
                        btnThem.setDisable(false);
                        tblNhanvien.setDisable(false);
                        tblNhanvien.getSelectionModel().clearSelection();
                        Reset();
                    }   
                    else{
                        Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Đăng ký thất bại");
                        alert.setTitle("Thông báo");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                    }
                }
                else{
                    System.out.println("Them nhan vien that bai");
                }
                           
            }
            else if (flag == 0){
                NhanVien nv = new NhanVien(nhanvien.getManhanvien(), ten, diachi, gioitinh, ngaysinh, cmnd, trangthai, luong);
                if(nv.SuaNhanVien()){
                    TaiKhoan tk = new TaiKhoan(tentaikhoan, matkhau, 0, nv.getManhanvien());
                    if (tk.SuaTaiKhoan()){
                        InitTableView();
                        System.out.println("Sua thanh cong");
                        btnLuu.setDisable(true);
                        btnHuy.setDisable(true);
                        paneINFO.setDisable(true);
                        paneTK.setDisable(true);
                        btnThem.setDisable(false);
                        tblNhanvien.setDisable(false);
                        Reset();
                        tblNhanvien.getSelectionModel().clearSelection();
                    }   
                }
                else
                    System.out.println("Sua that bai");  
            }
        }
    }
    
    @FXML
    private void Handler_btnShow(ActionEvent event) {
        showTooltip(stageMain, btnShow, txtMatKhau.getText().toString(), null);
    }
    
    @FXML
    private void Handler_btnShow1(ActionEvent event) {
        showTooltip(stageMain, btnShow1, txtMatKhau1.getText().toString(), null);
    }
    
    public static void showTooltip(Stage owner, Control control, String tooltipText,
    ImageView tooltipGraphic){
        Point2D p = control.localToScene(0.0, 0.0);

        final Tooltip customTooltip = new Tooltip();
        customTooltip.setText(tooltipText);

        control.setTooltip(customTooltip);
        customTooltip.setAutoHide(true);

        customTooltip.show(owner, p.getX()
            + control.getScene().getX() + control.getScene().getWindow().getX(), p.getY()
            + control.getScene().getY() + control.getScene().getWindow().getY());
    }
    
    private void Reset(){
        txtTenTaiKhoan.clear();
        txtMatKhau.clear();
        txtMatKhau1.clear();
        txtTenNhanVien.clear();
        txtDiaChi.clear();
        txtCMND.clear();
        txtLuong.clear();
    }
    
    @FXML
    private void Handler_btnHuy(ActionEvent event) {
        btnLuu.setDisable(true);
        btnHuy.setDisable(true);
        paneINFO.setDisable(true);
        paneTK.setDisable(true);
        btnThem.setDisable(false);
        tblNhanvien.setDisable(false);
        tblNhanvien.getSelectionModel().clearSelection();
        Reset();
    }

}
