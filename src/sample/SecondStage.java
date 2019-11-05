package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SecondStage implements Initializable {
    @FXML
    private NumberAxis xAxis = new NumberAxis();
    @FXML
    private NumberAxis yAxis = new NumberAxis();
    @FXML
    private LineChart<Number, Number> exactChart, eulerChart, eulerImpChart, rungeKuttaChart;

    static private int N;
    static private double x0;
    static private double X;
    static private double y0;
    static private double h;
    static private double xScale;
    static private double yScale;

    //  Exact Solution y = c1 * cosx + sinx
    private double c1;

    private void plotExact(){
        XYChart.Series exactSeries = new XYChart.Series();

        double[] x = new double[N];
        double[] y = new double[N];

        x[0] = x0;
        for(int i = 1; i < N; i++){
            x[i] = x[i-1] + h;
        }

        for(int i = 0; i < N; i++) {
            y[i] = c1 * Math.cos(x[i]) + Math.sin(x[i]);
        }

        for(int i = 0; i < N; i++){
            exactSeries.getData().add(new XYChart.Data(x[i], y[i]));
        }
        exactSeries.setName("Exact");
        exactChart.getData().addAll(exactSeries);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        y0 = Controller.y0;
        x0 = Controller.x0;
        X = Controller.X;
        N = Controller.N;
        xScale = Controller.xScale;
        yScale = Controller.yScale;

        h = (X - x0)/N;

        c1 = (y0 - Math.sin(x0))/Math.cos(x0);

        xAxis.setLowerBound(x0);
        xAxis.setUpperBound(X);
        xAxis.setTickUnit(xScale);

        yAxis.setLowerBound(-2);
        yAxis.setUpperBound(2);
        yAxis.setTickUnit(yScale);
        plotExact();
    }
}
