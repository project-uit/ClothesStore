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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 *
 * @author quochung
 */
public class LoadTableFromDB {

    private String query;

    public LoadTableFromDB(String query) {
        this.query = query;
    }

    public LoadTableFromDB() {
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void LoadTable(TableView tableview) {
        DBConnection db = new DBConnection();
        Connection con = db.getConnecttion();
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        if (con != null) {
            try {
                Statement stmnt = con.createStatement();
                ResultSet rs = stmnt.executeQuery(query);
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
