/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import clothesstore_model.ChiTietSanPham;
import clothesstore_model.MauSac;
import clothesstore_model.SanPham;
import clothesstore_model.Size;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author quochung
 */
public class FXML_ChiTietSanPhamController implements Initializable {

    @FXML
    private JFXButton btnThem;
    @FXML
    private JFXButton btnXoa;

    @FXML
    private JFXButton btn_addsize;
    @FXML
    private JFXButton btn_addmausac;
    @FXML
    private JFXButton btnHuy;
    @FXML
    private JFXButton btnDongy;
    @FXML
    private JFXButton btnBack;
    @FXML
    private JFXComboBox<Size> cmb_size;
    @FXML
    private JFXComboBox<String> cmb_gioitinh;
    @FXML
    private JFXComboBox<MauSac> cmb_mausac;
    @FXML
    private TableView<String> table_view;
    @FXML
    private Label lb_masanpham;
    @FXML
    private JFXTextField txt_fi_machitietsanpham;
    private String masanpham;
    private int flag = 0;

    public void setLbMasanpham_LoadTableView(String lb) {
        lb_masanpham.setText("Mã sản phẩm: " + lb);
        masanpham = lb.trim();
        viewListTable();
        table_view.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (table_view.getSelectionModel().getSelectedItem() != null) {
                    btnXoa.setDisable(false);

                    String mactsp = newValue.toString().split(",")[0].substring(1).trim();
                    txt_fi_machitietsanpham.setText(mactsp);
                    StringProperty temp
                            = new SimpleStringProperty(newValue.toString().split(",")[1].substring(1).trim());
                    Size size = new Size(temp);
                    cmb_size.getSelectionModel().select(size);
                    temp = new SimpleStringProperty(newValue.toString().split(",")[2].substring(1).trim());
                    StringProperty mamau
                            = new SimpleStringProperty(newValue.toString().split(",")[4].substring(1).trim());
                    IntegerProperty trangthai = new SimpleIntegerProperty(1);
                    MauSac mausac = new MauSac(mamau, temp/* tên màu */, trangthai);
                    cmb_mausac.getSelectionModel().select(mausac);
                    cmb_gioitinh.getSelectionModel().select(newValue.toString().split(",")[3].substring(1).trim());
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InitEvent();
        ContextMenuTable();
        btnHuy.setVisible(false);
        btnDongy.setVisible(false);
    }

    private void viewListTable() {
        ChiTietSanPham ctsp = new ChiTietSanPham();
        StringProperty masp = new SimpleStringProperty(masanpham);
        ctsp.setMasanpham(masp);
        if (!table_view.getColumns().isEmpty()) {
            table_view.getColumns().clear();
        }
        ctsp.LoadTable(table_view);
        table_view.getColumns().get(0).setText("Mã chi tiết sản phẩm");
        table_view.getColumns().get(1).setText("Tên size");
        table_view.getColumns().get(2).setText("Màu sắc");
        table_view.getColumns().get(3).setText("Giới tính");
        table_view.getColumns().get(4).setVisible(false);
        table_view.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    @FXML
    private void ClickEvent(ActionEvent evt) {
        JFXButton btn = (JFXButton) evt.getSource();
        if (btn == btn_addmausac) {
            Show_FXML_MauSac();
        } else if (btn == btn_addsize) {
            Show_FXML_Size();
        } else if (btn == btnThem) {
            flag = 1;
            txt_fi_machitietsanpham.setText("");
            table_view.getSelectionModel().clearSelection();
            btnXoa.setDisable(true);
            btnThem.setDisable(true);

            btnHuy.setVisible(true);
            btnDongy.setVisible(true);
            table_view.setDisable(true);
        } else if (btn == btnXoa) {
            Optional<ButtonType> action = ShowMessage
                    .showMessageBox(Alert.AlertType.CONFIRMATION, "Thông báo", null, "Bạn có chắc muốn xóa không?")
                    .showAndWait();
            if (action.get() == ButtonType.OK) {
                DeleteChiTietSanPham();
            }

        }
    }

    @FXML
    private void Dongy_huy_clickvent(ActionEvent event) throws IOException {
        JFXButton btn = (JFXButton) event.getSource();
        if (btn == btnDongy) {
            btnDongy_process();
        } else if (btn == btnHuy) {
            btnHuy_process();
        }
    }

    private void btnDongy_process() {
        if (flag == 1) {
            insertChiTietSanpham();
        }
    }

    private void btnHuy_process() {
        flag = 0;
        btnXoa.setDisable(false);

        btnThem.setDisable(false);
        btnHuy.setVisible(false);
        btnDongy.setVisible(false);
        btnXoa.setDisable(true);

        table_view.getSelectionModel().clearSelection();
        table_view.setDisable(false);
    }

    private void Show_FXML_MauSac() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/clothesstore_view/FXML_MauSac.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Màu sắc của sản phẩm");
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.setOnCloseRequest((WindowEvent event1) -> {
                cmb_mausac.getItems().clear();
                MauSac mausac = new MauSac();
                mausac.LoadCmB(cmb_mausac);
                cmb_mausac.getSelectionModel().selectFirst();
            });
            stage.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(FXML_HangHoaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void Show_FXML_Size() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/clothesstore_view/FXML_Size.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setResizable(false);
            stage.setTitle("Size của sản phẩm");
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.setOnCloseRequest((WindowEvent event1) -> {
                cmb_size.getItems().clear();
                Size size = new Size();
                size.LoadCmB(cmb_size);
                cmb_size.getSelectionModel().selectFirst();

            });
            stage.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(FXML_HangHoaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void BacktoSanPham() {
        try {
            AnchorPane hanghoa = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_HangHoa.fxml"));
            FXML_ClothesStoreController.rootP.getChildren().setAll(hanghoa);
            FXML_ClothesStoreController.rootP.setLeftAnchor(hanghoa, 0.0);
            FXML_ClothesStoreController.rootP.setRightAnchor(hanghoa, 0.0);
            FXML_ClothesStoreController.rootP.setTopAnchor(hanghoa, 0.0);
            FXML_ClothesStoreController.rootP.setBottomAnchor(hanghoa, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXML_ChiTietSanPhamController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void insertChiTietSanpham() {
        String _mamau = cmb_mausac.getValue().getMamau().get().split("]")[0].substring(0);
        String mactsp = this.masanpham
                + cmb_gioitinh.getSelectionModel().getSelectedItem()
                + cmb_size.getValue().getTensize().get()
                + _mamau;

        StringProperty machitietsanpham = new SimpleStringProperty(mactsp);
        StringProperty _masanpham = new SimpleStringProperty(this.masanpham);
        StringProperty tensize = cmb_size.getValue().getTensize();
        StringProperty mamau = new SimpleStringProperty(_mamau);
        String _gioitinh = cmb_gioitinh.getSelectionModel().getSelectedItem();

        int x = Integer.parseInt(_gioitinh);
        IntegerProperty gioitinh = new SimpleIntegerProperty(x);
        ChiTietSanPham chitietsanpham
                = new ChiTietSanPham(machitietsanpham, _masanpham, tensize, mamau, gioitinh);

        if (chitietsanpham.isEmpty()) {
            ShowMessage
                    .showMessageBox(Alert.AlertType.WARNING, "Thông báo", null, "Bạn phải điền đẩy đủ thông tin bắt buộc")
                    .showAndWait();
            return;
        }
        if (chitietsanpham.insert() == 1) {
            viewListTable();
            ShowMessage
                    .showMessageBox(Alert.AlertType.INFORMATION, "Thông báo", null, "Thêm dữ liệu thành công")
                    .showAndWait();
        } else if (chitietsanpham.insert() == 2) {
            btnHuy.setVisible(false);
            btnDongy.setVisible(false);
            table_view.setDisable(false);
            btnThem.setDisable(false);
            int n = table_view.getItems().size();
            for (int i = 0; i < n; i++) {
                Object obj = table_view.getColumns().get(0).getCellObservableValue(i).getValue().toString();
                if (obj.equals(mactsp)) {
                    table_view.getSelectionModel().select(i);
                    break;
                }
            }
            ShowMessage
                    .showMessageBox(Alert.AlertType.WARNING, "Thông báo", null, "Chi tiết sản phẩm đã tồn tại!")
                    .showAndWait();
        } else {
            ShowMessage
                    .showMessageBox(Alert.AlertType.INFORMATION, "Thông báo", null, "Thêm dữ liệu thất bại")
                    .showAndWait();
        }
    }

    private void DeleteChiTietSanPham() {
        StringProperty mactsp = new SimpleStringProperty(txt_fi_machitietsanpham.getText());
        ChiTietSanPham ctsp = new ChiTietSanPham(mactsp);
        if (ctsp.delete()) {
            viewListTable();
            ShowMessage
                    .showMessageBox(Alert.AlertType.INFORMATION, "Thông báo", null, "Xóa dữ liệu thành công")
                    .showAndWait();

        } else {
            ShowMessage
                    .showMessageBox(Alert.AlertType.ERROR, "Thông báo", null, "Xóa dữ liệu thất bại")
                    .showAndWait();
        }
    }


    private void ContextMenuTable() {
        ContextMenu context = new ContextMenu();

        MenuItem itemXoa = new MenuItem("Xóa");
        itemXoa.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                Optional<ButtonType> action = ShowMessage
                        .showMessageBox(Alert.AlertType.CONFIRMATION, "Thông báo", null, "Bạn có chắc muốn xóa không?")
                        .showAndWait();
                if (action.get() == ButtonType.OK) {
                    DeleteChiTietSanPham();
                }

            }
        });
        context.getItems().addAll(itemXoa);
        table_view.setContextMenu(context);
    }

    private void InitEvent() {
        btnBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                BacktoSanPham();
            }
        });
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("1");
        list.add("0");
        cmb_gioitinh.setItems(list);
        cmb_gioitinh.setConverter(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                if (object.equals("1")) {
                    return "Nam";
                } else {
                    return "Nữ";
                }
            }

            @Override
            public String fromString(String string) {
                return null;
            }

        });
        cmb_gioitinh.getSelectionModel().selectFirst();
        cmb_mausac.setConverter(new StringConverter<MauSac>() {
            @Override
            public String toString(MauSac object) {
                return object.getTenmau().get();
            }

            @Override
            public MauSac fromString(String string) {
                return null;
            }
        });
        cmb_size.setConverter(new StringConverter<Size>() {
            @Override
            public Size fromString(String string) {
                return null;
            }

            @Override
            public String toString(Size object) {
                return object.getTensize().get();
            }
        });

        MauSac mausac = new MauSac();
        mausac.LoadCmB(cmb_mausac);
        cmb_mausac.getSelectionModel().selectFirst();
        Size size = new Size();
        size.LoadCmB(cmb_size);
        cmb_size.getSelectionModel().selectFirst();

    }
}
