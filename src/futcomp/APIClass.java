package futcomp;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest;

public class APIClass {

    public String getTeamJson(String teamName) throws IOException, InterruptedException {
        
        String url = "https://www.thesportsdb.com/api/v1/json/123/searchteams.php?t=" + teamName;
        
        HttpClient client = HttpClient.newHttpClient();
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        return response.body();
    }    

    public String getTeamData(String teamID) throws IOException, InterruptedException {
        
        String url = "https://www.thesportsdb.com/api/v1/json/123/eventslast.php?id=" + teamID;
        
        HttpClient client = HttpClient.newHttpClient();
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        return response.body();
    
    }

    public String getTeamJsonByID(String teamID) throws IOException, InterruptedException {

        String url = "https://www.thesportsdb.com/api/v1/json/123/lookupteam.php?id=" + teamID;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();

    }
    
}
