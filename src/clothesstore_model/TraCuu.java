/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 *
 * @author quochung
 */
public class TraCuu {

    private String query = "select  sp.masanpham,sp.tensanpham,sp.tennhomhang,sp.tennhasanxuat,"
            + "ctsp.tenmau,ctsp.gioitinh, ctsp.tensize,ctsp.soluong,sp.giaban "
            + "from sanpham sp "
            + "join chitietsanpham ctsp on sp.masanpham = ctsp.masanpham "
            + "where ctsp.soluong >=0 ";

    public TraCuu() {
    }

    public String getquery() {
        return query;
    }

   

    public FilteredList filterList_sanpham(String _query) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        if (con != null) {
            try {
                Statement stmnt = con.createStatement();
                ResultSet rs = stmnt.executeQuery(_query);
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
        return new FilteredList<>(data);
    }
}
