package nl.maastrichtuniversity.myusc.model;

import java.util.List;

public class SportDto {

    private String name;

    private SportType sportType;

    public String getName() {
        return name;
    }

    public SportType getSportType() {
        return sportType;
    }

    public void setSportType(SportType sportType) {
        this.sportType = sportType;
    }

    public void setName(String name) {
        this.name = name;
    }

}
