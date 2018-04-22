/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;


import clothesstore_model.NhaSanXuat;
import clothesstore_model.NhomHang;
import clothesstore_model.SanPham;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author dieunguyen
 */
public class FXML_HangHoaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton btn_add_nhasanxuat;
    @FXML
    private JFXButton btnThem;
    @FXML
    private JFXButton btnXoa;
    @FXML
    private JFXButton btnSua;
    @FXML
    private JFXButton btnHuy;
    @FXML
    private JFXButton btnDongy;
    @FXML
    private JFXButton btn_add_nhomhang;
    @FXML
    private JFXComboBox<String> cmb_nhasanxuat;
    @FXML
    private JFXComboBox<String> cmb_nhomhang;
    @FXML
    private JFXButton btnExportExcel;
    @FXML
    private JFXTextField txt_fi_tensanpham;
    @FXML
    private JFXTextField txt_fi_masanpham;
    @FXML
    private JFXTextArea txt_area_ghichu;
    @FXML
    private JFXTreeTableView<SanPham> tree_table_vi;

    private JFXTreeTableColumn<SanPham, String> col_masanpham = new JFXTreeTableColumn<>("Mã sản phẩm");
    private JFXTreeTableColumn<SanPham, String> col_tensanpham = new JFXTreeTableColumn<>("Tên sản phẩm");
    private JFXTreeTableColumn<SanPham, String> col_nhasanxuat = new JFXTreeTableColumn<>("Tên nhà sản xuất");
    private JFXTreeTableColumn<SanPham, String> col_nhomhang = new JFXTreeTableColumn<>("Tên nhóm hàng");
    private JFXTreeTableColumn<SanPham, String> col_ghichu = new JFXTreeTableColumn<>("Ghi chú");
    private int flag = 0;
    static List<SanPham> listspvuanhap = new ArrayList<>();

    @FXML
    private void ClickEvent(ActionEvent event) throws IOException {
        JFXButton btn = (JFXButton) event.getSource();
        if (btn == btn_add_nhasanxuat) {
            ShowFXML_NhaSanXuat();
            tree_table_vi.getSelectionModel().clearSelection();
        } else if (btn == btn_add_nhomhang) {
            ShowFXML_NhomHang();
            tree_table_vi.getSelectionModel().clearSelection();
        } else if (btn == btnThem) {
            flag = 1;
            txt_fi_masanpham.setText("");
            txt_fi_tensanpham.setText("");
            txt_area_ghichu.setText("");
            btnXoa.setDisable(true);
            btnThem.setDisable(true);
            btnSua.setDisable(true);
            btnHuy.setVisible(true);
            btnDongy.setVisible(true);
            tree_table_vi.setDisable(true);
        } else if (btn == btnXoa) {
            Optional<ButtonType> action = ShowMessage
                    .showMessageBox(Alert.AlertType.CONFIRMATION, "Thông báo", null, "Bạn có chắc muốn xóa không?")
                    .showAndWait();
            if (action.get() == ButtonType.OK) {
                DeleteSanPham();
            }

        } else if (btn == btnSua) {
            flag = 2;
            btnThem.setDisable(true);
            btnXoa.setDisable(true);
            tree_table_vi.setDisable(true);
            btnThem.setDisable(true);
            btnSua.setDisable(true);
            btnHuy.setVisible(true);
            btnDongy.setVisible(true);

        } else if (btn == btnExportExcel) {
            exportExcel();

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

    @FXML
    private void OnMouseClick_clearSelectTable(MouseEvent evt) {
        // tree_table_vi.getSelectionModel().clearSelection();
    }

    private void btnDongy_process() {
        if (flag == 1) {
            insertSanpham();

        } else if (flag == 2) {
            UpdateSanPham();
        }
    }

    private void btnHuy_process() {
        flag = 0;
        btnXoa.setDisable(false);
        btnSua.setDisable(false);
        btnThem.setDisable(false);
        btnHuy.setVisible(false);
        btnDongy.setVisible(false);
        btnXoa.setDisable(true);
        btnSua.setDisable(true);
        tree_table_vi.getSelectionModel().clearSelection();
        tree_table_vi.setDisable(false);
    }

    private void exportExcel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel file 2003(.xls)", ".xls"),
                new FileChooser.ExtensionFilter("Excel file 2007(.xlsx)", ".xlsx")
        );
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {

            try {
                SanPham sp = new SanPham();
                XSSFWorkbook wb = sp.exportExcel();
                String path_name_File = file.getPath();
                FileOutputStream output = new FileOutputStream(path_name_File
                        + fileChooser.getSelectedExtensionFilter().getExtensions().get(0));
                wb.write(output);
                output.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FXML_HangHoaController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FXML_HangHoaController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void ShowFXML_NhaSanXuat() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/clothesstore_view/FXML_Nhasanxuat.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setResizable(false);
            stage.setTitle("Nhà sản xuất");
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.setOnCloseRequest((WindowEvent event1) -> {
                cmb_nhasanxuat.getItems().clear();
                NhaSanXuat tennsx = new NhaSanXuat();
                tennsx.getNSXList(cmb_nhasanxuat);
                cmb_nhasanxuat.getSelectionModel().selectFirst();

            });
            stage.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(FXML_HangHoaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void ShowFXML_NhomHang() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/clothesstore_view/FXML_NhomHang.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setResizable(false);
            stage.setTitle("Nhóm hàng");
            stage.setScene(new Scene(root1));
            stage.setOnCloseRequest((WindowEvent event1) -> {
                cmb_nhomhang.getItems().clear();
                NhomHang tennsx = new NhomHang();
                tennsx.getNHList(cmb_nhomhang);

                cmb_nhomhang.getSelectionModel().selectFirst();
            });
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXML_HangHoaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void ShowFXML_ChiTietSanPham(String masp) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/clothesstore_view/FXML_ChiTietSanPham.fxml"));
            AnchorPane chitietsanpham = fxmlLoader.load();
            FXML_ClothesStoreController.rootP.getChildren().setAll(chitietsanpham);
            FXML_ChiTietSanPhamController ctsp = fxmlLoader.getController();           
            ctsp.setLbMasanpham_LoadTableView(masp);

            KeyCombination Alt_leftArrow = new KeyCodeCombination(KeyCode.LEFT, KeyCodeCombination.ALT_DOWN);
            chitietsanpham.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (Alt_leftArrow.match(event)) {
                        ctsp.BacktoSanPham();
                    }
                }
            });
            chitietsanpham.requestFocus();
            FXML_ClothesStoreController.rootP.setLeftAnchor(chitietsanpham, 0.0);
            FXML_ClothesStoreController.rootP.setRightAnchor(chitietsanpham, 0.0);
            FXML_ClothesStoreController.rootP.setTopAnchor(chitietsanpham, 0.0);
            FXML_ClothesStoreController.rootP.setBottomAnchor(chitietsanpham, 0.0);
        } catch (IOException ex) {
            Logger.getLogger(FXML_HangHoaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void insertSanpham() {
        StringProperty masp = new SimpleStringProperty(txt_fi_masanpham.getText());
        StringProperty tensp = new SimpleStringProperty(txt_fi_tensanpham.getText());
        StringProperty tennsx = new SimpleStringProperty(cmb_nhasanxuat.getValue());
        StringProperty tennhomhang = new SimpleStringProperty(cmb_nhomhang.getValue());
        StringProperty ghichu = new SimpleStringProperty(txt_area_ghichu.getText());

        SanPham sanpham = new SanPham(masp, tensp, tennsx, tennhomhang, ghichu);

        if (sanpham.isEmpty()) {
            ShowMessage
                    .showMessageBox(Alert.AlertType.WARNING, "Thông báo", null, "Bạn phải điền đẩy đủ thông tin bắt buộc")
                    .showAndWait();
            return;
        }
        if (sanpham.insert()) {
            ShowFXML_ChiTietSanPham(sanpham.getMasanpham().get());
            ShowMessage
                    .showMessageBox(Alert.AlertType.INFORMATION, "Thông báo", null, "Thêm dữ liệu thành công")
                    .showAndWait();
        } else {
            ShowMessage
                    .showMessageBox(Alert.AlertType.ERROR, "Thông báo", null, "Thêm dữ liệu thất bại")
                    .showAndWait();
        }
    }

    private void DeleteSanPham() {
        StringProperty masp = new SimpleStringProperty(txt_fi_masanpham.getText());
        SanPham sanpham = new SanPham(masp);
        if (sanpham.delete()==1) {
            viewListTable();
            ShowMessage
                    .showMessageBox(Alert.AlertType.INFORMATION, "Thông báo", null, "Xóa dữ liệu thành công")
                    .showAndWait();

        } else if(sanpham.delete()==2){
            ShowMessage
                    .showMessageBox(Alert.AlertType.WARNING, "Thông báo", null, "Sản phẩm đã nhập vào kho.\n Bạn không thể xóa!")
                    .showAndWait();
        }
        else 
             ShowMessage
                    .showMessageBox(Alert.AlertType.ERROR, "Thông báo", null, "Xóa dữ liệu thất bại")
                    .showAndWait();
    }

    private void UpdateSanPham() {
        StringProperty masp = new SimpleStringProperty(txt_fi_masanpham.getText());
        StringProperty tensp = new SimpleStringProperty(txt_fi_tensanpham.getText());
        StringProperty tennsx = new SimpleStringProperty(cmb_nhasanxuat.getValue());
        StringProperty tennhomhang = new SimpleStringProperty(cmb_nhomhang.getValue());
        StringProperty ghichu = new SimpleStringProperty(txt_area_ghichu.getText());
        SanPham sanpham = new SanPham(masp, tensp, tennsx, tennhomhang, ghichu);

        if (sanpham.update()) {
            viewListTable();
            ShowMessage
                    .showMessageBox(Alert.AlertType.INFORMATION, "Thông báo", null, "Cập nhật dữ liệu thành công")
                    .showAndWait();

        } else {
            ShowMessage
                    .showMessageBox(Alert.AlertType.ERROR, "Thông báo", null, "Cập nhật dữ liệu thất bại")
                    .showAndWait();
        }
    }

    private void viewListTable() {
        SanPham sp = new SanPham();
        ObservableList<SanPham> splist = sp.getSPList();
        if (splist.isEmpty()) {
            return;
        }
        TreeItem<SanPham> root = new RecursiveTreeItem<>(splist, RecursiveTreeObject::getChildren);
        tree_table_vi.setRoot(root);
        tree_table_vi.setShowRoot(false);

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
                    DeleteSanPham();
                }

            }
        });
        MenuItem itemThemchitietsp = new MenuItem("Thêm chi tiết sản phẩm");
        itemThemchitietsp.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                ShowFXML_ChiTietSanPham(txt_fi_masanpham.getText());
            }
        });
        context.getItems().addAll(itemXoa, itemThemchitietsp);
        tree_table_vi.setContextMenu(context);
    }

    private void loadingColfromDB() {
        col_masanpham.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SanPham, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SanPham, String> param) {
                return param.getValue().getValue().getMasanpham();
            }
        });
        col_tensanpham.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SanPham, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SanPham, String> param) {
                return param.getValue().getValue().getTensanpham();
            }
        });
        col_nhasanxuat.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SanPham, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SanPham, String> param) {
                return param.getValue().getValue().getTennhasanxuat();
            }
        });
        col_nhomhang.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SanPham, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SanPham, String> param) {
                return param.getValue().getValue().getTennhomhang();
            }
        });
        col_ghichu.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SanPham, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SanPham, String> param) {
                return param.getValue().getValue().getGhichu();
            }
        });
        tree_table_vi.getColumns().setAll(col_masanpham, col_tensanpham, col_nhasanxuat, col_nhomhang, col_ghichu);
        tree_table_vi.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
        tree_table_vi.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (tree_table_vi.getSelectionModel().getSelectedItem() != null) {
                    btnXoa.setDisable(false);
                    btnSua.setDisable(false);

                    TreeItem<SanPham> sanphamItem = tree_table_vi.getSelectionModel().getSelectedItem();
                    txt_fi_masanpham.setText("" + sanphamItem.getValue().getMasanpham().get());
                    txt_fi_tensanpham.setText("" + sanphamItem.getValue().getTensanpham().get());
                    txt_area_ghichu.setText("" + sanphamItem.getValue().getGhichu().get());
                    cmb_nhasanxuat.getSelectionModel().select(sanphamItem.getValue().getTennhasanxuat().get());
                    cmb_nhomhang.getSelectionModel().select(sanphamItem.getValue().getTennhomhang().get());

                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        NhaSanXuat tennsx = new NhaSanXuat();
        tennsx.getNSXList(cmb_nhasanxuat);
        NhomHang nhomhhang = new NhomHang();
        nhomhhang.getNHList(cmb_nhomhang);
        loadingColfromDB();
        viewListTable();
        ContextMenuTable();
        btnHuy.setVisible(false);
        btnDongy.setVisible(false);
        cmb_nhasanxuat.getSelectionModel().selectFirst();
        cmb_nhomhang.getSelectionModel().selectFirst();
    }

}
