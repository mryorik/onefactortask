package com.onefactor.dmodel;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * User: Yaroslav Frolikov
 * Date: 11.02.16 21:10
 */

@Entity
@DynamicInsert
@DynamicUpdate
public class Placemark {
    @Id
    private Long userId;

    @NotNull
    private Double lon;

    @NotNull
    private Double lat;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}
