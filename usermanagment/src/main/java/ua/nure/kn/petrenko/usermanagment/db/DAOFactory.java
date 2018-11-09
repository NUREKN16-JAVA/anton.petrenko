package ua.nure.kn.petrenko.usermanagment.db;

import java.io.IOException;
import java.util.Properties;

public class DAOFactory {
    private final Properties properties;
    private final static DAOFactory INSTANCE = new DAOFactory();
    private UserDAO userDAO;

    DAOFactory() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("settings.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
