/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import clothesstore_model.ChiTietDoiTra;
import clothesstore_model.ChiTietHoaDonDoiTra;
import clothesstore_model.DoiTra;
import clothesstore_model.HoaDon;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.StringConverter;
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
            clMaCTSP, clTenSP, clSoLuong, clGiaBan, clThanhTien,
            _clMaHoaDon, _clTenNV, _clSDT, _clNgayDoiTra, _clLyDo,
            clMaCTSP1, clTenSP1, clGiaBan1, clSoLuong1, clThanhTien1,
            clMaCTSP2, clTenSP2, clGiaBan2, clSoLuong2, clThanhTien2;
    @FXML
    private TableView<HoaDon> tblHoaDon;
    @FXML
    private TableView<DoiTra> tblDoiTra;
    @FXML
    private TableView<ChiTietDoiTra> tblChiTietHoaDon;
    @FXML
    private TableView<ChiTietDoiTra> tblHangDoiTra;
    @FXML
    private TableView<ChiTietHoaDonDoiTra> tblHangThayThe;
    @FXML
    private TextField txtSearch, txtMaHD;
    @FXML
    private JFXDatePicker dtpFrom, dtpTo, dtpNgay;
    @FXML
    private TitledPane titledPane;
    @FXML
    private Label lb1, lb2, lb3;

    private int total1 = 0, total2 = 0;
    public static Stage stageDoiTra_Create;
    public static int mahd;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initTableHoaDon();
        initTableDoiTra();
        initTableHangDoiTra();
        initTableHangThayThe();
        initTextFieldSearch();
        FormatDate();
        tblChiTietHoaDon.setPlaceholder(new Label("Chọn hoá đơn để xem chi tiết"));
       
  
    }

    private Date selectedDateFrom, selectedDateTo, selectedDate;

    private void FormatDate() {
        dtpFrom.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null) {
                    return "";
                }
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, dateTimeFormatter);
            }
        });
        dtpTo.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null) {
                    return "";
                }
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, dateTimeFormatter);
            }
        });
        dtpNgay.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null) {
                    return "";
                }
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, dateTimeFormatter);
            }
        });
    }

    private String FormatTien(int soTien) {
        return String.format("%,8d%n", soTien).trim();
    }

    private void initTextFieldSearch() {
        List<HoaDon> listHD = new HoaDon().getListHoaDon();
        List<Integer> arr_maHD = new ArrayList();
        listHD.forEach(item -> arr_maHD.add(item.getMahoadon().get()));
        TextFields.bindAutoCompletion(txtSearch, arr_maHD);
        TextFields.bindAutoCompletion(txtMaHD, arr_maHD);
        txtMaHD.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (newValue.length() != 0) {
                    ObservableList<DoiTra> list = observableArrayList();
                    list = new DoiTra().getListKhoDoiTraFromMaHD(Integer.valueOf(newValue.trim()));
                    if (list.isEmpty()) {
                        tblDoiTra.getItems().clear();
                    } else {
                        tblDoiTra.getItems().clear();
                        tblDoiTra.setItems(list);
                    }
                } else if (newValue.length() == 0) {
                    ObservableList<DoiTra> list = observableArrayList();
                    list = new DoiTra().getListKhoDoiTra();
                    tblDoiTra.getItems().clear();
                    tblDoiTra.setItems(list);
                }
            }
        });

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
                return new ReadOnlyObjectWrapper(new SimpleDateFormat("dd-MM-yyyy").format(p.getValue().getNgayban()));
            }
        });

        clTongTien.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<HoaDon, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<HoaDon, Integer> p) {
                return new ReadOnlyObjectWrapper(FormatTien(p.getValue().getTongtien().get()));
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
                } else {
                    btnCreate.setDisable(true);
                }
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
                return new ReadOnlyObjectWrapper(FormatTien(p.getValue().getGiaban().get()));
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
                return new ReadOnlyObjectWrapper(FormatTien(p.getValue().getThanhtien().get()));
            }
        });
        ChiTietDoiTra cthd = new ChiTietDoiTra();
        ObservableList<ChiTietDoiTra> list = observableArrayList();
        list = cthd.getCTHDfromMaHD(mahd);
        tblChiTietHoaDon.setItems(list);
    }

    private void initTableDoiTra() {
        _clMaHoaDon.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DoiTra, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<DoiTra, Integer> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getMahoadon().get());
            }
        });
        _clTenNV.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DoiTra, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DoiTra, String> p) {
                return p.getValue().getTennhanvien();
            }
        });
        _clSDT.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DoiTra, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DoiTra, String> p) {
                return p.getValue().getSodienthoai();
            }
        });
        _clNgayDoiTra.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DoiTra, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DoiTra, String> p) {
                return new ReadOnlyObjectWrapper(new SimpleDateFormat("dd-MM-yyyy").format(p.getValue().getNgaytra()));
            }
        });

        _clLyDo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DoiTra, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<DoiTra, Integer> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getLydo().get());
            }
        });

        tblDoiTra.setRowFactory(tv -> {
            TableRow<DoiTra> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    titledPane.setExpanded(false);
                    int maDoiTra = tblDoiTra.getItems().get(row.getIndex()).getMadoitra().get();
                    ObservableList<ChiTietDoiTra> list1 = observableArrayList();
                    list1 = new ChiTietDoiTra().getListChiTietDoiTraFromID(maDoiTra);
                    tblHangDoiTra.setItems(list1);
                    total1 = 0;
                    for (ChiTietDoiTra item : tblHangDoiTra.getItems()) {
                        total1 += item.getGiaban().get() * item.getSoluongmua().get();
                    }
                    lb1.setText("Tổng tiền hàng đổi trả:    " + total1);

                    ObservableList<ChiTietHoaDonDoiTra> list2 = observableArrayList();
                    list2 = new ChiTietHoaDonDoiTra().getListcthdDoiTrafromID(maDoiTra);
                    tblHangThayThe.setItems(list2);

                    total2 = 0;
                    for (ChiTietHoaDonDoiTra item : tblHangThayThe.getItems()) {
                        total2 += item.getGiaban().get() * item.getSoluongmua().get();
                    }
                    lb2.setText("Tổng tiền hàng đổi trả:    " + total2);

                    lb3.setText("Thành tiền:                " + (total2 - total1));
                }
            });
            return row;
        });

        ObservableList<DoiTra> list = observableArrayList();
        list = new DoiTra().getListKhoDoiTra();
        tblDoiTra.setItems(list);
    }

    public void initTableHangDoiTra() {
        clMaCTSP1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietDoiTra, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ChiTietDoiTra, String> p) {
                return p.getValue().getMachitietsanpham();
            }
        });
        clTenSP1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietDoiTra, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ChiTietDoiTra, String> p) {
                return p.getValue().getTensanpham();
            }
        });
        clGiaBan1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietDoiTra, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ChiTietDoiTra, Integer> p) {
                return new ReadOnlyObjectWrapper(FormatTien(p.getValue().getGiaban().get()));
            }
        });
        clSoLuong1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietDoiTra, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ChiTietDoiTra, Integer> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getSoluongmua().get());
            }
        });

        clThanhTien1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietDoiTra, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ChiTietDoiTra, Integer> p) {
                return new ReadOnlyObjectWrapper(FormatTien(p.getValue().getSoluongmua().get() * p.getValue().getGiaban().get()));
            }
        });
    }

    public void initTableHangThayThe() {
        clMaCTSP2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, String> p) {
                return p.getValue().getMachitietsanpham();
            }
        });
        clTenSP2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, String> p) {
                return p.getValue().getTensanpham();
            }
        });
        clGiaBan2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, Integer> p) {
                return new ReadOnlyObjectWrapper(FormatTien(p.getValue().getGiaban().get()));
            }
        });
        clSoLuong2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, Integer> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getSoluongmua().get());
            }
        });
        clThanhTien2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, Integer> p) {
                return new ReadOnlyObjectWrapper(FormatTien(p.getValue().getSoluongmua().get() * p.getValue().getGiaban().get()));
            }
        });
    }

    @FXML
    private void ChangeDate() {
        selectedDate = java.sql.Date.valueOf(dtpNgay.getValue());
        ObservableList<DoiTra> list = observableArrayList();
        list = new DoiTra().getListKhoDoiTraFromMaDate(selectedDate);
        tblDoiTra.setItems(list);
        txtMaHD.clear();
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
    private void Handler_btnCreate() {
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
