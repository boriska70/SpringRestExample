package com.hp.hpa.rest;

/**
 * User: belozovs
 * Date: 3/13/13
 * Description
 */
public class MyData {

    private String name;

    public MyData(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public String getNameByPrefix(String prefix){
        if(name.startsWith(prefix)){
            return name;
        } else {
            return "Not found";
        }
    }

}
