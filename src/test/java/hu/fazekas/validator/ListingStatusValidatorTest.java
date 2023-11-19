package hu.fazekas.validator;

import hu.fazekas.dao.ListingStatusDao;
import hu.fazekas.dao.impl.ListingStatusDaoImpl;
import hu.fazekas.dto.ListingStatusDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListingStatusValidatorTest {

    private ListingStatusDto listingStatus;
    private final ListingStatusDao listingStatusDao = mock(ListingStatusDaoImpl.class);
    private final ListingStatusValidator listingStatusValidator = new ListingStatusValidator(listingStatusDao);

    @BeforeEach
    void init(){
        listingStatus = new ListingStatusDto();
    }

    @Test
    void isValid_listingStatusIdNull_returnFalse(){
        listingStatus.setId(null);

        assertFalse(listingStatusValidator.isValid(listingStatus));
    }

    @Test
    void isValid_nonExistentListingStatus_returnFalse(){
        listingStatus.setId(1L);

        when(listingStatusDao.existsById(listingStatus.getId())).thenReturn(true);

        assertFalse(listingStatusValidator.isValid(listingStatus));

        verify(listingStatusDao).existsById(listingStatus.getId());
    }

    @Test
    void isValid_success(){
        listingStatus.setId(1L);

        when(listingStatusDao.existsById(listingStatus.getId())).thenReturn(false);

        assertTrue(listingStatusValidator.isValid(listingStatus));

        verify(listingStatusDao).existsById(listingStatus.getId());
    }
}