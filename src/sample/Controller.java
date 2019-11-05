package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    TextField y0Field, x0Field, XField, NField, xScaleField, yScaleField;
    public static double y0, x0, X, xScale, yScale;
    public static int N;

    @FXML
    private void buttonPressed(ActionEvent event) throws IOException {

        y0 = Double.parseDouble(y0Field.getText());
        x0 = Double.parseDouble(x0Field.getText());
        X = Double.parseDouble(XField.getText());
        N = Integer.parseInt(NField.getText());
        xScale = Double.parseDouble(xScaleField.getText());
        yScale = Double.parseDouble(yScaleField.getText());

        Parent second = FXMLLoader.load(getClass().getResource("charts.fxml"));
        Stage secondStage = new Stage();
        secondStage.setScene(new Scene(second, 1024,720));
        secondStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
