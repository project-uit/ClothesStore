/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import clothesstore_model.ChiTietDoiTra;
import clothesstore_model.ChiTietHoaDon;
import clothesstore_model.HoaDon;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author dieunguyen
 */
public class FXML_DoiTraController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton btnCreate;
    @FXML
    private TableColumn clMaHoaDon, clTenNV, clSDT, clNgayBan, clTongTien,
            clMaCTSP, clTenSP, clSoLuong, clGiaBan, clThanhTien;
    @FXML
    private TableView<HoaDon> tblHoaDon;
    @FXML
    private TableView<ChiTietDoiTra> tblChiTietHoaDon;
    @FXML
    private TextField txtSearch;
    @FXML
    private JFXDatePicker dtpFrom, dtpTo;
    public static Stage stageDoiTra_Create;
    public static int mahd;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initTableHoaDon();
        initTextFieldSearch();
    }

    private Date selectedDateFrom, selectedDateTo;

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

        tblHoaDon.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                if (tblHoaDon.getSelectionModel().getSelectedItem() != null) {
                    HoaDon hd = tblHoaDon.getSelectionModel().getSelectedItem();
                    mahd = hd.getMahoadon().get();
                    initTableChiTietHoaDon(mahd);
                    btnCreate.setDisable(false);
                }
                else
                    btnCreate.setDisable(true);
            }
        });
        HoaDon hd = new HoaDon();
        ObservableList<HoaDon> list = observableArrayList();
        list = hd.getListHoaDon();
        tblHoaDon.setItems(list);
    }

    private void initTableChiTietHoaDon(int mahd) {
        //getCTHDfromMaHD
        clMaCTSP.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietDoiTra, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ChiTietDoiTra, String> p) {
                return p.getValue().getMachitietsanpham();
            }
        });
        clTenSP.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietDoiTra, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ChiTietDoiTra, String> p) {
                return p.getValue().getTensanpham();
            }
        });
        clGiaBan.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietDoiTra, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ChiTietDoiTra, Integer> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getGiaban().get());
            }
        });
        clSoLuong.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietDoiTra, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ChiTietDoiTra, Integer> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getSoluongmua().get());
            }
        });
        clThanhTien.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietDoiTra, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ChiTietDoiTra, Integer> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getThanhtien().get());
            }
        });
        ChiTietDoiTra cthd = new ChiTietDoiTra();
        ObservableList<ChiTietDoiTra> list = observableArrayList();
        list = cthd.getCTHDfromMaHD(mahd);
        tblChiTietHoaDon.setItems(list);
    }

    @FXML
    private void ChangeDateFrom() {
        if (dtpTo.getValue() == null) {
            LocalDateTime localdate = LocalDateTime.now();
            java.util.Date _date = null;
            try {
                _date = new SimpleDateFormat("yyyy-MM-dd").parse(localdate.toString());
            } catch (ParseException ex) {
                Logger.getLogger(FXML_DoiTraController.class.getName()).log(Level.SEVERE, null, ex);
            }
            selectedDateTo = new java.sql.Date(_date.getTime());
        } else {
            selectedDateTo = java.sql.Date.valueOf(dtpTo.getValue());
        }
        try {
            selectedDateFrom = java.sql.Date.valueOf(dtpFrom.getValue());
            HoaDon hd = new HoaDon();
            ObservableList<HoaDon> list = observableArrayList();
            list = hd.getListHoaDonFromDate(selectedDateFrom, selectedDateTo);
            tblHoaDon.setItems(list);
        } catch (Exception ex) {
        }
    }

    @FXML
    private void ChangeDateTo() {
        if (dtpFrom.getValue() == null) {
            Calendar cal = Calendar.getInstance();
            cal.set(0000, Calendar.JANUARY, 1); //Year, month and day of month
            java.util.Date _date = cal.getTime();
            selectedDateFrom = new java.sql.Date(_date.getTime());
        } else {
            selectedDateFrom = java.sql.Date.valueOf(dtpFrom.getValue());
        }

        try {
            selectedDateTo = java.sql.Date.valueOf(dtpTo.getValue());
            HoaDon hd = new HoaDon();
            ObservableList<HoaDon> list = observableArrayList();
            list = hd.getListHoaDonFromDate(selectedDateFrom, selectedDateTo);
            tblHoaDon.setItems(list);
        } catch (Exception ex) {
        }
    }

    @FXML
    private void Handlder_linkBoChon() {
        dtpFrom.setValue(null);
        dtpTo.setValue(null);
        initTableHoaDon();
    }
    
    @FXML
    private void Handler_btnCreate(){
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_DoiTra_Create.fxml"));
            Scene scene = new Scene(root);
            stageDoiTra_Create = new Stage();
            stageDoiTra_Create.initModality(Modality.APPLICATION_MODAL);
            stageDoiTra_Create.initStyle(StageStyle.UNDECORATED);
            stageDoiTra_Create.setResizable(false);
            stageDoiTra_Create.setScene(scene);
            stageDoiTra_Create.show();
        } catch (IOException ex) {
            Logger.getLogger(FXML_DoiTraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
