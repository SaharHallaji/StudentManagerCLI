package com.studentManager.config;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.*;
import java.sql.*;
import java.util.*;

public class DatabaseConfig {
    static Dotenv dotenv = Dotenv.load();
    private static final String url = dotenv.get("DB_URL");
    private static final String username = dotenv.get("DB_USER");
    private static final String password = dotenv.get("DB_PASSWORD");

    private static Connection connection;

    private static void validateEnv() {
        if (url == null || username == null || password == null) {
            throw new IllegalStateException("Database environment variables are missing!");
        }
    }

    public static void connectToDB() {
        validateEnv();
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Connected to database.");
            }
            createTable();
        } catch (SQLException exp) {
            throw new RuntimeException("Cannot establish a connection to the database.", exp);
        }
    }

    public static void disconnectDB() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException exp) {
            System.err.println("Error while closing database connection: " + exp.getMessage());
        } finally {
            connection = null;
        }
    }

    private static void setParameters(PreparedStatement preparedStatement, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
    }

    public static List<Map<String, String>> executeQuery(String sql, Object... params) throws SQLException {
        List<Map<String, String>> results = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            setParameters(preparedStatement, params);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (resultSet.next()) {
                    Map<String, String> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.put(metaData.getColumnName(i), Objects.toString(resultSet.getObject(i), ""));
                    }
                    results.add(row);
                }
            }
        }
        return results;
    }

    public static String executeUpdate(String sql, Object... params) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setParameters(preparedStatement, params);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return Objects.toString(generatedKeys.getObject(1), "");
                    }
                }
            }
            return null;
        }
    }


    private static String readSqlFile() throws IOException {
        try(
                //TODO is this a hard code?!!
           InputStream inputStream = DatabaseConfig.class.getClassLoader().getResourceAsStream("schema.sql");
           BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))
        ){

            String line;
            StringBuilder sqlCode = new StringBuilder();

            while((line = reader.readLine()) != null) {
              sqlCode.append(line).append("\n");
            }

            return sqlCode.toString();
        }catch (Exception exp) {
            System.err.println("Error reading SQL file: " + exp.getMessage());
            return null;
        }
    }

    private static void createTable() {
        try {
            String createTable = readSqlFile();
            if (createTable == null) throw new RuntimeException("Can not create the table!!");
            executeUpdate(createTable);
            System.out.println("Table created successfully.");

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
