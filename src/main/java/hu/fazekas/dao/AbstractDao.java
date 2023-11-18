package hu.fazekas.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class AbstractDao {
    protected String DB_URL;
    protected String DB_USER;
    protected String DB_PASSWORD;

    protected AbstractDao() {
        loadFromProperties();
    }

    private void loadFromProperties(){
        try (InputStream input = AbstractDao.class.getClassLoader().getResourceAsStream("config.properties")) {

            Properties properties = new Properties();

            if (input == null) {
                System.out.println("Unable to find config.properties");
                return;
            }

            properties.load(input);

            this.DB_URL = properties.getProperty("db.url");
            this.DB_USER = properties.getProperty("db.user");
            this.DB_PASSWORD = properties.getProperty("db.password");

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
