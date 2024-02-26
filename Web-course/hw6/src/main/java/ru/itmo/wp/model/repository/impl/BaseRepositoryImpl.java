package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.Entity;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.BaseRepository;

import javax.sql.DataSource;
import java.sql.*;

public abstract class BaseRepositoryImpl implements BaseRepository {
    protected final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    @Override
    public int findCount(String entity) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT count(*) FROM " + entity)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt(1);
                    }
                    return 0;
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find " + entity + ".", e);
        }
    }
}
