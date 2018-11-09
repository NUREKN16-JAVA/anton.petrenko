package ua.nure.kn.petrenko.usermanagment.db;

import ua.nure.kn.petrenko.usermanagment.User;

import java.util.Collection;

public interface UserDAO {
    User create (User user) throws DataBaseException;
    void update (User user) throws DataBaseException;
    void delete (User user) throws DataBaseException;
    User find (Long id) throws DataBaseException;
    Collection findAll () throws DataBaseException;
    void setConnectionFactory(ConnectionFactory connectionFactory);
}
