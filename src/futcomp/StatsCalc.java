/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package futcomp;

/**
 *
 * @author gio
 */
public class StatsCalc {
    
    public String getScoreText(TeamStats stats) {
      return stats.getTeamScore() + "-" + stats.getOtherScore();
  }

    public String getResult(TeamStats stats){
        int teamScore = stats.getTeamScore();
        int otherScore = stats.getOtherScore();
        if(teamScore > otherScore){
            String results = ("WON");
            return results;
        }
        else if(teamScore == otherScore){
            String results = ("DRAW");
            return results;
        }
        else{
            String results = ("LOST");
            return results;
        }
        
    }
    
    public int getPoints(TeamStats stats){
        String result = getResult(stats);
        int points = 0;
        if(result.equals("WON")){
            points = 3;
            return points;
        }
        else if(result.equals("DRAW")){
            points = 1;
            return points;
        }
        else{
            return points;
        }
        
    }
    public double getRating(TeamStats stats){
        int points = getPoints(stats);
        int goalDifference = stats.getTeamScore() - stats.getOtherScore();
        double rating = points + (goalDifference * 0.5);
        return rating;
    }
    
    public String predict(Team team1, TeamStats stats1, Team team2, TeamStats stats2){
        String team1Name = team1.getTeamName();
        String team2Name = team2.getTeamName();
        
        double rating1 = getRating(stats1);
        double rating2 = getRating(stats2);
        
        if(rating1 > rating2){
            return team1Name;
        }
        else if (rating2 > rating1){
            return team2Name;
        }
        else{
            return "DRAW";
        }
    }
}
