package futcomp;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class InternationalController {

    @FXML private ComboBox<String> teamOneBox;
    @FXML private ComboBox<String> teamTwoBox;
    @FXML private Label matchupLabel;
    @FXML private Label resultLabel;
    @FXML private Label teamOneShortNameLabel;
    @FXML private Label teamOneStadiumLabel;
    @FXML private Label teamTwoShortNameLabel;
    @FXML private Label teamTwoStadiumLabel;
    @FXML private ImageView teamOneBadge;
    @FXML private ImageView teamTwoBadge;

    @FXML
    private void initialize() {
        teamOneBox.getItems().addAll(
                "Argentina",
                "Brazil",
                "England",
                "France",
                "Germany",
                "Italy",
                "Portugal",
                "Spain",
                "Mexico",
                "USA",
                "Canada"
        );
        teamTwoBox.getItems().setAll(teamOneBox.getItems());
        teamOneBox.setOnAction(event -> updateTeamOneInfo());
        teamTwoBox.setOnAction(event -> updateTeamTwoInfo());
        resultLabel.setText("Prediction Result");
    }

    @FXML
    private void handlePredict() {
        String teamOne = teamOneBox.getValue();
        String teamTwo = teamTwoBox.getValue();

        if (teamOne == null || teamTwo == null) {
            matchupLabel.setText("VS");
            resultLabel.setText("Choose both teams");
            return;
        }

        matchupLabel.setText(teamOne + " vs " + teamTwo);

        if (teamOne.equals(teamTwo)) {
            resultLabel.setText("Choose two different teams");
            return;
        }

        resultLabel.setText("Calculating prediction...");
        runPrediction(teamOne, teamTwo);
    }

    private void resetPredictionText() {
        matchupLabel.setText("VS");
        resultLabel.setText("Prediction Result");
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("title.fxml"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void updateTeamOneInfo() {
        resetPredictionText();
        updateTeamInfo(teamOneBox.getValue(), teamOneShortNameLabel, teamOneStadiumLabel);
    }

    private void updateTeamTwoInfo() {
        resetPredictionText();
        updateTeamInfo(teamTwoBox.getValue(), teamTwoShortNameLabel, teamTwoStadiumLabel);
    }

    private void updateTeamInfo(String teamName, Label shortNameLabel, Label stadiumLabel) {
        if (teamName == null) {
            shortNameLabel.setText("Short Name: ");
            stadiumLabel.setText("Stadium: ");
        }
        else {
            shortNameLabel.setText("Short Name: " + getShortName(teamName));
            stadiumLabel.setText("Stadium: " + getStadium(teamName));
        }
    }

    /*
    TheSportsDB test key was returning Arsenal for lookupteam.php even when
    the selected team ID was different, so this API label lookup is disabled.
    private void loadTeamInfo(String teamName, Label shortNameLabel, Label stadiumLabel) {
        Thread infoThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Team team = getTeamData(teamName);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (team.getTeamName().equals(teamName)) {
                                shortNameLabel.setText("Short Name: " + team.getShortName());
                                stadiumLabel.setText("Stadium: " + team.getStadium());
                            }
                            else {
                                shortNameLabel.setText("Short Name: API mismatch");
                                stadiumLabel.setText("Stadium: API mismatch");
                            }
                        }
                    });
                }
                catch (Exception error) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            shortNameLabel.setText("Short Name: unavailable");
                            stadiumLabel.setText("Stadium: unavailable");
                        }
                    });
                }
            }
        });

        infoThread.start();
    }
    */

    private void runPrediction(String teamOne, String teamTwo) {
        Thread predictionThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String predictionText;

                try {
                    predictionText = getPredictionText(teamOne, teamTwo);
                }
                catch (Exception error) {
                    predictionText = "Prediction unavailable. Check team data or API connection.";
                }

                String finalPredictionText = predictionText;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        resultLabel.setText(finalPredictionText);
                    }
                });
            }
        });

        predictionThread.start();
    }

    private String getPredictionText(String teamOne, String teamTwo) throws Exception {
        APIClass API = new APIClass();
        JsonParse parse = new JsonParse();
        StatsCalc calc = new StatsCalc();

        Team teamData1 = new Team(teamOne, "", "", getTeamID(teamOne));
        Team teamData2 = new Team(teamTwo, "", "", getTeamID(teamTwo));

        String statsJson1 = API.getTeamData(teamData1.getTeamID());
        String statsJson2 = API.getTeamData(teamData2.getTeamID());

        TeamStats stats1 = parse.teamStatsData(statsJson1, teamData1.getTeamID());
        TeamStats stats2 = parse.teamStatsData(statsJson2, teamData2.getTeamID());

        String winner = calc.predict(teamData1, stats1, teamData2, stats2);

        if (winner.equals("DRAW")) {
            return "This match is predicted to be close based on recent form.";
        }
        else {
            return winner + " is predicted to have the stronger side because of recent points and goal difference.";
        }
    }

    /*
    private Team getTeamData(String teamName) throws Exception {
        APIClass API = new APIClass();
        JsonParse parse = new JsonParse();

        String teamJson = API.getTeamJsonByID(getTeamID(teamName));

        return parse.teamData(teamJson);
    }
    */

    private String getTeamID(String teamName) throws Exception {
        if (teamName.equals("Argentina")) {
            return "134509";
        }
        else if (teamName.equals("Brazil")) {
            return "134496";
        }
        else if (teamName.equals("England")) {
            return "133914";
        }
        else if (teamName.equals("France")) {
            return "133913";
        }
        else if (teamName.equals("Germany")) {
            return "133907";
        }
        else if (teamName.equals("Italy")) {
            return "133910";
        }
        else if (teamName.equals("Portugal")) {
            return "133908";
        }
        else if (teamName.equals("Spain")) {
            return "133909";
        }
        else if (teamName.equals("Mexico")) {
            return "134497";
        }
        else if (teamName.equals("Canada")) {
            return "140073";
        }
        else if (teamName.equals("USA")) {
            return "134514";
        }
        else {
            throw new Exception("Team ID not found");
        }
    }

    private String getShortName(String teamName) {
        if (teamName.equals("Argentina")) return "ARG";
        else if (teamName.equals("Brazil")) return "BRA";
        else if (teamName.equals("England")) return "ENG";
        else if (teamName.equals("France")) return "FRA";
        else if (teamName.equals("Germany")) return "GER";
        else if (teamName.equals("Italy")) return "ITA";
        else if (teamName.equals("Portugal")) return "POR";
        else if (teamName.equals("Spain")) return "ESP";
        else if (teamName.equals("Mexico")) return "MEX";
        else if (teamName.equals("USA")) return "USA";
        else if (teamName.equals("Canada")) return "CAN";
        else return "";
    }

    private String getStadium(String teamName) {
        if (teamName.equals("Argentina")) return "Estadio Mas Monumental";
        else if (teamName.equals("Brazil")) return "Estadio do Maracana";
        else if (teamName.equals("England")) return "Wembley Stadium";
        else if (teamName.equals("France")) return "Stade de France";
        else if (teamName.equals("Germany")) return "Olympiastadion Berlin";
        else if (teamName.equals("Italy")) return "Stadio Olimpico";
        else if (teamName.equals("Portugal")) return "Estadio Nacional";
        else if (teamName.equals("Spain")) return "No fixed home stadium";
        else if (teamName.equals("Mexico")) return "Estadio Azteca";
        else if (teamName.equals("USA")) return "Various stadiums";
        else if (teamName.equals("Canada")) return "Various stadiums";
        else return "";
    }
}
