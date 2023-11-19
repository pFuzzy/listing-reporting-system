package hu.fazekas.service.impl;

import hu.fazekas.dao.ListingDao;
import hu.fazekas.dto.ListingDto;
import hu.fazekas.service.ListingService;
import hu.fazekas.util.CsvWriter;
import hu.fazekas.validator.ListingValidator;

public class ListingServiceImpl implements ListingService {

    private final ListingValidator listingValidator;
    private final ListingDao listingDao;
    private final CsvWriter csvWriter;

    public ListingServiceImpl(ListingValidator listingValidator, ListingDao listingDao, CsvWriter csvWriter){
        this.listingValidator = listingValidator;
        this.listingDao = listingDao;
        this.csvWriter = csvWriter;
    }

    @Override
    public void saveListing(ListingDto listing) {
        try{
            listingValidator.validateListing(listing);
            listingDao.saveListing(listing);
        }
        catch (IllegalArgumentException ex){
            csvWriter.writeToCsv(ex.getMessage());
        }

    }


}
