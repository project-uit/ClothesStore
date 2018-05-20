/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
public class TonKho {

    public static Integer getsoluongtonkho() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        int soluong = 0;
        if (con != null) {
            try (
                    Statement stmnt = con.createStatement();
                    ResultSet rs = stmnt.executeQuery("select sum(soluong) from chitietsanpham");) {
                while (rs.next()) {
                    soluong = rs.getInt(1);
                }
            } catch (SQLException ex) {

            }

        }
        return soluong;
    }

    public static Integer getvontonkho() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        int giatritonkho = 0;
        if (con != null) {
            String query = "select giavon*(select sum(soluong) from chitietsanpham ctsp "
                    + "where ctsp.masanpham=sp.masanpham), ctpn.maphieunhap "
                    + "from chitietphieunhap ctpn,phieunhap pn, sanpham sp "
                    + "where ctpn.maphieunhap = pn.maphieunhap "
                    + "and sp.masanpham=ctpn.masanpham "
                    + "and ctpn.maphieunhap = "
                    + "(SELECT ctpn1.maphieunhap "
                    + "FROM chitietphieunhap ctpn1,phieunhap pn1 "
                    + "WHERE  ctpn1.maphieunhap = pn1.maphieunhap  and ctpn1.masanpham = ctpn.masanpham            \n"
                    + "ORDER BY ctpn1.maphieunhap DESC "
                    + "LIMIT 1);";
            try (
                    Statement stmnt = con.createStatement();
                    ResultSet rs = stmnt.executeQuery(query);) {
                while (rs.next()) {
                    giatritonkho += rs.getInt(1);
                }
            } catch (SQLException ex) {

            }
        }
        return giatritonkho;
    }

    public static Integer getgiatritonkho() {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        int giatritonkho = 0;
        if (con != null) {
            try (
                    Statement stmnt = con.createStatement();
                    ResultSet rs = stmnt.executeQuery("select sum(giaban*soluong) "
                            + "from sanpham sp,chitietsanpham ctsp "
                            + "where ctsp.masanpham =sp.masanpham");) {
                while (rs.next()) {
                    giatritonkho = rs.getInt(1);
                }
            } catch (SQLException ex) {

            }
        }
        return giatritonkho;
    }

    public static void loadtable(TableView tableview, int chucnang) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        if (con != null) {
            try {
                String call = "{call tonkho_hangton(?)}";
                CallableStatement stmnt = con.prepareCall(call);
                stmnt.setInt(1, chucnang);
                ResultSet rs = stmnt.executeQuery();
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    final int j = i;
                    TableColumn col = new TableColumn("" + i);
                    col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                            return new ReadOnlyObjectWrapper(param.getValue().get(j));
                        }
                    });
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
