/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import javafx.scene.control.Alert;

/**
 *
 * @author quochung
 */
public class ShowMessage {
    /*
    Chỉ sử dụng cho việc xuất thông báo cho người dùng
    */
    public static Alert showMessageBox(Alert.AlertType type, String title, String headertext, String context) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headertext);
        alert.setContentText(context);
        return alert;
    }
}
