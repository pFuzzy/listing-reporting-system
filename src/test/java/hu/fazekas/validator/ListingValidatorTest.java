package hu.fazekas.validator;

import hu.fazekas.dao.ListingDao;
import hu.fazekas.dao.ListingStatusDao;
import hu.fazekas.dao.LocationDao;
import hu.fazekas.dao.MarketplaceDao;
import hu.fazekas.dao.impl.ListingDaoImpl;
import hu.fazekas.dao.impl.ListingStatusDaoImpl;
import hu.fazekas.dao.impl.LocationDaoImpl;
import hu.fazekas.dao.impl.MarketplaceDaoImpl;
import hu.fazekas.dto.ListingDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ListingValidatorTest {

    private final ListingStatusDao listingStatusDao = mock(ListingStatusDaoImpl.class);
    private final MarketplaceDao marketplaceDao = mock(MarketplaceDaoImpl.class);
    private final LocationDao locationDao = mock(LocationDaoImpl.class);
    private final ListingDao listingDao = mock(ListingDaoImpl.class);
    private ListingValidator listingValidator = new ListingValidator(listingStatusDao, marketplaceDao, locationDao, listingDao);
    private ListingDto listing;

    @BeforeEach
    void init(){
        listing = new ListingDto();
        listing.setId(UUID.randomUUID());
        listing.setTitle("title");
        listing.setDescription("description");
        listing.setLocationId(UUID.randomUUID());
        listing.setListingPrice(100);
        listing.setCurrency("HUF");
        listing.setQuantity(2);
        listing.setListingStatusId(1L);
        listing.setMarketPlaceId(1L);
        listing.setOwnerEmailAddress("test@test");

    }
    @Test
    void validateListing_marketplaceIdInvalid_throwError() {
        listing.setMarketPlaceId(null);

        Exception nullIdException = assertThrows(IllegalArgumentException.class,() -> listingValidator.validateListing(listing));
        assertEquals(nullIdException.getMessage(), listing.getId() + "," + "No marketplace found," + "marketplaceId");

        listing.setMarketPlaceId(1L);
        when(marketplaceDao.existsById(1L)).thenReturn(false);

        Exception nonExistentIdException = assertThrows(IllegalArgumentException.class,() -> listingValidator.validateListing(listing));
        assertEquals(nonExistentIdException.getMessage(), listing.getId() + "," + "No marketplace found," + "marketplaceId");

        verify(marketplaceDao).existsById(listing.getMarketPlaceId());




    }

    @Test
    void validateListing_listingIdInvalid_throwsError() {
        listing.setId(null);

        when(marketplaceDao.existsById(listing.getMarketPlaceId())).thenReturn(true);
        when(marketplaceDao.getMarketplaceNameById(listing.getMarketPlaceId())).thenReturn("Amazon");

        Exception idNullException = assertThrows(IllegalArgumentException.class,() -> listingValidator.validateListing(listing));
        assertEquals(idNullException.getMessage(), listing.getId() + "," + "Amazon," + "id");

        listing.setId(UUID.randomUUID());
        when(listingDao.existsById(listing.getId())).thenReturn(true);

        Exception nonExistentIdException = assertThrows(IllegalArgumentException.class,() -> listingValidator.validateListing(listing));
        assertEquals(nonExistentIdException.getMessage(), listing.getId() + "," + "Amazon," + "id");

        verify(marketplaceDao, times(2)).existsById(listing.getMarketPlaceId());
        verify(marketplaceDao, times(2)).getMarketplaceNameById(listing.getMarketPlaceId());
        verify(listingDao).existsById(listing.getId());

    }

    @Test
    void validateListing_titleInvalid_throwsError(){
        listing.setTitle(null);

        when(marketplaceDao.existsById(listing.getMarketPlaceId())).thenReturn(true);
        when(marketplaceDao.getMarketplaceNameById(listing.getMarketPlaceId())).thenReturn("Amazon");

        Exception invalidTitleException = assertThrows(IllegalArgumentException.class,() -> listingValidator.validateListing(listing));
        assertEquals(invalidTitleException.getMessage(), listing.getId() + "," + "Amazon," + "title");

        verify(marketplaceDao).existsById(listing.getMarketPlaceId());
        verify(marketplaceDao).getMarketplaceNameById(listing.getMarketPlaceId());
    }

    @Test
    void validateListing_descriptionInvalid_throwsError(){
        listing.setDescription(null);

        when(marketplaceDao.existsById(listing.getMarketPlaceId())).thenReturn(true);
        when(marketplaceDao.getMarketplaceNameById(listing.getMarketPlaceId())).thenReturn("Amazon");

        Exception invalidDescriptionException = assertThrows(IllegalArgumentException.class,() -> listingValidator.validateListing(listing));
        assertEquals(invalidDescriptionException.getMessage(), listing.getId() + "," + "Amazon," + "description");

        verify(marketplaceDao).existsById(listing.getMarketPlaceId());
        verify(marketplaceDao).getMarketplaceNameById(listing.getMarketPlaceId());
    }

    @Test
    void validateListing_locationInvalid_throwsError(){
        listing.setLocationId(null);

        when(marketplaceDao.existsById(listing.getMarketPlaceId())).thenReturn(true);
        when(marketplaceDao.getMarketplaceNameById(listing.getMarketPlaceId())).thenReturn("Amazon");

        Exception locationIdNullException = assertThrows(IllegalArgumentException.class,() -> listingValidator.validateListing(listing));
        assertEquals(locationIdNullException.getMessage(), listing.getId() + "," + "Amazon," + "locationId");

        listing.setLocationId(UUID.randomUUID());

        Exception nonExistentLocationIdException = assertThrows(IllegalArgumentException.class,() -> listingValidator.validateListing(listing));
        assertEquals(nonExistentLocationIdException.getMessage(), listing.getId() + "," + "Amazon," + "locationId");

        verify(marketplaceDao, times(2)).existsById(listing.getMarketPlaceId());
        verify(marketplaceDao, times(2)).getMarketplaceNameById(listing.getMarketPlaceId());
        verify(locationDao).existsById(listing.getLocationId());
    }

    @Test
    void validateListing_listingPriceInvalid_throwsError(){
        listing.setListingPrice(null);

        when(marketplaceDao.existsById(listing.getMarketPlaceId())).thenReturn(true);
        when(marketplaceDao.getMarketplaceNameById(listing.getMarketPlaceId())).thenReturn("Amazon");
        when(locationDao.existsById(listing.getLocationId())).thenReturn(true);

        Exception listingPriceNullException = assertThrows(IllegalArgumentException.class,() -> listingValidator.validateListing(listing));
        assertEquals(listingPriceNullException.getMessage(), listing.getId() + "," + "Amazon," + "listingPrice");

        listing.setListingPrice(0);

        Exception listingPriceIsNotPositiveException = assertThrows(IllegalArgumentException.class,() -> listingValidator.validateListing(listing));
        assertEquals(listingPriceIsNotPositiveException.getMessage(), listing.getId() + "," + "Amazon," + "listingPrice");

        verify(marketplaceDao, times(2)).existsById(listing.getMarketPlaceId());
        verify(marketplaceDao, times(2)).getMarketplaceNameById(listing.getMarketPlaceId());
        verify(locationDao, times(2)).existsById(listing.getLocationId());

    }

    @Test
    void validateListing_currencyInvalid_throwsError(){
        listing.setCurrency(null);

        when(marketplaceDao.existsById(listing.getMarketPlaceId())).thenReturn(true);
        when(marketplaceDao.getMarketplaceNameById(listing.getMarketPlaceId())).thenReturn("Amazon");
        when(locationDao.existsById(listing.getLocationId())).thenReturn(true);

        Exception currencyNullException = assertThrows(IllegalArgumentException.class,() -> listingValidator.validateListing(listing));
        assertEquals(currencyNullException.getMessage(), listing.getId() + "," + "Amazon," + "currency");

        listing.setCurrency("HUFF");

        Exception currencyLengthException = assertThrows(IllegalArgumentException.class,() -> listingValidator.validateListing(listing));
        assertEquals(currencyLengthException.getMessage(), listing.getId() + "," + "Amazon," + "currency");

        verify(marketplaceDao, times(2)).existsById(listing.getMarketPlaceId());
        verify(marketplaceDao, times(2)).getMarketplaceNameById(listing.getMarketPlaceId());
        verify(locationDao, times(2)).existsById(listing.getLocationId());
    }

    @Test
    void validateListing_quantityInvalid_throwsError(){
        listing.setQuantity(null);

        when(marketplaceDao.existsById(listing.getMarketPlaceId())).thenReturn(true);
        when(marketplaceDao.getMarketplaceNameById(listing.getMarketPlaceId())).thenReturn("Amazon");
        when(locationDao.existsById(listing.getLocationId())).thenReturn(true);

        Exception quantityNullException = assertThrows(IllegalArgumentException.class,() -> listingValidator.validateListing(listing));
        assertEquals(quantityNullException.getMessage(), listing.getId() + "," + "Amazon," + "quantity");

        listing.setQuantity(0);

        Exception quantityIsNotPositiveException = assertThrows(IllegalArgumentException.class,() -> listingValidator.validateListing(listing));
        assertEquals(quantityIsNotPositiveException.getMessage(), listing.getId() + "," + "Amazon," + "quantity");

        verify(marketplaceDao, times(2)).existsById(listing.getMarketPlaceId());
        verify(marketplaceDao, times(2)).getMarketplaceNameById(listing.getMarketPlaceId());
        verify(locationDao, times(2)).existsById(listing.getLocationId());
    }

    @Test
    void validateListing_listingStatusInvalid_throwsError(){
        listing.setListingStatusId(null);

        when(marketplaceDao.existsById(listing.getMarketPlaceId())).thenReturn(true);
        when(marketplaceDao.getMarketplaceNameById(listing.getMarketPlaceId())).thenReturn("Amazon");
        when(locationDao.existsById(listing.getLocationId())).thenReturn(true);

        Exception listingStatusNullException = assertThrows(IllegalArgumentException.class,() -> listingValidator.validateListing(listing));
        assertEquals(listingStatusNullException.getMessage(), listing.getId() + "," + "Amazon," + "listingStatusId");

        listing.setListingStatusId(1L);
        when(listingStatusDao.existsById(listing.getListingStatusId())).thenReturn(false);

        Exception nonExistentListingStatus = assertThrows(IllegalArgumentException.class,() -> listingValidator.validateListing(listing));
        assertEquals(nonExistentListingStatus.getMessage(), listing.getId() + "," + "Amazon," + "listingStatusId");

        verify(marketplaceDao, times(2)).existsById(listing.getMarketPlaceId());
        verify(marketplaceDao, times(2)).getMarketplaceNameById(listing.getMarketPlaceId());
        verify(locationDao, times(2)).existsById(listing.getLocationId());
        verify(listingStatusDao).existsById(listing.getListingStatusId());
    }

    @Test
    void validateListing_emailInvalid_throwsError(){
        listing.setOwnerEmailAddress(null);

        when(marketplaceDao.existsById(listing.getMarketPlaceId())).thenReturn(true);
        when(marketplaceDao.getMarketplaceNameById(listing.getMarketPlaceId())).thenReturn("Amazon");
        when(locationDao.existsById(listing.getLocationId())).thenReturn(true);
        when(listingStatusDao.existsById(listing.getListingStatusId())).thenReturn(true);

        Exception emailNullException = assertThrows(IllegalArgumentException.class,() -> listingValidator.validateListing(listing));
        assertEquals(emailNullException.getMessage(), listing.getId() + "," + "Amazon," + "email");

        listing.setOwnerEmailAddress("invalidEmail");

        Exception invalidEmailException = assertThrows(IllegalArgumentException.class,() -> listingValidator.validateListing(listing));
        assertEquals(invalidEmailException.getMessage(), listing.getId() + "," + "Amazon," + "email");

        verify(marketplaceDao, times(2)).existsById(listing.getMarketPlaceId());
        verify(marketplaceDao, times(2)).getMarketplaceNameById(listing.getMarketPlaceId());
        verify(locationDao, times(2)).existsById(listing.getLocationId());
        verify(listingStatusDao, times(2)).existsById(listing.getListingStatusId());
    }

    @Test
    void validateListing_success(){
        when(marketplaceDao.existsById(listing.getMarketPlaceId())).thenReturn(true);
        when(locationDao.existsById(listing.getLocationId())).thenReturn(true);
        when(listingStatusDao.existsById(listing.getListingStatusId())).thenReturn(true);

        assertDoesNotThrow(() -> listingValidator.validateListing(listing));

        verify(marketplaceDao, times(1)).existsById(listing.getMarketPlaceId());
        verify(locationDao, times(1)).existsById(listing.getLocationId());
        verify(listingStatusDao, times(1)).existsById(listing.getListingStatusId());
    }
}