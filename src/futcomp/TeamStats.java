/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package futcomp;

/**
 *
 * @author gio
 */
public class TeamStats {
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
