package hu.fazekas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListingDto {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;


    @JsonProperty("location_id")
    private UUID locationId;


    @JsonProperty("listing_price")
    private Integer listingPrice;


    @JsonProperty("currency")
    private String currency;


    @JsonProperty("quantity")
    private Integer quantity;


    @JsonProperty("listing_status")
    private Long listingStatusId;


    @JsonProperty("marketplace")
    private Long marketPlaceId;

    @JsonProperty("upload_time")
    @JsonFormat(pattern = "M/D/YY")
    private Date uploadTime;


    @JsonProperty("owner_email_address")
    private String ownerEmailAddress;

}
