package hu.fazekas.validator;

import hu.fazekas.dao.ListingDao;
import hu.fazekas.dao.ListingStatusDao;
import hu.fazekas.dao.LocationDao;
import hu.fazekas.dao.MarketplaceDao;
import hu.fazekas.dto.ListingDto;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListingValidator {

    private final ListingStatusDao listingStatusDao;
    private final MarketplaceDao marketplaceDao;
    private final LocationDao locationDao;
    private final ListingDao listingDao;

    public ListingValidator(ListingStatusDao listingStatusDao, MarketplaceDao marketplaceDao, LocationDao locationDao, ListingDao listingDao) {
        this.listingStatusDao = listingStatusDao;
        this.marketplaceDao = marketplaceDao;
        this.locationDao = locationDao;
        this.listingDao = listingDao;
    }

    public void validateListing(ListingDto listingDto)  throws IllegalArgumentException{
        validateMarketplace(listingDto);
        validateId(listingDto);
        validateTitle(listingDto);
        validateDescription(listingDto);
        validateLocation(listingDto);
        validateListingPrice(listingDto);
        validateCurrency(listingDto);
        validateQuantity(listingDto);
        validateListingStatus(listingDto);
        validateEmail(listingDto);
    }

    private void validateId(ListingDto listing ){
        if(listing.getId() == null || listingDao.existsById(listing.getId())){
            throw new IllegalArgumentException(createErrorMessage(listing, "id"));
        }
    }

    private void validateTitle(ListingDto listing){
        if (listing.getTitle() == null){
            throw new IllegalArgumentException(createErrorMessage(listing, "title"));
        }
    }

    private void validateDescription(ListingDto listing){
        if(listing.getDescription() == null){
            throw new IllegalArgumentException(createErrorMessage(listing, "description"));
        }
    }

    private void validateLocation(ListingDto listing){
        UUID locationId = listing.getLocationId();
        if(locationId == null || !locationDao.existsById(locationId)){
            throw new IllegalArgumentException(createErrorMessage(listing, "locationId"));
        }
    }

    private void validateListingPrice(ListingDto listing){
        Integer listingPrice = listing.getListingPrice();
        if(listingPrice == null || listingPrice <= 0){
            throw new IllegalArgumentException(createErrorMessage(listing, "listingPrice"));
        }
    }

    private void validateCurrency(ListingDto listing){
        String currency = listing.getCurrency();
        if(currency == null || currency.length() != 3){
            throw new IllegalArgumentException(createErrorMessage(listing, "currency"));
        }
    }

    private void validateQuantity(ListingDto listing){
        Integer quantity = listing.getQuantity();
        if(quantity == null || quantity <= 0){
            throw new IllegalArgumentException(createErrorMessage(listing, "quantity"));
        }
    }

    private void validateListingStatus(ListingDto listing){
        Long listingStatusId = listing.getListingStatusId();
        if(listingStatusId == null || !listingStatusDao.existsById(listingStatusId)){
            throw new IllegalArgumentException(createErrorMessage(listing, "listingStatusId"));
        }
    }

    private void validateMarketplace(ListingDto listing){
        Long marketplaceId = listing.getMarketPlaceId();
        if(marketplaceId == null || !marketplaceDao.existsById(marketplaceId)){
            throw new IllegalArgumentException(createErrorMessage(listing,"marketplaceId"));
        }
    }

    private void validateEmail(ListingDto listing){
        String email = listing.getOwnerEmailAddress();
        if(email == null){
            throw new IllegalArgumentException(createErrorMessage(listing, "email"));
        }
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if(!matcher.matches()){
            throw new IllegalArgumentException(createErrorMessage(listing, "email"));
        }
    }

    private String createErrorMessage(ListingDto listingDto, String invalidField){
        if(invalidField.equals("marketplaceId")){
            return listingDto.getId() + "," +
                    "No marketplace found, " +
                    invalidField;
        }
        return listingDto.getId() + "," +
                marketplaceDao.getMarketplaceNameById(listingDto.getMarketPlaceId()) + "," +
                invalidField;
    }

}
