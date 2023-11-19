package hu.fazekas.validator;

import hu.fazekas.dao.MarketplaceDao;
import hu.fazekas.dao.impl.MarketplaceDaoImpl;
import hu.fazekas.dto.MarketplaceDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MarketplaceValidatorTest {

    private MarketplaceDto marketplace;
    private final MarketplaceDao marketplaceDao = mock(MarketplaceDaoImpl.class);
    private final MarketplaceValidator marketplaceValidator = new MarketplaceValidator(marketplaceDao);

    @BeforeEach
    void init(){
        marketplace = new MarketplaceDto();
    }

    @Test
    void isValid_marketplaceNull_returnFalse(){
        marketplace.setId(null);

        assertFalse(marketplaceValidator.isValid(marketplace));
    }

    @Test
    void isValid_nonExistentMarketplace_returnFalse(){
        marketplace.setId(1L);

        when(marketplaceDao.existsById(marketplace.getId())).thenReturn(true);

        assertFalse(marketplaceValidator.isValid(marketplace));

        verify(marketplaceDao).existsById(marketplace.getId());
    }

    @Test
    void isValid_success(){
        marketplace.setId(1L);

        when(marketplaceDao.existsById(marketplace.getId())).thenReturn(false);

        assertTrue(marketplaceValidator.isValid(marketplace));

        verify(marketplaceDao).existsById(marketplace.getId());
    }

}