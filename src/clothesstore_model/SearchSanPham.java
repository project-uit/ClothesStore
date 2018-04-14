/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author 15520
 */
public class SearchSanPham {
    private StringProperty keysanpham;
    private IntegerProperty tensanpham;

    public static SearchSanPham newInstance() {
            return new SearchSanPham();
        }

    public SearchSanPham(String keysanpham, Integer tensanpham) {
        this.keysanpham =new SimpleStringProperty(keysanpham);
        this.tensanpham = new SimpleIntegerProperty(tensanpham);
    }
    
  
    
    
    public SearchSanPham keysanpham(String keysanpham) {
            this.keysanpham =new SimpleStringProperty(keysanpham);
            return this;
        }

        public SearchSanPham tensanpham(Integer tensanpham) {
            this.tensanpham =new SimpleIntegerProperty(tensanpham);
            return this;
        }
    public String getKeysanpham() {
        return keysanpham.getValue();
    }

    public Integer getTensanpham() {
        return tensanpham.getValue();
    }

    public SearchSanPham() {
    }

    public void setKeysanpham(StringProperty keysanpham) {
        this.keysanpham = keysanpham;
    }

    public void setTensanpham(IntegerProperty tensanpham) {
        this.tensanpham = tensanpham;
    }

}
