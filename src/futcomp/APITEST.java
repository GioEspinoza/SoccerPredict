package futcomp;

//handles all input/output exceptions (calls to network can fail)
import java.io.IOException;

//Builds URL
import java.net.URI;

//core class for http (sends requests)
import java.net.http.HttpClient;

//will represent actual request (GET, POST, etc)
import java.net.http.HttpResponse;

//Represents the response from the server requested for.
import java.net.http.HttpRequest;

/**
 *
 * @author gio
 */
public class APITEST {
    
    public static void main(String[] args) throws IOException, InterruptedException {
        String teamName = "Peru";
        
        //API Endpoint with quey parameter
        //t=Peru -> tells api what team to look for
        String url = "https://www.thesportsdb.com/api/v1/json/123/searchteams.php?t=" + teamName;
        //create client that will handle sending the requests
        HttpClient client = HttpClient.newHttpClient();
        
        //make the http request
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        //URI Will convert the string to an actual URL
        //GET will specify the request to us actually triyng to fetch data
        //build will finalize the request
        
        //send request and wait
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        //body will equal the actual json data we get back from API
        System.out.println(response.body());
        System.out.println(response.statusCode());
        //REMEMBER FLOW OF AN API CALL, 
        //URL -> Request -> Client sends -> Server Responds -> read response
    }
}