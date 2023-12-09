package com.example.spotifyfestival.DatabasePackage.DBHelpers;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Helper class for performing CRUD (Create, Read, Update, Delete) operations on a database.
 */
public class CRUDHelper {

    protected String location;

    public CRUDHelper(String location) {
        this.location = location;
    }

    /**
     * Reads data from the database.
     *
     * @param tableName      Name of the table to read from.
     * @param fieldName      Name of the field to retrieve.
     * @param fieldDataType  Data type of the field.
     * @param indexFieldName Name of the field to use as an index.
     * @param indexDataType  Data type of the index field.
     * @param index          Value of the index field.
     * @return The retrieved data.
     */
    public Object read(String tableName, String fieldName, int fieldDataType,
                              String indexFieldName, int indexDataType, Object index) {
        // Build the SQL query
        StringBuilder queryBuilder = new StringBuilder("SELECT ");
        queryBuilder.append(fieldName);
        queryBuilder.append(" FROM ");
        queryBuilder.append(tableName);
        queryBuilder.append(" WHERE ");
        queryBuilder.append(indexFieldName);
        queryBuilder.append(" = ");
        queryBuilder.append(convertObjectToSQLField(index, indexDataType));

        try (Connection connection = DBUtils.getConnection(location)) {
            // Prepare and execute the query
            PreparedStatement statement = connection.prepareStatement(queryBuilder.toString());
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                // Retrieve and return the data based on the field type
                switch (fieldDataType) {
                    case Types.INTEGER:
                        return rs.getInt(fieldName);
                    case Types.VARCHAR:
                        return rs.getString(fieldName);
                    default:
                        throw new IllegalArgumentException("Index type " + indexDataType +
                                " from sql.Types is not yet supported.");
                }
            }
        } catch (SQLException exception) {
            // Log an error if the operation fails
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not fetch from " + tableName +
                            " by index " + index + " and column " + fieldName);
            return null;
        }
    }

    /**
     * Updates data in the database.
     *
     * @param tableName      Name of the table to update.
     * @param columns        Array of column names to update.
     * @param values         Array of values to set for the columns.
     * @param types          Array of data types for the values.
     * @param indexFieldName Name of the field to use as an index.
     * @param indexDataType  Data type of the index field.
     * @param index          Value of the index field.
     * @return The number of affected rows.
     */
    public long update(String tableName, String[] columns, Object[] values, int[] types,
                              String indexFieldName, int indexDataType, Object index) {
        // Determine the number of columns to update
        int number = Math.min(Math.min(columns.length, values.length), types.length);
        // Build the SQL update query
        StringBuilder queryBuilder = new StringBuilder("UPDATE " + tableName + " SET ");
        for (int i = 0; i < number; i++) {
            queryBuilder.append(columns[i]);
            queryBuilder.append(" = ");
            queryBuilder.append(convertObjectToSQLField(values[i], types[i]));
            if (i < number - 1) queryBuilder.append(", ");
        }
        queryBuilder.append(" WHERE ");
        queryBuilder.append(indexFieldName);
        queryBuilder.append(" = ");
        queryBuilder.append(convertObjectToSQLField(index, indexDataType));
        try (Connection conn = DBUtils.getConnection(location)) {
            // Prepare and execute the update query
            PreparedStatement pstmt = conn.prepareStatement(queryBuilder.toString());
            return pstmt.executeUpdate(); // Number of affected rows
        } catch (SQLException ex) {
            // Log an error if the operation fails
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not update database");
            return -1;
        }
    }

    /**
     * Creates a new record in the database.
     *
     * @param tableName Name of the table to insert into.
     * @param columns   Array of column names to insert into.
     * @param values    Array of values to insert for the columns.
     * @param types     Array of data types for the values.
     * @return The ID of the newly created record.
     */
    public long create(String tableName, String[] columns, Object[] values, int[] types) {
        // Determine the number of columns to insert
        int number = Math.min(Math.min(columns.length, values.length), types.length);
        // Build the SQL insert query
        StringBuilder queryBuilder = new StringBuilder("INSERT INTO " + tableName + " (");
        for (int i = 0; i < number; i++) {
            queryBuilder.append(columns[i]);
            if (i < number - 1) queryBuilder.append(", ");
        }
        queryBuilder.append(") VALUES (");
        for (int i = 0; i < number; i++) {
            // Append values based on their data types
            switch (types[i]) {
                case Types.VARCHAR:
                    queryBuilder.append("'");
                    queryBuilder.append((String) values[i]);
                    queryBuilder.append("'");
                    break;
                case Types.INTEGER:
                    queryBuilder.append((int) values[i]);
                    break;
                case Types.DOUBLE:
                    queryBuilder.append((double) values[i]);
                    break;
            }
            if (i < number - 1) queryBuilder.append(", ");
        }
        queryBuilder.append(");");
        try (Connection conn = DBUtils.getConnection(location)) {
            // Prepare and execute the insert query
            PreparedStatement pstmt = conn.prepareStatement(queryBuilder.toString(), Statement.RETURN_GENERATED_KEYS);
            int affectedRows = pstmt.executeUpdate();
            // Check the affected rows and return the generated ID
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                }
            }
        } catch (SQLException ex) {
            // Log an error if the operation fails
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not add record to database");
            ex.printStackTrace();
            return -1;
        }
        return -1;
    }

    /**
     * Deletes a record from the database by ID.
     *
     * @param tableName Name of the table to delete from.
     * @param id        ID of the record to delete.
     * @return The number of affected rows.
     */
    public int delete(String tableName, Integer id, String deleteQuery) {
        //Build the SQL delete query
//        String sql = "DELETE FROM " + tableName + " WHERE idField = ?";
//        try (Connection conn = DBUtils.connect(location)) {
//            // Prepare and execute the delete query
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            pstmt.setInt(1, id);
//            return pstmt.executeUpdate();
//        } catch (SQLException e) {
//            // Log an error if the operation fails
//            Logger.getAnonymousLogger().log(
//                    Level.SEVERE,
//                    LocalDateTime.now() + ": Could not delete from " + tableName +
//                            " by id " + id + " because " + e.getCause());
//            return -1;
//        }
        if (id == null) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not delete from " + tableName +
                            " because the provided id is null.");
            return -1; // or throw an exception, depending on your error handling strategy
        }
        // Build the SQL delete query
        try (Connection connection = DBUtils.getConnection("festivalDB")) {
            // Prepare and execute the delete query
            PreparedStatement pstmt = connection.prepareStatement(deleteQuery);
            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            // Log an error if the operation fails
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not delete from " + tableName +
                            " by id " + id + " because " + e.getCause());
            return -1;
        }
    }

    /**
     * Converts an object to its corresponding SQL field representation.
     *
     * @param value The value to convert.
     * @param type  The SQL data type of the value.
     * @return The SQL field representation of the value.
     */
    private String convertObjectToSQLField(Object value, int type) {
        StringBuilder queryBuilder = new StringBuilder();
        // Convert values based on their data types
        switch (type) {
            case Types.VARCHAR:
                queryBuilder.append("'");
                queryBuilder.append(value);
                queryBuilder.append("'");
                break;
            case Types.INTEGER:
                queryBuilder.append(value);
                break;
            default:
                throw new IllegalArgumentException("Index type " + type +
                        " from sql.Types is not yet supported.");
        }
        return queryBuilder.toString();
    }

    private boolean isIdDuplicate(int id, String tableName, String idField) {
        // Implement a method to check if the ID already exists in the database
        // This could be a SQL query or any mechanism specific to your database
        // Return true if the ID is found, indicating a duplicate
        // Return false if the ID is not found, indicating it's unique

        // Example SQL query (assuming 'id' is the primary key):
        String checkQuery = "SELECT COUNT(*) FROM " + tableName + " WHERE " + idField + " = ?";
        try (Connection connection = DBUtils.getConnection("festivalDB");
             PreparedStatement pstmt = connection.prepareStatement(checkQuery)) {
            pstmt.setInt(1, id);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0; // If count > 0, ID is a duplicate
                }
            }
        } catch (SQLException e) {
            // Handle exceptions
            return false; // Consider it as not a duplicate to avoid insertion errors
        }
        return false;
    }
}