package hu.fazekas.validator;

import hu.fazekas.dao.LocationDao;
import hu.fazekas.dto.LocationDto;

public class LocationValidator {

    private final LocationDao locationDao;

    public LocationValidator(LocationDao locationDao){
        this.locationDao = locationDao;
    }

    public Boolean isValid(LocationDto location){
        return location.getId() != null && !locationDao.existsById(location.getId());
    }
}
