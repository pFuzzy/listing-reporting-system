package hu.fazekas.service;

import hu.fazekas.dto.ListingDto;
import hu.fazekas.dto.ListingReportDto;
import hu.fazekas.dto.MonthlyListingReportDto;

import java.util.List;

public interface ListingService {

    void saveListing(ListingDto listing);

    ListingReportDto getListingReport();

    List<MonthlyListingReportDto> getMonthlyListingReports();

    List<Integer> getMonthsWithoutListing(List<MonthlyListingReportDto> monthlyListingReports);
}
