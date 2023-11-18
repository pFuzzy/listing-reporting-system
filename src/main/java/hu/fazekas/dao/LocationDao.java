package hu.fazekas.dao;

import hu.fazekas.dto.LocationDto;

import java.util.UUID;

public interface LocationDao {

    void saveLocation(LocationDto location);

    Boolean existsById(UUID id);
}
