/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package futcomp;

/**
 *
 * @author gio
 */
public class EventStats {
    private String eventId;
    private String teamId;
    private String opponentId;
    private boolean isHomeTeam;

    private double shotsOnGoal;
    private double opponentShotsOnGoal;

    private double totalShots;
    private double opponentTotalShots;

    private double expectedGoals;
    private double opponentExpectedGoals;
    
    public EventStats(String eventId, String teamId, String opponentId, boolean isHomeTeam,
            double shotsOnGoal, double opponentShotsOnGoal,
            double totalShots, double opponentTotalShots,
            double expectedGoals, double opponentExpectedGoals) {
        this.eventId = eventId;
        this.teamId = teamId;
        this.opponentId = opponentId;
        this.isHomeTeam = isHomeTeam;
        this.shotsOnGoal = shotsOnGoal;
        this.opponentShotsOnGoal = opponentShotsOnGoal;
        this.totalShots = totalShots;
        this.opponentTotalShots = opponentTotalShots;
        this.expectedGoals = expectedGoals;
        this.opponentExpectedGoals = opponentExpectedGoals;
    }

    public String getEventId() {
        return eventId;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getOpponentId() {
        return opponentId;
    }

    public boolean isHomeTeam() {
        return isHomeTeam;
    }

    public double getShotsOnGoal() {
        return shotsOnGoal;
    }

    public double getOpponentShotsOnGoal() {
        return opponentShotsOnGoal;
    }

    public double getTotalShots() {
        return totalShots;
    }

    public double getOpponentTotalShots() {
        return opponentTotalShots;
    }

    public double getExpectedGoals() {
        return expectedGoals;
    }

    public double getOpponentExpectedGoals() {
        return opponentExpectedGoals;
    }

}
