package com.threektechone.resorthub.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name= "resort_image")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResortImage {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="image_id")
    private int imageId;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "resort_id", nullable = false)
    private Resort resort;
}
