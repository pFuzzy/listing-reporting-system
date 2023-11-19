package hu.fazekas.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyListingReportDto {

    private Integer month;
    private Integer ebayListingCount;
    private Double ebayTotalListingPrice;
    private Double averageEbayListingPrice;
    private Integer amazonListingCount;
    private Double amazonTotalListingPrice;
    private Double averageAmazonListingPrice;
    private String bestListerEmailAddress;
}
