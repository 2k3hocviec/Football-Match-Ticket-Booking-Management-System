
package com.objects;

public class Seat {
    private String match_id;
    private String stadium_id;
    private String seat_id;
    private boolean status;

    public Seat(String match_id, String stadium_id, String seat_id, boolean status) {
        this.match_id = match_id;
        this.stadium_id = stadium_id;
        this.seat_id = seat_id;
        this.status = status;
    }

    public Seat() {
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public String getStadium_id() {
        return stadium_id;
    }

    public void setStadium_id(String stadium_id) {
        this.stadium_id = stadium_id;
    }

    public String getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(String seat_id) {
        this.seat_id = seat_id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
