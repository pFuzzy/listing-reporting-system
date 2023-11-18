package hu.fazekas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("manager_name")
    private String managerName;

    @JsonProperty("phone")
    private String phoneNumber;

    @JsonProperty("address_primary")
    private String primaryAddress;

    @JsonProperty("address_secondary")
    private String secondaryAddress;

    @JsonProperty("country")
    private String country;

    @JsonProperty("town")
    private String town;

    @JsonProperty("postal_code")
    private String postalCode;
}
