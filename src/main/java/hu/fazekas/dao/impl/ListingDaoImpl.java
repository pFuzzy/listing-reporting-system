package hu.fazekas.dao.impl;

import hu.fazekas.dao.AbstractDao;
import hu.fazekas.dao.ListingDao;
import hu.fazekas.dto.ListingDto;
import hu.fazekas.dto.ListingReportDto;
import hu.fazekas.dto.MonthlyListingReportDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListingDaoImpl extends AbstractDao implements ListingDao {

    @Override
    public void saveListing(ListingDto listing) {
        try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO listing(id, title, description, location_id, listing_price, currency, quantity, listing_status, marketplace," +
                            " upload_time, owner_email_address  )" +
                            "VALUES (?,?,?,?,?,?,?,?,?,?,?)")) {

            statement.setString(1, String.valueOf(listing.getId()));
            statement.setString(2, listing.getTitle());
            statement.setString(3, listing.getDescription());
            statement.setString(4, String.valueOf(listing.getLocationId()));
            statement.setInt(5,listing.getListingPrice());
            statement.setString(6, listing.getCurrency());
            statement.setInt(7, listing.getQuantity());
            statement.setLong(8, listing.getListingStatusId());
            statement.setLong(9, listing.getMarketPlaceId());
            statement.setDate(10, listing.getUploadTime() != null ? new Date(listing.getUploadTime().getTime()): null);
            statement.setString(11, listing.getOwnerEmailAddress());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean existsById(UUID id) {
        try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement statement = connection.prepareStatement("SELECT id FROM listing WHERE id = ?")) {

            statement.setString(1, id.toString());

            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ListingReportDto getListingReport() {
        try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT COUNT(listing.id) as total_count,\n" +
                            "    COUNT(CASE WHEN listing.marketplace = 1 THEN listing.id END) as ebay_listing_count,\n" +
                            "    ROUND(SUM(CASE WHEN listing.marketplace = 1 THEN listing.listing_price END), 2) as ebay_total_listing_price,\n" +
                            "    ROUND(AVG(CASE WHEN listing.marketplace = 1 THEN listing.listing_price END), 2) as average_ebay_listing_price,\n" +
                            "    COUNT(CASE WHEN listing.marketplace = 2 THEN listing.id END) as amazon_listing_count,\n" +
                            "    ROUND(SUM(CASE WHEN listing.marketplace = 2 THEN listing.listing_price END), 2) as amazon_total_listing_price,\n" +
                            "    ROUND(AVG(CASE WHEN listing.marketplace = 2 THEN listing.listing_price END), 2) as average_amazon_listing_price,\n" +
                            "    (SELECT listing.owner_email_address\n" +
                            "    FROM listing\n" +
                            "    GROUP BY listing.owner_email_address\n" +
                            "    ORDER BY COUNT(*) DESC\n" +
                            "    LIMIT 1) as best_lister_email_address\n" +
                            "FROM listing")) {

            ResultSet resultSet = statement.executeQuery();

            ListingReportDto listingReportDto = new ListingReportDto();

            while(resultSet.next()){
                listingReportDto.setTotalCount(resultSet.getInt("total_count"));
                listingReportDto.setEbayListingCount(resultSet.getInt("ebay_listing_count"));
                listingReportDto.setEbayTotalListingPrice(resultSet.getDouble("ebay_total_listing_price"));
                listingReportDto.setAverageEbayListingPrice(resultSet.getDouble("average_ebay_listing_price"));
                listingReportDto.setAmazonListingCount(resultSet.getInt("amazon_listing_count"));
                listingReportDto.setAmazonTotalListingPrice(resultSet.getDouble("amazon_total_listing_price"));
                listingReportDto.setAverageAmazonListingPrice(resultSet.getDouble("average_amazon_listing_price"));
                listingReportDto.setBestListerEmailAddress(resultSet.getString("best_lister_email_address"));
            }

            return listingReportDto;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MonthlyListingReportDto> getMonthlyListingReports() {
        try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT EXTRACT(MONTH FROM listing.upload_time) as month,\n" +
                            "       COUNT(CASE WHEN listing.marketplace = 1 THEN listing.id END) as ebay_listing_count,\n" +
                            "       ROUND(SUM(CASE WHEN listing.marketplace = 1 THEN listing.listing_price END), 2) as ebay_total_listing_price,\n" +
                            "       ROUND(AVG(CASE WHEN listing.marketplace = 1 THEN listing.listing_price END), 2) as average_ebay_listing_price,\n" +
                            "       COUNT(CASE WHEN listing.marketplace = 2 THEN listing.id END) as amazon_listing_count,\n" +
                            "       ROUND(SUM(CASE WHEN listing.marketplace = 2 THEN listing.listing_price END), 2) as amazon_total_listing_price,\n" +
                            "       ROUND(AVG(CASE WHEN listing.marketplace = 2 THEN listing.listing_price END), 2) as average_amazon_listing_price\n" +
                            "FROM listing\n" +
                            "WHERE listing.upload_time IS NOT NULL\n" +
                            "GROUP BY EXTRACT(MONTH FROM listing.upload_time)")) {

            ResultSet resultSet = statement.executeQuery();

            List<MonthlyListingReportDto> listingReports = new ArrayList<>();

            while(resultSet.next()){
                MonthlyListingReportDto monthlyListingReportDto = new MonthlyListingReportDto();

                monthlyListingReportDto.setMonth(resultSet.getInt("month"));
                monthlyListingReportDto.setEbayListingCount(resultSet.getInt("ebay_listing_count"));
                monthlyListingReportDto.setEbayTotalListingPrice(resultSet.getDouble("ebay_total_listing_price"));
                monthlyListingReportDto.setAverageEbayListingPrice(resultSet.getDouble("average_ebay_listing_price"));
                monthlyListingReportDto.setAmazonListingCount(resultSet.getInt("amazon_listing_count"));
                monthlyListingReportDto.setAmazonTotalListingPrice(resultSet.getDouble("amazon_total_listing_price"));
                monthlyListingReportDto.setAverageAmazonListingPrice(resultSet.getDouble("average_amazon_listing_price"));

                listingReports.add(monthlyListingReportDto);

            }

            return listingReports;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBestListerEmailByMonth(Integer month) {
        try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT listing.owner_email_address as email\n" +
                            "FROM listing\n" +
                            "WHERE EXTRACT(MONTH FROM listing.upload_time) = ?\n" +
                            "GROUP BY listing.owner_email_address\n" +
                            "ORDER BY count(*) DESC\n" +
                            "LIMIT 1")) {

            statement.setInt(1, month);

            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            return resultSet.getString("email");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
