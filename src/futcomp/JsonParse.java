/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package futcomp;
import org.json.JSONObject; //needed to make object that will parse json data
import org.json.JSONArray; //will create the json array with data

public class JsonParse {
    public Team teamData(String jsonData) {
        JSONObject parse = new JSONObject(jsonData); //makes jsondata into json object named parse
        
        JSONArray teams = parse.getJSONArray("teams"); //assigns parse as an array
        JSONObject team = teams.getJSONObject(0); //assigsn team as first index of the json array
        
        String shortName = team.getString("strTeamShort"); //from team (the array) parse for data wanted
        String teamName = team.getString("strTeam");
        String stadium = team.getString("strStadium");
        String teamID = team.getString("idTeam");
        String badgeUrl = team.getString("strBadge");
        
        return new Team(teamName, shortName, stadium, teamID, badgeUrl);
        
    }
   
    public TeamStats teamStatsData(String jsonData, String teamID){
        JSONObject parse = new JSONObject(jsonData); 

        JSONArray results = parse.getJSONArray("results");
        JSONObject result = getFirstFinishedEvent(results);
        
        // The API gives the score as string
        // Integer.parseInt converts number string into a int.
        String homeScoreText = result.getString("intHomeScore");
        int homeScore = Integer.parseInt(homeScoreText);
        
        String homeID = result.getString("idHomeTeam");
                
        String awayScoreText = result.getString("intAwayScore");
        int awayScore = Integer.parseInt(awayScoreText);
        
        String eventID = result.getString("idEvent");
        int teamScore;
        int otherScore;

        if (teamID.equals(homeID)) {
            teamScore = homeScore;
            otherScore = awayScore;
        }
        else {
            teamScore = awayScore;
            otherScore = homeScore;
        }
        
        return new TeamStats(teamScore, otherScore, eventID);
    }

    public String getOpponentId(String jsonData, String teamID) {
        JSONObject parse = new JSONObject(jsonData);
        JSONArray results = parse.getJSONArray("results");
        JSONObject result = getFirstFinishedEvent(results);

        String homeID = result.getString("idHomeTeam");
        String awayID = result.getString("idAwayTeam");

        if (teamID.equals(homeID)) {
            return awayID;
        }
        else {
            return homeID;
        }
    }

    public boolean isHomeTeam(String jsonData, String teamID) {
        JSONObject parse = new JSONObject(jsonData);
        JSONArray results = parse.getJSONArray("results");
        JSONObject result = getFirstFinishedEvent(results);

        return teamID.equals(result.getString("idHomeTeam"));
    }
    
    

    public EventStats teamEventStatsData(String jsonData, String teamId, String opponentId, boolean isHomeTeam) {
        JSONObject parse = new JSONObject(jsonData);
        JSONArray eventStats = parse.getJSONArray("eventstats");

        String eventId = "";
        double shotsOnGoal = 0;
        double opponentShotsOnGoal = 0;
        double totalShots = 0;
        double opponentTotalShots = 0;
        double expectedGoals = 0;
        double opponentExpectedGoals = 0;

        for (int i = 0; i < eventStats.length(); i++) {
            JSONObject currentStat = eventStats.getJSONObject(i);

            if (eventId.equals("")) {
                eventId = currentStat.getString("idEvent");
            }

            String statName = currentStat.getString("strStat");
            double teamValue = getTeamStatValue(currentStat, isHomeTeam);
            double opponentValue = getTeamStatValue(currentStat, !isHomeTeam);

            if (statName.equals("Shots on Goal")) {
                shotsOnGoal = teamValue;
                opponentShotsOnGoal = opponentValue;
            }
            else if (statName.equals("Total Shots")) {
                totalShots = teamValue;
                opponentTotalShots = opponentValue;
            }
            else if (statName.equals("expected_goals")) {
                expectedGoals = teamValue;
                opponentExpectedGoals = opponentValue;
            }
        }

        return new EventStats(eventId, teamId, opponentId, isHomeTeam,
                shotsOnGoal, opponentShotsOnGoal,
                totalShots, opponentTotalShots,
                expectedGoals, opponentExpectedGoals);
    }

    private double getTeamStatValue(JSONObject currentStat, boolean isHomeTeam) {
        if (isHomeTeam) {
            return parseDoubleStat(currentStat.getString("intHome"));
        }
        else {
            return parseDoubleStat(currentStat.getString("intAway"));
        }
    }

    private double parseDoubleStat(String statText) {
        if (statText == null || statText.equals("")) {
            return 0;
        }

        return Double.parseDouble(statText);
    }

    private JSONObject getFirstFinishedEvent(JSONArray results) {
        for (int i = 0; i < results.length(); i++) {
            JSONObject result = results.getJSONObject(i);

            if (!result.isNull("intHomeScore") && !result.isNull("intAwayScore")) {
                return result;
            }
        }

        return results.getJSONObject(0);
    }
}
