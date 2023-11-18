package hu.fazekas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketplaceDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("marketplace_name")
    private String marketplaceName;
}
