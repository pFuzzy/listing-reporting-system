package hu.fazekas.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListingReportDto {

    private Integer totalCount;
    private Integer ebayListingCount;
    private Double ebayTotalListingPrice;
    private Double averageEbayListingPrice;
    private Integer amazonListingCount;
    private Double amazonTotalListingPrice;
    private Double averageAmazonListingPrice;
    private String bestListerEmailAddress;

}
