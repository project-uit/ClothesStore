/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import static clothesstore_controller.FXML_DangNhapController.stageMain;
import static clothesstore_view.ClothesStore._rootDangNhap;
import static clothesstore_view.ClothesStore.stageDangNhap;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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
    public  VBox vbox_minimized;
    public static VBox _vbox_mini;
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
                AnchorPane hanghoa = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_SanPham.fxml"));
                //Roottemp = hanghoa;
                FXML_ClothesStoreController.rootP.getChildren().setAll(hanghoa);

                root.setLeftAnchor(hanghoa, 0.0);
                root.setRightAnchor(hanghoa, 0.0);
                root.setTopAnchor(hanghoa, 0.0);
                root.setBottomAnchor(hanghoa, 0.0);
                break;
            case "btnHoaDonMuaHang":
                AnchorPane nhapkho = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_HoaDonMuaHang.fxml"));
                FXML_ClothesStoreController.rootP.getChildren().setAll(nhapkho);
                root.setLeftAnchor(nhapkho, 0.0);
                root.setRightAnchor(nhapkho, 0.0);
                root.setTopAnchor(nhapkho, 0.0);
                root.setBottomAnchor(nhapkho, 0.0);
                break;
            case "btnNhapKho":
                AnchorPane nhapkho1 = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_NhapKho.fxml"));
                FXML_ClothesStoreController.rootP.getChildren().setAll(nhapkho1);
                root.setLeftAnchor(nhapkho1, 0.0);
                root.setRightAnchor(nhapkho1, 0.0);
                root.setTopAnchor(nhapkho1, 0.0);
                root.setBottomAnchor(nhapkho1, 0.0);
                break;
            case "btnTonKho":
                AnchorPane tonkho = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_TonKho.fxml"));
                FXML_ClothesStoreController.rootP.getChildren().setAll(tonkho);
                root.setLeftAnchor(tonkho, 0.0);
                root.setRightAnchor(tonkho, 0.0);
                root.setTopAnchor(tonkho, 0.0);
                root.setBottomAnchor(tonkho, 0.0);
                break;
            case "btnTraCuu":
                AnchorPane tracuu = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_TraCuu.fxml"));
                FXML_ClothesStoreController.rootP.getChildren().setAll(tracuu);
                root.setLeftAnchor(tracuu, 0.0);
                root.setRightAnchor(tracuu, 0.0);
                root.setTopAnchor(tracuu, 0.0);
                root.setBottomAnchor(tracuu, 0.0);
                break;
            case "btnBieuDo":
                AnchorPane bieudo = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_ThongKe.fxml"));
                FXML_ClothesStoreController.rootP.getChildren().setAll(bieudo);
                root.setLeftAnchor(bieudo, 0.0);
                root.setRightAnchor(bieudo, 0.0);
                root.setTopAnchor(bieudo, 0.0);
                root.setBottomAnchor(bieudo, 0.0);
                break;
            case "btnQuanLyTK":
                AnchorPane qltk = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_QuanLyTaiKhoan.fxml"));
                FXML_ClothesStoreController.rootP.getChildren().setAll(qltk);
                root.setLeftAnchor(qltk, 0.0);
                root.setRightAnchor(qltk, 0.0);
                root.setTopAnchor(qltk, 0.0);
                root.setBottomAnchor(qltk, 0.0);
                break;
            case "btnDangXuat":
                ButtonType yes = new ButtonType("Đăng xuất", ButtonBar.ButtonData.OK_DONE);
                ButtonType cancel = new ButtonType("Huỷ", ButtonBar.ButtonData.CANCEL_CLOSE);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Bạn có chắc chắn muốn đăng xuất",
                        yes,
                        cancel);

                alert.setTitle("Nhắc nhở");
                alert.setHeaderText(null);
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == yes) {
                    stageMain.close();
                    Parent pane = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_DangNhap.fxml"));
                    _rootDangNhap.getChildren().removeAll();
                    _rootDangNhap.getChildren().setAll(pane);
                    stageDangNhap.show();
                }
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
        alert.setHeaderText(null);
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
        _vbox_mini = vbox_minimized;
        try {
            AnchorPane tongquan = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_TongQuan.fxml"));
            root.setLeftAnchor(tongquan, 0.0);
            root.setRightAnchor(tongquan, 0.0);
            root.setTopAnchor(tongquan, 0.0);
            root.setBottomAnchor(tongquan, 0.0);
            FXML_ClothesStoreController.rootP.getChildren().setAll(tongquan);

            AnchorPane box = FXMLLoader.load(getClass().getResource("/clothesstore_view/SidePanelContent.fxml"));
            KeyCombination keyCombination = new KeyCodeCombination(KeyCode.Q, KeyCodeCombination.ALT_DOWN);
            drawer.setSidePane(box);
            box.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (keyCombination.match(event)) {
                        if (drawer.isShown()) {
                            drawer.close();
                            rootm.setLeftAnchor(root, 230.0);
                            box_minimized.setVisible(false);
                        } else {
                            drawer.open();
                            rootm.setLeftAnchor(root, 52.0);
                            box_minimized.setVisible(true);
                        }
                    }
                }
            });
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
