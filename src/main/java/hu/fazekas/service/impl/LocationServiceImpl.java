package hu.fazekas.service.impl;

import hu.fazekas.dao.LocationDao;
import hu.fazekas.dto.LocationDto;
import hu.fazekas.service.LocationService;
import hu.fazekas.validator.LocationValidator;

public class LocationServiceImpl implements LocationService {

    private final LocationValidator locationValidator;
    private final LocationDao locationDao;

    public LocationServiceImpl(LocationValidator locationValidator, LocationDao locationDao){
        this.locationValidator = locationValidator;
        this.locationDao = locationDao;
    }


    @Override
    public void saveLocation(LocationDto location) {
        if(locationValidator.isValid(location)){
            locationDao.saveLocation(location);
        }
    }
}
