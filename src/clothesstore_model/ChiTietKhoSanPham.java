/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author dieunguyen
 */
public class ChiTietKhoSanPham {

    private StringProperty machitietsp;
    private StringProperty tenchitietsp;
    private StringProperty soluong;

    public ChiTietKhoSanPham(StringProperty machitietsp, StringProperty tenchitietsp, StringProperty soluong) {
        this.machitietsp = machitietsp;
        this.tenchitietsp = tenchitietsp;
        this.soluong = soluong;
    }

    public ChiTietKhoSanPham(String machitietsp, String tenchitietsp, String soluong) {
        this.machitietsp = new SimpleStringProperty(machitietsp);
        this.tenchitietsp = new SimpleStringProperty(tenchitietsp);
        this.soluong = new SimpleStringProperty(soluong);
    }

    public StringProperty getMachitietsp() {
        return machitietsp;
    }

    public StringProperty getTenchitietsp() {
        return tenchitietsp;
    }

    public StringProperty getSoluong() {
        return soluong;
    }

    public void setMachitietsp(StringProperty machitietsp) {
        this.machitietsp = machitietsp;
    }

    public void setTenchitietsp(StringProperty tenchitietsp) {
        this.tenchitietsp = tenchitietsp;
    }

    public void setSoluong(StringProperty soluong) {
        this.soluong = soluong;
    }
    
}
