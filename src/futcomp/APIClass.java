package futcomp;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class APIClass {

    private static final String API_KEY = loadApiKey();

    private static String loadApiKey() {
        String environmentKey = System.getenv("THESPORTSDB_API_KEY");

        if (environmentKey != null && !environmentKey.isBlank()) {
            return environmentKey.trim();
        }

        Path envFile = Path.of(".env");

        if (Files.exists(envFile)) {
            Properties properties = new Properties();

            try (var reader = Files.newBufferedReader(envFile)) {
                properties.load(reader);
                String fileKey = properties.getProperty("THESPORTSDB_API_KEY");

                if (fileKey != null && !fileKey.isBlank()) {
                    return fileKey.trim();
                }
            }
            catch (IOException error) {
                throw new IllegalStateException("Unable to read the local .env file.", error);
            }
        }

        throw new IllegalStateException(
                "THESPORTSDB_API_KEY must be set in the environment or local .env file."
        );
    }

    private String apiUrl(String endpoint) {
        return "https://www.thesportsdb.com/api/v1/json/" + API_KEY + "/" + endpoint;
    }

    public String getTeamJson(String teamName) throws IOException, InterruptedException {
        
        String url = apiUrl("searchteams.php?t=" + encodeUrlParameter(teamName));
        
        HttpClient client = HttpClient.newHttpClient();
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        return response.body();
    }    

    private String encodeUrlParameter(String parameter) {
        return URLEncoder.encode(parameter, StandardCharsets.UTF_8);
    }

    public String getStatsJson(String teamID) throws IOException, InterruptedException {
        
        String url = apiUrl("eventslast.php?id=" + teamID);
        
        HttpClient client = HttpClient.newHttpClient();
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        return response.body();
    
    }

//    public String getTeamJsonByID(String teamID) throws IOException, InterruptedException {
//
//        String url = apiUrl("lookupteam.php?id=" + teamID);
//
//        HttpClient client = HttpClient.newHttpClient();
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(url))
//                .GET()
//                .build();
//
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        return response.body();
//
//    }
    public String getEventStatJson(String eventID) throws IOException, InterruptedException {

        String url = apiUrl("lookupeventstats.php?id=" + eventID);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();

    }

}
