package com.lbet.models.domain;

import javax.persistence.*;

@Entity
public class ImagesData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long imageId;
    @Lob
    byte[] imgData;

    public ImagesData() {
    }

    public ImagesData(byte[] imgData) {
        this.imgData = imgData;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public byte[] getImgData() {
        return imgData;
    }

    public void setImgData(byte[] imgData) {
        this.imgData = imgData;
    }
}
