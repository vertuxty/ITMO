package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Entity;
import ru.itmo.wp.model.domain.Event;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public interface BaseRepository {
    int findCount(String entity);
}
