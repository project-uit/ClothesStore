/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_model;

import clothesstore_controller.ShowMessage;
import com.jfoenix.controls.JFXComboBox;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 *
 * @author quochung
 */
public class ChiTietSanPham {

    private StringProperty machitietsanpham;
    private StringProperty masanpham;
    private StringProperty tensize;
    private StringProperty mamau;
    private IntegerProperty gioitinh;
    private IntegerProperty soluong;

    public ChiTietSanPham() {
    }

    public ChiTietSanPham(StringProperty machitietsanpham) {
        this.machitietsanpham = machitietsanpham;
    }

    public ChiTietSanPham(StringProperty machitietsanpham, StringProperty masanpham, StringProperty tensize, StringProperty mamau, IntegerProperty gioitinh, IntegerProperty soluong) {
        this.machitietsanpham = machitietsanpham;
        this.masanpham = masanpham;
        this.tensize = tensize;
        this.mamau = mamau;
        this.gioitinh = gioitinh;
        this.soluong = soluong;
    }

    public ChiTietSanPham(StringProperty machitietsanpham, StringProperty masanpham, StringProperty tensize, StringProperty mamau, IntegerProperty gioitinh) {
        this.machitietsanpham = machitietsanpham;
        this.masanpham = masanpham;
        this.tensize = tensize;
        this.mamau = mamau;
        this.gioitinh = gioitinh;
    }

    public ChiTietSanPham(StringProperty machitietsanpham, IntegerProperty soluong) {
        this.machitietsanpham = machitietsanpham;
        this.soluong = soluong;
    }

    public void setMachitietsanpham(StringProperty machitietsanpham) {
        this.machitietsanpham = machitietsanpham;
    }

    public void setMasanpham(StringProperty masanpham) {
        this.masanpham = masanpham;
    }

    public void setSize(StringProperty tensize) {
        this.tensize = tensize;
    }

    public void setMausac(StringProperty mamau) {
        this.mamau = mamau;
    }

    public void setGioitinh(IntegerProperty gioitinh) {
        this.gioitinh = gioitinh;
    }

    public void setSoluong(IntegerProperty soluong) {
        this.soluong = soluong;
    }

    public StringProperty getMachitietsanpham() {
        return machitietsanpham;
    }

    public StringProperty getMasanpham() {
        return masanpham;
    }

    public StringProperty getSize() {
        return tensize;
    }

    public StringProperty getMausac() {
        return mamau;
    }

    public IntegerProperty getGioitinh() {
        return gioitinh;
    }

    public IntegerProperty getSoluong() {
        return soluong;
    }

    public boolean isEmpty() {
        return (tensize.get().isEmpty() || mamau.get().isEmpty());
    }

    public int insert() {

        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {

            String query = "insert into chitietsanpham(machitietsanpham,masanpham,tensize,mamau,gioitinh)"
                    + " values(?,?,?,?,?)";
            try {
                PreparedStatement ptm = con.prepareStatement(query);
                ptm.setString(1, machitietsanpham.get());
                ptm.setString(2, masanpham.get());
                ptm.setString(3, tensize.get());
                ptm.setString(4, mamau.get());
                ptm.setInt(5, gioitinh.get());
                int check = ptm.executeUpdate();
                if (check != 0) {
                    ptm.close();
                    con.close();
                    return 1;
                }

            } catch (MySQLIntegrityConstraintViolationException ex) {
                return 2;
            } catch (SQLException ex) {
                System.out.println("" + ex);
            }
        }
        return 0;
    }

    public boolean delete() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {

            String query = "delete from chitietsanpham where machitietsanpham = ?";

            try {
                PreparedStatement ptm = con.prepareStatement(query);

                ptm.setString(1, machitietsanpham.get());

                int check = ptm.executeUpdate();
                if (check != 0) {

                    ptm.close();
                    con.close();
                    return true;
                }

            } catch (SQLException ex) {

            }
        }
        return false;
    }

    public boolean update() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            String query;
            query = "update chitietsanpham set tensize=?, mamau=?,gioitinh=? where machitietsanpham = ?";
            try {
                PreparedStatement ptm = con.prepareStatement(query);
                ptm.setString(1, tensize.get());
                ptm.setString(2, mamau.get());
                ptm.setInt(3, gioitinh.get());
                ptm.setString(4, machitietsanpham.get());
                int check = ptm.executeUpdate();
                if (check != 0) {
                    ptm.close();
                    con.close();
                    return true;
                }

            } catch (SQLException ex) {

            }
        }
        return false;
    }

    public static List<ChiTietSanPham> getListCTSPFromMasp(String masp) {
        List<ChiTietSanPham> listctsp = new ArrayList();
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String query = "select * from chitietsanpham where masanpham = '" + masp + "'";
        if (con != null) {
            try {
                java.sql.Statement stmnt = con.createStatement();
                ResultSet rs = stmnt.executeQuery(query);
                while (rs.next()) {
                    StringProperty _machitietsanpham = new SimpleStringProperty(rs.getString(0));
                    StringProperty _masanpham = new SimpleStringProperty(rs.getString(1));
                    StringProperty _tensize = new SimpleStringProperty(rs.getString(2));
                    StringProperty _mamau = new SimpleStringProperty(rs.getString(3));
                    IntegerProperty _gioitinh = new SimpleIntegerProperty(rs.getInt(4));
                    IntegerProperty _sl = new SimpleIntegerProperty(rs.getInt(5));
                    ChiTietSanPham ctsp = 
                            new ChiTietSanPham(_machitietsanpham, _masanpham, _tensize, _mamau, _gioitinh, _sl);
                    listctsp.add(ctsp);

                }

                stmnt.close();
                con.close();
            } catch (SQLException ex) {

            }

        }
        return listctsp;
    }

    public void LoadTable(TableView tableview) {
        String query = "Select machitietsanpham,tensize,tenmau,gioitinh,mausac.mamau "
                + "from chitietsanpham,mausac "
                + "where chitietsanpham.mamau=mausac.mamau and masanpham = '" + masanpham.get() + "'";
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        if (con != null) {
            try {
                java.sql.Statement stmnt = con.createStatement();
                ResultSet rs = stmnt.executeQuery(query);
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    final int j = i;
                    TableColumn col = new TableColumn("" + i);
                    if (i == 3) {
                        col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                                if (param.getValue().get(j).equals("1")) {
                                    return new ReadOnlyObjectWrapper("Nam");
                                } else {
                                    return new ReadOnlyObjectWrapper("Nữ");
                                }

                            }
                        });
                    } else {
                        col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                                return new ReadOnlyObjectWrapper(param.getValue().get(j));
                            }
                        });
                    }
                    tableview.getColumns().addAll(col);
                }

                while (rs.next()) {
                    ObservableList<String> row = FXCollections.observableArrayList();
                    int columnCount = rs.getMetaData().getColumnCount();
                    for (int i = 1; i <= columnCount; i++) {
                        row.add(rs.getString(i));
                    }
                    data.add(row);
                }
                tableview.setItems(data);
                stmnt.close();
                con.close();
            } catch (SQLException ex) {

            }
        }
    }
}
