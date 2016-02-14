package com.onefactor.service;

import com.onefactor.dmodel.Placemark;
import com.onefactor.dto.LocalityDTO;
import com.onefactor.dto.NewPlacemarkDTO;
import com.onefactor.dto.UsersCellStatsDTO;

/**
 * User: Yaroslav Frolikov
 * Date: 11.02.16 22:09
 */
public interface LocalityService {
    /**
     * Получить категорию местоположения пользователя, находящегося в заданной точке.
     * @param id идентификатор пользователя
     * @param latitude широта текущего местоположения пользователя
     * @param longitude долгота текущего местоположения пользователя
     * @return данные о категории местоположения
     */
    LocalityDTO getLocality(Long id, Double latitude, Double longitude) throws ServiceException;

    /**
     * Создать новую метку
     * @param id идентификатор пользователя
     * @param newPlacemarkDTO данные метки
     * @return
     */
    Placemark putPlacemark(Long id, NewPlacemarkDTO newPlacemarkDTO);

    /**
     * Получить статистику по пользователям в ячейке, в которую попали заданные координаты.
     * @param latitude широта
     * @param longitude долгота
     * @return статистика по пользователям в ячейке
     */
    UsersCellStatsDTO getUsersCellStats(Double latitude, Double longitude);
}
