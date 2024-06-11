package com.winpams.core;


import com.winpams.core.annotations.Catch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Catch
public class Database implements AutoCloseable {
    public final Connection connection;

    private Database() throws SQLException {
        Config config = Config.getInstance();

        String url = config.getValue("DB_URL");
        String user = config.getValue("DB_USER");
        String password = config.getValue("DB_PASSWORD");

        this.connection = DriverManager.getConnection(url, user, password);
    }

    @Override
    public void close() throws Exception {
        if (connection == null)
            return;

        connection.close();
    }

    private static class Holder {
        private static final Database INSTANCE;

        static {
            try {
                INSTANCE = new Database();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Database getInstance() {
        return Database.Holder.INSTANCE;
    }


    public void execute(String query) throws SQLException {
        Statement statement = connection.createStatement();

        statement.executeUpdate(query);

        statement.close();
    }
}
