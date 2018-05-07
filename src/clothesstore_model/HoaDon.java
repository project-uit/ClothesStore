/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_model;

import com.jfoenix.controls.JFXComboBox;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author quochung
 */
public class HoaDon {

    private IntegerProperty mahoadon;
    private IntegerProperty manhanvien;
    private StringProperty sodienthoai;
    private Date ngayban;
    private IntegerProperty tongtien;

    public HoaDon() {
    }

    public HoaDon(IntegerProperty mahoadon) {
        this.mahoadon = mahoadon;
    }

    public HoaDon(IntegerProperty mahoadon, IntegerProperty manhanvien, StringProperty sodienthoai, Date ngayban, IntegerProperty tongtien) {
        this.mahoadon = mahoadon;
        this.manhanvien = manhanvien;
        this.sodienthoai = sodienthoai;
        this.ngayban = ngayban;
        this.tongtien = tongtien;
    }

    public HoaDon(IntegerProperty mahoadon, IntegerProperty tongtien) {
        this.mahoadon = mahoadon;
        this.tongtien = tongtien;
    }

    public IntegerProperty getMahoadon() {
        return mahoadon;
    }

    public IntegerProperty getManhanvien() {
        return manhanvien;
    }

    public StringProperty getSodienthoai() {
        return sodienthoai;
    }

    public Date getNgayban() {
        return ngayban;
    }

    public IntegerProperty getTongtien() {
        return tongtien;
    }

    public void setMahoadon(IntegerProperty mahoadon) {
        this.mahoadon = mahoadon;
    }

    public void setManhanvien(IntegerProperty manhanvien) {
        this.manhanvien = manhanvien;
    }

    public void setSodienthoai(StringProperty sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public void setNgayban(Date ngayban) {
        this.ngayban = ngayban;
    }

    public void setTongtien(IntegerProperty tongtien) {
        this.tongtien = tongtien;
    }

    public boolean insert() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            String query = "insert into hoadon(manhanvien,ngayban)"
                    + " values(?,now())";
            try {
                PreparedStatement ptm = con.prepareStatement(query);
                ptm.setInt(1, manhanvien.get());
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

    public boolean checktongtien() {
        return tongtien.isEqualTo(0).get();
    }

    public String getngaythanhtoanhd() {
        String day = "";
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();

        if (con != null) {
            String query = "select DATE_FORMAT(ngayban, '%d/%m/%Y %T') "
                    + "from hoadon where mahoadon = " + mahoadon.get();
            try {
                PreparedStatement ptm = con.prepareStatement(query);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    day = rs.getString(1);
                }
                ptm.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("" + ex);
            }
        }
        return day;
    }

    public Integer getMaHD() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        Integer mhd = 0;
        if (con != null) {
            String query = "select mahoadon from hoadon where tongtien is null";
            try {
                PreparedStatement ptm = con.prepareStatement(query);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    mhd = rs.getInt(1);
                }
                ptm.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("" + ex);
            }
        }
        return mhd;
    }

    public boolean delete() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            String query = "delete from hoadon "
                    + "where mahoadon= ?";
            try {
                PreparedStatement ptm = con.prepareStatement(query);
                ptm.setInt(1, mahoadon.get());
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

    public Integer tongtien(Integer mahoadon) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        Integer sum = 0;
        if (con != null) {
            String query = "select sum(thanhtien) from chitiethoadon "
                    + "where mahoadon= ?";
            try {
                PreparedStatement ptm = con.prepareStatement(query);
                ptm.setInt(1, mahoadon);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    sum = rs.getInt(1);
                }
            } catch (SQLException ex) {
                System.out.println("" + ex);
            }
        }
        return sum;
    }

    public boolean UpdateTongtien() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            String query = "update hoadon set tongtien=? "
                    + "where mahoadon = ?";
            try {
                PreparedStatement ptm = con.prepareStatement(query);
                ptm.setInt(1, tongtien.get());
                ptm.setInt(2, mahoadon.get());
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

    public void inhoadon() {
        try {
            //fix path
            String path = "invoice_report.jrxml";
            JasperReport jr = JasperCompileManager.compileReport(path);
            HashMap<String, Object> para = new HashMap<>();
            CuaHang ch = CuaHang.getObject();
            para.put("tencuahang", ch.getTencuahang().get());
            para.put("tennhanvien", "Hizen");
            para.put("ngayban", getngaythanhtoanhd());
            para.put("mahoadon", "" + mahoadon.get());
            para.put("diachi", ch.getDiachi().get());
            para.put("sodienthoai", ch.getSodienthoai().get());
            para.put("email", ch.getEmail().get());
            para.put("tongtien", "" + tongtien.get());
            ChiTietHoaDon cthd = new ChiTietHoaDon();
            cthd.setMahoadon(mahoadon);
            ObservableList<ObservableList> data = cthd.getListCTHD();
            ArrayList<HashMap<String, Object>> list = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                HashMap<String, Object> paras = new HashMap<>();
                String tensp = data.get(i).toString().split(",")[0].substring(1);
                String soluong = data.get(i).toString().split(",")[1].substring(1);
                String giaban = data.get(i).toString().split(",")[2].substring(1);
                int n = data.get(i).toString().split(",")[3].length();
                String thanhtien = data.get(i).toString().split(",")[3].substring(1, n - 1);
                paras.put("tensp", tensp);
                paras.put("soluong", soluong);
                paras.put("giaban", giaban);
                paras.put("thanhtien", thanhtien);
                list.add(paras);
            }

            JRBeanCollectionDataSource jcs = new JRBeanCollectionDataSource(list);
            JasperPrint jp = JasperFillManager.fillReport(jr, para, jcs);
            JasperViewer.viewReport(jp, false);

        } catch (JRException ex) {
            Logger.getLogger(HoaDon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List getDoanhThu12months(Integer year) {
        List doanhthu12months = new ArrayList();
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            int i = 1;
            while (i <= 12) {
                String query = "select sum(tongtien) from hoadon "
                        + "where MONTH(ngayban)= ? and YEAR(ngayban) = ?";
                try {
                    PreparedStatement ptm = con.prepareStatement(query);
                    ptm.setInt(1, i);
                    ptm.setInt(2, year);
                    ResultSet rs = ptm.executeQuery();
                    while (rs.next()) {
                        Integer sum = rs.getInt(1);
                        doanhthu12months.add(sum);
                    }
                    i++;
                } catch (SQLException ex) {
                    System.out.println("" + ex);
                }
            }
        }
        return doanhthu12months;
    }

    public Integer getActivities_TienBanHang(String time) {
        Integer tienbanhang = 0;
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            String query = "";
            switch (time) {
                case "homnay":
                    query = "select sum(tongtien) from hoadon where DATE(ngayban) = CURDATE();";
                    break;
                case "tuannay":
                    query = "select sum(tongtien) from hoadon where YEARWEEK(ngayban) = YEARWEEK(NOW());";
                    break;
                case "tuantruoc":
                    query = "select sum(tongtien) from hoadon where YEARWEEK(ngayban) = YEARWEEK(NOW()) - 1;";
                    break;
                case "thangnay":
                    query = "select sum(tongtien) from hoadon where month(ngayban) = month(NOW());";
                    break;
                case "thangtruoc":
                    query = "select sum(tongtien) from hoadon where month(ngayban) = month(NOW()) - 1;";
                    break;
            }
            try {
                PreparedStatement ptm = con.prepareStatement(query);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    tienbanhang = rs.getInt(1);
                }
            } catch (SQLException ex) {
                System.out.println("" + ex);
            }
        }
        return tienbanhang;
    }

    public Integer getActivities_TienSoDonHang(String time) {
        Integer sohoadon = 0;
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            String query = "";
            switch (time) {
                case "homnay":
                    query = "select count(mahoadon) from hoadon where DATE(ngayban) = CURDATE();";
                    break;
                case "tuannay":
                    query = "select count(mahoadon) from hoadon where YEARWEEK(ngayban) = YEARWEEK(NOW());";
                    break;
                case "tuantruoc":
                    query = "select count(mahoadon) from hoadon where YEARWEEK(ngayban) = YEARWEEK(NOW()) - 1;";
                    break;
                case "thangnay":
                    query = "select count(mahoadon) from hoadon where month(ngayban) = month(NOW());";
                    break;
                case "thangtruoc":
                    query = "select count(mahoadon) from hoadon where month(ngayban) = month(NOW()) - 1;";
                    break;
            }
            try {
                PreparedStatement ptm = con.prepareStatement(query);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    sohoadon = rs.getInt(1);
                }
            } catch (SQLException ex) {
                System.out.println("" + ex);
            }
        }
        return sohoadon;
    }

    public Integer getActivities_TienSoHangHoa(String time) {
        Integer sohanghoa = 0;
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            String query = "";
            switch (time) {
                case "homnay":
                    query = "select sum(soluongmua) from hoadon, chitiethoadon where hoadon.mahoadon = chitiethoadon.mahoadon and DATE(ngayban) = CURDATE();";
                    break;
                case "tuannay":
                    query = "select sum(soluongmua) from hoadon, chitiethoadon where hoadon.mahoadon = chitiethoadon.mahoadon and YEARWEEK(ngayban) = YEARWEEK(NOW());";
                    break;
                case "tuantruoc":
                    query = "select sum(soluongmua) from hoadon, chitiethoadon where hoadon.mahoadon = chitiethoadon.mahoadon and YEARWEEK(ngayban) = YEARWEEK(NOW())-1;";
                    break;
                case "thangnay":
                    query = "select sum(soluongmua) from hoadon, chitiethoadon where hoadon.mahoadon = chitiethoadon.mahoadon and month(ngayban) = month(NOW());";
                    break;
                case "thangtruoc":
                    query = "select sum(soluongmua) from hoadon, chitiethoadon where hoadon.mahoadon = chitiethoadon.mahoadon and month(ngayban) = month(NOW())-1;";
                    break;
            }
            try {
                PreparedStatement ptm = con.prepareStatement(query);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    sohanghoa = rs.getInt(1);
                }
            } catch (SQLException ex) {
                System.out.println("" + ex);
            }
        }
        return sohanghoa;
    }

    public Integer getCountHetHang() {
        Integer count = 0;
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            String query = "select count(machitietsanpham) from chitietsanpham where soluong = 0;";
            try {
                PreparedStatement ptm = con.prepareStatement(query);
                ResultSet rs = ptm.executeQuery();
                while (rs.next()) {
                    count = rs.getInt(1);
                }
            } catch (SQLException ex) {
                System.out.println("" + ex);
            }
        }
        return count;
    }

    public void load_cmb_year(JFXComboBox<Integer> cmb) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            try (
                    Statement stmnt = con.createStatement();
                    ResultSet rs = stmnt.executeQuery("select distinct year(ngayban) from hoadon");) {
                while (rs.next()) {
                    cmb.getItems().add(rs.getInt(1));
                }
            } catch (SQLException ex) {

            }

        }
    }
    
    public void load_cmb_year_(ComboBox cmb) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        if (con != null) {
            try (
                    Statement stmnt = con.createStatement();
                    ResultSet rs = stmnt.executeQuery("select distinct year(ngayban) from hoadon");) {
                while (rs.next()) {
                    cmb.getItems().add(rs.getInt(1));
                }
            } catch (SQLException ex) {

            }

        }
    }
}
