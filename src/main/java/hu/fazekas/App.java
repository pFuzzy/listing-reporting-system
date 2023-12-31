package hu.fazekas;


import hu.fazekas.dao.ListingDao;
import hu.fazekas.dao.ListingStatusDao;
import hu.fazekas.dao.LocationDao;
import hu.fazekas.dao.MarketplaceDao;
import hu.fazekas.dao.impl.ListingDaoImpl;
import hu.fazekas.dao.impl.ListingStatusDaoImpl;
import hu.fazekas.dao.impl.LocationDaoImpl;
import hu.fazekas.dao.impl.MarketplaceDaoImpl;
import hu.fazekas.dto.*;
import hu.fazekas.service.ListingService;
import hu.fazekas.service.ListingStatusService;
import hu.fazekas.service.LocationService;
import hu.fazekas.service.MarketplaceService;
import hu.fazekas.service.impl.ListingServiceImpl;
import hu.fazekas.service.impl.ListingStatusServiceImpl;
import hu.fazekas.service.impl.LocationServiceImpl;
import hu.fazekas.service.impl.MarketplaceServiceImpl;
import hu.fazekas.util.CsvWriter;
import hu.fazekas.util.JsonHandler;
import hu.fazekas.util.RequestHandler;
import hu.fazekas.validator.ListingStatusValidator;
import hu.fazekas.validator.ListingValidator;
import hu.fazekas.validator.LocationValidator;
import hu.fazekas.validator.MarketplaceValidator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class App 
{
    public static void main( String[] args ) throws URISyntaxException, IOException, InterruptedException {
        LocationDao locationDao = new LocationDaoImpl();
        ListingStatusDao listingStatusDao = new ListingStatusDaoImpl();
        MarketplaceDao marketplaceDao = new MarketplaceDaoImpl();
        ListingDao listingDao = new ListingDaoImpl();
        CsvWriter csvWriter = new CsvWriter();
        JsonHandler jsonHandler = new JsonHandler();

        LocationService locationService = initLocationService(locationDao);
        ListingStatusService listingStatusService = initListingStatusService(listingStatusDao);
        MarketplaceService marketplaceService = initMarketplaceService(marketplaceDao);
        ListingService listingService = initListingService(locationDao, listingStatusDao, marketplaceDao, listingDao, csvWriter);

        RequestHandler requestHandler = new RequestHandler();

        LocationDto[] locations = requestHandler.getLocations();

        for (LocationDto location : locations) {
            locationService.saveLocation(location);
        }

        ListingStatusDto[] listingStatuses = requestHandler.getListingStatuses();

        for (ListingStatusDto listingStatus:listingStatuses) {
            listingStatusService.saveListingStatus(listingStatus);
        }

        MarketplaceDto[] marketplaces = requestHandler.getMarketplaces();

        for (MarketplaceDto marketplace : marketplaces) {
            marketplaceService.saveMarketPlace(marketplace);
        }

        ListingDto[] listings = requestHandler.getListings();

        for (ListingDto listing : listings) {
            listingService.saveListing(listing);
        }

        ListingReportDto listingReport = listingService.getListingReport();
        List<MonthlyListingReportDto> monthlyListingReports = listingService.getMonthlyListingReports();
        List<Integer> monthsWithoutListing = listingService.getMonthsWithoutListing(monthlyListingReports);

        jsonHandler.createJsonReport(listingReport, monthlyListingReports, monthsWithoutListing);




    }

    private static LocationService initLocationService(LocationDao locationDao){
        LocationValidator locationValidator = new LocationValidator(locationDao);
        return new LocationServiceImpl(locationValidator, locationDao);
    }

    private static ListingStatusService initListingStatusService(ListingStatusDao listingStatusDao) {
        ListingStatusValidator listingStatusValidator = new ListingStatusValidator(listingStatusDao);
        return new ListingStatusServiceImpl(listingStatusValidator, listingStatusDao);
    }

    private static MarketplaceService initMarketplaceService(MarketplaceDao marketplaceDao) {
        MarketplaceValidator marketplaceValidator = new MarketplaceValidator(marketplaceDao);
        return new MarketplaceServiceImpl(marketplaceValidator, marketplaceDao);
    }

    private static ListingService initListingService(LocationDao locationDao, ListingStatusDao listingStatusDao,
                                                     MarketplaceDao marketplaceDao,ListingDao listingDao, CsvWriter csvWriter) {
        ListingValidator listingValidator = new ListingValidator(listingStatusDao, marketplaceDao, locationDao, listingDao);
        return new ListingServiceImpl(listingValidator, listingDao , csvWriter);
    }

}
