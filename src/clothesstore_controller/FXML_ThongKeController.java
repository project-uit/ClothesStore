/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clothesstore_controller;

import clothesstore_model.HoaDon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import clothesstore_model.ThongKe;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
    @FXML
    private ComboBox cmbYear;
    @FXML
    private JFXButton btnPrint;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initCbbYear();
        InitCmb_thongkesp_banchay();
        piechart_thongkesp_banchay_load();
        btn_xuatbaocao.setOnAction(e -> {
            exportExcel_thongkesp_banchay();
        });
        lb_ten_pie.setText(tenbd + "Năm " + cmb_nam.getSelectionModel().getSelectedItem()
                + " Quý " + cmb_quy.getSelectionModel().getSelectedItem());
    }

    private void initCbbYear() {
        HoaDon hd = new HoaDon();
        hd.load_cmb_year_(cmbYear);
        cmbYear.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                int y = Integer.valueOf(t1);
                chartDoanhThu.setTitle("Thống kê doanh thu trong năm " + t1);
                chartDoanhThu.getData().clear();
                initChartDoanhThu(y);
            }
        });
        try {
            cmbYear.getSelectionModel().select("" + Collections.max(cmbYear.getItems()));
        } catch (Exception e) {
        }

    }

    private void initChartDoanhThu(Integer year) {
        HoaDon hd = new HoaDon();
        List dt = new ArrayList<>();
        dt = hd.getDoanhThu12months(year);
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
        data.getData().add(new XYChart.Data<>("Tháng 10", (Number) dt.get(9)));
        data.getData().add(new XYChart.Data<>("Tháng 11", (Number) dt.get(10)));
        data.getData().add(new XYChart.Data<>("Tháng 12", (Number) dt.get(11)));
        chartDoanhThu.getData().setAll(data);
    }

    @FXML
    private void Handler_btnPrint() {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
        double scaleX = pageLayout.getPrintableWidth() / chartDoanhThu.getBoundsInParent().getWidth();
        double scaleY = pageLayout.getPrintableHeight() / chartDoanhThu.getBoundsInParent().getHeight();
        Scale scale = new Scale(scaleX, scaleY);
        chartDoanhThu.getTransforms().add(scale);

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean success = job.printPage(chartDoanhThu);
            if (success) {
                job.endJob();
                chartDoanhThu.getTransforms().remove(scale);
            }
        }
    }

    @FXML
    private PieChart piechart_thongkesp_banchay;
    @FXML
    private JFXComboBox<Integer> cmb_nam;
    @FXML
    private JFXComboBox<Integer> cmb_quy;
    @FXML
    private JFXButton btn_xuatbaocao;
    @FXML
    private Label lb_ten_pie;
    private String tenbd = "Biểu đồ tròn thống kê năm sản phẩm bán chạy nhất trong ";

    private void piechart_thongkesp_banchay_load() {
        if (!piechart_thongkesp_banchay.getData().isEmpty()) {
            piechart_thongkesp_banchay.getData().clear();
        }
        Integer nam = cmb_nam.getSelectionModel().getSelectedItem();
        Integer quy = cmb_quy.getSelectionModel().getSelectedItem();
        if (nam == null) {
            return;
        }
        HashMap<String, Integer> hm_sp = ThongKe.thongke_sp_banchay(quy, nam);
        Integer tongsl = ThongKe.tongsp_daban(quy, nam);
        Set set = hm_sp.entrySet();
        Iterator i = set.iterator();
        if (tongsl > 0) {
            Integer tongsl_5sp = 0;
            while (i.hasNext()) {
                Map.Entry<String, Integer> me = (Map.Entry) i.next();
                tongsl_5sp += me.getValue();
                PieChart.Data slice = new PieChart.Data(me.getKey(), me.getValue());
                piechart_thongkesp_banchay.getData().add(slice);
            }
            if ((tongsl - tongsl_5sp) > 0) {
                PieChart.Data slice = new PieChart.Data("SP khác", (tongsl - tongsl_5sp));
                piechart_thongkesp_banchay.getData().add(slice);
            }
            piechart_thongkesp_banchay.setLegendSide(Side.LEFT);
        }
        lb_ten_pie.setText(tenbd + "Năm " + cmb_nam.getSelectionModel().getSelectedItem()
                + " Quý " + cmb_quy.getSelectionModel().getSelectedItem());
    }

    private void exportExcel_thongkesp_banchay() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel file 2003(.xls)", ".xls"),
                new FileChooser.ExtensionFilter("Excel file 2007(.xlsx)", ".xlsx")
        );
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                Integer nam = cmb_nam.getSelectionModel().getSelectedItem();
                Integer quy = cmb_quy.getSelectionModel().getSelectedItem();
                XSSFWorkbook wb = ThongKe.export_thongkesp_banchay_trongquy(quy, nam);
                String path_name_File = file.getPath();
                FileOutputStream output = new FileOutputStream(path_name_File
                        + fileChooser.getSelectedExtensionFilter().getExtensions().get(0));
                wb.write(output);
                output.close();

            } catch (FileNotFoundException ex) {
                Logger.getLogger(FXML_SanPhamController.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (IOException ex) {
                Logger.getLogger(FXML_SanPhamController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void InitCmb_thongkesp_banchay() {
        HoaDon hd = new HoaDon();
        hd.load_cmb_year(cmb_nam);
        cmb_quy.getItems().add(1);
        cmb_quy.getItems().add(2);
        cmb_quy.getItems().add(3);
        cmb_quy.getItems().add(4);
        cmb_quy.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                if (object == 1) {
                    return "Quý 1";
                } else if (object == 2) {
                    return "Quý 2";
                } else if (object == 3) {
                    return "Quý 3";
                } else {
                    return "Quý 4";
                }
            }

            @Override
            public Integer fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        cmb_nam.setConverter(new StringConverter<Integer>() {

            @Override
            public String toString(Integer object) {
                return "Năm " + object.toString();
            }

            @Override
            public Integer fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        cmb_quy.getSelectionModel().selectFirst();
        cmb_nam.getSelectionModel().selectFirst();
        cmb_nam.setOnAction(e -> {
            piechart_thongkesp_banchay_load();
        });
        cmb_quy.setOnAction(e -> {
            piechart_thongkesp_banchay_load();
        });
    }
}
