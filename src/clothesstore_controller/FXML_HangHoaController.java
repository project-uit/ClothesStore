/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import clothesstore_model.nhasanxuat_model;
import clothesstore_model.nhomhang_model;
import clothesstore_model.sanpham_model;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
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
    JFXButton btnHuy;
    @FXML
    JFXButton btnDongy;
    @FXML
    private JFXButton btn_add_nhomhang;
    @FXML
    private JFXComboBox<String> cmb_nhasanxuat;
    @FXML
    private JFXComboBox<String> cmb_nhomhang;
    @FXML
    JFXButton btnExportExcel;
    @FXML
    private JFXTextField txt_fi_tensanpham;
    @FXML
    private JFXTextField txt_fi_masanpham;
    @FXML
    private JFXTextField txt_fi_ghichu;
    @FXML
    private JFXTreeTableView<sanpham_model> tree_table_vi;

    private JFXTreeTableColumn<sanpham_model, String> col_masanpham = new JFXTreeTableColumn<>("Mã sản phẩm");
    private JFXTreeTableColumn<sanpham_model, String> col_tensanpham = new JFXTreeTableColumn<>("Tên sản phẩm");
    private JFXTreeTableColumn<sanpham_model, String> col_nhasanxuat = new JFXTreeTableColumn<>("Tên nhà sản xuất");
    private JFXTreeTableColumn<sanpham_model, String> col_nhomhang = new JFXTreeTableColumn<>("Tên nhóm hàng");
    private JFXTreeTableColumn<sanpham_model, String> col_ghichu = new JFXTreeTableColumn<>("Ghi chú");
    private int flag = 0;

    @FXML
    private void ClickEvent(ActionEvent event) throws IOException {
        JFXButton btn = (JFXButton) event.getSource();
        if (btn == btn_add_nhasanxuat) {
            ShowFXML_NhaSanXuat();

        } else if (btn == btn_add_nhomhang) {

            ShowFXML_NhomHang();
        } else if (btn == btnThem) {
            flag = 1;
            btnXoa.setDisable(true);
            btnThem.setDisable(true);
            btnSua.setDisable(true);
            btnHuy.setVisible(true);
            btnDongy.setVisible(true);
            tree_table_vi.setDisable(true);
        } else if (btn == btnXoa) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có chắc muốn xóa không?");

            Optional<ButtonType> action = alert.showAndWait();
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
    private void OnMouseClick_txtfield(MouseEvent evt) {
         tree_table_vi.getSelectionModel().clearSelection();
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
                sanpham_model sp = new sanpham_model();
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
                nhasanxuat_model tennsx = new nhasanxuat_model();
                tennsx.getNSXList(cmb_nhasanxuat);
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
                nhomhang_model tennsx = new nhomhang_model();
                tennsx.getNHList(cmb_nhomhang);

            });
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXML_HangHoaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void insertSanpham() {
        StringProperty masp = new SimpleStringProperty(txt_fi_masanpham.getText());
        StringProperty tensp = new SimpleStringProperty(txt_fi_tensanpham.getText());
        StringProperty tennsx = new SimpleStringProperty(cmb_nhasanxuat.getValue());
        StringProperty tennhomhang = new SimpleStringProperty(cmb_nhomhang.getValue());
        StringProperty ghichu = new SimpleStringProperty(txt_fi_ghichu.getText());

        sanpham_model sanpham = new sanpham_model(masp, tensp, tennsx, tennhomhang, ghichu);
        if (sanpham.insert()) {
            viewListTable();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Thêm dữ liệu thành công");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Thêm dữ liệu thất bại");
            alert.showAndWait();
        }
    }

    private void DeleteSanPham() {
        StringProperty masp = new SimpleStringProperty(txt_fi_masanpham.getText());
        sanpham_model sanpham = new sanpham_model(masp);
        if (sanpham.delete()) {
            viewListTable();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Xóa dữ liệu thành công");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Xóa dữ liệu thất bại");
            alert.showAndWait();
        }
    }

    private void UpdateSanPham() {
        StringProperty masp = new SimpleStringProperty(txt_fi_masanpham.getText());
        StringProperty tensp = new SimpleStringProperty(txt_fi_tensanpham.getText());
        StringProperty tennsx = new SimpleStringProperty(cmb_nhasanxuat.getValue());
        StringProperty tennhomhang = new SimpleStringProperty(cmb_nhomhang.getValue());
        StringProperty ghichu = new SimpleStringProperty(txt_fi_ghichu.getText());
        sanpham_model sanpham = new sanpham_model(masp, tensp, tennsx, tennhomhang, ghichu);
        if (sanpham.update()) {
            viewListTable();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Cập nhật dữ liệu thành công");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Cập nhật dữ liệu thất bại");
            alert.showAndWait();
        }
    }

    private void viewListTable() {
        sanpham_model sp = new sanpham_model();
        ObservableList<sanpham_model> splist = sp.getSPList();
        if (splist.isEmpty()) {
            return;
        }
        TreeItem<sanpham_model> root = new RecursiveTreeItem<>(splist, RecursiveTreeObject::getChildren);
        tree_table_vi.setRoot(root);
        tree_table_vi.setShowRoot(false);
        ContextMenu context = new ContextMenu();
        MenuItem item1 = new MenuItem("Xóa");
        item1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có chắc muốn xóa không?");

                Optional<ButtonType> action = alert.showAndWait();
                if (action.get() == ButtonType.OK) {
                    DeleteSanPham();
                }

            }
        });
        MenuItem item2 = new MenuItem("Menu Item 2");
        item2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Menu 2");
            }
        });
        context.getItems().addAll(item1, item2);
        tree_table_vi.setContextMenu(context);
    }

    private void loadingColfromDB() {
        col_masanpham.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<sanpham_model, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<sanpham_model, String> param) {
                return param.getValue().getValue().getMasanpham();
            }
        });
        col_tensanpham.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<sanpham_model, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<sanpham_model, String> param) {
                return param.getValue().getValue().getTensanpham();
            }
        });
        col_nhasanxuat.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<sanpham_model, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<sanpham_model, String> param) {
                return param.getValue().getValue().getTennhasanxuat();
            }
        });
        col_nhomhang.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<sanpham_model, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<sanpham_model, String> param) {
                return param.getValue().getValue().getTennhomhang();
            }
        });
        col_ghichu.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<sanpham_model, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<sanpham_model, String> param) {
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
                    TreeItem<sanpham_model> sanphamItem = tree_table_vi.getSelectionModel().getSelectedItem();
                    txt_fi_masanpham.setText("" + sanphamItem.getValue().getMasanpham().get());
                    txt_fi_tensanpham.setText("" + sanphamItem.getValue().getTensanpham().get());
                    txt_fi_ghichu.setText("" + sanphamItem.getValue().getGhichu().get());
                    cmb_nhasanxuat.getSelectionModel().select(sanphamItem.getValue().getTennhasanxuat().get());
                    cmb_nhomhang.getSelectionModel().select(sanphamItem.getValue().getTennhomhang().get());

                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        nhasanxuat_model tennsx = new nhasanxuat_model();
        tennsx.getNSXList(cmb_nhasanxuat);
        nhomhang_model nhomhhang = new nhomhang_model();
        nhomhhang.getNHList(cmb_nhomhang);
        loadingColfromDB();
        viewListTable();
        btnHuy.setVisible(false);
        btnDongy.setVisible(false);

    }

}
