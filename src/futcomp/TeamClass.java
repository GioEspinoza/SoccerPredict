/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package futcomp;

/**
 *
 * @author gio
 */
public class TeamClass {
    private String teamName;
    private String shortName;
    private String stadium;
    private String teamID;
    
    public TeamClass(String teamName, String shortName, String stadium, String teamID){
        this.teamName = teamName;
        this.shortName = shortName;
        this.stadium = stadium;
        this.teamID = teamID;
        }

    public String getTeamName() {
        return teamName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getStadium() {
        return stadium;
    }

    public String getTeamID() {
        return teamID;
    }
    
}

class TeamStats {
    private int teamScore;
    private int otherScore;
    private String recentGameDate;

    public TeamStats(int teamScore, int otherScore, String recentGameDate){
        this.teamScore = teamScore;
        this.otherScore = otherScore;
        this.recentGameDate = recentGameDate;
        
    }

    public int getTeamScore() {
        return teamScore;
    }

    public int getOtherScore() {
        return otherScore;
    }

    public String getRecentGameDate() {
        return recentGameDate;
    }
    
    
}
