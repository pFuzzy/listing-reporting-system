package hu.fazekas.dao;

import hu.fazekas.dto.MarketplaceDto;

public interface MarketplaceDao {

    void saveMarketPlace(MarketplaceDto marketplace);

    Boolean existsById(Long id);
}
