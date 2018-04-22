/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_model;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public ChiTietSanPham(String machitietsanpham, String masanpham, String mamau, String tensize, int gioitinh, int soluong) {
        this.machitietsanpham = new SimpleStringProperty(machitietsanpham);
        this.masanpham = new SimpleStringProperty(masanpham);
        this.tensize = new SimpleStringProperty(tensize);
        this.mamau = new SimpleStringProperty(mamau);
        this.gioitinh = new SimpleIntegerProperty(gioitinh);
        this.soluong = new SimpleIntegerProperty(soluong);
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

    public boolean updateSoLuongFromMaCTSP(String MaCTSP, int soluong) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            String query;
            query = "update chitietsanpham set soluong = ? where machitietsanpham = ?";
            try {
                PreparedStatement ptm = con.prepareStatement(query);
                ptm.setInt(1, soluong);
                ptm.setString(2, MaCTSP);

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

    public int getSoLuongFromMaCTSP(String MaCTSP) {
        int soluong = 0;
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT soluong FROM chitietsanpham WHERE machitietsanpham = ?";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setString(1, MaCTSP);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    soluong = rs.getInt("soluong");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ChiTietSanPham.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return soluong;
    }

    public ObservableList<ChiTietSanPham> getListCTSPFromMasp(String msp) {
        ObservableList<ChiTietSanPham> list = FXCollections.observableArrayList();
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT * FROM chitietsanpham where masanpham = '" + msp + "'";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    ChiTietSanPham ctsp = new ChiTietSanPham(rs.getString("machitietsanpham"),
                             rs.getString("masanpham"),
                             rs.getString("tensize"),
                             rs.getString("mamau"),
                             rs.getInt("gioitinh"),
                             rs.getInt("soluong"));
                    list.add(ctsp);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
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
                
                int Col_count = rs.getMetaData().getColumnCount();
                for (int i = 0; i < Col_count; i++) {
                    final int j = i;
                    TableColumn col = new TableColumn("" + i);
                    if (i == 3) {
                        col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                                if (param.getValue().get(j).equals("1")) {
                                    return new SimpleStringProperty("Nam");
                                } else {
                                    return new SimpleStringProperty("Nữ");
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

                System.out.println(""+ex);
            }

        }

    }
}
