/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package futcomp;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException; 
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
                "Manchester+City",
                "Manchester+United",
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
            allTeamsData.put(teamNames[i], currTeam);
         }
        return allTeamsData;
 
    }
    
    public JSONObject loadAllStatsTeams()throws IOException, InterruptedException{
        JsonParse parse = new JsonParse();
        JSONObject allTeamsStats = new JSONObject();
        String teamJson;
        for(int i = 0; i < teamNames.length; i++){ 
            JSONObject currTeam = new JSONObject();
            teamJson = API.getTeamJson(teamNames[i]);
            Team teamParsed = parse.teamData(teamJson);
            
            String teamId = teamParsed.getTeamID();
            String teamStatsJson = API.getStatsJson(teamId);
            TeamStats teamStatsParsed = parse.teamStatsData(teamStatsJson, teamId);

            String eventId = teamStatsParsed.getEventId();
            String opponentId = parse.getOpponentId(teamStatsJson, teamId);
            boolean isHomeTeam = parse.isHomeTeam(teamStatsJson, teamId);
            String eventStatsJson = API.getEventStatJson(eventId);
            EventStats eventStatsParsed = parse.teamEventStatsData(eventStatsJson, teamId, opponentId, isHomeTeam);

            currTeam.put("teamName", teamParsed.getTeamName());
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

            allTeamsStats.put(teamParsed.getTeamName(), currTeam);
        }

        return allTeamsStats;
    }
//    Files.writeString(filePath, API.getTeamJson(teamNames[i]));
    
    
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


    //public String loadOrCreate()throws IOException, InterruptedException{
    //    Files.createDirectories(filePath.getParent()); //will create file directory
    //    return Files.readString(filePath);
    //}
}
