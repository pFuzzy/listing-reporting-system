package hu.fazekas.validator;

import hu.fazekas.dao.ListingDao;
import hu.fazekas.dao.ListingStatusDao;
import hu.fazekas.dao.LocationDao;
import hu.fazekas.dao.MarketplaceDao;
import hu.fazekas.dto.ListingDto;

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

    public Boolean isValid(ListingDto listingDto)  {
        return listingDto.getId() != null && !listingDao.existsById(listingDto.getId()) &&
                listingDto.getTitle() != null &&
                listingDto.getDescription() != null &&
                listingDto.getLocationId() != null && locationDao.existsById(listingDto.getLocationId()) &&
                listingDto.getListingPrice() != null && listingDto.getListingPrice() > 0 &&
                listingDto.getCurrency() != null && listingDto.getCurrency().length() == 3 &&
                listingDto.getQuantity() != null && listingDto.getQuantity() > 0 &&
                listingDto.getListingStatusId() != null && listingStatusDao.existsById(listingDto.getListingStatusId()) &&
                listingDto.getMarketPlaceId() != null && marketplaceDao.existsById(listingDto.getMarketPlaceId()) &&
                listingDto.getOwnerEmailAddress() != null && isValidEmail(listingDto.getOwnerEmailAddress());
    }

    private Boolean isValidEmail(String email) {
        String regex = "^(.+)@(.+)$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}
