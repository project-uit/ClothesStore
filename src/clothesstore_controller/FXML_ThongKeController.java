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
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
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
    @FXML
    private AnchorPane APchart_nhomhang;
    @FXML
    private BarChart<String, Number> chartnhomhang;
    @FXML
    private NumberAxis NAxis;
    @FXML
    private CategoryAxis CAxis;
    @FXML
    private JFXTextField year;
    @FXML
    private Label lblthang;
    @FXML
    private JFXComboBox<Integer> cmb_month;
    @FXML
    private JFXButton btnrefresh;
    @FXML
    private JFXCheckBox checkboxyear;
    @FXML
    private JFXCheckBox checkboxmonth;

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
            // exportExcel_thongkesp_banchay();
            Integer nam = cmb_nam.getSelectionModel().getSelectedItem();
            Integer quy = cmb_quy.getSelectionModel().getSelectedItem();
            ThongKe.pdf_thongkesp_banchay_trongquy(piechart_thongkesp_banchay,
                    "Thống kê 5 sản phẩm bán chạy trong quý " + quy + " năm " + nam, quy, nam);
        });
        lb_ten_pie.setText(tenbd + "Năm " + cmb_nam.getSelectionModel().getSelectedItem()
                + " Quý " + cmb_quy.getSelectionModel().getSelectedItem());
        Init_cmbMonth();
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
//        Printer printer = Printer.getDefaultPrinter();
//        PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
//        double scaleX = pageLayout.getPrintableWidth() / chartDoanhThu.getBoundsInParent().getWidth();
//        double scaleY = pageLayout.getPrintableHeight() / chartDoanhThu.getBoundsInParent().getHeight();
//        Scale scale = new Scale(scaleX, scaleY);
//        chartDoanhThu.getTransforms().add(scale);
//
//        PrinterJob job = PrinterJob.createPrinterJob();
//        if (job != null) {
//            boolean success = job.printPage(chartDoanhThu);
//            if (success) {
//                job.endJob();
//                chartDoanhThu.getTransforms().remove(scale);
//            }
//        }
        Object nam = cmbYear.getSelectionModel().getSelectedItem();
        ThongKe.pdf_thongke_doanhthu(chartDoanhThu, "Thống kê doanh thu trong năm "+nam, Integer.valueOf(nam+""));
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

    @FXML
    private void handler_refresh(ActionEvent event) {
        if (year.getText().isEmpty()) {
            ShowMessage
                    .showMessageBox(Alert.AlertType.WARNING, "Thông báo", null, "Bạn phải nhập năm")
                    .showAndWait();
            return;
        }
        if (checkboxyear.isSelected() == true) {
            setChartYear(Integer.valueOf(year.getText()));
        } else if (checkboxmonth.isSelected() == true) {
            setChartMonth(Integer.valueOf(year.getText()), Integer.valueOf(cmb_month.getValue()));
        }
    }

    @FXML
    private void handler_checkboxyear(ActionEvent event) {
        boolean isSelectedyear = checkboxyear.isSelected();
        if (isSelectedyear == true) {
            checkboxmonth.setSelected(false);
            lblthang.setVisible(false);
            cmb_month.setVisible(false);

        } else {
            checkboxmonth.setSelected(true);
            lblthang.setVisible(true);
            cmb_month.setVisible(true);
        }
    }

    @FXML
    private void handler_checkboxmonth(ActionEvent event) {
        boolean isSelectedmonth = checkboxmonth.isSelected();
        if (isSelectedmonth == true) {
            checkboxyear.setSelected(false);
            lblthang.setVisible(true);
            cmb_month.setVisible(true);
        } else {
            checkboxyear.setSelected(true);
            lblthang.setVisible(false);
            cmb_month.setVisible(false);
        }
    }

    private void setMaxBarWidth(double maxBarWidth, double minCategoryGap) {
        double barWidth = 0;
        do {
            double catSpace = CAxis.getCategorySpacing();
            double avilableBarSpace = catSpace - (chartnhomhang.getCategoryGap() + chartnhomhang.getBarGap());
            barWidth = (avilableBarSpace / chartnhomhang.getData().size()) - chartnhomhang.getBarGap();
            if (barWidth > maxBarWidth) {
                avilableBarSpace = (maxBarWidth + chartnhomhang.getBarGap()) * chartnhomhang.getData().size();
                chartnhomhang.setCategoryGap(catSpace - avilableBarSpace - chartnhomhang.getBarGap());
            }
        } while (barWidth > maxBarWidth);

        do {
            double catSpace = CAxis.getCategorySpacing();
            double avilableBarSpace = catSpace - (minCategoryGap + chartnhomhang.getBarGap());
            barWidth = Math.min(maxBarWidth,
                    (avilableBarSpace / chartnhomhang.getData().size()) - chartnhomhang.getBarGap());
            avilableBarSpace = (barWidth + chartnhomhang.getBarGap()) * chartnhomhang.getData().size();
            chartnhomhang.setCategoryGap(catSpace - avilableBarSpace - chartnhomhang.getBarGap());
        } while (barWidth < maxBarWidth && chartnhomhang.getCategoryGap() > minCategoryGap);
    }

    private void Init_cmbMonth() {
        cmb_month.getItems().add(1);
        cmb_month.getItems().add(2);
        cmb_month.getItems().add(3);
        cmb_month.getItems().add(4);
        cmb_month.getItems().add(5);
        cmb_month.getItems().add(6);
        cmb_month.getItems().add(7);
        cmb_month.getItems().add(8);
        cmb_month.getItems().add(9);
        cmb_month.getItems().add(10);
        cmb_month.getItems().add(11);
        cmb_month.getItems().add(12);
        cmb_month.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                if (object == 1) {
                    return "Tháng 1";
                } else if (object == 2) {
                    return "Tháng 2";
                } else if (object == 3) {
                    return "Tháng 3";
                } else if (object == 4) {
                    return "Tháng 4";
                } else if (object == 5) {
                    return "Tháng 5";
                } else if (object == 6) {
                    return "Tháng 6";
                } else if (object == 7) {
                    return "Tháng 7";
                } else if (object == 8) {
                    return "Tháng 8";
                } else if (object == 9) {
                    return "Tháng 9";
                } else if (object == 10) {
                    return "Tháng 10";
                } else if (object == 11) {
                    return "Tháng 11";
                } else {
                    return "Tháng 12";
                }
            }

            @Override
            public Integer fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        cmb_month.getSelectionModel().selectFirst();
    }

    private void setChartYear(Integer year) {
        chartnhomhang.getData().clear();
        XYChart.Series<String, Number> data = new XYChart.Series<>();
        data.setName("Số lượng từng nhóm hàng theo năm");
        HashMap<String, Integer> hm_sp = ThongKe.thongke_nhomhang_year(year);
        Set set = hm_sp.entrySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {
            Map.Entry<String, Integer> me = (Map.Entry) i.next();
            data.getData().add(new XYChart.Data<>(me.getKey(), me.getValue()));
        }
        chartnhomhang.getData().addAll(data);
        setMaxBarWidth(40, 10);
        chartnhomhang.widthProperty().addListener((obs, b, b1) -> {
            Platform.runLater(() -> setMaxBarWidth(40, 10));
        });
    }

    private void setChartMonth(Integer year, Integer month) {
        chartnhomhang.getData().clear();

        XYChart.Series<String, Number> data = new XYChart.Series<>();
        data.setName("Số lượng từng nhóm hàng theo tháng");
        HashMap<String, Integer> hm_sp = ThongKe.thongke_nhomhang_month(month, year);
        Set set = hm_sp.entrySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {
            Map.Entry<String, Integer> me = (Map.Entry) i.next();
            data.getData().add(new XYChart.Data<>(me.getKey(), me.getValue()));
        }
        chartnhomhang.getData().addAll(data);
        setMaxBarWidth(40, 10);
        chartnhomhang.widthProperty().addListener((obs, b, b1) -> {
            Platform.runLater(() -> setMaxBarWidth(40, 10));
        });
    }
}
