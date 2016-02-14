package com.onefactor.dao;

import com.onefactor.dmodel.MapCell;
import com.onefactor.dmodel.MapCellPK;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: Yaroslav Frolikov
 * Date: 13.02.16 18:49
 */
public class MapCellJdbcDao implements MapCellDao {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public MapCell find(MapCellPK id) {
        return (MapCell) sessionFactory.getCurrentSession().get(MapCell.class, id);
    }

    @Override
    public Long getUsersCountInCell(int tile_x, int tile_y) {
        return (Long) sessionFactory.getCurrentSession()
                .createQuery("select count(id) from Placemark where lat >= :tile_y and lat < :tile_y_1" +
                        " and lon >= :tile_x and lon < :tile_x_1")
                .setParameter("tile_x", (double) tile_x)
                .setParameter("tile_x_1", tile_x + 1.0)
                .setParameter("tile_y", (double) tile_y)
                .setParameter("tile_y_1", tile_y + 1.0)
                .uniqueResult();
    }
}
