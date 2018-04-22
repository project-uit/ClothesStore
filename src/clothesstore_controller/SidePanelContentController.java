package clothesstore_controller;

import static clothesstore_controller.FXML_ClothesStoreController.rootP;
import static clothesstore_controller.FXML_DangNhapController.UserID;
import static clothesstore_controller.FXML_DangNhapController.stageMain;
import static clothesstore_view.ClothesStore._rootDangNhap;
import static clothesstore_view.ClothesStore.stageDangNhap;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


public class SidePanelContentController implements Initializable {

    @FXML
    private VBox vbox;
    public static VBox _vbox;
    
    @FXML
    private Label lbUser;
    @FXML
    private AnchorPane paneDrawer;
    @FXML
    private JFXButton btnTongQuan;
    @FXML
    private JFXButton btnDonHang;
    @FXML
    private JFXButton btnHangHoa;
    @FXML
    private JFXButton btnPhieuNhap;
    @FXML
    private JFXButton btnLoiNhuan;
    @FXML
    private JFXButton btnBieuDo;
    @FXML
    private JFXButton btnQuanLyTK;
    @FXML
    private JFXButton btnDangXuat;
    @FXML
    private JFXButton btnThoat;
    @FXML
    private JFXButton btnNhapKho;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //todo
        _vbox = vbox;
        lbUser.setText("Xin chào "+UserID);
    }    
    
    @FXML
    private void changeTab(ActionEvent event) throws IOException {
        JFXButton btn = (JFXButton) event.getSource();
        System.out.println(btn.getText());
        FXML_ClothesStoreController.rootP.getChildren().removeAll();
        switch(btn.getId())
        {
            case "btnTongQuan":       
                AnchorPane tongquan = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_TongQuan.fxml"));           
                FXML_ClothesStoreController.rootP.getChildren().setAll(tongquan); 
                rootP.setLeftAnchor(tongquan, 0.0);
                rootP.setRightAnchor(tongquan, 0.0);
                rootP.setTopAnchor(tongquan, 0.0);
                rootP.setBottomAnchor(tongquan, 0.0);
                break;
            case "btnDonHang":
                AnchorPane donhang = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_DonHang.fxml"));
                FXML_ClothesStoreController.rootP.getChildren().setAll(donhang);
                rootP.setLeftAnchor(donhang, 0.0);
                rootP.setRightAnchor(donhang, 0.0);
                rootP.setTopAnchor(donhang, 0.0);
                rootP.setBottomAnchor(donhang, 0.0);
                break;
            case "btnHangHoa":
                AnchorPane hanghoa = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_HangHoa.fxml"));
                FXML_ClothesStoreController.rootP.getChildren().setAll(hanghoa);
                rootP.setLeftAnchor(hanghoa, 0.0);
                rootP.setRightAnchor(hanghoa, 0.0);
                rootP.setTopAnchor(hanghoa, 0.0);
                rootP.setBottomAnchor(hanghoa, 0.0);
                break;
            case "btnPhieuNhap":
                AnchorPane phieunhap = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_PhieuNhap.fxml"));
                FXML_ClothesStoreController.rootP.getChildren().setAll(phieunhap);
                rootP.setLeftAnchor(phieunhap, 0.0);
                rootP.setRightAnchor(phieunhap, 0.0);
                rootP.setTopAnchor(phieunhap, 0.0);
                rootP.setBottomAnchor(phieunhap, 0.0);
                break;
            case "btnNhapKho":
                AnchorPane nhapkho = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_NhapKho.fxml"));
                FXML_ClothesStoreController.rootP.getChildren().setAll(nhapkho);
                rootP.setLeftAnchor(nhapkho, 0.0);
                rootP.setRightAnchor(nhapkho, 0.0);
                rootP.setTopAnchor(nhapkho, 0.0);
                rootP.setBottomAnchor(nhapkho, 0.0);
                break;
            case "btnQuanLyTK":
                AnchorPane QLTK = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_QuanLyTaiKhoan.fxml"));
                
                FXML_ClothesStoreController.rootP.getChildren().setAll(QLTK);
                rootP.setLeftAnchor(QLTK, 0.0);
                rootP.setRightAnchor(QLTK, 0.0);
                rootP.setTopAnchor(QLTK, 0.0);
                rootP.setBottomAnchor(QLTK, 0.0);
                
                //rootP.widthProperty().addListener( ( observable, oldValue, newValue ) -> FXML_QuanLyTaiKhoanController._QLNVpane.setPrefWidth( newValue.doubleValue() ) );
                //rootP.heightProperty().addListener( ( observable, oldValue, newValue ) -> FXML_QuanLyTaiKhoanController._QLNVpane.setPrefHeight( newValue.doubleValue() ) );
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
                    break;
                }           
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

        if (result.isPresent() && result.get() == yes) {
            System.exit(0);
        }            
    }
    
}
