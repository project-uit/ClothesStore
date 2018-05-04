/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import clothesstore_model.HoaDon;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author dieunguyen
 */
public class FXML_TongQuanController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label lbtienbanhang_homnay, lbsodonhang_hanghoa_homnay, lbtonkholau, lbhethang,
            lbtienbanhang, lbsodonhang, lbsohanghoa, lbhanghoa;
    @FXML
    private ComboBox cmb;
    HoaDon hd = new HoaDon();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO       
        lbtienbanhang_homnay.setText("Tiền bán hàng: \n" + String.valueOf(hd.getActivities_TienBanHang("homnay")));
        lbsodonhang_hanghoa_homnay.setText("Số đơn hàng: " + String.valueOf(hd.getActivities_TienSoDonHang("homnay"))
                + "\nSố hàng hóa: " + String.valueOf(hd.getActivities_TienSoHangHoa("homnay")));
        lbhethang.setText("Hết hàng: " + String.valueOf(hd.getCountHetHang()));
     
        initCMB();
    }
    public void initCMB() {
        cmb.getItems().addAll("Tuần này", "Tuần trước", "Tháng này", "Tháng trước");
        cmb.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                switch (t1) {
                    case "Tuần này":
                        lbtienbanhang.setText("Tiền bán hàng: \t" + String.valueOf(hd.getActivities_TienBanHang("tuannay")));
                        lbsodonhang.setText("Số đơn hàng: \t\t" + String.valueOf(hd.getActivities_TienSoDonHang("tuannay")));
                        lbsohanghoa.setText("Số hàng hóa: \t\t" + String.valueOf(hd.getActivities_TienSoHangHoa("tuannay")));
                        break;
                    case "Tuần trước":
                        lbtienbanhang.setText("Tiền bán hàng: \t" + String.valueOf(hd.getActivities_TienBanHang("tuantruoc")));
                        lbsodonhang.setText("Số đơn hàng: \t\t" + String.valueOf(hd.getActivities_TienSoDonHang("tuantruoc")));
                        lbsohanghoa.setText("Số hàng hóa: \t\t" + String.valueOf(hd.getActivities_TienSoHangHoa("tuantruoc")));
                        break;
                    case "Tháng này":
                        lbtienbanhang.setText("Tiền bán hàng: \t" + String.valueOf(hd.getActivities_TienBanHang("thangnay")));
                        lbsodonhang.setText("Số đơn hàng: \t\t" + String.valueOf(hd.getActivities_TienSoDonHang("thangnay")));
                        lbsohanghoa.setText("Số hàng hóa: \t\t" + String.valueOf(hd.getActivities_TienSoHangHoa("thangnay")));
                        break;
                    case "Tháng trước":
                        lbtienbanhang.setText("Tiền bán hàng: \t" + String.valueOf(hd.getActivities_TienBanHang("thangtruoc")));
                        lbsodonhang.setText("Số đơn hàng: \t\t" + String.valueOf(hd.getActivities_TienSoDonHang("thangtruoc")));
                        lbsohanghoa.setText("Số hàng hóa: \t\t" + String.valueOf(hd.getActivities_TienSoHangHoa("thangtruoc")));
                        break;
                }
            }
        });
        cmb.getSelectionModel().selectFirst();
    }
}
