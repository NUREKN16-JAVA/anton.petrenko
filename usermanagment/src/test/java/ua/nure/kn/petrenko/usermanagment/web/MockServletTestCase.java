package ua.nure.kn.petrenko.usermanagment.web;

import com.mockobjects.dynamic.Mock;
import com.mockrunner.servlet.BasicServletTestCaseAdapter;
import ua.nure.kn.petrenko.usermanagment.db.DAOFactory;
import ua.nure.kn.petrenko.usermanagment.db.MockDaoFactory;

import java.util.Properties;

public abstract class MockServletTestCase extends BasicServletTestCaseAdapter {
    private Mock mockUserDao;

    public Mock getMockUserDao() {
        return mockUserDao;
    }

    public void setMockUserDao(Mock mockUserDao) {
        this.mockUserDao = mockUserDao;
    }

    public void setUp() throws Exception {
        super.setUp();
        Properties properties = new Properties();
        properties.setProperty("dao.factory", MockDaoFactory.class.getName());

        DAOFactory.init(properties);

        setMockUserDao(((MockDaoFactory) DAOFactory.getInstance()).getMockUserDao());
    }

    public void tearDown() throws Exception {
        getMockUserDao().verify();
        super.tearDown();

    }
}
