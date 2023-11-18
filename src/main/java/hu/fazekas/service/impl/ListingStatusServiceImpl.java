package hu.fazekas.service.impl;

import hu.fazekas.dao.ListingStatusDao;
import hu.fazekas.dto.ListingStatusDto;
import hu.fazekas.service.ListingStatusService;
import hu.fazekas.validator.ListingStatusValidator;

public class ListingStatusServiceImpl implements ListingStatusService {

    private final ListingStatusValidator listingStatusValidator;
    private final ListingStatusDao listingStatusDao;

    public ListingStatusServiceImpl(ListingStatusValidator listingStatusValidator, ListingStatusDao listingStatusDao){
        this.listingStatusValidator = listingStatusValidator;
        this.listingStatusDao = listingStatusDao;
    }

    @Override
    public void saveListingStatus(ListingStatusDto listingStatus) {
        if(listingStatusValidator.isValid(listingStatus)){
            listingStatusDao.saveListingStatus(listingStatus);
        }
    }
}
