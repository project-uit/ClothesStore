/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author dieunguyen
 */
public class ClothesStore extends Application {

    public static Stage stageDangNhap;
    public static AnchorPane _rootDangNhap;

    @Override
    public void start(Stage stage) throws Exception {

        AnchorPane root = FXMLLoader.load(getClass().getResource("/clothesstore_view/FXML_DangNhap.fxml"));
        Scene scene = new Scene(root);
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.getIcons().add(new Image("/clothesstore_view/img/logo.jpg"));
        stage.show();
        stageDangNhap = stage;
        _rootDangNhap = root;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
