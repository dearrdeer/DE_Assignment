package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class SecondStage implements Initializable {
    @FXML
    private Pane pane1, pane2, pane3;
    @FXML
    private exactChartController chart1;
    @FXML
    private eulerChartController chart2;

    static private int N;
    static private double x0;
    static private double X;
    static private double y0;
    static private double h;
    static private double xScale;
    static private double yScale;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
