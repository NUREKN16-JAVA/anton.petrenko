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

    public static DAOFactory getInstance() {
        return INSTANCE;
    }

    private ConnectionFactory createConnection () {
        String user = properties.getProperty("connection.user");
        String password = properties.getProperty("connection.password");
        String url = properties.getProperty("connection.url");
        String driver = properties.getProperty("connection.driver");

        return new ConnectionFactoryImpl(user, password, url, driver);
    }

    public UserDAO getUserDAO () throws ReflectiveOperationException {
        try {
            Class UserDOAClass = Class.forName(properties.getProperty("dao.ua.nure.kn.petrenko.usermanagment.db.UserDAO"));
            userDAO = (UserDAO) UserDOAClass.newInstance();
            userDAO.setConnectionFactory(createConnection());
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new ReflectiveOperationException(e);
        }

        return userDAO;
    }
}
