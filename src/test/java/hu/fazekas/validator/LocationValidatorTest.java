package hu.fazekas.validator;

import hu.fazekas.dao.LocationDao;
import hu.fazekas.dao.impl.LocationDaoImpl;
import hu.fazekas.dto.LocationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LocationValidatorTest {

    private LocationDto location;
    private final LocationDao locationDao = mock(LocationDaoImpl.class);
    private final LocationValidator locationValidator = new LocationValidator(locationDao);

    @BeforeEach
    void init(){
        location = new LocationDto();
    }

    @Test
    void isValid_locationIdNull_returnFalse(){
        location.setId(null);

        assertFalse(locationValidator.isValid(location));
    }

    @Test
    void isValid_nonExistentLocationId_returnFalse(){
        location.setId(UUID.randomUUID());
        when(locationDao.existsById(location.getId())).thenReturn(true);

        assertFalse(locationValidator.isValid(location));

        verify(locationDao).existsById(location.getId());
    }

    @Test
    void isValid_success(){
        location.setId(UUID.randomUUID());

        when(locationDao.existsById(location.getId())).thenReturn(false);

        assertTrue(locationValidator.isValid(location));

        verify(locationDao).existsById(location.getId());
    }
}