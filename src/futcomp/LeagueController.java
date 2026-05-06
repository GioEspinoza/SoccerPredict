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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LeagueController {

    @FXML private ComboBox<String> leagueOneBox;
    @FXML private ComboBox<String> leagueTwoBox;
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
        leagueOneBox.getItems().addAll(
                "English Premier League",
                "Spanish La Liga",
                "Italian Serie A",
                "German Bundesliga",
                "French Ligue 1"
        );
        leagueTwoBox.getItems().setAll(leagueOneBox.getItems());

        leagueOneBox.setOnAction(event -> loadTeamsForLeagueOne());
        leagueTwoBox.setOnAction(event -> loadTeamsForLeagueTwo());
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

    private void updateTeamInfo(String teamName, Label shortNameLabel, Label stadiumLabel, ImageView badgeView) {
        if (teamName == null) {
            shortNameLabel.setText("Short Name: ");
            stadiumLabel.setText("Stadium: ");
            badgeView.setImage(null);
        }
        else {
            shortNameLabel.setText("Short Name: " + getShortName(teamName));
            stadiumLabel.setText("Stadium: " + getStadium(teamName));
            loadTeamBadge(teamName, badgeView);
        }
    }

    private void loadTeamBadge(String teamName, ImageView badgeView) {
        badgeView.setImage(null);
        String badgeUrl = getBadgeUrl(teamName);

        if (!badgeUrl.equals("")) {
            Image badge = new Image(badgeUrl);
            badgeView.setImage(badge);
        }

        /*
        TheSportsDB test key returns Arsenal badge data for every team,
        so the API badge lookup is disabled and hard-coded URLs are used.

        Thread badgeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Team team = getTeamData(teamName);
                    Image badge = new Image(team.getBadgeUrl());

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            badgeView.setImage(badge);
                        }
                    });
                }
                catch (Exception error) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            badgeView.setImage(null);
                        }
                    });
                }
            }
        });

        badgeThread.start();
        */
    }
    
    private void updateTeamOneInfo() {
        resetPredictionText();
        updateTeamInfo(teamOneBox.getValue(), teamOneShortNameLabel, teamOneStadiumLabel, teamOneBadge);
    }

    private void updateTeamTwoInfo() {
        resetPredictionText();
        updateTeamInfo(teamTwoBox.getValue(), teamTwoShortNameLabel, teamTwoStadiumLabel, teamTwoBadge);
    }

    /*
    TheSportsDB test key was returning Arsenal for lookupteam even when
    the selected team was different, so this API function is disabled.
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

        String statsJson1 = API.getStatsJson(teamData1.getTeamID());
        String statsJson2 = API.getStatsJson(teamData2.getTeamID());

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

    private void loadTeamsForLeagueOne() {
        loadTeamsForLeague(leagueOneBox.getValue(), teamOneBox);
        teamOneShortNameLabel.setText("Short Name: ");
        teamOneStadiumLabel.setText("Stadium: ");
        teamOneBadge.setImage(null);
        resetPredictionText();
    }

    private void loadTeamsForLeagueTwo() {
        loadTeamsForLeague(leagueTwoBox.getValue(), teamTwoBox);
        teamTwoShortNameLabel.setText("Short Name: ");
        teamTwoStadiumLabel.setText("Stadium: ");
        teamTwoBadge.setImage(null);
        resetPredictionText();
    }

    private void loadTeamsForLeague(String league, ComboBox<String> teamBox) {
        teamBox.getItems().clear();
        teamBox.setValue(null);

        if (league == null) {
            return;
        }

        if (league.equals("English Premier League")) {
            teamBox.getItems().addAll("Arsenal", "Liverpool", "Manchester City", "Manchester United", "Chelsea");
        }
        else if (league.equals("Spanish La Liga")) {
            teamBox.getItems().addAll("FC Barcelona", "Real Madrid", "Atletico Madrid", "Villarreal", "Real Betis");
        }
        else if (league.equals("Italian Serie A")) {
            teamBox.getItems().addAll("Inter Milan", "AC Milan", "Juventus", "Napoli", "Roma");
        }
        else if (league.equals("German Bundesliga")) {
            teamBox.getItems().addAll("Bayern Munich", "Borussia Dortmund", "Bayer Leverkusen", "RB Leipzig", "Eintracht Frankfurt");
        }
        else if (league.equals("French Ligue 1")) {
            teamBox.getItems().addAll("Paris SG", "Monaco", "Marseille", "Lyon", "Lille");
        }
    }

    private String getTeamID(String teamName) throws Exception {
        if (teamName.equals("Arsenal")) {
            return "133604";
        }
        else if (teamName.equals("Liverpool")) {
            return "133602";
        }
        else if (teamName.equals("Manchester City")) {
            return "133613";
        }
        else if (teamName.equals("Manchester United")) {
            return "133612";
        }
        else if (teamName.equals("Chelsea")) {
            return "133610";
        }
        else if (teamName.equals("FC Barcelona")) {
            return "133739";
        }
        else if (teamName.equals("Real Madrid")) {
            return "133738";
        }
        else if (teamName.equals("Atletico Madrid")) {
            return "133729";
        }
        else if (teamName.equals("Villarreal")) {
            return "133740";
        }
        else if (teamName.equals("Real Betis")) {
            return "133722";
        }
        else if (teamName.equals("Inter Milan")) {
            return "133681";
        }
        else if (teamName.equals("AC Milan")) {
            return "133667";
        }
        else if (teamName.equals("Juventus")) {
            return "133676";
        }
        else if (teamName.equals("Napoli")) {
            return "133670";
        }
        else if (teamName.equals("Roma")) {
            return "133682";
        }
        else if (teamName.equals("Bayern Munich")) {
            return "133664";
        }
        else if (teamName.equals("Borussia Dortmund")) {
            return "133650";
        }
        else if (teamName.equals("Bayer Leverkusen")) {
            return "133666";
        }
        else if (teamName.equals("RB Leipzig")) {
            return "134695";
        }
        else if (teamName.equals("Eintracht Frankfurt")) {
            return "133814";
        }
        else if (teamName.equals("Paris SG")) {
            return "133714";
        }
        else if (teamName.equals("Monaco")) {
            return "133823";
        }
        else if (teamName.equals("Marseille")) {
            return "133707";
        }
        else if (teamName.equals("Lyon")) {
            return "133713";
        }
        else if (teamName.equals("Lille")) {
            return "133711";
        }
        else {
            throw new Exception("Team ID not found");
        }
    }

    private Team getTeamData(String teamName) throws Exception {
        APIClass API = new APIClass();
        JsonParse parse = new JsonParse();

        String teamJson = API.getTeamJsonByID(getTeamID(teamName));

        return parse.teamData(teamJson);
    }

    private String getShortName(String teamName) {
        if (teamName.equals("Arsenal")) 
            return "ARS";
        else if (teamName.equals("Liverpool")) 
            return "LIV";
        else if (teamName.equals("Manchester City")) 
            return "MCI";
        else if (teamName.equals("Manchester United")) 
            return "MUN";
        else if (teamName.equals("Chelsea")) 
            return "CHE";
        else if (teamName.equals("FC Barcelona")) 
            return "BAR";
        else if (teamName.equals("Real Madrid")) 
            return "RMA";
        else if (teamName.equals("Atletico Madrid")) 
            return "ATM";
        else if (teamName.equals("Villarreal")) 
            return "VIL";
        else if (teamName.equals("Real Betis")) 
            return "BET";
        else if (teamName.equals("Inter Milan")) 
            return "INT";
        else if (teamName.equals("AC Milan")) 
            return "MIL";
        else if (teamName.equals("Juventus")) 
            return "JUV";
        else if (teamName.equals("Napoli")) 
            return "NAP";
        else if (teamName.equals("Roma")) 
            return "ROM";
        else if (teamName.equals("Bayern Munich")) 
            return "BAY";
        else if (teamName.equals("Borussia Dortmund")) 
            return "DOR";
        else if (teamName.equals("Bayer Leverkusen")) 
            return "LEV";
        else if (teamName.equals("RB Leipzig")) 
            return "RBL";
        else if (teamName.equals("Eintracht Frankfurt")) 
            return "SGE";
        else if (teamName.equals("Paris SG")) 
            return "PSG";
        else if (teamName.equals("Monaco")) 
            return "ASM";
        else if (teamName.equals("Marseille")) 
            return "OM";
        else if (teamName.equals("Lyon")) 
            return "LYO";
        else if (teamName.equals("Lille")) 
            return "LIL";
        else return "";
    }

    private String getStadium(String teamName) {
        if (teamName.equals("Arsenal")) 
            return "Emirates Stadium";
        else if (teamName.equals("Liverpool")) 
            return "Anfield";
        else if (teamName.equals("Manchester City")) 
            return "Etihad Stadium";
        else if (teamName.equals("Manchester United")) 
            return "Old Trafford";
        else if (teamName.equals("Chelsea")) 
                return "Stamford Bridge";
        else if (teamName.equals("FC Barcelona")) 
            return "Spotify Camp Nou";
        else if (teamName.equals("Real Madrid")) 
            return "Santiago Bernabeu";
        else if (teamName.equals("Atletico Madrid")) 
            return "Metropolitano Stadium";
        else if (teamName.equals("Villarreal")) 
            return "Estadio de la Ceramica";
        else if (teamName.equals("Real Betis")) 
            return "Estadio Benito Villamarin";
        else if (teamName.equals("Inter Milan")) 
            return "San Siro";
        else if (teamName.equals("AC Milan")) 
            return "San Siro";
        else if (teamName.equals("Juventus")) 
            return "Allianz Stadium";
        else if (teamName.equals("Napoli")) 
            return "Stadio Diego Armando Maradona";
        else if (teamName.equals("Roma")) 
            return "Stadio Olimpico";
        else if (teamName.equals("Bayern Munich")) 
            return "Allianz Arena";
        else if (teamName.equals("Borussia Dortmund")) 
            return "Signal Iduna Park";
        else if (teamName.equals("Bayer Leverkusen")) 
            return "BayArena";
        else if (teamName.equals("RB Leipzig")) 
            return "Red Bull Arena";
        else if (teamName.equals("Eintracht Frankfurt")) 
            return "Deutsche Bank Park";
        else if (teamName.equals("Paris SG")) 
            return "Parc des Princes";
        else if (teamName.equals("Monaco")) 
            return "Stade Louis II";
        else if (teamName.equals("Marseille")) 
            return "Orange Velodrome";
        else if (teamName.equals("Lyon")) 
            return "Groupama Stadium";
        else if (teamName.equals("Lille")) 
            return "Stade Pierre-Mauroy";
        else return "";
    }

    private String getBadgeUrl(String teamName) {
        if (teamName.equals("Arsenal"))
            return "https://r2.thesportsdb.com/images/media/team/badge/uyhbfe1612467038.png";
        else if (teamName.equals("Liverpool"))
            return "https://r2.thesportsdb.com/images/media/team/badge/kfaher1737969724.png";
        else if (teamName.equals("Manchester City"))
            return "https://r2.thesportsdb.com/images/media/team/badge/vwpvry1467462651.png";
        else if (teamName.equals("Manchester United"))
            return "https://r2.thesportsdb.com/images/media/team/badge/xzqdr11517660252.png";
        else if (teamName.equals("Chelsea"))
            return "https://r2.thesportsdb.com/images/media/team/badge/yvwvtu1448813215.png";
        else if (teamName.equals("FC Barcelona"))
            return "https://r2.thesportsdb.com/images/media/team/badge/wq9sir1639406443.png";
        else if (teamName.equals("Real Madrid"))
            return "https://r2.thesportsdb.com/images/media/team/badge/vwvwrw1473502969.png";
        else if (teamName.equals("Atletico Madrid"))
            return "https://r2.thesportsdb.com/images/media/team/badge/0ulh3q1719984315.png";
        else if (teamName.equals("Villarreal"))
            return "https://r2.thesportsdb.com/images/media/team/badge/vrypqy1473503073.png";
        else if (teamName.equals("Real Betis"))
            return "https://r2.thesportsdb.com/images/media/team/badge/2oqulv1663245386.png";
        else if (teamName.equals("Inter Milan"))
            return "https://r2.thesportsdb.com/images/media/team/badge/ryhu6d1617113103.png";
        else if (teamName.equals("AC Milan"))
            return "https://r2.thesportsdb.com/images/media/team/badge/wvspur1448806617.png";
        else if (teamName.equals("Juventus"))
            return "https://r2.thesportsdb.com/images/media/team/badge/uxf0gr1742983727.png";
        else if (teamName.equals("Napoli"))
            return "https://r2.thesportsdb.com/images/media/team/badge/l8qyxv1742982541.png";
        else if (teamName.equals("Roma"))
            return "https://r2.thesportsdb.com/images/media/team/badge/jwro2s1760820674.png";
        else if (teamName.equals("Bayern Munich"))
            return "https://r2.thesportsdb.com/images/media/team/badge/01ogkh1716960412.png";
        else if (teamName.equals("Borussia Dortmund"))
            return "https://r2.thesportsdb.com/images/media/team/badge/tqo8ge1716960353.png";
        else if (teamName.equals("Bayer Leverkusen"))
            return "https://r2.thesportsdb.com/images/media/team/badge/3x9k851726760113.png";
        else if (teamName.equals("RB Leipzig"))
            return "https://r2.thesportsdb.com/images/media/team/badge/zjgapo1594244951.png";
        else if (teamName.equals("Eintracht Frankfurt"))
            return "https://r2.thesportsdb.com/images/media/team/badge/rurwpy1473453269.png";
        else if (teamName.equals("Paris SG"))
            return "https://r2.thesportsdb.com/images/media/team/badge/rwqrrq1473504808.png";
        else if (teamName.equals("Monaco"))
            return "https://r2.thesportsdb.com/images/media/team/badge/exjf5l1678808044.png";
        else if (teamName.equals("Marseille"))
            return "https://r2.thesportsdb.com/images/media/team/badge/uutsyt1473504764.png";
        else if (teamName.equals("Lyon"))
            return "https://r2.thesportsdb.com/images/media/team/badge/blk9771656932845.png";
        else if (teamName.equals("Lille"))
            return "https://r2.thesportsdb.com/images/media/team/badge/2giize1534005340.png";
        else return "";
    }
}
