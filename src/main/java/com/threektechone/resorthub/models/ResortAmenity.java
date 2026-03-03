package com.threektechone.resorthub.models;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "resort_amenities")
public class ResortAmenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "amenity_id")
    private int amenityId;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "icon", length = 255)
    private String icon;

    @Column(name = "description", length = 255)
    private String description;

    @ManyToMany(mappedBy = "amenities")
    private Set<Resort> resorts;

    public ResortAmenity() {}

    public ResortAmenity(String name, String icon,String description) {
        this.name = name;
        this.icon = icon;
        this.description = description;
    }

    public int getAmenityId() {
    return amenityId;
}

public void setAmenityId(int amenityId) {
    this.amenityId = amenityId;
}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public String getIcon() {
    return icon;
}

public void setIcon(String icon) {
    this.icon = icon;
}

public String getDescription() {
    return description;
}

public void setDescription(String description) {
    this.description = description;
}

public Set<Resort> getResorts() {
    return resorts;
}

public void setResorts(Set<Resort> resorts) {
    this.resorts = resorts;
}





    
    

    
}
