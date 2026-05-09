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
    
//    public String getScoreText(TeamStats stats) {
//      return stats.getTeamScore() + "-" + stats.getOtherScore();
//  }

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

    public double getAttackRating(TeamStats stats, EventStats eventStats){
        double goalRating = stats.getTeamScore() * 2.0;
        double shotsOnGoalRating = eventStats.getShotsOnGoal() * 0.3;
        double totalShotsRating = eventStats.getTotalShots() * 0.1;
        double expectedGoalsRating = eventStats.getExpectedGoals() * 1.5;

        double rating = goalRating + shotsOnGoalRating + totalShotsRating + expectedGoalsRating;
        return rating;
    }

    public double getDefenseRating(TeamStats stats, EventStats eventStats){
        double goalDefenseRating = stats.getOtherScore() * -2.0;
        double shotsOnGoalDefenseRating = eventStats.getOpponentShotsOnGoal() * -0.3;
        double totalShotsDefenseRating = eventStats.getOpponentTotalShots() * -0.1;
        double expectedGoalsDefenseRating = eventStats.getOpponentExpectedGoals() * -1.5;

        double rating = goalDefenseRating + shotsOnGoalDefenseRating + totalShotsDefenseRating + expectedGoalsDefenseRating;
        return rating;
    }

    public double getFinalTeamRating(TeamStats stats, EventStats eventStats){
        double matchRating = getRating(stats);
        double attackRating = getAttackRating(stats, eventStats);
        double defenseRating = getDefenseRating(stats, eventStats);

        double rating = matchRating + attackRating + defenseRating;
        return rating;
    }

    public boolean hasEnoughData(TeamStats stats, EventStats eventStats){
        if (stats.getEventId().equals("")) {
            return false;
        }

        if (eventStats.getShotsOnGoal() == 0
                && eventStats.getOpponentShotsOnGoal() == 0
                && eventStats.getTotalShots() == 0
                && eventStats.getOpponentTotalShots() == 0
                && eventStats.getExpectedGoals() == 0
                && eventStats.getOpponentExpectedGoals() == 0) {
            return false;
        }

        return true;
    }

//    public String predict(Team team1, TeamStats stats1, Team team2, TeamStats stats2){
//        String team1Name = team1.getTeamName();
//        String team2Name = team2.getTeamName();
//
//        double rating1 = getRating(stats1);
//        double rating2 = getRating(stats2);
//
//        if(rating1 > rating2){
//            return team1Name;
//        }
//        else if (rating2 > rating1){
//            return team2Name;
//        }
//        else{
//            return "DRAW";
//        }
//    }
//    
    public String predict(Team team1, TeamStats stats1, EventStats eventStats1,
            Team team2, TeamStats stats2, EventStats eventStats2){
        String team1Name = team1.getTeamName();
        String team2Name = team2.getTeamName();

        if (!hasEnoughData(stats1, eventStats1) || !hasEnoughData(stats2, eventStats2)) {
            return "NO_DATA";
        }

        double rating1 = getFinalTeamRating(stats1, eventStats1);
        double rating2 = getFinalTeamRating(stats2, eventStats2);

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
