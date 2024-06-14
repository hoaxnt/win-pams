package com.winpams.data;


import com.winpams.core.exceptions.ConnectionError;
import com.winpams.data.annotations.Entity;
import com.winpams.data.model.BaseModel;
import org.javatuples.Pair;

import java.sql.*;
import java.util.Map;
import java.util.StringJoiner;


// TODO: Add where filter

public class Database implements AutoCloseable {
    public enum Operation {
        SELECT,
        INSERT,
        UPDATE,
        DELETE,
        WHERE
    }

    private static Connection connection;

    private Database() throws ConnectionError {
        if (connection == null)
            throw new ConnectionError("Connection not established. Call connect() first.");
    }

    public static void connect(String url, String user, String password) throws SQLException, ConnectionError {
        if (connection != null) {
            throw new ConnectionError("Connection already established");
        }

        connection = DriverManager.getConnection(url, user, password);
    }

    @Override
    public void close() throws SQLException {
        if (connection == null) return;

        connection.close();
    }

    private static class Holder {
        private static final Database INSTANCE;

        static {
            try {
                INSTANCE = new Database();
            } catch (ConnectionError e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Database instance() {
        return Database.Holder.INSTANCE;
    }


    public ResultSet execute(String query, Object... params) throws SQLException {
        if (query.contains("?")) {

            PreparedStatement statement = connection.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            return statement.executeQuery();
        }

        return connection.createStatement().executeQuery(query);
    }

    public <T extends BaseModel> Pair<String, Object[]> buildQuery(T model, Operation mode, String[] selectColumns, Integer id) throws Exception {
        String tableName = model.getClass().getAnnotation(Entity.class).name();
        Map<String, Object> columns = model.dump();
        String query;
        Object[] params;

        switch (mode) {
            case INSERT:
                StringJoiner insertColumnsJoiner = new StringJoiner(", ");
                StringJoiner valuesJoiner = new StringJoiner(", ");
                columns.forEach((key, value) -> {
                    insertColumnsJoiner.add(key);
                    valuesJoiner.add("?");
                });
                params = columns.values().toArray();
                query = "INSERT INTO " + tableName + " (" + insertColumnsJoiner.toString() + ") VALUES (" + valuesJoiner.toString() + ")";
                break;

            case UPDATE:
                StringJoiner updateJoiner = new StringJoiner(", ");
                columns.forEach((key, value) -> {
                    updateJoiner.add(key + " = ?");
                });
                params = new Object[columns.size() + 1];
                int i = 0;
                for (Object value : columns.values()) {
                    params[i++] = value;
                }
                params[i] = model.id;  // Assuming the model has a getId() method
                query = "UPDATE " + tableName + " SET " + updateJoiner.toString() + " WHERE id = ?";

                break;
            case DELETE:
                params = new Object[] { model.id };
                query = "DELETE FROM " + tableName + " WHERE id = ?";
                break;

            case SELECT:
                String selectColumnsString = (selectColumns == null || selectColumns.length == 0) ? "*" : String.join(", ", selectColumns);
                params = new Object[] { id };
                query = "SELECT " + selectColumnsString + " FROM " + tableName;

                if (id != null) {
                    query += " WHERE id = ?";
                }

                break;

            default:
                throw new IllegalArgumentException("Invalid operation: " + mode);
        }

        return new Pair<>(query, params);
    }

    public <T extends BaseModel> Pair<String, Object[]> buildQuery(T model, Operation mode) throws Exception {
        return buildQuery(model, mode, null, null);
    }

    public <T extends BaseModel> Pair<String, Object[]> buildQuery(T model, Operation mode, String[] selectColumns) throws Exception {
        return buildQuery(model, mode, selectColumns, null);
    }

    public <T extends BaseModel> Pair<String, Object[]> buildQuery(T model, Operation mode, Integer id) throws Exception {
        return buildQuery(model, mode, null, id);
    }
}
