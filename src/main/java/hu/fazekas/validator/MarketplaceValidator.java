package hu.fazekas.validator;

import hu.fazekas.dao.MarketplaceDao;
import hu.fazekas.dto.MarketplaceDto;

public class MarketplaceValidator {

    private final MarketplaceDao  marketplaceDao;

    public MarketplaceValidator(MarketplaceDao marketplaceDao) {
        this.marketplaceDao = marketplaceDao;
    }

    public Boolean isValid(MarketplaceDto marketplace){
        return marketplace.getId() != null && !marketplaceDao.existsById(marketplace.getId());
    }
}
