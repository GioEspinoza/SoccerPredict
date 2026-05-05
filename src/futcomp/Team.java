/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package futcomp;

/**
 *
 * @author gio
 */
public class Team {
    private String teamName;
    private String shortName;
    private String stadium;
    private String teamID;
    private String badgeUrl;
    
    public Team(String teamName, String shortName, String stadium, String teamID){
        this(teamName, shortName, stadium, teamID, "");
        }

    public Team(String teamName, String shortName, String stadium, String teamID, String badgeUrl){
        this.teamName = teamName;
        this.shortName = shortName;
        this.stadium = stadium;
        this.teamID = teamID;
        this.badgeUrl = badgeUrl;
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

    public String getBadgeUrl() {
        return badgeUrl;
    }
    
}
