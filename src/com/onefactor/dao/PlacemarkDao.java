package com.onefactor.dao;

import com.onefactor.dmodel.Placemark;

/**
 * User: Yaroslav Frolikov
 * Date: 11.02.16 23:43
 */
public interface PlacemarkDao {
    Placemark find(Long id);

    void store(Placemark placemark);
}
