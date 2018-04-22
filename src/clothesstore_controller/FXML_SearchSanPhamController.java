/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import clothesstore_model.ChiTietPhieuNhap;
import static clothesstore_controller.FXML_ChiTietPhieuNhapController.stageQuanLySearchSanPham;
import javafx.util.StringConverter;
import clothesstore_model.SearchSanPham;

/**
 * FXML Controller class
 *
 * @author 15520
 */
public class FXML_SearchSanPhamController implements Initializable {

    @FXML
    private TableView<String> tableviewsearchsanpham;

    @FXML
    private JFXComboBox cbbsearchsanpham;

    private String data;
    private int query;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap();
        ctpn.LoadSearchSanPhamChuaNhapTableView(tableviewsearchsanpham);
        tableviewsearchsanpham.getColumns().get(0).setText("Mã sản phẩm");
        tableviewsearchsanpham.getColumns().get(1).setText("Tên sản phẩm");
        tableviewsearchsanpham.getColumns().get(2).setText("Số lượng");
        tableviewsearchsanpham.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableviewsearchsanpham.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if (tableviewsearchsanpham.getSelectionModel().getSelectedItem() != null) {
                    // newValue lấy hết giá trị trong 1 hàng
                    String Finalvaluetablerow = newValue.toString().split(",")[0].substring(1); // lấy giá trị ở cột thứ i
                    data = Finalvaluetablerow;
                }
            }
        });

        SearchSanPham ob = SearchSanPham.newInstance().keysanpham("Sản phẩm chưa từng nhập").tensanpham(1);
        SearchSanPham ob1 = SearchSanPham.newInstance().keysanpham("Sản phẩm đã từng nhập").tensanpham(2);
        cbbsearchsanpham.getItems().addAll(ob, ob1);
        cbbsearchsanpham.setConverter(new StringConverter<SearchSanPham>() {
            @Override
            public String toString(SearchSanPham object) {
                return object.getKeysanpham();
            }

            @Override
            public SearchSanPham fromString(String string) {
                return null;
            }
        });

        cbbsearchsanpham.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SearchSanPham>() {
            @Override
            public void changed(ObservableValue<? extends SearchSanPham> observable, SearchSanPham oldValue, SearchSanPham newValue) {
                query = newValue.getTensanpham();
            }
        });

        cbbsearchsanpham.getSelectionModel().selectFirst();

    }

    @FXML
    private void handler_Thêm(ActionEvent event) {
        stageQuanLySearchSanPham.close();
    }

    String getData() {
        return data;
    }

    @FXML
    private void handler_chon(ActionEvent event) {
        tableviewsearchsanpham.getColumns().clear();
        if (query == 1) {
            ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap();
            ctpn.LoadSearchSanPhamChuaNhapTableView(tableviewsearchsanpham);
        } else if (query == 2) {
            ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap();
            ctpn.LoadSearchSanPhamDaNhapTableView(tableviewsearchsanpham);
        }
        // Chỉnh header text cho column
        tableviewsearchsanpham.getColumns().get(0).setText("Mã sản phẩm");
        tableviewsearchsanpham.getColumns().get(1).setText("Tên sản phẩm");
        tableviewsearchsanpham.getColumns().get(2).setText("Số lượng");
        tableviewsearchsanpham.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
}
