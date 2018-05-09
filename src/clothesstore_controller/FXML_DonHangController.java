/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import clothesstore_model.ChiTietKhoSanPham;
import clothesstore_model.HoaDon;
import com.jfoenix.controls.JFXDatePicker;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

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
        initTextFieldSearch();
    }

    private void initTextFieldSearch() {
        List<HoaDon> listHD = new HoaDon().getListHoaDon();
        List<Integer> arr_maHD = new ArrayList();
        listHD.forEach(item -> arr_maHD.add(item.getMahoadon().get()));
        TextFields.bindAutoCompletion(txtSearch, arr_maHD);
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (newValue.length() != 0) {
                    HoaDon hd = new HoaDon().getHoaDonFromMaHD(Integer.valueOf(newValue.trim()));
                    if (hd.getMahoadon() == null) {
                        tblHoaDon.getItems().clear();
                    } else {
                        ObservableList<HoaDon> list = observableArrayList();
                        list.add(hd);
                        tblHoaDon.getItems().clear();
                        tblHoaDon.setItems(list);
                    }
                } else if (newValue.length() == 0) {
                    HoaDon hd = new HoaDon();
                    ObservableList<HoaDon> list = observableArrayList();
                    list = hd.getListHoaDon();
                    tblHoaDon.getItems().clear();
                    tblHoaDon.setItems(list);
                }
            }
        });
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
                return p.getValue().getSodienthoai();
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
