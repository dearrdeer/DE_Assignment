package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

public class exactChartController implements Initializable {
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private LineChart<Number, Number> chart;

    private int N;
    private double x0;
    private double X;
    private double y0;
    private double h;

    private double c1;


    public void initParam(){
        this.N = 2000;
        this.x0 = Controller.x0;
        this.X = Controller.X;
        this.y0 = Controller.y0;

        this.h = (X-x0)/N;
        this.c1 = (this.y0 - Math.sin(this.x0))/Math.cos(this.x0);

        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(Controller.xLowerBound);
        xAxis.setUpperBound(Controller.xUpperBound);
        xAxis.setTickUnit(Controller.xScale);

        yAxis.setTickUnit(Controller.yScale);

        chart.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        chart.setCreateSymbols(false);
    }

    public void plotChart(){
        XYChart.Series exactSeries = new XYChart.Series();

        double[] x = new double[N];
        double[] y = new double[N];

        x[0] = x0;
        for(int i = 1; i < N; i++){
            x[i] = x[i-1] + h;
        }

        y[0] = y0;
        for(int i = 1; i < N; i++) {
            y[i] = (c1 + Math.tan(x[i])) * Math.cos(x[i]);
        }

        for(int i = 0; i < N; i++){
            exactSeries.getData().add(new XYChart.Data(x[i], y[i]));
        }
        exactSeries.setName("Exact");
        chart.getData().addAll(exactSeries);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initParam();
        plotChart();
    }
}
