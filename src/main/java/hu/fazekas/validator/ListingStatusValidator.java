package hu.fazekas.validator;

import hu.fazekas.dao.ListingStatusDao;
import hu.fazekas.dto.ListingStatusDto;

public class ListingStatusValidator {

    private final ListingStatusDao listingStatusDao;

    public ListingStatusValidator(ListingStatusDao listingStatusDao){
        this.listingStatusDao = listingStatusDao;
    }

    public Boolean isValid(ListingStatusDto listingStatusDto){
        return listingStatusDto.getId() != null && !listingStatusDao.existsById(listingStatusDto.getId());
    }
}
