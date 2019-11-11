package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

public class eulerChartController implements Initializable {
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

        yAxis.setAutoRanging(false);
        xLocalErrorAxis.setAutoRanging(false);
        xLocalErrorAxis.setLowerBound(Controller.xLowerBound);
        xLocalErrorAxis.setUpperBound(Controller.xUpperBound);
        xLocalErrorAxis.setTickUnit(h);

        yLocalErrorAxis.setAutoRanging(false);

        xGlobalErrorAxis.setAutoRanging(false);
        xGlobalErrorAxis.setLowerBound(Controller.xLowerBound);
        xGlobalErrorAxis.setUpperBound(Controller.xUpperBound);
        xGlobalErrorAxis.setTickUnit(h);

        yGlobalErrorAxis.setAutoRanging(false);

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

    public static double computeExact(double x){
        double c = (Controller.y0 - Math.sin(Controller.x0))/Math.cos(Controller.x0);
        return c * Math.cos(x) + Math.sin(x);
    }

    public void plotChart(){
        double yLowerBound = 0, localLowerBound = 0, globalLowerBound = 0;
        double yUpperBound = 0, localUpperBound = 0, globalUpperBound = 0;
        XYChart.Series exactSeries = new XYChart.Series();
        XYChart.Series errorSeries = new XYChart.Series();
        XYChart.Series localErrorSeries = new XYChart.Series();

        double[] x = new double[N+1];
        double[] y = new double[N+1];

        x[0] = x0;
        for(int i = 1; i <= N; i++){
            x[i] = x[i-1] + h;
        }

        y[0] = y0;
        errorSeries.getData().add(new XYChart.Data(x[0], 0));
        localErrorSeries.getData().add(new XYChart.Data(x[0], 0));
        for(int i = 1; i <= N; i++) {
            y[i] = y[i-1] + h * computeFunction(x[i-1], y[i-1]);
            double y_exact = computeExact(x[i-1]);
            double localError = computeExact(x[i]) - (y_exact + h * computeFunction(x[i-1], y_exact));
            errorSeries.getData().add(new XYChart.Data(x[i], y[i] - computeExact(x[i])));
            localErrorSeries.getData().add(new XYChart.Data(x[i], localError));

            if(x[i] < Controller.xLowerBound || x[i] > Controller.xUpperBound)continue;
            yLowerBound = Math.min(yLowerBound, y[i]);
            yUpperBound = Math.max(yUpperBound, y[i]);
            localLowerBound = Math.min(localLowerBound, localError);
            localUpperBound = Math.max(localUpperBound, localError);
            globalLowerBound = Math.min(globalLowerBound, y[i] - computeExact(x[i]));
            globalUpperBound = Math.max(globalUpperBound, y[i] - computeExact(x[i]));
        }

        for(int i = 0; i <= N; i++){
            exactSeries.getData().add(new XYChart.Data(x[i], y[i]));
        }
        exactSeries.setName("Euler");
        errorSeries.setName("Global Error");
        localErrorSeries.setName("Local Error");

        yAxis.setLowerBound(yLowerBound);
        yAxis.setUpperBound(yUpperBound);
        yLocalErrorAxis.setLowerBound(localLowerBound);
        yLocalErrorAxis.setUpperBound(localUpperBound);
        yGlobalErrorAxis.setLowerBound(globalLowerBound);
        yGlobalErrorAxis.setUpperBound(globalUpperBound);

        chart.getData().addAll(exactSeries);
        localErrorChart.getData().addAll(localErrorSeries);
        globalErrorChart.getData().addAll(errorSeries);
    }

    public static double getMaxError(double new_n){
        double new_h = (Controller.X - Controller.x0)/new_n;
        double x = Controller.x0;
        double y = Controller.y0;
        double maxError = 0;
        for(int i = 1; i < new_n; i++) {
            double k1 = new_h * computeFunction(x, y);
            y = y + k1;
            x = x + new_h;
            if(maxError < Math.abs(y - computeExact(x)))maxError = Math.abs(y - computeExact(x));
        }

        return maxError;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initParam();
        plotChart();
    }
}
