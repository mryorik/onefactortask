package com.onefactor.service;

import com.onefactor.api.ApiErrorCode;
import com.onefactor.dao.MapCellDao;
import com.onefactor.dao.PlacemarkDao;
import com.onefactor.dmodel.MapCell;
import com.onefactor.dmodel.MapCellPK;
import com.onefactor.dmodel.Placemark;
import com.onefactor.dto.LocalityDTO;
import com.onefactor.dto.NewPlacemarkDTO;
import com.onefactor.dto.UsersCellStatsDTO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.Math.*;

/**
 * User: Yaroslav Frolikov
 * Date: 11.02.16 22:09
 */
public class LocalityServiceImpl implements LocalityService {
    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    PlacemarkDao placemarkDao;

    @Autowired
    MapCellDao mapCellDao;

    @Transactional
    @Override
    public LocalityDTO getLocality(Long id, Double latitude, Double longitude) throws ServiceException {
        Placemark placemark = placemarkDao.find(id);

        if (placemark == null) {
            throw new ServiceException(String.format("Placemark for user with id = %d not found", id),
                    ApiErrorCode.NOT_FOUND);
        }

        MapCell mapCell = mapCellDao.find(new MapCellPK(longitude.intValue(), latitude.intValue()));
        if (mapCell == null) {
            throw new ServiceException(String.format("No data found for cell tile_x = %d, tile_y = %d",
                    longitude.intValue(), latitude.intValue()), ApiErrorCode.NOT_FOUND);
        }

        Double lat1 = placemark.getLat() / 180 * PI;
        Double lon1 = placemark.getLon() / 180 * PI;
        Double lat2 = latitude / 180 * PI;
        Double lon2 = longitude / 180 * PI;
        double distance = 6371 * acos(sin(lat1) * sin(lat2) + cos(lat1) * cos(lat2) * cos(lon1 - lon2));

        LocalityDTO locality = new LocalityDTO();
        locality.setDistance(distance);

        locality.setMean_distance(mapCell.getMean_distance());
        locality.setNear_the_placemark(distance <= mapCell.getMean_distance());

        return locality;
    }

    @Transactional
    @Override
    public Placemark putPlacemark(Long id, NewPlacemarkDTO newPlacemarkDTO) {
        Placemark placemark = new Placemark();
        placemark.setUserId(id);
        placemark.setLat(newPlacemarkDTO.getLat());
        placemark.setLon(newPlacemarkDTO.getLon());

        placemarkDao.store(placemark);

        return placemark;
    }

    @Transactional
    @Override
    public UsersCellStatsDTO getUsersCellStats(Double latitude, Double longitude) {
        UsersCellStatsDTO dto = new UsersCellStatsDTO();

        int tile_y = latitude.intValue();
        int tile_x = longitude.intValue();

        dto.setCount(mapCellDao.getUsersCountInCell(tile_x, tile_y));

        return dto;
    }
}
