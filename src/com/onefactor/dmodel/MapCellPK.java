package com.onefactor.dmodel;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * User: Yaroslav Frolikov
 * Date: 13.02.16 18:34
 */
@Embeddable
public class MapCellPK implements Serializable {
    @NotNull
    private Integer tile_x;

    @NotNull
    private Integer tile_y;

    public MapCellPK(Integer tile_x, Integer tile_y) {
        this.tile_x = tile_x;
        this.tile_y = tile_y;
    }

    public Integer getTile_x() {
        return tile_x;
    }

    public void setTile_x(Integer tile_x) {
        this.tile_x = tile_x;
    }

    public Integer getTile_y() {
        return tile_y;
    }

    public void setTile_y(Integer tile_y) {
        this.tile_y = tile_y;
    }
}
