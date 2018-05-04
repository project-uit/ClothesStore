/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import clothesstore_model.HoaDon;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * FXML Controller class
 *
 * @author dieunguyen
 */
public class FXML_ThongKeController implements Initializable {

    @FXML
    private BarChart<String, Number> chartDoanhThu;
    @FXML
    CategoryAxis x;
    @FXML
    NumberAxis y;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initChartDoanhThu();
    }

    private void initChartDoanhThu() {  
        HoaDon hd = new HoaDon();
        List dt = new ArrayList<>();
        dt = hd.getDoanhThu12months();
        XYChart.Series<String, Number> data = new XYChart.Series<>();
        data.setName("Doanh thu");
        data.getData().add(new XYChart.Data<>("Tháng 1", (Number) dt.get(0)));
        data.getData().add(new XYChart.Data<>("Tháng 2", (Number) dt.get(1)));
        data.getData().add(new XYChart.Data<>("Tháng 3", (Number) dt.get(2)));
        data.getData().add(new XYChart.Data<>("Tháng 4", (Number) dt.get(3)));
        data.getData().add(new XYChart.Data<>("Tháng 5", (Number) dt.get(4)));
        data.getData().add(new XYChart.Data<>("Tháng 6", (Number) dt.get(5)));
        data.getData().add(new XYChart.Data<>("Tháng 7", (Number) dt.get(6)));
        data.getData().add(new XYChart.Data<>("Tháng 8", (Number) dt.get(7)));
        data.getData().add(new XYChart.Data<>("Tháng 9", (Number) dt.get(8)));
        data.getData().add(new XYChart.Data<>("Tháng 10",(Number) dt.get(9)));
        data.getData().add(new XYChart.Data<>("Tháng 11",(Number) dt.get(10)));
        data.getData().add(new XYChart.Data<>("Tháng 12",(Number) dt.get(11)));   
        chartDoanhThu.getData().addAll(data);
    }
}
