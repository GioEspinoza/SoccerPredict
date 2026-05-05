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
        JSONObject result = results.getJSONObject(0); 
        
        // The API gives the score as string
        // Integer.parseInt converts number string into a int.
        String homeScoreText = result.getString("intHomeScore");
        int homeScore = Integer.parseInt(homeScoreText);
        
        String homeID = result.getString("idHomeTeam");
                
        String awayScoreText = result.getString("intAwayScore");
        int awayScore = Integer.parseInt(awayScoreText);
        
        String awayID = result.getString("idAwayTeam");
        
        String recentGameDate = result.getString("dateEvent");

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
        
        return new TeamStats(teamScore, otherScore, recentGameDate);
    }
}
