
package com.objects;


public class Ticket {
    private String ticket_id;
    private String order_id;
    private String match_id;
    private String stadium_id;
    private String seat_id;
    private int price;

    public Ticket() {
    }

    public Ticket(String ticket_id, String order_id, String match_id, String stadium_id, String seat_id, int price) {
        this.ticket_id = ticket_id;
        this.order_id = order_id;
        this.match_id = match_id;
        this.stadium_id = stadium_id;
        this.seat_id = seat_id;
        this.price = price;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
    
}
