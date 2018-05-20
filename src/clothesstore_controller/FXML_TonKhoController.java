/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import clothesstore_model.TonKho;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author quochung
 */
public class FXML_TonKhoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXComboBox<String> cmb_tonkho;
    @FXML
    private Label lb_vontonkho, lb_giatritonkho, lb_soluongtonkho;
    @FXML
    private TableView<String> table_view;
    private int chucnang;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initCmb_tonkho();
        initLabel();
        viewListTable();
    }

    private void initLabel() {        
        lb_vontonkho.setText(FormatTien(TonKho.getvontonkho()));
        lb_giatritonkho.setText(FormatTien(TonKho.getgiatritonkho()));
        lb_soluongtonkho.setText(TonKho.getsoluongtonkho().toString());
    }

    private String FormatTien(int soTien) {
        return String.format("%,8d%n", soTien).trim();
    }

    private void viewListTable() {
        if (!table_view.getColumns().isEmpty()) {
            table_view.getColumns().clear();
        }
        TonKho.loadtable(table_view, chucnang);
        table_view.getColumns().get(0).setText("Mã sản phẩm");
        table_view.getColumns().get(1).setText("Tên sản phẩm");
        table_view.getColumns().get(2).setText("Tồn kho tối thiểu");
        table_view.getColumns().get(3).setText("Số lượng");
        table_view.getColumns().get(4).setText("Tồn kho tối đa");
        if (chucnang == 7) {
            table_view.getColumns().get(5).setText("Ngày nhập");
            table_view.getColumns().get(6).setText("Ngày hết hạn");
        }
        table_view.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void initCmb_tonkho() {
        List<String> list_tonkho = new ArrayList<>();
        list_tonkho.add("Tất cả");
        list_tonkho.add("Hàng tồn");
        list_tonkho.add("Chưa nhập vào kho");
        list_tonkho.add("Hết hàng");
        list_tonkho.add("Sắp hết hàng");
        list_tonkho.add("Vượt định mức");
        list_tonkho.add("Tồn kho lâu");
        cmb_tonkho.getItems().setAll(list_tonkho);
        cmb_tonkho.getSelectionModel().selectFirst();
        chucnang = cmb_tonkho.getSelectionModel().getSelectedIndex() + 1;
        cmb_tonkho.setOnAction(e -> {
            chucnang = cmb_tonkho.getSelectionModel().getSelectedIndex() + 1;
            viewListTable();
        });
    }

}
