/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import clothesstore_model.ChiTietHoaDon;
import clothesstore_model.ChiTietKhachHang;
import clothesstore_model.ChiTietSanPham;
import clothesstore_model.HoaDon;
import clothesstore_model.KhachHang;
import clothesstore_model.SanPham;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXNodesList;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author quochung
 */
public class FXML_HoaDonController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton btnThem, btnXoa, btnLapHoaDon, btnThanhToan,
            btnInHoaDon, btnchucnang, btncaidat;
    @FXML
    private JFXNodesList nodelistbtn;
    @FXML
    private JFXTextField txt_fi_tongtien, txt_fi_machitietsanpham, txt_fi_dongia,
            txt_fi_thanhtien, txt_fi_tenkhachhang, txt_fi_sodienthoai;
    ;
    @FXML
    private Spinner<Integer> spin_soluong;
    @FXML
    private TableView<String> table_view;

    private Integer soluongmua, mahoadon, machitiethoadon, tongtien = 0;
    private int dongia;
    private boolean checkLaphoadon = true;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO               

        InitTextField();
        mahoadon = 0;
        Tooltip tiptext = new Tooltip("Lập hóa đơn\nIn hóa đơn");
        btncaidat.setTooltip(new Tooltip("Thay đổi thông tin hóa đơn"));
        btnchucnang.setTooltip(tiptext);
        nodelistbtn.addAnimatedNode(btnchucnang);
        nodelistbtn.addAnimatedNode(btnLapHoaDon);
        nodelistbtn.addAnimatedNode(btnInHoaDon);
        nodelistbtn.addAnimatedNode(btncaidat);
        nodelistbtn.setSpacing(5);
        table_view.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (table_view.getSelectionModel().getSelectedItem() != null) {
                    machitiethoadon = Integer.parseInt(newValue.toString().split(",")[0].substring(1).trim());
                    String mactsp = newValue.toString().split(",")[2].substring(1).trim();
                    txt_fi_machitietsanpham.setText(mactsp);
                    Integer soluongmua = Integer.parseInt(newValue.toString().split(",")[3].substring(1).trim());
                    spin_soluong.getValueFactory().setValue(soluongmua);
                }
            }
        });

        viewListTable();
        ContextMenuTable();
    }

    private void viewListTable() {
        if (!table_view.getColumns().isEmpty()) {
            table_view.getColumns().clear();
        }
        ChiTietHoaDon cthd = new ChiTietHoaDon();
        cthd.LoadTable(table_view);
        table_view.getColumns().get(0).setVisible(false);
        table_view.getColumns().get(1).setVisible(false);
        table_view.getColumns().get(2).setText("Mã ctsp");
        table_view.getColumns().get(3).setText("Số lượng mua");
        table_view.getColumns().get(4).setText("Giá bán");
        table_view.getColumns().get(5).setText("Thành tiền");
        table_view.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void ContextMenuTable() {
        ContextMenu context = new ContextMenu();

        MenuItem itemXoa = new MenuItem("Xóa");
        itemXoa.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                btnXoa_click();
            }
        });
      
        context.getItems().addAll(itemXoa);
        table_view.setContextMenu(context);
    }

    private void InitTextField() {
        txt_fi_machitietsanpham.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (newValue.length() >= 10) {
                    getDonGia(newValue);
                    InitSpinner(newValue);
                }
                if (newValue.length() == 0) {
                    SpinnerValueFactory<Integer> valueFactory
                            = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0);
                    spin_soluong.setValueFactory(valueFactory);
                    txt_fi_dongia.clear();

                    txt_fi_thanhtien.setText("" + 0);
                }
            }
        });
        List<String> arr_mactsp = new ChiTietSanPham().getListMactsp();

        TextFields.bindAutoCompletion(txt_fi_machitietsanpham, arr_mactsp);
        List<String> arr_sdt = new KhachHang().getListSDT();
        TextFields.bindAutoCompletion(txt_fi_sodienthoai, arr_sdt);
        txt_fi_sodienthoai.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    txt_fi_sodienthoai.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        txt_fi_sodienthoai.setOnKeyTyped(event -> {
            int maxCharacters = 13;
            if (txt_fi_sodienthoai.getText().length() > maxCharacters - 1) {
                event.consume();
            }
        });
        txt_fi_sodienthoai.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                KhachHang kh = new KhachHang();
                String tenkh = kh.getTenkhachhang(txt_fi_sodienthoai.getText());
                txt_fi_tenkhachhang.setText(tenkh);
            }
        });
    }

    private void getDonGia(String mactsp) {
        SanPham sp = new SanPham();
        dongia = sp.getDongia(mactsp);
        if (dongia != -1) {
            txt_fi_dongia.setText("" + FormatTien(dongia));
        }
    }

    private void InitSpinner(String _mactsp) {
        StringProperty mactsp = new SimpleStringProperty(_mactsp);
        ChiTietSanPham ctsp = new ChiTietSanPham(mactsp);
        int max = ctsp.getMAXSoluongCTSP();
        int min = 0;
        if (max != -1) {
            SpinnerValueFactory<Integer> valueFactory
                    = new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max);
            spin_soluong.setValueFactory(valueFactory);
        }
        spin_soluong.valueProperty().addListener((obs, oldValue, newValue)
                -> {
            soluongmua = newValue;
            int thanhtien = soluongmua * dongia;
            txt_fi_thanhtien.setText("" + FormatTien(thanhtien));
        }
        );
    }

    @FXML
    private void btncaidat_click(ActionEvent act) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/clothesstore_view/FXML_CuaHang.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Thông tin hóa đơn");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void Click_Event(ActionEvent act) {
        JFXButton btn = (JFXButton) act.getSource();
        if (btn == btnLapHoaDon) {
            if (checkLaphoadon) {
                laphoadon_click();
            } else {
                huyhoadon_click();
            }
        } else if (btn == btnThem) {
            btnthem_click();
        } else if (btn == btnThanhToan) {
            btnthanhtoan_click();

        } else if (btn == btnXoa) {
            btnXoa_click();
        } else if (btn == btnInHoaDon) {
            btnInHoaDon_click();
        }
    }

    private void btnInHoaDon_click() {

        HoaDon hd = new HoaDon(new SimpleIntegerProperty(mahoadon), new SimpleIntegerProperty(tongtien));
        hd.inhoadon();
        btnLapHoaDon.setText("Lập hóa đơn");
        btnThanhToan.setDisable(true);
        btnThem.setDisable(true);
        btnXoa.setDisable(true);
        btnInHoaDon.setDisable(true);

        checkLaphoadon = true;

        txt_fi_tongtien.setText("0");
        txt_fi_machitietsanpham.setText("");
        tongtien = 0;
        viewListTable();
        if (FXML_ClothesStoreController.rootP.getChildren().size() == 2) {
            FXML_ClothesStoreController.rootP.getChildren().remove(1);
        }
        
    }

    private void laphoadon_click() {

        btnThem.setDisable(false);
        btnXoa.setDisable(false);

        btnLapHoaDon.setText("Hủy hóa đơn");
        checkLaphoadon = false;

        HoaDon hoadon = new HoaDon();
        hoadon.setManhanvien(new SimpleIntegerProperty(FXML_DangNhapController.MaNhanVien));
        if (hoadon.insert()) {
            mahoadon = hoadon.getMaHD();
            System.out.println("" + mahoadon.toString());
        } else {
            ShowMessage
                    .showMessageBox(Alert.AlertType.ERROR, "Thông báo", null, "Lập hóa đơn thất bại")
                    .showAndWait();
        }
    }

    private void huyhoadon_click() {

        btnLapHoaDon.setText("Lập hóa đơn");
        btnThanhToan.setDisable(true);
        btnThem.setDisable(true);
        btnXoa.setDisable(true);
        btnInHoaDon.setDisable(true);

        checkLaphoadon = true;

        txt_fi_machitietsanpham.setText("");
        txt_fi_tongtien.setText("0");
        HoaDon hoadon = new HoaDon(new SimpleIntegerProperty(mahoadon));
        if (hoadon.delete()) {
            update_sl_ctsp(false);
            viewListTable();
            if (FXML_ClothesStoreController.rootP.getChildren().size() == 2) {
                FXML_ClothesStoreController.rootP.getChildren().remove(1);
            }
            tongtien = 0;
            ShowMessage
                    .showMessageBox(Alert.AlertType.INFORMATION, "Thông báo", null, "Hủy hóa đơn thành công")
                    .showAndWait();
           
        } else {
            ShowMessage
                    .showMessageBox(Alert.AlertType.ERROR, "Thông báo", null, "Hủy hóa đơn thất bại")
                    .showAndWait();
        }
    }

    private void btnthem_click() {
        btnThanhToan.setDisable(false);
        Integer slmua = 0;
        if (spin_soluong.getValue() != null) {
            slmua = spin_soluong.getValue();
        }
        String money = txt_fi_thanhtien.getText();
        int thanhtien = Integer.parseInt(money.replaceAll(",", ""));
        ChiTietHoaDon cthd = new ChiTietHoaDon(new SimpleIntegerProperty(mahoadon),
                new SimpleStringProperty(txt_fi_machitietsanpham.getText()),
                new SimpleIntegerProperty(slmua),
                new SimpleIntegerProperty(thanhtien));
        if (cthd.isEmpty()) {
            ShowMessage
                    .showMessageBox(Alert.AlertType.WARNING, "Thông báo", null, "Bạn phải nhập số lượng")
                    .showAndWait();
            return;
        }
        if (cthd.insert()) {
            ShowMessage
                    .showMessageBox(Alert.AlertType.INFORMATION, "Thông báo", null, "Thêm dữ liệu thành công")
                    .showAndWait();
            viewListTable();
            HoaDon hd = new HoaDon();
            tongtien = hd.tongtien(mahoadon);

            txt_fi_tongtien.setText("" + FormatTien(tongtien));
        } else {
            ShowMessage
                    .showMessageBox(Alert.AlertType.ERROR, "Thông báo", null, "Thêm dữ liệu thất bại")
                    .showAndWait();
        }
    }

    private void btnXoa_click() {
        ChiTietHoaDon cthd = new ChiTietHoaDon();
        cthd.setMachitiethoadon(new SimpleIntegerProperty(machitiethoadon));
        if (cthd.delete()) {
            ShowMessage
                    .showMessageBox(Alert.AlertType.INFORMATION, "Thông báo", null, "Xóa dữ liệu thành công")
                    .showAndWait();
            viewListTable();
            HoaDon hd = new HoaDon();
            tongtien = hd.tongtien(mahoadon);
            txt_fi_tongtien.setText("" + FormatTien(tongtien));
        } else {
            ShowMessage
                    .showMessageBox(Alert.AlertType.ERROR, "Thông báo", null, "Xóa dữ liệu thất bại")
                    .showAndWait();
        }
    }

    private void btnthanhtoan_click() {

        HoaDon hd = new HoaDon(new SimpleIntegerProperty(mahoadon), new SimpleIntegerProperty(tongtien));
        if (hd.checktongtien()) {
            ShowMessage
                    .showMessageBox(Alert.AlertType.WARNING, "Thông báo", null, "Bạn không thể thanh toán\nkhi tổng tiền bằng 0")
                    .showAndWait();
            return;
        }
        if (!txt_fi_sodienthoai.getText().isEmpty()) {
            KhachHang kh = new KhachHang(new SimpleStringProperty(txt_fi_tenkhachhang.getText()),
                    new SimpleStringProperty(txt_fi_sodienthoai.getText()));
            ChiTietKhachHang ctkh = new ChiTietKhachHang();
            ctkh.setSodienthoai(new SimpleStringProperty(txt_fi_sodienthoai.getText()));
            ctkh.setMahoadon(new SimpleIntegerProperty(mahoadon));
            int flag = -1;
            if (ctkh.insert()) {
                flag = 0;
            } else {
                if (kh.insert()) {
                    if (ctkh.insert()) {
                        flag = 1;
                    }
                }
            }
            if (flag == 0) {
                System.out.println("Lưu thành công thông tin kh cũ");
            } else if (flag == 1) {
                System.out.println("Lưu thành công thông tin kh mới");
            } else {
                System.out.println("Lưu thất bại thông tin kh");
            }
        }
        if (hd.UpdateTongtien()) {
            btnInHoaDon.setDisable(false);
            btnThem.setDisable(true);
            btnXoa.setDisable(true);
            btnThanhToan.setDisable(true);

            update_sl_ctsp(true);
            ShowMessage
                    .showMessageBox(Alert.AlertType.INFORMATION, "Thông báo", null, "Thanh toán thành công")
                    .showAndWait();
        } else {
            ShowMessage
                    .showMessageBox(Alert.AlertType.INFORMATION, "Thông báo", null, "Thanh toán thất bại")
                    .showAndWait();
        }

    }

    private void update_sl_ctsp(boolean function) {
        ObservableList listOb = table_view.getItems();
        for (int i = 0; i < listOb.size(); i++) {
            Integer soluong = Integer.parseInt(listOb.get(i).toString().split(",")[3].substring(1));
            String mactsp = listOb.get(i).toString().split(",")[2].substring(1);
            ChiTietSanPham ctsp = new ChiTietSanPham(new SimpleStringProperty(mactsp),
                    new SimpleIntegerProperty(soluong));
            ctsp.Update_soluong(function);
        }
    }

    private String FormatTien(int soTien) {
        return String.format("%,8d%n", soTien).trim();
    }
}
