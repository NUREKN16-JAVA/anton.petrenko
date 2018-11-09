package ua.nure.kn.petrenko.usermanagment.db;

import java.sql.Connection;

public interface ConnectionFactory {
    Connection createConnection () throws DataBaseException;
}
