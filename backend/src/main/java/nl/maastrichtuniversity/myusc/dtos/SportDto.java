package nl.maastrichtuniversity.myusc.dtos;

import nl.maastrichtuniversity.myusc.model.SportType;

public class SportDto {
    private Long id;
    private String name;

    private String description;

    private SportType sportType;

    public SportDto(String basketball, String aTeamSport, SportType sportType) {
    }

    public SportDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
