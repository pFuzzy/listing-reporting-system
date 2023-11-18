package hu.fazekas.dao.impl;

import hu.fazekas.dao.AbstractDao;
import hu.fazekas.dao.ListingStatusDao;
import hu.fazekas.dto.ListingStatusDto;

import java.sql.*;

public class ListingStatusDaoImpl extends AbstractDao implements ListingStatusDao {

    @Override
    public void saveListingStatus(ListingStatusDto listingStatus) {
        try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO listing_status(id, status_name)" +
                            "VALUES (?,?)")) {

            statement.setLong(1,listingStatus.getId());
            statement.setString(2, listingStatus.getStatusName());


            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean existsById(Long id) {
        try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM listing_status WHERE id = %d", id));

            return resultSet.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
