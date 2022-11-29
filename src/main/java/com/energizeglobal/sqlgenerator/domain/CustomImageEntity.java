package com.energizeglobal.sqlgenerator.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
@DiscriminatorValue("I")
public class CustomImageEntity {

    @Id
    @Column
    private int id;

    @ManyToOne
    @JoinColumn(name = "fk_id_image", referencedColumnName = "id")
    @JsonManagedReference
    private ImageEntity image;

    @Transient
    public static String getDecriminatorValue() {
        return CustomImageEntity.class.getAnnotation(DiscriminatorValue.class).value();
    }

    public ImageEntity getImage() {
        return image;
    }

    public void setImage(ImageEntity image) {
        this.image = image;
    }

}
