/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package futcomp;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException; 
import java.util.ArrayList;
// import java.nio.file.StandardOpenOption; //holds APPEND and CREATE to be used for adding data to existing json
import org.json.JSONObject; 

public class JsonStorage {
    String[] teamNames = {"Argentina",
                "Brazil",
                "England",
                "France",
                "Germany",
                "Italy",
                "Portugal",
                "Spain",
                "Mexico",
                "USA",
                "Canada",
                "Arsenal",
                "Liverpool",
                "Manchester City",
                "Manchester United",
                "Chelsea",
                "FC Barcelona",
                "Real Madrid",
                "Atletico Madrid",
                "Villarreal",
                "Real Betis",
                "Inter Milan",
                "AC Milan",
                "Juventus",
                "Napoli",
                "Roma",
                "Bayern Munich",
                "Borussia Dortmund",
                "Bayer Leverkusen",
                "RB Leipzig",
                "Eintracht Frankfurt",
                "Paris SG",
                "Monaco",
                "Marseille",
                "Lyon",
                "Lille"};
    APIClass API = new APIClass();
    Path fileDataPath = Path.of("data/team_data.json") ;
    Path fileStatsPath = Path.of("data/teamStats_data.json");
    public JSONObject loadAllTeamsData() throws IOException, InterruptedException{
        JSONObject allTeamsData = new JSONObject();
        JsonParse parse = new JsonParse();
        String teamJson;
        for(int i = 0; i < teamNames.length; i++){ 
            JSONObject currTeam = new JSONObject();
            teamJson = API.getTeamJson(teamNames[i]);
            Team teamParsed = parse.teamData(teamJson);
            currTeam.put("teamName", teamParsed.getTeamName());
            currTeam.put("teamID", teamParsed.getTeamID());
            currTeam.put("shortName", teamParsed.getShortName());
            currTeam.put("stadium", teamParsed.getStadium());
            currTeam.put("badgeUrl", teamParsed.getBadgeUrl());
            String league = getLeague(teamNames[i]);
            if (!league.equals("")) {
                currTeam.put("league", league);
            }
            allTeamsData.put(teamNames[i], currTeam);
         }
        return allTeamsData;
 
    }
    
    public JSONObject loadAllStatsTeams()throws IOException, InterruptedException{
        JsonParse parse = new JsonParse();
        JSONObject allTeamsStats = new JSONObject();
        JSONObject allTeamsData = new JSONObject(checkDataFile());

        for(int i = 0; i < teamNames.length; i++){ 
            JSONObject currTeam = new JSONObject();
            JSONObject teamData = allTeamsData.getJSONObject(teamNames[i]);
            
            String teamName = teamNames[i];
            String displayName = teamData.getString("teamName");
            String teamId = teamData.getString("teamID");
            String teamStatsJson = API.getStatsJson(teamId);
            TeamStats teamStatsParsed = parse.teamStatsData(teamStatsJson, teamId);

            String eventId = teamStatsParsed.getEventId();
            String opponentId = "";
            boolean isHomeTeam = false;
            EventStats eventStatsParsed = new EventStats(eventId, teamId, opponentId, isHomeTeam,
                    0, 0,
                    0, 0,
                    0, 0);

            if (!eventId.equals("")) {
                opponentId = parse.getOpponentId(teamStatsJson, teamId);
                isHomeTeam = parse.isHomeTeam(teamStatsJson, teamId);
                String eventStatsJson = API.getEventStatJson(eventId);
                eventStatsParsed = parse.teamEventStatsData(eventStatsJson, teamId, opponentId, isHomeTeam);
            }

            currTeam.put("teamName", displayName);
            currTeam.put("teamID", teamId);
            currTeam.put("eventID", eventId);
            currTeam.put("opponentID", opponentId);
            currTeam.put("isHomeTeam", isHomeTeam);
            currTeam.put("teamScore", teamStatsParsed.getTeamScore());
            currTeam.put("opponentScore", teamStatsParsed.getOtherScore());
            currTeam.put("shotsOnGoal", eventStatsParsed.getShotsOnGoal());
            currTeam.put("opponentShotsOnGoal", eventStatsParsed.getOpponentShotsOnGoal());
            currTeam.put("totalShots", eventStatsParsed.getTotalShots());
            currTeam.put("opponentTotalShots", eventStatsParsed.getOpponentTotalShots());
            currTeam.put("expectedGoals", eventStatsParsed.getExpectedGoals());
            currTeam.put("opponentExpectedGoals", eventStatsParsed.getOpponentExpectedGoals());

            allTeamsStats.put(teamName, currTeam);
        }

        return allTeamsStats;
    }

    
    public String checkDataFile() throws IOException, InterruptedException{
        if (Files.exists(fileDataPath)){ //checks if file exist
            return Files.readString(fileDataPath); //returns the string stored in file
        }
        else{
            Files.createDirectories(fileDataPath.getParent());
            createDataFile();
            return Files.readString(fileDataPath);
        }
    }
    
    public String checkStatsFile() throws IOException, InterruptedException{
        if (Files.exists(fileStatsPath)){ //checks if file exist
            return Files.readString(fileStatsPath); //returns the string stored in file
        }
        else{
            Files.createDirectories(fileStatsPath.getParent());
            createStatsFile();
            return Files.readString(fileStatsPath);
        }
    }

    public void createDataFile() throws IOException, InterruptedException{
        Files.createDirectories(fileDataPath.getParent()); //getParent returns folder part of path, creating the folder that will host the file
        JSONObject allTeamsData = loadAllTeamsData();
        Files.writeString(fileDataPath, allTeamsData.toString(4)); //will write string to json and save, the 4 will indent to format json
    }
    
    public void createStatsFile() throws IOException, InterruptedException{
        Files.createDirectories(fileStatsPath.getParent()); //getParent returns folder part of path, creating the folder that will host the file
        JSONObject allTeamsStats = loadAllStatsTeams();
        Files.writeString(fileStatsPath, allTeamsStats.toString(4)); //will write string to json and save, the 4 will indent to format json
    }

    public Team getStoredTeam(String teamName) throws IOException {
        JSONObject allTeamsData = new JSONObject(readStoredFile(fileDataPath));
        JSONObject teamData = allTeamsData.getJSONObject(teamName);

        return new Team(
                teamData.getString("teamName"),
                teamData.getString("shortName"),
                teamData.getString("stadium"),
                teamData.getString("teamID"),
                teamData.getString("badgeUrl"));
    }

    public TeamStats getStoredTeamStats(String teamName) throws IOException {
        JSONObject allTeamsStats = new JSONObject(readStoredFile(fileStatsPath));
        JSONObject teamStats = allTeamsStats.getJSONObject(teamName);

        return new TeamStats(
                teamStats.getInt("teamScore"),
                teamStats.getInt("opponentScore"),
                teamStats.getString("eventID"));
    }

    public EventStats getStoredEventStats(String teamName) throws IOException {
        JSONObject allTeamsStats = new JSONObject(readStoredFile(fileStatsPath));
        JSONObject teamStats = allTeamsStats.getJSONObject(teamName);

        return new EventStats(
                teamStats.getString("eventID"),
                teamStats.getString("teamID"),
                teamStats.getString("opponentID"),
                teamStats.getBoolean("isHomeTeam"),
                teamStats.getDouble("shotsOnGoal"),
                teamStats.getDouble("opponentShotsOnGoal"),
                teamStats.getDouble("totalShots"),
                teamStats.getDouble("opponentTotalShots"),
                teamStats.getDouble("expectedGoals"),
                teamStats.getDouble("opponentExpectedGoals"));
    }

    public ArrayList<String> getStoredTeamsForLeague(String league) throws IOException {
        JSONObject allTeamsData = new JSONObject(readStoredFile(fileDataPath));
        ArrayList<String> leagueTeams = new ArrayList<>();

        for(int i = 0; i < teamNames.length; i++){
            String teamName = teamNames[i];

            if (allTeamsData.has(teamName)) {
                JSONObject teamData = allTeamsData.getJSONObject(teamName);

                if (teamData.has("league") && teamData.getString("league").equals(league)) {
                    leagueTeams.add(teamName);
                }
            }
        }

        return leagueTeams;
    }

    private String getLeague(String teamName) {
        if (teamName.equals("Arsenal")
                || teamName.equals("Liverpool")
                || teamName.equals("Manchester City")
                || teamName.equals("Manchester United")
                || teamName.equals("Chelsea")) {
            return "English Premier League";
        }
        else if (teamName.equals("FC Barcelona")
                || teamName.equals("Real Madrid")
                || teamName.equals("Atletico Madrid")
                || teamName.equals("Villarreal")
                || teamName.equals("Real Betis")) {
            return "Spanish La Liga";
        }
        else if (teamName.equals("Inter Milan")
                || teamName.equals("AC Milan")
                || teamName.equals("Juventus")
                || teamName.equals("Napoli")
                || teamName.equals("Roma")) {
            return "Italian Serie A";
        }
        else if (teamName.equals("Bayern Munich")
                || teamName.equals("Borussia Dortmund")
                || teamName.equals("Bayer Leverkusen")
                || teamName.equals("RB Leipzig")
                || teamName.equals("Eintracht Frankfurt")) {
            return "German Bundesliga";
        }
        else if (teamName.equals("Paris SG")
                || teamName.equals("Monaco")
                || teamName.equals("Marseille")
                || teamName.equals("Lyon")
                || teamName.equals("Lille")) {
            return "French Ligue 1";
        }
        else {
            return "";
        }
    }

    private String readStoredFile(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            throw new IOException("Local data file not found: " + filePath);
        }

        return Files.readString(filePath);
    }

}
