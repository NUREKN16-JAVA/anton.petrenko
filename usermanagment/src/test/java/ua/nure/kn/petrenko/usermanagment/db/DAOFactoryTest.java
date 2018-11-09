package ua.nure.kn.petrenko.usermanagment.db;

import org.junit.Assert;
import org.junit.Test;

public class DAOFactoryTest {
    @Test
    public void getUserDAOTest () {
        DAOFactory daoFactory = DAOFactory.getInstance();
        Assert.assertNotNull(daoFactory);
        UserDAO userDAO;
        try {
            userDAO = daoFactory.getUserDAO();
            Assert.assertNotNull(userDAO);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }
}
