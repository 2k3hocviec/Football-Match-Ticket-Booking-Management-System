
package com.objects;

public class Stadium {
    private String stadium_id;
    private String name;
    private String location;
    private int capacity;

    public Stadium() {
    }

    public Stadium(String stadium_id, String name, String location, int capacity) {
        this.stadium_id = stadium_id;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
    }

    public String getStadium_id() {
        return stadium_id;
    }

    public void setStadium_id(String stadium_id) {
        this.stadium_id = stadium_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    
}
