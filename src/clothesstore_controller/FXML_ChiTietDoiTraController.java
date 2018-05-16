/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import clothesstore_model.DoiTra;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author dieunguyen
 */
public class FXML_ChiTietDoiTraController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private DoiTra doitra;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    public void setDT(DoiTra dt){
        doitra = dt;
    }
}
