package org.example;

public class Country {
    private final String cName;
    private final String cFlag;

    Country(String cName, String cFlag){
        this.cName = cName;
        this.cFlag = cFlag;
    }

    public String getName(){
        return cName;
    }

    public String getFlag(){
        return cFlag;
    }
}
