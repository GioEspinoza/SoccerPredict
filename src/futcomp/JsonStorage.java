/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package futcomp;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException; 

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
    Path filePath = Path.of("data/team_data.json") ; //creates path object with path i want to check for

    
    
    public String loadOrCreate()throws IOException{
                if(Files.exists(filePath)){ // checks if file exist
            ...
        }
        else{
            Files.createDirectories(filePath.getParent()); //will create file directory
            for(int i = 0; i < teamNames.length; i++){
                
            }
        }
    }
}