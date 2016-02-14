package com.onefactor.dao;

import com.onefactor.dmodel.MapCell;
import com.onefactor.dmodel.MapCellPK;

/**
 * User: Yaroslav Frolikov
 * Date: 13.02.16 18:47
 */
public interface MapCellDao {
    MapCell find(MapCellPK id);

    Long getUsersCountInCell(int tile_x, int tile_y);
}
