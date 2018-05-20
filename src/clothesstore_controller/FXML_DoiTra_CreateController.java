/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import static clothesstore_controller.FXML_ClothesStoreController.rootP;
import static clothesstore_controller.FXML_DoiTraController.mahd;
import static clothesstore_controller.FXML_DoiTraController.stageDoiTra_Create;
import static clothesstore_controller.FXML_SearchSanPhamController.listSP;
import clothesstore_model.DoiTra;
import com.jfoenix.controls.JFXDatePicker;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dieunguyen
 */
public class FXML_DoiTra_CreateController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXDatePicker dtpNgay;
    @FXML
    private TextArea txtLyDo;
    @FXML
    private Label lbMaHoaDon;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dtpNgay.setValue(LocalDate.now());
        lbMaHoaDon.setText("Hoá đơn " + mahd);
    }

    @FXML
    private void Handler_btnCreate() {
        DoiTra dt = new DoiTra(mahd, java.sql.Date.valueOf(dtpNgay.getValue()), txtLyDo.getText());
        stageDoiTra_Create.close();
        AnchorPane ctdt;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/clothesstore_view/FXML_ChiTietDoiTra.fxml"));
            ctdt = fxmlLoader.load();
            SildingWindowAnimation silde = new SildingWindowAnimation();
            silde.SildeTo(rootP,
                    ctdt,
                    SildingWindowAnimation.Direction.SildeLeft);
            ctdt.requestFocus();

            rootP.setLeftAnchor(ctdt, 0.0);
            rootP.setRightAnchor(ctdt, 0.0);
            rootP.setTopAnchor(ctdt, 0.0);
            rootP.setBottomAnchor(ctdt, 0.0);

            FXML_ChiTietDoiTraController controller = fxmlLoader.getController();
            controller.setDT(dt);
            //controller.initTableHangDoiTra(mahd);
            
        } catch (IOException ex) {
            Logger.getLogger(FXML_DoiTra_CreateController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Handler_btnExit() {
        stageDoiTra_Create.close();
    }
}
