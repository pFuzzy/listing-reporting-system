package hu.fazekas.dao;

import hu.fazekas.dto.ListingDto;
import hu.fazekas.dto.ListingReportDto;
import hu.fazekas.dto.MonthlyListingReportDto;

import java.util.List;
import java.util.UUID;

public interface ListingDao {

    void saveListing(ListingDto listing);

    Boolean existsById(UUID id);

    ListingReportDto getListingReport();

    List<MonthlyListingReportDto> getMonthlyListingReports();

    String getBestListerEmailByMonth(Integer month);
}
