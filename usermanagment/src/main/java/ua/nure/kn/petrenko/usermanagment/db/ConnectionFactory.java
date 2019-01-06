package ua.nure.kn.petrenko.usermanagment.db;

import ua.nure.kn.petrenko.usermanagment.db.exception.DataBaseException;

import java.sql.Connection;

/**
 * Interface to create connection with DB
 */
public interface ConnectionFactory {
    Connection createConnection () throws DataBaseException;
}
