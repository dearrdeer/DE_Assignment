package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    TextField y0Field, x0Field, XField, NField, xLowerBoundField, xUpperBoundField, yLowerBoundField, yUpperBoundField;
    public static double y0, x0, X, xLowerBound, xUpperBound;
    public static int N, N_begin, N_end;

    @FXML
    private void buttonPressed(ActionEvent event) throws IOException {

        y0 = Double.parseDouble(y0Field.getText());
        x0 = Double.parseDouble(x0Field.getText());
        X = Double.parseDouble(XField.getText());
        N = Integer.parseInt(NField.getText());

        xLowerBound = Double.parseDouble(xLowerBoundField.getText());
        xUpperBound = Double.parseDouble(xUpperBoundField.getText());

        N_begin = Integer.parseInt(yLowerBoundField.getText());
        N_end = Integer.parseInt(yUpperBoundField.getText());

        Parent root = FXMLLoader.load(getClass().getResource("charts.fxml"));
        Scene scene = new Scene(root, 1024, 1000);
        scene.getStylesheets().addAll(getClass().getResource("style.css").toString());
        Stage secondStage = new Stage();
        secondStage.setScene(scene);
        secondStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
