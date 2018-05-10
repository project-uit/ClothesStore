/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import clothesstore_model.MauSac;
import clothesstore_model.NhaSanXuat;
import clothesstore_model.NhomHang;
import clothesstore_model.TraCuu;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import java.util.function.Function;
import java.util.function.Predicate;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.util.StringConverter;

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
    TableView table_view;
    @FXML
    JFXTextField txt_fi_tensanpham;
    @FXML
    JFXComboBox<String> cmb_mausac, cmb_nhasanxuat, cmb_nhomhang;
    @FXML
    JFXComboBox<Integer> cmb_gioitinh;
    @FXML
    JFXButton btn_search, btn_refresh;
    private String query;
    private String gioitinh = "and gioitinh=",
            tensanpham = "and tensanpham=",
            tenmau = "and tenmau=",
            tennhomhang = "and tennhomhang=",
            tennhasanxuat = "and tennhasanxuat=";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InitCmb();
        InitTable();
        TraCuu tracuu = new TraCuu();
        query = tracuu.getquery();
        viewListTable();
        table_view.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        btn_search.setOnAction(e -> {
            viewListTable();
        });
        btn_refresh.setOnAction(e -> {
            TraCuu tra_cuu = new TraCuu();
            query = tra_cuu.getquery();
            viewListTable();
        });
    }

    private void viewListTable() {
        TraCuu tracuu = new TraCuu();
        table_view.setItems(tracuu.filterList_sanpham(query));
    }

    private void InitCmb() {
        MauSac ms = new MauSac();
        cmb_mausac.getItems().add("Tất cả");
        ms.LoadCmB(cmb_mausac);
        cmb_mausac.getSelectionModel().selectFirst();
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
            System.out.println("Cmb ms: " + query);
        });
        ObservableList<Integer> list = FXCollections.observableArrayList();
        list.add(3);
        list.add(0);
        list.add(1);
        list.add(2);
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
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });
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
            System.out.println("Cmb gioi tinh: " + query);
        });
        NhaSanXuat nsx = new NhaSanXuat();
        cmb_nhasanxuat.getItems().add("Tất cả");
        nsx.getNSXList(cmb_nhasanxuat);
        cmb_nhasanxuat.getSelectionModel().selectFirst();
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
            System.out.println("Cmb nsx: " + query);
        });
        NhomHang nhomhang = new NhomHang();
        cmb_nhomhang.getItems().add("Tất cả");
        nhomhang.getNHList(cmb_nhomhang);
        cmb_nhomhang.getSelectionModel().selectFirst();
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
            System.out.println("Cmb nhomhang: " + query);
        });
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
            } else {
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new ReadOnlyObjectWrapper(param.getValue().get(j));
                    }
                });
            }
            table_view.getColumns().addAll(col);
        }
    }

}
