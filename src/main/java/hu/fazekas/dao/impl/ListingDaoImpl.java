package hu.fazekas.dao.impl;

import hu.fazekas.dao.AbstractDao;
import hu.fazekas.dao.ListingDao;
import hu.fazekas.dto.ListingDto;

import java.sql.*;
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
}
