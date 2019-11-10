package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

public class rungeKuttaChartController implements Initializable {
    @FXML
    private NumberAxis xAxis, xLocalErrorAxis, xGlobalErrorAxis;
    @FXML
    private NumberAxis yAxis, yLocalErrorAxis, yGlobalErrorAxis;
    @FXML
    private LineChart<Number, Number> chart, localErrorChart, globalErrorChart;

    private int N;
    private double x0;
    private double X;
    private double y0;
    private double h;

    private double c1;


    public void initParam(){
        this.N = Controller.N;
        this.x0 = Controller.x0;
        this.X = Controller.X;
        this.y0 = Controller.y0;

        this.h = (X-x0)/N;
        this.c1 = (this.y0 - Math.sin(this.x0))/Math.cos(this.x0);

        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(Controller.xLowerBound);
        xAxis.setUpperBound(Controller.xUpperBound);
        xAxis.setTickUnit(Controller.xScale);

        xLocalErrorAxis.setAutoRanging(false);
        xLocalErrorAxis.setLowerBound(Controller.xLowerBound);
        xLocalErrorAxis.setUpperBound(Controller.xUpperBound);
        xLocalErrorAxis.setTickUnit(Controller.xScale);

        xGlobalErrorAxis.setAutoRanging(false);
        xGlobalErrorAxis.setLowerBound(Controller.xLowerBound);
        xGlobalErrorAxis.setUpperBound(Controller.xUpperBound);
        xGlobalErrorAxis.setTickUnit(Controller.xScale);


        chart.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        chart.setCreateSymbols(false);

        localErrorChart.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        localErrorChart.setCreateSymbols(false);

        globalErrorChart.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        globalErrorChart.setCreateSymbols(false);
    }

    static public double computeFunction(double x, double y){
        return (1/Math.cos(x) - y*Math.tan(x));
    }

    public double computeExact(double x){
        return c1 * Math.cos(x) + Math.sin(x);
    }

    public void plotChart(){
        XYChart.Series exactSeries = new XYChart.Series();
        XYChart.Series errorSeries = new XYChart.Series();
        XYChart.Series localErrorSeries = new XYChart.Series();

        double[] x = new double[N];
        double[] y = new double[N];

        x[0] = x0;
        for(int i = 1; i < N; i++){
            x[i] = x[i-1] + h;
        }

        y[0] = y0;
        for(int i = 1; i < N; i++) {
            double k1 = h * computeFunction(x[i-1], y[i-1]);
            double k2 = h * computeFunction(x[i-1] + 0.5 * h, y[i-1] + 0.5 * k1 * h);
            double k3 = h * computeFunction(x[i-1] + 0.5 * h, y[i-1] + k2 * h * 0.5);
            double k4 = h * computeFunction(x[i-1] + h, y[i-1] + h*k3);

            y[i] = y[i-1] + (k1 + 2*k2 + 2*k3 + k4) / 6;

            double k1_exact = h * computeFunction(x[i-1], computeExact(x[i-1]));
            double k2_exact = h * computeFunction(x[i-1] + 0.5 * h, computeExact(x[i-1] + 0.5 * k1_exact * h));
            double k3_exact = h * computeFunction(x[i-1] + 0.5 * h, computeExact(x[i-1] + 0.5 * k2_exact * h));
            double k4_exact = h * computeFunction(x[i-1] + h, computeExact(x[i-1] + h * k3));

            double localError = computeExact(x[i]) - computeExact(x[i-1]) - (k1_exact + 2 * k2_exact + 2 * k3_exact + k4_exact) / 6;

            errorSeries.getData().add(new XYChart.Data(x[i], y[i] - computeExact(x[i])));
            localErrorSeries.getData().add(new XYChart.Data(x[i], localError));
        }

        for(int i = 0; i < N; i++){
            exactSeries.getData().add(new XYChart.Data(x[i], y[i]));
        }
        exactSeries.setName("Runge-Kutta");
        errorSeries.setName("Global Error");
        localErrorSeries.setName("Local Error");
        chart.getData().addAll(exactSeries);
        localErrorChart.getData().addAll(localErrorSeries);
        globalErrorChart.getData().addAll(errorSeries);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initParam();
        plotChart();
    }
}
