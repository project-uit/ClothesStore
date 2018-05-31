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
import java.util.prefs.Preferences;
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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.textfield.TextFields;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

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
            btnInHoaDon, btncaidat;
    @FXML
    private JFXTextField txt_fi_tongtien, txt_fi_machitietsanpham, txt_fi_dongia,
            txt_fi_thanhtien, txt_fi_tenkhachhang, txt_fi_sodienthoai;

    @FXML
    private Spinner<Integer> spin_soluong;
    @FXML
    private TableView<String> table_view;
    private boolean check_thanhtoan = false;
    private Integer soluongmua, machitiethoadon, tongtien = 0, mahoadon = 0;
    private int dongia;
    private boolean checkLaphoadon = true;
    private Preferences preference;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        InitTextField();
        btncaidat.setTooltip(new Tooltip("Thay đổi thông tin hóa đơn"));
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
        preference = Preferences.userNodeForPackage(FXML_HoaDonController.class);
        if (preference != null) {
            try {
                mahoadon = preference.getInt("mahoadon", 0);
                check_thanhtoan = preference.getBoolean("check_thanhtoan", false);
            } catch (Exception e) {
            }
        }
        ContextMenuTable();
        if (mahoadon > 0 && check_thanhtoan) {
            btnInHoaDon.setDisable(false);
            tongtien = new ChiTietHoaDon(new SimpleIntegerProperty(mahoadon)).tongtien();
            txt_fi_tongtien.setText("" + FormatTien(tongtien));
            btnLapHoaDon.setText("Hủy hóa đơn");
            checkLaphoadon = false;
            updateTable_InHoaDon();
        } else if (mahoadon > 0) {
            btnThem.setDisable(false);
            btnXoa.setDisable(false);
            btnLapHoaDon.setText("Hủy hóa đơn");
            checkLaphoadon = false;
            btnThanhToan.setDisable(false);
            tongtien = new ChiTietHoaDon(new SimpleIntegerProperty(mahoadon)).tongtien();
            txt_fi_tongtien.setText("" + FormatTien(tongtien));
            System.out.println("Ma hoadon: " + mahoadon + " tong tien: " + tongtien);
        }
        if (check_thanhtoan == false) {
            viewListTable();
        }

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

    public void updateTable_InHoaDon() {
        if (!table_view.getColumns().isEmpty()) {
            table_view.getColumns().clear();
        }
        ChiTietHoaDon cthd = new ChiTietHoaDon(new SimpleIntegerProperty(mahoadon));
        cthd.LoadTable_data(table_view);
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
                if (table_view.getSelectionModel().getSelectedItem() != null) {
                    btnXoa_click();
                }
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
                if (txt_fi_sodienthoai.getText().isEmpty()) {
                    txt_fi_tenkhachhang.setText("");
                }
                if (!newValue.matches("\\d*")) {
                    txt_fi_sodienthoai.setText(newValue.replaceAll("[^\\d]", ""));
                } else {
                    if (txt_fi_sodienthoai.getText().length() > 9) {
                        KhachHang kh = new KhachHang();
                        String tenkh = kh.getTenkhachhang(txt_fi_sodienthoai.getText());
                        txt_fi_tenkhachhang.setText(tenkh);
                    }
                }
            }
        });
        txt_fi_sodienthoai.setOnKeyTyped(event -> {
            int maxCharacters = 11;
            if (txt_fi_sodienthoai.getText().length() > maxCharacters - 1) {
                event.consume();
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
        update_max_soluong();
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
        mahoadon = 0;
        check_thanhtoan = false;
        preference.putBoolean("check_thanhtoan", false);
        preference.putInt("mahoadon", 0);
        viewListTable();
        if (FXML_ClothesStoreController.rootP.getChildren().size() == 2) {
            FXML_ClothesStoreController.rootP.getChildren().remove(1);
        }

    }

    private void laphoadon_click() {
        btnThem.setDisable(false);
        btnXoa.setDisable(false);
        btnThanhToan.setDisable(false);
        btnLapHoaDon.setText("Hủy hóa đơn");
        checkLaphoadon = false;
        HoaDon hoadon = new HoaDon();
        hoadon.setManhanvien(new SimpleIntegerProperty(FXML_DangNhapController.MaNhanVien));
        if (hoadon.insert()) {
            mahoadon = hoadon.getMaHD();
            preference.putInt("mahoadon", mahoadon);

        } else {
            TrayNotification tray = new TrayNotification("Thông báo",
                    "Lập hóa đơn thất bại", NotificationType.ERROR);
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndDismiss(Duration.seconds(1.5));
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
            if (check_thanhtoan) {
                update_sl_ctsp(false);
            }
            viewListTable();
            if (FXML_ClothesStoreController.rootP.getChildren().size() == 2) {
                FXML_ClothesStoreController.rootP.getChildren().remove(1);
            }
            tongtien = 0;
            mahoadon = 0;
            check_thanhtoan = false;
            preference.putBoolean("check_thanhtoan", false);
            preference.putInt("mahoadon", 0);
            TrayNotification tray = new TrayNotification("Thông báo",
                    "Hủy hóa đơn thành công", NotificationType.SUCCESS);
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndDismiss(Duration.seconds(1.5));

        } else {
            TrayNotification tray = new TrayNotification("Thông báo",
                    "Hủy hóa đơn thất bại", NotificationType.ERROR);
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndDismiss(Duration.seconds(1.5));
        }
    }
    
    private void btnthem_click() {

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
            TrayNotification tray = new TrayNotification("Thông báo",
                    "Bạn phải nhập số lượng", NotificationType.WARNING);
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndDismiss(Duration.seconds(1.5));
            return;
        }
        if (cthd.insert()) {
            viewListTable();
            HoaDon hd = new HoaDon();
            tongtien = hd.tongtien(mahoadon);
            txt_fi_tongtien.setText("" + FormatTien(tongtien));
            txt_fi_machitietsanpham.setText(txt_fi_machitietsanpham.getText());
            update_max_soluong();
        } else {
            TrayNotification tray = new TrayNotification("Thông báo",
                    "Thêm dữ liệu thất bại", NotificationType.ERROR);
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndDismiss(Duration.seconds(1.5));
        }
    }

    private void btnXoa_click() {
        ChiTietHoaDon cthd = new ChiTietHoaDon();
        cthd.setMachitiethoadon(new SimpleIntegerProperty(machitiethoadon));
        if (cthd.delete()) {
            viewListTable();
            HoaDon hd = new HoaDon();
            tongtien = hd.tongtien(mahoadon);
            txt_fi_tongtien.setText("" + FormatTien(tongtien));
            update_max_soluong();
        } else {
            TrayNotification tray = new TrayNotification("Thông báo",
                    "Xóa dữ liệu thất bại", NotificationType.ERROR);
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndDismiss(Duration.seconds(1.5));
        }
    }

    private void btnthanhtoan_click() {

        HoaDon hd = new HoaDon(new SimpleIntegerProperty(mahoadon), new SimpleIntegerProperty(tongtien));
        if (hd.checktongtien()) {
            TrayNotification tray = new TrayNotification("Thông báo",
                    "Bạn không thể thanh toán khi tổng tiền bằng 0", NotificationType.WARNING);
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndDismiss(Duration.seconds(1.5));
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
            check_thanhtoan = true;
            preference.putBoolean("check_thanhtoan", check_thanhtoan);
            TrayNotification tray = new TrayNotification("Thông báo",
                    "Thanh toán thành công", NotificationType.SUCCESS);
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndDismiss(Duration.seconds(1.5));
        } else {
            TrayNotification tray = new TrayNotification("Thông báo",
                    "Thanh toán thất bại", NotificationType.ERROR);
            tray.setAnimationType(AnimationType.POPUP);
            tray.showAndDismiss(Duration.seconds(1.5));
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

    private void update_max_soluong() {
        ObservableList listOb = table_view.getItems();
        ChiTietSanPham ctsp = new ChiTietSanPham(new SimpleStringProperty(txt_fi_machitietsanpham.getText()));
        int max = ctsp.getMAXSoluongCTSP();
        int tong = max;
        for (int i = 0; i < listOb.size(); i++) {
            Integer soluong = Integer.parseInt(listOb.get(i).toString().split(",")[3].substring(1));
            String mactsp = listOb.get(i).toString().split(",")[2].substring(1);
            if (mactsp.equals(txt_fi_machitietsanpham.getText()) && max > 0) {
                SpinnerValueFactory<Integer> valueFactory
                        = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, tong -= soluong);
                spin_soluong.setValueFactory(valueFactory);
            }
        }
        if (tong == max) {
            SpinnerValueFactory<Integer> valueFactory
                    = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, max);
            spin_soluong.setValueFactory(valueFactory);
        }
    }

    private String FormatTien(int soTien) {
        return String.format("%,8d%n", soTien).trim();
    }
}
