/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import static clothesstore_controller.FXML_ClothesStoreController._vbox_mini;
import static clothesstore_controller.SidePanelContentController._vbox;
import clothesstore_model.NhanVien;
import clothesstore_model.TaiKhoan;
import static clothesstore_view.ClothesStore.stageDangNhap;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.animation.FadeTransition;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author user
 */
public class FXML_DangNhapController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    JFXCheckBox checkbox_remember;
    @FXML
    private JFXTextField txtUser;

    @FXML
    private JFXPasswordField txtPass;

    @FXML
    private Label lbError;

    private Preferences preference;

    public static Stage stageMain;
    public static Stage stageSplash;
    public static String UserID;
    public static int MaNhanVien;
    public static String TenNhanVien;
    public static int quyen;

    @FXML
    private void Login(ActionEvent event) throws IOException {
        String user = txtUser.getText().trim();
        String password = txtPass.getText().trim();

        if (!user.equals("") && !password.equals("")) {
            TaiKhoan tk = new TaiKhoan(user, password);
            if (tk.CheckLogin()) {
                if (checkbox_remember.isSelected()) {
                    NhanVien nv = new NhanVien();
                    nv = nv.getNhanVienfromUser(user);
                    TenNhanVien = nv.getTennhanvien();
                    MaNhanVien = nv.getManhanvien();
                    preference.put("userID", user);
                    preference.put("password", password);
                } else {
                    preference.put("userID", "");
                    preference.put("password", "");
                }

                UserID = user;
                txtUser.clear();
                txtPass.clear();
                stageDangNhap.close();
                loadSplashScreen();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_ClothesStore.fxml"));
                    Scene scene = new Scene(root);
                    stageMain = new Stage();
                    stageMain.setScene(scene);
                    stageMain.setTitle("Quản lý cửa hàng quần áo");
                    stageMain.getIcons().add(new Image("/clothesstore_view/img/logo.jpg"));
                    stageMain.setOnCloseRequest((WindowEvent evt) -> {
                        System.exit(0);
                    });

                } catch (IOException ex) {
                    Logger.getLogger(FXML_DangNhapController.class.getName()).log(Level.SEVERE, null, ex);
                }

                quyen = tk.GetQuyenFromID(user);
                if (quyen == 0) {
                    List<Object> listnode = new ArrayList<Object>();
                    for (Node node : _vbox.getChildren()) {
                        if (node instanceof JFXButton) {
                            if (node.getId().equals("btnTonKho") || node.getId().equals("btnPhieuNhap")
                                    || node.getId().equals("btnBieuDo")
                                    || node.getId().equals("btnQuanLyTK")) {
                                listnode.add(node);
                            }
                        }
                    }
                    for (Object node : listnode) {
                        _vbox.getChildren().remove(node);
                    }
                     List<Object> listnode1 = new ArrayList<Object>();
                    for (Node node : FXML_ClothesStoreController._vbox_mini.getChildren()) {
                        if (node instanceof JFXButton) {
                            if (node.getId().equals("btnTonKho") || node.getId().equals("btnHoaDonMuaHang")
                                    || node.getId().equals("btnBieuDo")
                                    || node.getId().equals("btnQuanLyTK")) {
                                listnode1.add(node);
                            }
                        }
                    }
                    for (Object node : listnode1) {
                        FXML_ClothesStoreController._vbox_mini.getChildren().remove(node);
                    }
                }
            } else {
                lbError.setText("Tên tài khoản hoặc mật khẩu không chính xác!");
            }
        } else {
            preference.put("userID", "");
            preference.put("password", "");
        }
    }

    private void handleValidation() {
        RequiredFieldValidator fieldValidator = new RequiredFieldValidator();
        fieldValidator.setMessage("Tài khoản không thể trống");
        fieldValidator.setIcon(new FontAwesomeIconView(FontAwesomeIcon.TIMES));
        txtUser.getValidators().add(fieldValidator);
        txtPass.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oldVal, Boolean newVal) -> {
            if (newVal) {
                txtUser.validate();
            }

        });
        RequiredFieldValidator fieldValidator2 = new RequiredFieldValidator();
        fieldValidator2.setMessage("Mật khẩu không thể trống");
        fieldValidator2.setIcon(new FontAwesomeIconView(FontAwesomeIcon.TIMES));
        txtPass.getValidators().add(fieldValidator2);
        txtPass.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                txtPass.validate();
            }
        });

    }

    private void loadSplashScreen() {
        try {
            Parent pane = FXMLLoader.load(getClass().getResource("/clothesstore_view/SplashFXML.fxml"));
            Scene scene = new Scene(pane);
            scene.setFill(Color.TRANSPARENT);
            stageSplash = new Stage();
            stageSplash.initStyle(StageStyle.TRANSPARENT);
            
            stageSplash.setScene(scene);            
            stageSplash.show();

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(2.5), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(2.5), pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();

            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });

            fadeOut.setOnFinished((e) -> {
                stageMain.show();
                stageSplash.close();
            });

        } catch (IOException ex) {
            Logger.getLogger(FXML_DangNhapController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void exit(ActionEvent event) {
        ButtonType yes = new ButtonType("Thoát", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Huỷ", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Bạn có chắc chắn muốn thoát",
                yes,
                cancel);

        alert.setTitle("Nhắc nhở");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        String user = txtUser.getText().trim();
        String password = txtPass.getText().trim();
        if (result.isPresent() && result.get() == yes) {

            if (!user.equals("") && !password.equals("")) {
                TaiKhoan tk = new TaiKhoan(user, password);
                if (tk.CheckLogin()) {
                    if (checkbox_remember.isSelected()) {
                        preference.put("userID", user);
                        preference.put("password", password);
                    } else {
                        preference.put("userID", "");
                        preference.put("password", "");
                    }
                }
            }
            System.exit(0);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        handleValidation();
        preference = Preferences.userNodeForPackage(FXML_DangNhapController.class);
        if (preference != null) {
            if (preference.get("userID", null) != null && !preference.get("userID", null).isEmpty()) {
                txtUser.setText(preference.get("userID", null));
                txtPass.setText(preference.get("password", null));
                checkbox_remember.setSelected(true);
            }

        }
    }

}
