package ua.nure.kn.petrenko.usermanagment.db;

import java.sql.Connection;

/**
 * Interface to create connection with DB
 */
public interface ConnectionFactory {
    Connection createConnection () throws DataBaseException;
}
