package futcomp;

//handles all input/output exceptions (calls to network can fail)
import java.io.IOException;

//Builds URL
//import java.net.URI;

//core class for http (sends requests)
//import java.net.http.HttpClient;

//will represent actual request (GET, POST, etc)
//import java.net.http.HttpResponse;

//Represents the response from the server requested for.
//import java.net.http.HttpRequest;
import java.util.Scanner;

/**
 *
 * @author gio
 */
public class APITEST {
    
    // IOException is for network failure, BAD Urls, and servers not responding
    //InterruptedException is when thread is stopped while waiting
    // where we do client.send the block is waiting for a response and if something interrupts Java will throw this
    public static void main(String[] args) throws IOException, InterruptedException {
        String winner;
        Scanner input = new Scanner(System.in);
        String choice1;
        String choice2;
        System.out.println("Select a team (1-3)");
        System.out.println("""
                           1. France
                           2. Argentina
                           3. Spain
                           """);
        
        int teamName = input.nextInt();
        if(teamName == 1){
            choice1 = "France";
            choice2 = "Argentina";
        }
        else if(teamName == 2){
            choice1 = "Argentina";
            choice2 = "Spain";
        }
        else{
            choice1 = "Spain";
            choice2 = "France";
        }
      
        
        //our api object that will call for api data
        APIClass API = new APIClass();
        

        //json object that will parse the api data
        JsonParse parse = new JsonParse();
        

        //stats object that will do the calculations
        StatsCalc calc = new StatsCalc();
        
        
        //hold the team profile json data in a string
        String teamJson1 = API.getTeamJson(choice1);
        String teamJson2 = API.getTeamJson(choice2);
        

        //set up team data for basic team profile data used for gui
        Team teamData1 = parse.teamData(teamJson1);
        Team teamData2 = parse.teamData(teamJson2);
      
        
        //hold the last game json data in a string
        String statsJson1 = API.getTeamData(teamData1.getTeamID());
        String statsJson2 = API.getTeamData(teamData2.getTeamID());
       
        
        //set up team stas used for actual calculation and prediciton
        TeamStats stats1 = parse.teamStatsData(statsJson1, teamData1.getTeamID());
        TeamStats stats2 = parse.teamStatsData(statsJson2, teamData2.getTeamID());
        
        //hold winner of comparison in winner variable
        winner = calc.predict(teamData1, stats1, teamData2, stats2);
        
        
        //System.out.println(parse.teamData(jsonData));
        
        System.out.println("Predicted Stronger Team: " + winner);
    }
}
 ////////////////////////////////////////////////////////////////////////////////    NOTES     
        //API Endpoint with quey parameter
        //t=Peru -> tells api what team to look for
    //    String url = "https://www.thesportsdb.com/api/v1/json/123/searchteams.php?t=" + teamName;
        //create client that will handle sending the requests
    //    HttpClient client = HttpClient.newHttpClient();
        
        //make the http request
        //newBuilder starts building a request object step by step before build() finalizes it
    //    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        //URI Will convert the string to an actual URL
        //GET will specify the request to us actually triyng to fetch data
        //build will finalize the request
        
        //send request and wait
        //Bodyhandlers.ofString tells java that when the server does repsonse, it will give the body as a string
    //    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        //body will equal the actual json data we get back from API
    //    System.out.println(response.body());
    //    System.out.println(response.statusCode());
        //REMEMBER FLOW OF AN API CALL, 
        //URL -> Request -> Client sends -> Server Responds -> read response
