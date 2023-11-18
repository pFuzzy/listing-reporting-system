package hu.fazekas.dao;

import hu.fazekas.dto.ListingDto;

import java.util.UUID;

public interface ListingDao {

    void saveListing(ListingDto listing);

    Boolean existsById(UUID id);
}
