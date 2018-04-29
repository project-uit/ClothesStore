/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author dieunguyen
 */
public class FXML_ClothesStoreController implements Initializable {

    @FXML
    private JFXDrawer drawer;

    @FXML
    private AnchorPane box_minimized;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private AnchorPane root, rootm;

    public static AnchorPane rootP, rootM;// Roottemp;

    @FXML
    private void changeTab(ActionEvent event) throws IOException {
        JFXButton btn = (JFXButton) event.getSource();
        System.out.println(btn.getText());
        FXML_ClothesStoreController.rootP.getChildren().removeAll();
        switch (btn.getId()) {
            case "btnTongQuan":
                AnchorPane tongquan = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_TongQuan.fxml"));
                FXML_ClothesStoreController.rootP.getChildren().setAll(tongquan);
                root.setLeftAnchor(tongquan, 0.0);
                root.setRightAnchor(tongquan, 0.0);
                root.setTopAnchor(tongquan, 0.0);
                root.setBottomAnchor(tongquan, 0.0);
                break;
            case "btnDonHang":
                AnchorPane donhang = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_HoaDon.fxml"));
                FXML_ClothesStoreController.rootP.getChildren().setAll(donhang);
                root.setLeftAnchor(donhang, 0.0);
                root.setRightAnchor(donhang, 0.0);
                root.setTopAnchor(donhang, 0.0);
                root.setBottomAnchor(donhang, 0.0);
                break;
            case "btnHangHoa":
                AnchorPane hanghoa = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_HangHoa.fxml"));
                //Roottemp = hanghoa;
                FXML_ClothesStoreController.rootP.getChildren().setAll(hanghoa);

                root.setLeftAnchor(hanghoa, 0.0);
                root.setRightAnchor(hanghoa, 0.0);
                root.setTopAnchor(hanghoa, 0.0);
                root.setBottomAnchor(hanghoa, 0.0);
                break;
            case "btnNhapKho":
                AnchorPane nhapkho = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_NhapKho.fxml"));
                FXML_ClothesStoreController.rootP.getChildren().setAll(nhapkho);

                root.setLeftAnchor(nhapkho, 0.0);
                root.setRightAnchor(nhapkho, 0.0);
                root.setTopAnchor(nhapkho, 0.0);
                root.setBottomAnchor(nhapkho, 0.0);
                break;
        }
    }

    @FXML
    private void exit(ActionEvent event) {
        ButtonType yes = new ButtonType("Thoát", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Huỷ", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(AlertType.CONFIRMATION,
                "Bạn có chắc chắn muốn thoát",
                yes,
                cancel);

        alert.setTitle("Nhắc nhở");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == yes) {
            System.exit(0);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rootP = root;
        rootM = rootm;
        box_minimized.setVisible(false);
        InitDrawer();
        //Roottemp = new AnchorPane();
        try {
            AnchorPane tongquan = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_TongQuan.fxml"));
            root.setLeftAnchor(tongquan, 0.0);
            root.setRightAnchor(tongquan, 0.0);
            root.setTopAnchor(tongquan, 0.0);
            root.setBottomAnchor(tongquan, 0.0);
            FXML_ClothesStoreController.rootP.getChildren().setAll(tongquan);

            AnchorPane box = FXMLLoader.load(getClass().getResource("/clothesstore_view/SidePanelContent.fxml"));
            drawer.setSidePane(box);

            rootm.widthProperty().addListener((observable, oldValue, newValue) -> box.setPrefWidth(newValue.doubleValue()));
            rootm.heightProperty().addListener((observable, oldValue, newValue) -> box.setPrefHeight(newValue.doubleValue()));

        } catch (IOException ex) {
            Logger.getLogger(FXML_ClothesStoreController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void InitDrawer() {
        //HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
        //transition.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            //transition.setRate(transition.getRate()*-1);
            //transition.play();           
            if (drawer.isShown()) {
                drawer.close();
                rootm.setLeftAnchor(root, 230.0);
                box_minimized.setVisible(false);
            } else {
                drawer.open();
                rootm.setLeftAnchor(root, 52.0);
                box_minimized.setVisible(true);
            }
        });

        drawer.addEventHandler(MouseEvent.DRAG_DETECTED, (e) -> {
            //transition.setRate(transition.getRate() * -1);
            //transition.play();
            if (drawer.isShown()) {
            } else {
                rootm.setLeftAnchor(root, 52.0);
                box_minimized.setVisible(true);
            }
        });
    }
}
