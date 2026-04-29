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
        Scanner input = new Scanner(System.in);
        String choice;
        System.out.println("Select a team (1-3)");
        System.out.println("""
                           1. Peru
                           2. Argentina
                           3. Spain
                           """);
        
        int teamName = input.nextInt();
        if(teamName == 1){
            choice = "Peru";
        }
        else if(teamName == 2){
            choice = "Argentina";
        }
        else{
            choice = "Spain";
        }
        
        APIClass API = new APIClass();
        JsonParse parse = new JsonParse();
        
        String jsonData = API.getTeamJson(choice);
        
        System.out.println(parse.teamData(jsonData));
        System.out.println(API.getTeamJson(choice));
       
    }
}
 ////////////////////////////////////////////////////////////////////////////////        
        //API Endpoint with quey parameter
        //t=Peru -> tells api what team to look for
    //    String url = "https://www.thesportsdb.com/api/v1/json/123/searchteams.php?t=" + teamName;
        //create client that will handle sending the requests
    //    HttpClient client = HttpClient.newHttpClient();
        
        //make the http request
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