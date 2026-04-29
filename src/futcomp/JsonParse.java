/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package futcomp;
import org.json.JSONObject; //needed to make object that will parse json data
import org.json.JSONArray; //will create the json array with data

public class JsonParse {
    public String teamData(String jsonData) {
        JSONObject parse = new JSONObject(jsonData); //makes jsondata into json object named parse
        JSONArray teams = parse.getJSONArray("teams"); //assigns parse as an array
        JSONObject team = teams.getJSONObject(0); //assigsn team as first index of the json array
        return team.getString("strTeamShort"); //from team (the array) parse for data wanted
    }
}

