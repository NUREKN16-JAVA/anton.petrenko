package ua.nure.kn.petrenko.usermanagment.db;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.junit.*;
import static org.junit.Assert.*;
import ua.nure.kn.petrenko.usermanagment.User;

import java.time.LocalDate;
import java.util.Collection;

public class HsqlDBUserDAOTest extends DatabaseTestCase {
    private User user;
    private ConnectionFactory connectionFactory;
    private UserDAO dao;
    private final Long ID = 1L;
    private final String FIRSTNAME = "Vasyl";
    private final String LASTNAME = "Pupkin";
    private final LocalDate DATEOFBIRTH = LocalDate.of(1999, 3, 10);


    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        connectionFactory = new ConnectionFactoryImpl();
        return  new DatabaseConnection(connectionFactory.createConnection());
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        IDataSet dataSet = new XmlDataSet(getClass().getClassLoader().getResourceAsStream("usersDataSet.xml"));
        return dataSet;
    }

    @Before
    public void setUp() throws Exception {
        dao = DAOFactory.getInstance().getUserDAO();
        user = new User(null, FIRSTNAME, LASTNAME, DATEOFBIRTH);
    }

    @Test
    public void testCreateMethod() {
        try {
            Assert.assertNull(user.getId());
            User ResulUser = dao.create(user);
            Assert.assertNotNull(ResulUser);
            Assert.assertNotNull(ResulUser.getId());
            Assert.assertEquals(user.getFirstName(), ResulUser.getFirstName());
            Assert.assertEquals(user.getLastName(), ResulUser.getLastName());
            Assert.assertEquals(user.getDateOfBirth(), ResulUser.getDateOfBirth());
        } catch (DataBaseException e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    @Test
    public void testFindAllMethod() {
        try{
            User testUser = dao.create(user);
            int ExpectedCollectionSize = 3;
            Collection AllUsers = dao.findAll();
            Assert.assertNotNull("Collection is null", AllUsers);
            Assert.assertEquals("Collection size.", ExpectedCollectionSize, AllUsers.size());
        } catch (DataBaseException e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    @Test
    public void testFindMethod() {
        try {
            User testUser = dao.find(ID);
            Assert.assertNotNull(testUser);
            Assert.assertEquals(testUser.getFirstName(), user.getFirstName());
            Assert.assertEquals(testUser.getLastName(), user.getLastName());
            Assert.assertEquals(testUser.getDateOfBirth(), user.getDateOfBirth());
        } catch (DataBaseException e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateMethod() {
        try {
            String testFirstName = "Guy";
            String testLastName = "Fox";
            LocalDate testDateOfBirth = LocalDate.now();
            User testUser = new User(ID, testFirstName, testLastName, testDateOfBirth);
            dao.update(testUser);
            User updatedUser = dao.find(testUser.getId());
            Assert.assertNotNull(updatedUser);
            Assert.assertEquals(testUser.getFirstName(), updatedUser.getFirstName());
            Assert.assertEquals(testUser.getLastName(), updatedUser.getLastName());
            Assert.assertEquals(testUser.getDateOfBirth(), updatedUser.getDateOfBirth());
        } catch (DataBaseException e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteMethod() {
        try {
            User testUser = new User(ID, FIRSTNAME, LASTNAME, DATEOFBIRTH);
            dao.delete(testUser);
            Assert.assertNull(dao.find(ID));
        } catch (DataBaseException e) {
            e.getMessage();
            e.printStackTrace();
        }
    }
}
