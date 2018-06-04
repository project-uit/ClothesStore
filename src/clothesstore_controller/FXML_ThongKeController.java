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
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

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
    private JFXTextField year;

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

            Integer nam = cmb_nam.getSelectionModel().getSelectedItem();
            Integer quy = cmb_quy.getSelectionModel().getSelectedItem();
            if (nam == null || quy == null) {
                return;
            }
            ThongKe.pdf_thongkesp_banchay_trongquy(piechart_thongkesp_banchay,
                    "Thống kê 5 sản phẩm bán chạy trong quý " + quy + " năm " + nam, quy, nam);
        });
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
        for (int i = 0; i < 12; i++) {
            data.getData().add(new XYChart.Data<>("Tháng " + (i + 1), (Number) dt.get(i)));
        }

        chartDoanhThu.getData().setAll(data);
    }

    @FXML
    private void Handler_btnPrint() {
        Object nam = cmbYear.getSelectionModel().getSelectedItem();
        if (nam == null) {
            return;
        }
        ThongKe.pdf_thongke_doanhthu(chartDoanhThu, "Thống kê doanh thu trong năm " + nam, Integer.valueOf(nam + ""));
    }

    @FXML
    private PieChart piechart_thongkesp_banchay;
    @FXML
    private JFXComboBox<Integer> cmb_nam;
    @FXML
    private JFXComboBox<Integer> cmb_quy;
    @FXML
    private JFXButton btn_xuatbaocao;

    private String tenbd = "Thống kê năm sản phẩm bán chạy nhất trong ";

    private void piechart_thongkesp_banchay_load() {
        if (!piechart_thongkesp_banchay.getData().isEmpty()) {
            piechart_thongkesp_banchay.getData().clear();
        }
        piechart_thongkesp_banchay.setTitle(tenbd + "Năm " + cmb_nam.getSelectionModel().getSelectedItem()
                + " Quý " + cmb_quy.getSelectionModel().getSelectedItem());
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
                return "Quý " + object;
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
    private NumberAxis NAxis;
    @FXML
    private CategoryAxis CAxis;
    @FXML
    private JFXComboBox<Integer> Cmb_year;
    @FXML
    private AnchorPane APchart_nhomhang;
    @FXML
    private BarChart<String, Number> chartnhomhang;
    @FXML
    private JFXComboBox<Integer> cmb_month;

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

    @FXML
    private void Handler_XuatThongkeNhomHang(ActionEvent event) {
        Integer nam = Cmb_year.getSelectionModel().getSelectedItem();
        Integer thang = cmb_month.getSelectionModel().getSelectedItem();
        if (nam == null) {
            return;
        }
        ThongKe.pdf_thongke_nhomhang(chartnhomhang, "Thống kê số lượng của từng nhóm hàng", nam, thang);
    }

    private void Init_cmbMonth() {
        for (int i = 0; i <= 12; i++) {
            cmb_month.getItems().add(i);
        }
        cmb_month.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                if (object == 0) {
                    return "Tất cả";
                }
                return "Tháng " + object;
            }

            @Override
            public Integer fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        cmb_month.getSelectionModel().selectFirst();
        HoaDon hd = new HoaDon();
        hd.load_cmb_year(Cmb_year);
        Cmb_year.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return "Năm " + object.toString();
            }

            @Override
            public Integer fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        Cmb_year.getSelectionModel().selectFirst();
        Cmb_year.setOnAction(e -> {
            Integer _year = Cmb_year.getSelectionModel().getSelectedItem();
            if (cmb_month.getSelectionModel().getSelectedItem() == 0) {
                setChartYear(_year);
            } else {
                Integer month = cmb_month.getSelectionModel().getSelectedItem();
                setChartMonth(_year, month);
            }
            setMaxBarWidth(30, 10);
        });
        cmb_month.setOnAction(e -> {
            Integer _year = Cmb_year.getSelectionModel().getSelectedItem();
            if (cmb_month.getSelectionModel().getSelectedItem() == 0) {
                setChartYear(_year);

            } else {
                Integer month = cmb_month.getSelectionModel().getSelectedItem();
                setChartMonth(_year, month);
            }

        });
        setMaxBarWidth(40, 10);
        chartnhomhang.widthProperty().addListener((obs, b, b1) -> {
            setMaxBarWidth(40, 10);
            Platform.runLater(() -> setMaxBarWidth(40, 10));
        });
        CAxis.setLabel("Tên nhóm hàng");
        NAxis.setLabel("Số lượng");
        Integer _year = Cmb_year.getSelectionModel().getSelectedItem();
        setChartYear(_year);

    }

    private void setChartYear(Integer year) {
        if (year == null) {
            return;
        }
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
    }

    private void setChartMonth(Integer year, Integer month) {
        if (year == null || month == null) {
            return;
        }
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
    }
}
