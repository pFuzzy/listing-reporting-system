package hu.fazekas.service.impl;

import hu.fazekas.dao.MarketplaceDao;
import hu.fazekas.dto.MarketplaceDto;
import hu.fazekas.service.MarketplaceService;
import hu.fazekas.validator.MarketplaceValidator;

public class MarketplaceServiceImpl implements MarketplaceService {

    private final MarketplaceValidator marketplaceValidator;
    private final MarketplaceDao marketplaceDao;

    public MarketplaceServiceImpl(MarketplaceValidator marketplaceValidator, MarketplaceDao marketplaceDao){
        this.marketplaceValidator = marketplaceValidator;
        this.marketplaceDao = marketplaceDao;
    }

    @Override
    public void saveMarketPlace(MarketplaceDto marketplaceDto) {
        if(marketplaceValidator.isValid(marketplaceDto)){
            marketplaceDao.saveMarketPlace(marketplaceDto);
        }
    }
}
