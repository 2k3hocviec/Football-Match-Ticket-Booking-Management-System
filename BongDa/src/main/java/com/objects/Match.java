
package com.objects;

import java.time.LocalDateTime;


public class Match {
    private String match_id;
    private String home_team;
    private String away_team;
    private String stadium_id;
    private LocalDateTime match_date;
    private String tournament;

    public Match(String match_id, String home_team, String away_team, String stadium_id, LocalDateTime match_date, String tournament) {
        this.match_id = match_id;
        this.home_team = home_team;
        this.away_team = away_team;
        this.stadium_id = stadium_id;
        this.match_date = match_date;
        this.tournament = tournament;
    }

    public Match() {
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public String getHome_team() {
        return home_team;
    }

    public void setHome_team(String home_team) {
        this.home_team = home_team;
    }

    public String getAway_team() {
        return away_team;
    }

    public void setAway_team(String away_team) {
        this.away_team = away_team;
    }

    public String getStadium_id() {
        return stadium_id;
    }

    public void setStadium_id(String stadium_id) {
        this.stadium_id = stadium_id;
    }

    public LocalDateTime getMatch_date() {
        return match_date;
    }

    public void setMatch_date(LocalDateTime match_date) {
        this.match_date = match_date;
    }

    public String getTournament() {
        return tournament;
    }

    public void setTournament(String tournament) {
        this.tournament = tournament;
    }
    
    
}
