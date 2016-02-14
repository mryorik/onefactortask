package com.onefactor.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * User: Yaroslav Frolikov
 * Date: 11.02.16 21:39
 */
public class NewPlacemarkDTO {
    @Min(-90)
    @Max(90)
    private Double lat;

    @Min(-180)
    @Max(180)
    private Double lon;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
