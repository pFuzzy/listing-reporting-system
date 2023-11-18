package hu.fazekas.dao.impl;

import hu.fazekas.dao.AbstractDao;
import hu.fazekas.dao.LocationDao;
import hu.fazekas.dto.LocationDto;

import java.sql.*;
import java.util.UUID;

public class LocationDaoImpl extends AbstractDao implements LocationDao {

    @Override
    public void saveLocation(LocationDto location) {
        try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO location(id, manager_name, phone_number, primary_address, secondary_address,country, town, postal_code)" +
                            "VALUES (?,?,?,?,?,?,?,?)")) {

            statement.setString(1, String.valueOf(location.getId()));
            statement.setString(2, location.getManagerName());
            statement.setString(3, location.getPhoneNumber());
            statement.setString(4,location.getPrimaryAddress());
            statement.setString(5,location.getSecondaryAddress());
            statement.setString(6, location.getCountry());
            statement.setString(7, location.getTown());
            statement.setString(8, location.getPostalCode());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean existsById(UUID id) {
        try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement statement = connection.prepareStatement("SELECT id FROM location WHERE id = ?")) {

            statement.setString(1, id.toString());

            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
