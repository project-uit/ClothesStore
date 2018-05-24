/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
public class ChiTietHoaDon {

    private IntegerProperty machitiethoadon;
    private IntegerProperty mahoadon;
    private StringProperty machitietsanpham;
    private IntegerProperty soluongmua;
    private IntegerProperty thanhtien;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(IntegerProperty mahoadon, StringProperty machitietsanpham, IntegerProperty soluongmua, IntegerProperty thanhtien) {
        this.mahoadon = mahoadon;
        this.machitietsanpham = machitietsanpham;
        this.soluongmua = soluongmua;
        this.thanhtien = thanhtien;
    }

    public IntegerProperty getMachitiethoadon() {
        return machitiethoadon;
    }

    public IntegerProperty getMahoadon() {
        return mahoadon;
    }

    public StringProperty getMachitietsanpham() {
        return machitietsanpham;
    }

    public IntegerProperty getSoluongmua() {
        return soluongmua;
    }

    public IntegerProperty getThanhtien() {
        return thanhtien;
    }

    public void setMachitiethoadon(IntegerProperty machitiethoadon) {
        this.machitiethoadon = machitiethoadon;
    }

    public void setMahoadon(IntegerProperty mahoadon) {
        this.mahoadon = mahoadon;
    }

    public void setMachitietsanpham(StringProperty machitietsanpham) {
        this.machitietsanpham = machitietsanpham;
    }

    public void setSoluongmua(IntegerProperty soluongmua) {
        this.soluongmua = soluongmua;
    }

    public void setThanhtien(IntegerProperty thanhtien) {
        this.thanhtien = thanhtien;
    }

    public boolean isEmpty() {
        return soluongmua.isEqualTo(0).get();
    }

    public boolean insert() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            String query = "insert into chitiethoadon(mahoadon,machitietsanpham,soluongmua,thanhtien)"
                    + " values(?,?,?,?)";
            try {
                PreparedStatement ptm = con.prepareStatement(query);
                ptm.setInt(1, mahoadon.get());
                ptm.setString(2, machitietsanpham.get());
                ptm.setInt(3, soluongmua.get());
                ptm.setInt(4, thanhtien.get());
                int check = ptm.executeUpdate();
                if (check != 0) {
                    ptm.close();
                    con.close();
                    return true;
                }

            } catch (SQLException ex) {
                System.out.println("" + ex);
            }
        }
        return false;
    }

    public boolean delete() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            String query = "delete from chitiethoadon "
                    + "where machitiethoadon = ?";
            try {
                PreparedStatement ptm = con.prepareStatement(query);
                ptm.setInt(1, machitiethoadon.get());
                int check = ptm.executeUpdate();
                if (check != 0) {
                    ptm.close();
                    con.close();
                    return true;
                }

            } catch (SQLException ex) {
                System.out.println("" + ex);
            }
        }
        return false;
    }

    public ObservableList getListCTHD() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        if (con != null) {
            try {
                String query = "select sp.tensanpham,cthd.soluongmua,sp.giaban,cthd.thanhtien "
                        + "from chitiethoadon cthd, chitietsanpham ctsp, sanpham sp, hoadon hd "
                        + "where cthd.machitietsanpham=ctsp.machitietsanpham and sp.masanpham = ctsp.masanpham "
                        + "and cthd.mahoadon=hd.mahoadon and cthd.mahoadon = " + mahoadon.get();
                Statement stmnt = con.createStatement();
                ResultSet rs = stmnt.executeQuery(query);
                while (rs.next()) {
                    ObservableList<String> row = FXCollections.observableArrayList();
                    int columnCount = rs.getMetaData().getColumnCount();
                    for (int i = 1; i <= columnCount; i++) {
                        row.add(rs.getString(i));
                    }
                    data.add(row);
                }
                stmnt.close();
                con.close();
            } catch (SQLException ex) {

            }
        }
        return data;
    }
    private String FormatTien(int soTien) {
        return String.format("%,8d%n", soTien).trim();
    }
    public void LoadTable(TableView tableview) {

        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        String query = "select cthd.machitiethoadon, cthd.mahoadon, cthd.machitietsanpham"
                + ", cthd.soluongmua, sp.giaban, cthd.thanhtien "
                + "from chitiethoadon cthd, chitietsanpham ctsp, sanpham sp, hoadon hd "
                + "where cthd.machitietsanpham=ctsp.machitietsanpham and sp.masanpham = ctsp.masanpham "
                + "and cthd.mahoadon=hd.mahoadon and hd.tongtien is null";
        if (con != null) {
            try {
                Statement stmnt = con.createStatement();
                ResultSet rs = stmnt.executeQuery(query);
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    final int j = i;
                    TableColumn col = new TableColumn("" + i);
                    if (j == 4 || j == 5) {
                        col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                                return new ReadOnlyObjectWrapper(FormatTien(Integer.valueOf(param.getValue().get(j).toString())));
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
