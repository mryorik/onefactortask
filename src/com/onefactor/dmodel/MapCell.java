package com.onefactor.dmodel;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * User: Yaroslav Frolikov
 * Date: 13.02.16 18:14
 */
@Entity
public class MapCell {
    @EmbeddedId
    MapCellPK id;

    @NotNull
    Double mean_distance;

    public MapCellPK getId() {
        return id;
    }

    public void setId(MapCellPK id) {
        this.id = id;
    }

    public Double getMean_distance() {
        return mean_distance;
    }

    public void setMean_distance(Double mean_distance) {
        this.mean_distance = mean_distance;
    }
}
