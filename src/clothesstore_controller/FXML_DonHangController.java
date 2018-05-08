/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import clothesstore_model.ChiTietKhoSanPham;
import clothesstore_model.HoaDon;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author dieunguyen
 */
public class FXML_DonHangController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXDatePicker dtpNgay;
    @FXML
    private TableColumn clMaHoaDon, clTenNV, clSDT, clNgayBan, clTongTien;
    @FXML
    private TableView tblHoaDon, tblChiTietHoaDon;
    @FXML
    private TextField txtSearch;
    @FXML
    private Hyperlink linkBoChon;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initTableHoaDon();
    }
    
    private void initTableHoaDon() {
        clMaHoaDon.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HoaDon, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<HoaDon, Integer> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getMahoadon().get());
            }
        });
        clTenNV.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HoaDon, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HoaDon, String> p) {
                return p.getValue().getTennhanvien();
            }
        });
        clSDT.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HoaDon, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HoaDon, String> p) {
                return p.getValue().getTennhanvien();
            }
        });
        clNgayBan.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HoaDon, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<HoaDon, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getNgayban());
            }
        });
        clTongTien.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HoaDon, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<HoaDon, Integer> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getTongtien().get());
            }
        });
        
        HoaDon hd = new HoaDon();
        ObservableList<HoaDon> list = observableArrayList();
        list = hd.getListHoaDon();
        tblHoaDon.setItems(list);
    }
}
