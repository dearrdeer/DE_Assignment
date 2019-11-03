package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import org.w3c.dom.ls.LSOutput;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();

    private int N = 100;
    private double x0 = Math.PI/2;
    private double X = 7;
    private double y0 = 1;
    private double h = (X-x0)/(N);

    //  Exact Solution y = c1 * cosx + sinx
    private double c1 = (y0 - Math.sin(x0))/Math.cos(x0);

    @FXML
    private LineChart<Number, Number> exactChart = new LineChart<Number, Number>(xAxis, yAxis);
    @FXML
    private LineChart<Number, Number> eulerChart = new LineChart<Number, Number>(xAxis, yAxis);
    @FXML
    private LineChart<Number, Number> impEulerChart = new LineChart<Number, Number>(xAxis, yAxis);
    @FXML
    private LineChart<Number, Number> rungeChart = new LineChart<Number, Number>(xAxis, yAxis);

    private double computeFunction(double x, double y){
        return (1.0 - y * Math.sin(x)) / Math.cos(x);
    }

    @FXML
    private void plotTheChart(ActionEvent event){
        System.out.println(c1);
        plotExact();
        plotEuler();
        plotImpEuler();
        plotRunge();
    }

    private void plotExact(){
        Series exactSeries = new Series();

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
            exactSeries.getData().add(new Data(x[i], y[i]));
        }
        exactSeries.setName("Exact");
        exactChart.getData().addAll(exactSeries);
    }

    private void plotEuler(){
        Series<Number,Number> eulerSeries = new Series<Number, Number>();
        double[] x = new double[N];
        double[] y = new double[N];

        x[0] = x0;
        y[0] = y0;

        for(int i = 1; i < N; i++ ){
            x[i] = x[i-1] + h;
            y[i] = y[i-1] + h * computeFunction(x[i-1], y[i-1]);
        }

        for(int i = 0; i < N; i++){
            eulerSeries.getData().add(new Data(x[i], y[i]));
        }
        eulerSeries.setName("Euler");
        eulerChart.getData().addAll(eulerSeries);


    }

    private void plotImpEuler(){
        Series<Number,Number> eulerSeries = new Series<Number, Number>();
        double[] x = new double[N];
        double[] y = new double[N];

        x[0] = x0;
        y[0] = y0;

        for(int i = 1; i < N; i++ ){
            x[i] = x[i-1] + h;
            double y_prime = y[i-1] + h * computeFunction(x[i-1], y[i-1]);
            y[i] = y[i-1] + 0.5 * h * (computeFunction(x[i-1], y[i-1]) + computeFunction(x[i], y_prime));
        }

        for(int i = 0; i < N; i++){
            eulerSeries.getData().add(new Data(x[i], y[i]));
        }
        eulerSeries.setName("Euler");
        impEulerChart.getData().addAll(eulerSeries);
    }

    private void plotRunge(){
        Series<Number,Number> rungeSeries = new Series<Number, Number>();
        double[] x = new double[N];
        double[] y = new double[N];

        x[0] = x0;
        y[0] = y0;

        for(int i = 1; i < N; i++ ){
            x[i] = x[i-1] + h;
            double k1 = h*computeFunction(x[i-1], y[i-1]);
            double k2 = h*computeFunction(x[i-1] + h/2, y[i-1] + k1/2);
            double k3 = h*computeFunction(x[i-1] + h/2, y[i-1] + k2/2);
            double k4 = h*computeFunction(x[i-1] + h, y[i-1] + k3);
            y[i] = y[i-1] + (k1 + 2*k2 + 2*k3 + k4)/6.0;
        }

        for(int i = 0; i < N; i++){
            rungeSeries.getData().add(new Data(x[i], y[i]));
        }
        rungeSeries.setName("Runge-Kutta");
        rungeChart.getData().addAll(rungeSeries);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
