/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import static clothesstore_controller.FXML_PhieuNhapController.mapn;
import clothesstore_model.ChiTietPhieuNhap;
import clothesstore_model.PhieuNhap;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author dieunguyen
 */
public class FXML_NhapKhoController implements Initializable {

    @FXML
    private AnchorPane QLPNpane;
    @FXML
    private TableView tableviewphieunhap;
    @FXML
    private TableColumn clmaphieunhap;
    @FXML
    private TableColumn clnhacungcap;
    @FXML
    private TableColumn clngaynhap;
    @FXML
    private TableColumn cltongtien;
    @FXML
    private TableColumn clmachitiet;
    @FXML
    private TableColumn clsanpham;
    @FXML
    private TableColumn clsoluong;
    @FXML
    private TableColumn clgiavon;
    @FXML
    private TableColumn clthanhtien;
    @FXML
    private TableView tableviewchitietphieunhap;
    @FXML
    private JFXButton btnnhapvaokho;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        InitTableViewPhieuNhap();
        tableviewphieunhap.setRowFactory( tv -> {
        TableRow<PhieuNhap> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
            PhieuNhap rowData = row.getItem();
            InitTableViewChiTietPhieuNhap(rowData.getMaphieunhap());
        }
        });
            return row ;
        });
        
    }    
    
    public void InitTableViewPhieuNhap(){
        PhieuNhap pn = new PhieuNhap();
        ObservableList <PhieuNhap> list = pn.getListPhieuNhap();
        clmaphieunhap.setCellValueFactory(new PropertyValueFactory("maphieunhap"));   
        clngaynhap.setCellValueFactory(new PropertyValueFactory("ngaynhap"));
        clnhacungcap.setCellValueFactory(new PropertyValueFactory("manhacungcap"));
        cltongtien.setCellValueFactory(new PropertyValueFactory("tongtien"));
        tableviewphieunhap.setItems(list);
    }
    public void InitTableViewChiTietPhieuNhap(int maphieunhap) {
        ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap();
        ObservableList<ChiTietPhieuNhap> list = ctpn.getTableChiTietPhieuNhap(maphieunhap);
        clmachitiet.setCellValueFactory(new PropertyValueFactory("machitietphieunhap"));
        clsanpham.setCellValueFactory(new PropertyValueFactory("masanpham"));
        clsoluong.setCellValueFactory(new PropertyValueFactory("soluongsanphamnhap"));
        clgiavon.setCellValueFactory(new PropertyValueFactory("giavon"));
        clthanhtien.setCellValueFactory(new PropertyValueFactory("thanhtien"));
        tableviewchitietphieunhap.setItems(list);
    }
    @FXML
    private void handler_themchitietphieunhap(ActionEvent event) {
    }
    
}
