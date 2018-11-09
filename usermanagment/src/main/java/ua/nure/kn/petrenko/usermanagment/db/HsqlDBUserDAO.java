package ua.nure.kn.petrenko.usermanagment.db;

import ua.nure.kn.petrenko.usermanagment.User;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;

class HsqlDBUserDAO implements UserDAO {
    private ConnectionFactory connectionFactory;
    private final String INSERT_USER = "insert into USERS (firstname, lastname, dateofbirth) values (?, ?, ?)";
    private final String UPDATE_USER = "update USERS set firstname = ?, lastname = ?, dateofbirth = ? where id = ?";
    private final String FIND_BY_ID = "select * from USERS where id = ?";
    private final String CALL_IDENTITY = "call IDENTITY()";
    private final String FIND_ALL_USERS = "select id, firstname, lastname, dateofbirth from users";
    private final String DELETE_USER = "delete from USERS where id = ?";

    HsqlDBUserDAO () {}

    HsqlDBUserDAO (ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public User create(User user) throws DataBaseException {
        Connection connection = connectionFactory.createConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_USER);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDate(3, Date.valueOf(user.getDateOfBirth()));
            int insertedRows = statement.executeUpdate();

            if (insertedRows != 1)
                throw new DataBaseException("Number of inserted rows: " + insertedRows);

            CallableStatement callableStatement = connection.prepareCall(CALL_IDENTITY);
            ResultSet keys = callableStatement.executeQuery();

            if (keys.next()) {
                user.setId(keys.getLong(1));
            }


            connection.close();
            statement.close();
            callableStatement.close();
            keys.close();
        } catch (SQLException | DataBaseException e) {
            throw new DataBaseException(e.toString());
        }
        return user;
    }

    @Override
    public void update(User user) throws DataBaseException {
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setDate(3, Date.valueOf(user.getDateOfBirth()));
            preparedStatement.setLong(4, user.getId());

            int insertedRows = preparedStatement.executeUpdate();

            if (insertedRows != 1) throw new DataBaseException("Number of inserted rows: " + insertedRows);

            connection.close();
            preparedStatement.close();
        } catch (DataBaseException | SQLException e) {
            throw new DataBaseException(e.toString());
        }
    }

    @Override
    public void delete(User user) throws DataBaseException {
        try {
            Connection connection = connectionFactory.createConnection();

            PreparedStatement statement = connection.prepareStatement(DELETE_USER);
            statement.setLong(1, user.getId());

            int removedRows = statement.executeUpdate();

            if (removedRows != 1) throw new DataBaseException("Number of removed rows: " + removedRows);

            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User find(Long id) throws DataBaseException {
        User user = null;
        try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet usersResultSet = statement.executeQuery();

            user = null;
            while (usersResultSet.next()) {
                user = new User();
                user.setId(usersResultSet.getLong(1));
                user.setFirstName(usersResultSet.getString(2));
                user.setLastName(usersResultSet.getString(3));
                Date date = usersResultSet.getDate(4);
                user.setDateOfBirth(date.toLocalDate());
            }

            connection.close();
            statement.close();
            usersResultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public Collection findAll() throws DataBaseException {
        LinkedList<User> result = new LinkedList<>();

        Connection connection = connectionFactory.createConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_USERS);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                Date date = resultSet.getDate(4);
                user.setDateOfBirth(date.toLocalDate());
                result.add(user);
            }

            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
        }

        return result;
    }
}
