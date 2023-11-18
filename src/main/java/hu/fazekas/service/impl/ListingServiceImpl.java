package hu.fazekas.service.impl;

import hu.fazekas.dao.ListingDao;
import hu.fazekas.dto.ListingDto;
import hu.fazekas.service.ListingService;
import hu.fazekas.validator.ListingValidator;

public class ListingServiceImpl implements ListingService {

    private final ListingValidator listingValidator;
    private final ListingDao listingDao;

    public ListingServiceImpl(ListingValidator listingValidator, ListingDao listingDao){
        this.listingValidator = listingValidator;
        this.listingDao = listingDao;
    }

    @Override
    public void saveListing(ListingDto listing) {
        if(listingValidator.isValid(listing)){
            listingDao.saveListing(listing);
        }
    }
}
