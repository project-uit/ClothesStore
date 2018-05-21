/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import static clothesstore_controller.FXML_DoiTraController.mahd;
import clothesstore_model.ChiTietDoiTra;
import clothesstore_model.ChiTietHoaDon;
import clothesstore_model.ChiTietHoaDonDoiTra;
import clothesstore_model.ChiTietSanPham;
import clothesstore_model.DoiTra;
import clothesstore_model.HoaDonDoiTra;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.util.Callback;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author dieunguyen
 */
public class FXML_ChiTietDoiTraController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableColumn clMaCTSP, clTenSP, clGiaBan, clSoLuong, clThanhTien,
            _clMaCTSP, _clTenSP, _clGiaBan, _clSoLuong, _clThanhTien;
    @FXML
    private TableView<ChiTietDoiTra> tblHangDoiTra;
    @FXML
    private TableView<ChiTietHoaDonDoiTra> tblHangThayThe;
    @FXML
    private JFXButton btnLuu, btnBack, btnAdd;
    @FXML
    private TextField txtSearch;
    @FXML
    private Label lb1, lb2, lb3;
    private DoiTra doitra;
    private int total1 = 0, total2 = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initTableHangDoiTra(mahd);
        initTableHangThayThe();
        initTextSearch();
    }

    public void setDT(DoiTra dt) {
        doitra = dt;
    }

    public void initTextSearch() {
        List<String> listCTSP = new ChiTietSanPham().getListMactsp();
        TextFields.bindAutoCompletion(txtSearch, listCTSP);
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (newValue.length() != 0) {
                    for (ChiTietHoaDonDoiTra item : tblHangThayThe.getItems()) {
                        if (item.getMachitietsanpham().get().equals(newValue)) {
                            btnAdd.setDisable(true);
                        } else {
                            btnAdd.setDisable(false);
                        }
                    }
                }
            }
        });
    }

    public void initTableHangDoiTra(int mahd) {
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
        tblHangDoiTra.setRowFactory(tv -> {
            TableRow<ChiTietDoiTra> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    TextInputDialog dialog = new TextInputDialog("0");

                    dialog.setTitle(null);
                    dialog.setHeaderText(null);
                    dialog.setContentText("Số lượng: ");

                    Optional<String> result = dialog.showAndWait();

                    result.ifPresent(name -> {
                        try {
                            int sl = Integer.valueOf(name);
                            row.getItem().setSoluongmua(new SimpleIntegerProperty(sl));
                            row.getItem().setThanhtien(new SimpleIntegerProperty(sl * row.getItem().getGiaban().get()));

                            for (ChiTietDoiTra item : tblHangDoiTra.getItems()) {
                                total1 += item.getThanhtien().get();
                            }
                            lb1.setText("Tổng tiền hàng đổi trả:    " + total1);
                            lb3.setText("Thành tiền : " + (total1 - total2));
                        } catch (NumberFormatException ex) {
                            row.getItem().setSoluongmua(new SimpleIntegerProperty(0));
                            row.getItem().setThanhtien(new SimpleIntegerProperty(0));
                        }
                        tblHangDoiTra.refresh();
                    });
                }
            });
            return row;
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
        tblHangDoiTra.setItems(list);
        for (ChiTietDoiTra item : tblHangDoiTra.getItems()) {
            item.setSoluongmua(new SimpleIntegerProperty(0));
            item.setThanhtien(new SimpleIntegerProperty(0));
        }
        tblHangDoiTra.refresh();
    }

    public void initTableHangThayThe() {
        _clMaCTSP.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, String> p) {
                return p.getValue().getMachitietsanpham();
            }
        });
        _clTenSP.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, String> p) {
                return p.getValue().getTensanpham();
            }
        });
        _clGiaBan.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, Integer> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getGiaban().get());
            }
        });
        _clSoLuong.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, Integer> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getSoluongmua().get());
            }
        });
        tblHangThayThe.setRowFactory(tv -> {
            TableRow<ChiTietHoaDonDoiTra> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    TextInputDialog dialog = new TextInputDialog("0");

                    dialog.setTitle(null);
                    dialog.setHeaderText(null);
                    dialog.setContentText("Số lượng: ");

                    Optional<String> result = dialog.showAndWait();

                    result.ifPresent(name -> {
                        try {
                            int sl = Integer.valueOf(name);

                            row.getItem().setSoluongmua(new SimpleIntegerProperty(sl));
                            row.getItem().setThanhtien(new SimpleIntegerProperty(sl * row.getItem().getGiaban().get()));

                            for (ChiTietHoaDonDoiTra item : tblHangThayThe.getItems()) {
                                total2 += item.getThanhtien().get();
                            }
                            lb2.setText("Tổng tiền hàng thay thế:   " + total2);
                            lb3.setText("Thành tiền:                " + (total1 - total2));
                        } catch (NumberFormatException ex) {
                            row.getItem().setSoluongmua(new SimpleIntegerProperty(0));
                            row.getItem().setThanhtien(new SimpleIntegerProperty(0));
                        }
                        tblHangThayThe.refresh();
                    });
                }
            });
            return row;
        });
        _clThanhTien.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<ChiTietHoaDonDoiTra, Integer> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getThanhtien().get());
            }
        });
    }

    @FXML
    private void Handler_btnAdd() {
        ChiTietHoaDonDoiTra ctsp = new ChiTietHoaDonDoiTra().getCTSPfromMa(txtSearch.getText().trim());
        if (ctsp.getMachitietsanpham() != null) {
            tblHangThayThe.getItems().add(ctsp);
            txtSearch.clear();
        }

    }

    @FXML
    private void Handler_btnLuu() {
        ButtonType yes = new ButtonType("Lưu", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Huỷ", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Bạn có chắc chắn muốn lưu",
                yes,
                cancel);

        alert.setTitle("Nhắc nhở");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == yes) {
            if (doitra.ThemDoiTra()) {
                for (ChiTietDoiTra item : tblHangDoiTra.getItems()) {
                    if (item.getSoluongmua().get() != 0) {
                        item.ThemChiTietDoiTra(doitra.getLastId());
                        item.updateSoLuong(item.getMachitietsanpham().get(), item.getSoluongmua().get());
                        item.updateHDkhiDoiTra1(mahd, item.getMachitietsanpham().get(), 
                                item.getSoluongmua().get(), item.getThanhtien().get());
                    }
                }
            } else {
                System.out.println("Thêm Đổi Trả thất bại");
            }

            int thanhtien = 0;
            thanhtien = tblHangThayThe.getItems().stream().map((item) -> item.getThanhtien().get()).reduce(thanhtien, Integer::sum);

            if (new HoaDonDoiTra().ThemHoaDonDoiTra(doitra.getLastId(), thanhtien)) {
                List<String> listctsp = new ArrayList();
                listctsp = new ChiTietDoiTra().getListMaCTSPfromHD(mahd);
                for (ChiTietHoaDonDoiTra item : tblHangThayThe.getItems()) {
                    if (item.getSoluongmua().get() != 0) {
                        item.ThemChiTietHoaDonDoiTra(new HoaDonDoiTra().getLastId());

                        if (listctsp.contains(item.getMachitietsanpham().get())) {
                            new ChiTietDoiTra().updateHDkhiDoiTra2(mahd, item.getMachitietsanpham().get(),
                                    item.getSoluongmua().get(),
                                    item.getThanhtien().get());
                        } else {
                            new ChiTietHoaDon(new SimpleIntegerProperty(mahd),
                                    item.getMachitietsanpham(),
                                    item.getSoluongmua(),
                                    item.getThanhtien()).insert();
                        }
                    }
                }
            } else {
                System.out.println("Thêm Hóa Đơn Đổi Trả thất bại");
            }

            btnLuu.setDisable(true);
        }
        
        new DoiTra().updateTongTienHoaDon(mahd, total2 - total1);
    }

    @FXML
    private void Handler_btnBack() {
        System.out.println("Back");
    }
}
