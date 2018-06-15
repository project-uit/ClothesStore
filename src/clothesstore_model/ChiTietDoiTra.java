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
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ChiTietDoiTra {

    private IntegerProperty mahoadon;
    private IntegerProperty soluongmua;
    private IntegerProperty thanhtien;
    private StringProperty machitietsanpham;
    private StringProperty tensanpham;
    private IntegerProperty giaban;

    public ChiTietDoiTra() {
    }

    public List<String> getListMaCTSPfromHD(int MaHD) {
        List<String> list = new ArrayList();
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT * FROM chitiethoadon WHERE mahoadon = " + MaHD + "";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    list.add(rs.getString("machitietsanpham"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public int getMAXctsp(int MaHD, String MaCTSP) {
        int max=0;
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT soluongmua FROM chitiethoadon "
                + "WHERE mahoadon = " + MaHD + " and machitietsanpham = '"+MaCTSP+"'";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    max = rs.getInt("soluongmua");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return max;
    }

    public boolean updateHDkhiDoiTra1(int mahd, String mactsp, int sl, int tt) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "UPDATE chitiethoadon set soluongmua = soluongmua - ?, thanhtien = thanhtien - ? where machitietsanpham = ? and mahoadon = ?;";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setInt(1, sl);
                ptm.setInt(2, tt);
                ptm.setString(3, mactsp);
                ptm.setInt(4, mahd);

                ptm.execute();

                ptm.close();
                con.close();

                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean updateHDkhiDoiTra2(int mahd, String mactsp, int sl, int tt) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "UPDATE chitiethoadon set soluongmua = soluongmua + ?, thanhtien = thanhtien + ? where machitietsanpham = ? and mahoadon = ?;";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setInt(1, sl);
                ptm.setInt(2, tt);
                ptm.setString(3, mactsp);
                ptm.setInt(4, mahd);

                ptm.execute();

                ptm.close();
                con.close();

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean updateHDkhiDoiTra_removeSL0(int mahd) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "DELETE from chitiethoadon where soluongmua = 0 and mahoadon = ?;";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setInt(1, mahd);

                ptm.execute();

                ptm.close();
                con.close();

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public ChiTietDoiTra(IntegerProperty mahoadon, StringProperty machitietsanpham, IntegerProperty soluongmua, IntegerProperty thanhtien) {
        this.mahoadon = mahoadon;
        this.machitietsanpham = machitietsanpham;
        this.soluongmua = soluongmua;
        this.thanhtien = thanhtien;
    }

    public ChiTietDoiTra(IntegerProperty mahoadon, StringProperty machitietsanpham, StringProperty tensanpham, IntegerProperty soluongmua, IntegerProperty giaban, IntegerProperty thanhtien) {
        this.mahoadon = mahoadon;
        this.machitietsanpham = machitietsanpham;
        this.tensanpham = tensanpham;
        this.soluongmua = soluongmua;
        this.giaban = giaban;
        this.thanhtien = thanhtien;
    }

    public ChiTietDoiTra(StringProperty machitietsanpham, StringProperty tensanpham, IntegerProperty soluongmua, IntegerProperty giaban) {
        this.machitietsanpham = machitietsanpham;
        this.tensanpham = tensanpham;
        this.soluongmua = soluongmua;
        this.giaban = giaban;
    }

    public IntegerProperty getMahoadon() {
        return mahoadon;
    }

    public StringProperty getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(StringProperty tensanpham) {
        this.tensanpham = tensanpham;
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

    public IntegerProperty getGiaban() {
        return giaban;
    }

    public void setGiaban(IntegerProperty giaban) {
        this.giaban = giaban;
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

    public ObservableList<ChiTietDoiTra> getListChiTietDoiTraFromID(int id) {
        ObservableList<ChiTietDoiTra> list = FXCollections.observableArrayList();
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "SELECT * FROM chitietdoitra, chitietsanpham, sanpham "
                + "Where chitietdoitra.machitietsanpham = chitietsanpham.machitietsanpham "
                + "and sanpham.masanpham = chitietsanpham.masanpham "
                + "and madoitra = " + id + "";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    ChiTietDoiTra ctdt = new ChiTietDoiTra(
                            new SimpleStringProperty(rs.getString("machitietsanpham")),
                            new SimpleStringProperty(rs.getString("tensanpham")),
                            new SimpleIntegerProperty(rs.getInt("soluong")),
                            new SimpleIntegerProperty(rs.getInt("giaban"))
                    );
                    list.add(ctdt);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public ObservableList getCTHDfromMaHD(int mahd) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        ObservableList data = FXCollections.observableArrayList();
        if (con != null) {
            try {
                String query = "select ctsp.machitietsanpham, sp.tensanpham,"
                        + "cthd.soluongmua,sp.giaban,cthd.thanhtien\n"
                        + "from chitiethoadon cthd, chitietsanpham ctsp, sanpham sp\n"
                        + "where cthd.machitietsanpham = ctsp.machitietsanpham and ctsp.masanpham = sp.masanpham\n"
                        + "and  cthd.mahoadon = " + mahd + ";";
                try (Statement stmnt = con.createStatement()) {
                    ResultSet rs = stmnt.executeQuery(query);
                    while (rs.next()) {

                        ChiTietDoiTra cthd = new ChiTietDoiTra(
                                new SimpleIntegerProperty(mahd),
                                new SimpleStringProperty(rs.getString(1)),
                                new SimpleStringProperty(rs.getString(2)),
                                new SimpleIntegerProperty(rs.getInt(3)),
                                new SimpleIntegerProperty(rs.getInt(4)),
                                new SimpleIntegerProperty(rs.getInt(5)));
                        data.add(cthd);
                    }
                }
                con.close();
            } catch (SQLException ex) {

            }
        }
        return data;
    }

    public boolean ThemChiTietDoiTra(int madoitra) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        String sql = "INSERT INTO chitietdoitra(madoitra , machitietsanpham , soluong ) VALUES(?, ?, ?);";
        if (con != null) {
            try {
                PreparedStatement ptm = con.prepareStatement(sql);
                ptm.setInt(1, madoitra);
                ptm.setString(2, machitietsanpham.get());
                ptm.setInt(3, soluongmua.get());
                ptm.execute();

                ptm.close();
                con.close();

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

//    public boolean updateSoLuong(String mactsp, int sl) {
//        DBConnection db = new DBConnection();
//        Connection con = db.getConnecttion();
//        String sql = "UPDATE chitietsanpham set soluong = soluong - ? where machitietsanpham = ?;";
//        if (con != null) {
//            try {
//                PreparedStatement ptm = con.prepareStatement(sql);
//                ptm.setInt(1, sl);
//                ptm.setString(2, mactsp);
//                ptm.execute();
//
//                ptm.close();
//                con.close();
//
//                return true;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return false;
//    }
}
