package hu.fazekas.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.fazekas.dto.ListingDto;
import hu.fazekas.dto.ListingStatusDto;
import hu.fazekas.dto.LocationDto;
import hu.fazekas.dto.MarketplaceDto;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;

public class RequestHandler {

    public ListingDto[] getListings() throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://my.api.mockaroo.com/listing?key=63304c70"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("d/M/yyyy"));

        return objectMapper.readValue(response.body(), ListingDto[].class);
    }

    public LocationDto[] getLocations() throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://my.api.mockaroo.com/location?key=63304c70"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(response.body(), LocationDto[].class);
    }

    public ListingStatusDto[] getListingStatuses() throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://my.api.mockaroo.com/listingStatus?key=63304c70"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(response.body(), ListingStatusDto[].class);
    }

    public MarketplaceDto[] getMarketplaces() throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://my.api.mockaroo.com/marketplace?key=63304c70"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(response.body(), MarketplaceDto[].class);
    }
}
