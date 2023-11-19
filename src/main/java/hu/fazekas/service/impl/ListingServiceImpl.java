package hu.fazekas.service.impl;

import hu.fazekas.dao.ListingDao;
import hu.fazekas.dto.ListingDto;
import hu.fazekas.dto.ListingReportDto;
import hu.fazekas.dto.MonthlyListingReportDto;
import hu.fazekas.service.ListingService;
import hu.fazekas.util.CsvWriter;
import hu.fazekas.validator.ListingValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Override
    public ListingReportDto getListingReport() {
        return listingDao.getListingReport();
    }

    @Override
    public List<MonthlyListingReportDto> getMonthlyListingReports() {
        List<MonthlyListingReportDto> monthlyListingReports = listingDao.getMonthlyListingReports();

        for (MonthlyListingReportDto monthlyListingReport :
                monthlyListingReports) {
            monthlyListingReport.setBestListerEmailAddress(listingDao.getBestListerEmailByMonth(monthlyListingReport.getMonth()));
        }
        return monthlyListingReports;
    }

    @Override
    public List<Integer> getMonthsWithoutListing(List<MonthlyListingReportDto> monthlyListingReports) {
        List<Integer> monthsWithoutListing = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        for (MonthlyListingReportDto monthlyListingReport :
                monthlyListingReports) {
            monthsWithoutListing.remove(monthlyListingReport.getMonth());
        }
        return monthsWithoutListing;
    }


}
