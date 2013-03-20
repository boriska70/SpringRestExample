package com.hp.hpa.rest;

/**
 * User: belozovs
 * Date: 3/13/13
 * Description
 */
public class MyData {

    private String name;
    private String planet;

    public MyData(String name) {
        this.name = name;
        this.planet = "Earth";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public String getNameByPrefix(String prefix) {
        if (name.startsWith(prefix)) {
            return name;
        }
        else {
            return "Not found";
        }
    }

}
