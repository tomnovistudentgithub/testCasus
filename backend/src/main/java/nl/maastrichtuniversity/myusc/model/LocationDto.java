package nl.maastrichtuniversity.myusc.model;

import jakarta.persistence.Column;

public class LocationDto {


    private Long id;
    private String name;
    private String buildingSection;
    private int capacity;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuildingSection() {
        return buildingSection;
    }

    public void setBuildingSection(String buildingSection) {
        this.buildingSection = buildingSection;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
