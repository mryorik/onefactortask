package com.onefactor.controller;

import com.onefactor.api.ApiResponse;
import com.onefactor.api.ApiResponseStatus;
import com.onefactor.dmodel.Placemark;
import com.onefactor.dto.LocalityDTO;
import com.onefactor.dto.NewPlacemarkDTO;
import com.onefactor.dto.UsersCellStatsDTO;
import com.onefactor.service.LocalityService;
import com.onefactor.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * User: Yaroslav Frolikov
 * Date: 11.02.16 20:44
 */
@RestController
public class UserController {
    @Autowired
    LocalityService localityService;

    @RequestMapping(value = "/users/{id}/locality", method = RequestMethod.GET)
    public ApiResponse getLocality(@PathVariable("id") Long id,
                            @RequestParam(value = "lat", required = true) Double latitude,
                            @RequestParam(value = "lon", required = false) Double longitude) {
        try {
            LocalityDTO dto = localityService.getLocality(id, latitude, longitude);
            return new ApiResponse(dto);
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ApiResponse(null, new ApiResponseStatus(e.getErrorCode(), e.getMessage()));
        }
    }

    @RequestMapping(value = "/users/{id}/placemark", method = RequestMethod.PUT)
    public ApiResponse putPlacemark(@PathVariable("id") Long id,
                             @RequestBody(required = true) @Valid NewPlacemarkDTO placemarkDTO,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ApiResponse(bindingResult);
        }

        Placemark placemark = localityService.putPlacemark(id, placemarkDTO);
        return new ApiResponse(placemark);
    }

    @RequestMapping(value = "/users/cellstats", method = RequestMethod.GET)
    public ApiResponse getCellStats(@RequestParam(value = "lat", required = true) Double latitude,
                             @RequestParam(value = "lon", required = false) Double longitude) {
        UsersCellStatsDTO dto = localityService.getUsersCellStats(latitude, longitude);
        return new ApiResponse(dto);
    }
}
