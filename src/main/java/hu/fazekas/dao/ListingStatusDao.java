package hu.fazekas.dao;

import hu.fazekas.dto.ListingStatusDto;

public interface ListingStatusDao {
    void saveListingStatus(ListingStatusDto listingStatus);

    Boolean existsById(Long id);
}
