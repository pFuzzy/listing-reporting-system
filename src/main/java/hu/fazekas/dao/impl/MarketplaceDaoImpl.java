package hu.fazekas.dao.impl;

import hu.fazekas.dao.AbstractDao;
import hu.fazekas.dao.MarketplaceDao;
import hu.fazekas.dto.MarketplaceDto;

import java.sql.*;

public class MarketplaceDaoImpl extends AbstractDao implements MarketplaceDao {

    @Override
    public void saveMarketPlace(MarketplaceDto marketplace) {
        try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO marketplace(id, marketplace_name)" +
                            "VALUES (?,?)")) {

            statement.setLong(1, marketplace.getId());
            statement.setString(2, marketplace.getMarketplaceName());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean existsById(Long id) {
        try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM marketplace WHERE id = %d", id));

            return resultSet.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
