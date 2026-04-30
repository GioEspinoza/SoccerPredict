/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package futcomp;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class PredictController {

    @FXML private ComboBox<String> teamOneBox;
    @FXML private ComboBox<String> teamTwoBox;
    @FXML private Label resultLabel;

    @FXML
    private void initialize() {
        teamOneBox.getItems().addAll("France", "Argentina", "Spain");
        teamTwoBox.getItems().addAll("France", "Argentina", "Spain");
    }

    @FXML
    private void handlePredict() {
        String teamOne = teamOneBox.getValue();
        String teamTwo = teamTwoBox.getValue();

        resultLabel.setText(teamOne + " vs " + teamTwo);
    }
}