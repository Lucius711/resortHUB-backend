package com.threektechone.resorthub.models;

import java.util.Set;

import com.threektechone.resorthub.enums.AmenityName;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "resort_amenities")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResortAmenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "amenity_id")
    private int amenityId;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private AmenityName name;

    @Column(name = "icon", length = 255)
    private String icon;

    @Column(name = "description", length = 255)
    private String description;

    @ManyToMany(mappedBy = "amenities")
    private Set<Resort> resorts;
       
}
