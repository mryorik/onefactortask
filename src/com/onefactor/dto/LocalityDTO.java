package com.onefactor.dto;

/**
 * User: Yaroslav Frolikov
 * Date: 11.02.16 21:23
 */
public class LocalityDTO {
    private Boolean near_the_placemark;
    private Double distance;
    private Double mean_distance;

    public Boolean getNear_the_placemark() {
        return near_the_placemark;
    }

    public void setNear_the_placemark(Boolean near_the_placemark) {
        this.near_the_placemark = near_the_placemark;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getMean_distance() {
        return mean_distance;
    }

    public void setMean_distance(Double mean_distance) {
        this.mean_distance = mean_distance;
    }
}
