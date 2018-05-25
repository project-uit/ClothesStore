/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import clothesstore_model.MauSac;
import clothesstore_model.NhaSanXuat;
import clothesstore_model.NhomHang;
import clothesstore_model.SanPham;
import clothesstore_model.Size;
import clothesstore_model.TraCuu;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author quochung
 */
public class FXML_TraCuuController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<String> table_view;
    @FXML
    private JFXTextField txt_fi_tensanpham, txt_fi_min_soluong, txt_fi_max_soluong,
            txt_fi_max_giaban, txt_fi_min_giaban, txt_fi_masanpham;
    @FXML
    private JFXComboBox<String> cmb_mausac, cmb_nhasanxuat, cmb_nhomhang, cmb_size;
    @FXML
    private JFXComboBox<Integer> cmb_gioitinh;
    @FXML
    private JFXButton btn_search, btn_refresh;
    @FXML
    private TitledPane titledPane;
    private String query;
    private String gioitinh = "and gioitinh=",
            tensanpham = "and tensanpham like ",
            tenmau = "and tenmau=",
            tennhomhang = "and tennhomhang=",
            tennhasanxuat = "and tennhasanxuat=",
            tensize = "and tensize=",
            soluong_min = "0",
            soluong_max = "and ctsp.soluong<=",
            giaban_min = "and sp.giaban>=",
            giaban_max = "and sp.giaban<=",
            masanpham = "and sp.masanpham=";
    private String masp;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        titledPane.setExpanded(false);
        //titledPane.widthProperty().addListener( ( observable, oldValue, newValue ) -> table_view.setLayoutX(newValue.doubleValue() ) );
        titledPane.expandedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                table_view.setLayoutY(table_view.getLayoutY() + titledPane.getHeight() - 10);

            } else {
                table_view.setLayoutY(table_view.getLayoutY() - titledPane.getHeight() + 10);
            }
        });

        InitCmb();
        InitTable();
        TraCuu tracuu = new TraCuu();
        query = tracuu.getquery();
        viewListTable();
        table_view.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                if (table_view.getSelectionModel().getSelectedItem() != null) {
                    masp = newValue.toString().split(",")[0].substring(1).trim();
                }
            }
        });
        table_view.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        btn_search.setOnAction(e -> {
            btn_search_click();
        });
        btn_refresh.setOnAction(e -> {
            TraCuu tra_cuu = new TraCuu();
            query = tra_cuu.getquery();
            viewListTable();
            txt_fi_min_soluong.clear();
            txt_fi_max_soluong.clear();
            txt_fi_min_giaban.clear();
            txt_fi_max_giaban.clear();
            txt_fi_tensanpham.clear();
            txt_fi_masanpham.clear();
            cmb_mausac.getSelectionModel().select(0);
            cmb_nhasanxuat.getSelectionModel().select(0);
            cmb_nhomhang.getSelectionModel().select(0);
            cmb_size.getSelectionModel().select(0);
            cmb_gioitinh.getSelectionModel().select(0);
        });
        initTextField();
        ContextMenu();
//        Pagination pagination = new Pagination
//        ((tracuu.filterList_sanpham(query.trim()).size() / rowsPerPage + 1), 0);
//        pagination.setPageFactory(this::createPage);

    }
//    private final static int dataSize = 10_023;
//    private final static int rowsPerPage = 1000;
//
//    private Node createPage(int pageIndex) {
//
//        TraCuu tracuu = new TraCuu();
//
//        int fromIndex = pageIndex * rowsPerPage;
//        int toIndex = Math.min(fromIndex + rowsPerPage, tracuu.filterList_sanpham(query.trim()).size());
//        table_view.setItems(FXCollections.observableArrayList(tracuu.filterList_sanpham(query.trim()).subList(fromIndex, toIndex)));
//        return new BorderPane(table_view);
//    }

    private void ContextMenu() {
        ContextMenu context = new ContextMenu();
        MenuItem itemGiaBan = new MenuItem("Sửa giá bán");
        itemGiaBan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TextInputDialog dialog = new TextInputDialog("0");
                dialog.setTitle("Sửa giá bán");
                dialog.setHeaderText("Mã sản phẩm " + masp);
                dialog.setContentText("Giá bán: ");
                Optional<String> result = dialog.showAndWait();
                result.ifPresent(num -> {
                    try {
                        SanPham sp = new SanPham();
                        sp.setMasanpham(new SimpleStringProperty(masp));
                        sp.setGiaban(new SimpleIntegerProperty(Integer.valueOf(num)));
                        if (sp.updateGiaban()) {
                            viewListTable();
                        }
                    } catch (NumberFormatException ex) {

                    }

                });
            }
        });
        context.getItems().addAll(itemGiaBan);
        table_view.setContextMenu(context);
    }

    private void initTextField() {
        OnlyNumberInTextField(txt_fi_min_soluong);
        OnlyNumberInTextField(txt_fi_max_soluong);
        OnlyNumberInTextField(txt_fi_min_giaban);
        OnlyNumberInTextField(txt_fi_max_giaban);
        List<String> arr_tensp = new SanPham().getlist_tensp();
        TextFields.bindAutoCompletion(txt_fi_tensanpham, arr_tensp);
        List<String> arr_masp = new SanPham().getlist_masp();
        TextFields.bindAutoCompletion(txt_fi_masanpham, arr_masp);
    }

    private void OnlyNumberInTextField(JFXTextField textField) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    private void btn_search_click() {
        //truy vấn theo tên sản phẩm
        if (query.contains(tensanpham)) {
            query = query.replace(tensanpham, "");
        }
        tensanpham = "and tensanpham like ";
        if (!txt_fi_tensanpham.getText().isEmpty()) {
            tensanpham += "'" + txt_fi_tensanpham.getText().trim() + "%' ";
            query += tensanpham;
        }
        //truy vấn theo mã sản phẩm
        if (query.contains(masanpham)) {
            query = query.replace(masanpham, "");
        }
        masanpham = "and sp.masanpham= ";
        if (!txt_fi_masanpham.getText().isEmpty()) {
            masanpham += "'" + txt_fi_masanpham.getText().trim() + "' ";
            query += masanpham;
        }
        //truy vấn theo số lượng
        // số lượng min        
        if (txt_fi_min_soluong.getText().isEmpty()) {

            query = query.replaceFirst(soluong_min, "0");
        } else {
            query = query.replaceFirst(soluong_min, txt_fi_min_soluong.getText().trim());
            soluong_min = txt_fi_min_soluong.getText().trim();
        }
        // số lượng max        
        if (query.contains(soluong_max)) {
            query = query.replace(soluong_max, "");
        }
        soluong_max = "and ctsp.soluong<=";
        if (!txt_fi_max_soluong.getText().isEmpty()) {
            soluong_max += txt_fi_max_soluong.getText().trim() + " ";
            query += soluong_max;
        }

        //giá bán min
        if (query.contains(giaban_min)) {
            query = query.replace(giaban_min, "");
        }
        giaban_min = "and sp.giaban>=";
        if (!txt_fi_min_giaban.getText().isEmpty()) {
            giaban_min += txt_fi_min_giaban.getText().trim() + " ";
            query += giaban_min;
        }

        //giá bán max
        if (query.contains(giaban_max)) {
            query = query.replace(giaban_max, "");
        }
        giaban_max = "and sp.giaban<=";
        if (!txt_fi_max_giaban.getText().isEmpty()) {
            giaban_max += txt_fi_max_giaban.getText().trim() + " ";
            query += giaban_max;
        }
        System.out.println(query);
        viewListTable();
    }

    private void viewListTable() {
        TraCuu tracuu = new TraCuu();
        table_view.setItems(tracuu.filterList_sanpham(query.trim()));
    }

    private void InitCmb() {
        MauSac ms = new MauSac();
        cmb_mausac.getItems().add("Tất cả");
        ms.LoadCmB(cmb_mausac);
        cmb_mausac.getSelectionModel().selectFirst();
        //truy vấn theo màu sắc
        cmb_mausac.setOnAction(e -> {
            if (query.contains(tenmau)) {
                query = query.replace(tenmau, "");
            }
            tenmau = tenmau.trim();
            for (int i = 0; i < cmb_mausac.getItems().size(); i++) {
                String temp = "'" + cmb_mausac.getItems().get(i) + "'";
                if (tenmau.contains(temp)) {
                    tenmau = tenmau.replace(temp, "");
                    break;
                }
            }
            tenmau += "'" + cmb_mausac.getValue().toString() + "' ";
            query += tenmau;
            if (cmb_mausac.getSelectionModel().getSelectedIndex() == 0) {

                query = query.replace(tenmau, "");
            }

        });
        //add list giới tính
        ObservableList<Integer> list = FXCollections.observableArrayList();
        list.add(3);//tất cả
        list.add(0);//nữ
        list.add(1);//nam
        list.add(2);//unisex
        cmb_gioitinh.setItems(list);
        cmb_gioitinh.getSelectionModel().selectFirst();
        cmb_gioitinh.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                if (object.equals(1)) {
                    return "Nam";
                } else if (object.equals(0)) {
                    return "Nữ";
                } else if (object.equals(2)) {
                    return "Unisex";
                }
                return "Tất cả";
            }

            @Override
            public Integer fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

        });
        //truy vấn theo giới tính
        cmb_gioitinh.setOnAction(e -> {
            if (query.contains(gioitinh)) {
                query = query.replace(gioitinh, "");
            }
            gioitinh = gioitinh.trim();
            if (gioitinh.length() > 13) {

                StringBuilder sb = new StringBuilder(gioitinh);
                gioitinh = sb.deleteCharAt(13).toString();

            }
            gioitinh += cmb_gioitinh.getValue().toString() + " ";
            query += gioitinh;
            if (cmb_gioitinh.getSelectionModel().getSelectedItem() == 3) {

                query = query.replace(gioitinh, "");
            }

        });
        NhaSanXuat nsx = new NhaSanXuat();
        cmb_nhasanxuat.getItems().add("Tất cả");
        nsx.getNSXList(cmb_nhasanxuat);
        cmb_nhasanxuat.getSelectionModel().selectFirst();
        //truy vấn theo nhà sản xuất
        cmb_nhasanxuat.setOnAction(e -> {
            if (query.contains(tennhasanxuat)) {
                query = query.replace(tennhasanxuat, "");
            }
            tennhasanxuat = tennhasanxuat.trim();
            for (int i = 0; i < cmb_nhasanxuat.getItems().size(); i++) {
                String temp = "'" + cmb_nhasanxuat.getItems().get(i) + "'";
                if (tennhasanxuat.contains(temp)) {
                    tennhasanxuat = tennhasanxuat.replace(temp, "");
                    break;
                }
            }
            tennhasanxuat += "'" + cmb_nhasanxuat.getValue().toString() + "' ";
            query += tennhasanxuat;
            if (cmb_nhasanxuat.getSelectionModel().getSelectedIndex() == 0) {

                query = query.replace(tennhasanxuat, "");
            }

        });
        NhomHang nhomhang = new NhomHang();
        cmb_nhomhang.getItems().add("Tất cả");
        nhomhang.getNHList(cmb_nhomhang);
        cmb_nhomhang.getSelectionModel().selectFirst();
        //truy vấn theo nhóm hàng
        cmb_nhomhang.setOnAction(e -> {
            if (query.contains(tennhomhang)) {
                query = query.replace(tennhomhang, "");
            }
            tennhomhang = tennhomhang.trim();
            for (int i = 0; i < cmb_nhomhang.getItems().size(); i++) {
                String temp = "'" + cmb_nhomhang.getItems().get(i) + "'";
                if (tennhomhang.contains(temp)) {
                    tennhomhang = tennhomhang.replace(temp, "");
                    break;
                }
            }
            tennhomhang += "'" + cmb_nhomhang.getValue().toString() + "' ";
            query += tennhomhang;
            if (cmb_nhomhang.getSelectionModel().getSelectedIndex() == 0) {

                query = query.replace(tennhomhang, "");
            }

        });
        Size sz = new Size();
        cmb_size.getItems().add("Tất cả");
        sz.loadcmb(cmb_size);
        cmb_size.getSelectionModel().selectFirst();
        //truy vấn theo size
        cmb_size.setOnAction(e -> {
            if (query.contains(tensize)) {
                query = query.replace(tensize, "");
            }
            tensize = tensize.trim();
            for (int i = 0; i < cmb_size.getItems().size(); i++) {
                String temp = "'" + cmb_size.getItems().get(i) + "'";
                if (tensize.contains(temp)) {
                    tensize = tensize.replace(temp, "");
                    break;
                }
            }
            tensize += "'" + cmb_size.getValue().toString() + "' ";
            query += tensize;
            if (cmb_size.getSelectionModel().getSelectedIndex() == 0) {

                query = query.replace(tensize, "");
            }

        });
    }

    private String FormatTien(int soTien) {
        return String.format("%,8d%n", soTien).trim();
    }

    private void InitTable() {
        for (int i = 0; i < 9; i++) {
            final int j = i;
            TableColumn col = new TableColumn("" + i);
            if (i == 5) {
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        if (param.getValue().get(j).equals("1")) {
                            return new SimpleStringProperty("Nam");
                        } else if (param.getValue().get(j).equals("0")) {
                            return new SimpleStringProperty("Nữ");
                        } else {
                            return new SimpleStringProperty("Unisex");
                        }
                    }
                });
            } else if (i == 8) {
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new ReadOnlyObjectWrapper(FormatTien(Integer.valueOf(param.getValue().get(j).toString())));
                    }
                });
            } else {
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new ReadOnlyObjectWrapper(param.getValue().get(j));
                    }
                });
            }

            table_view.getColumns().addAll(col);

        }
        table_view.getColumns().get(0).setText("Mã sản phẩm");
        table_view.getColumns().get(1).setText("Tên sản phẩm");
        table_view.getColumns().get(2).setText("Nhóm hàng");
        table_view.getColumns().get(3).setText("Nhà sản xuất");
        table_view.getColumns().get(4).setText("Màu sắc");
        table_view.getColumns().get(5).setText("Giới tính");
        table_view.getColumns().get(6).setText("Size");
        table_view.getColumns().get(7).setText("Số lượng");
        table_view.getColumns().get(8).setText("Giá bán");

    }

}
