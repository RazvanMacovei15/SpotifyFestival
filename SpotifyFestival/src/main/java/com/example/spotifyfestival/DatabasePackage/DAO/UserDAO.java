package com.example.spotifyfestival.DatabasePackage.DAO;

import com.example.spotifyfestival.DatabasePackage.DBHelpers.CRUDHelper;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.DBGenericRepository;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.DBUtils;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.User;
import com.example.spotifyfestival.GenericsPackage.GenericDAO;
import com.example.spotifyfestival.LabFacultate.DuplicateEntityException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO extends DBGenericRepository<Integer, User> implements GenericDAO<User> {
    //DB specific attributes
    private static final String LOCATION = "userBase";
    private static final String tableName = "AppUsers";
    private final String[] columns = {"id", "email", "username", "role", "spotify_id"};
    private final String[] updateColumns = {"email", "username", "role", "spotify_id"};
    private final String readQuery = "SELECT * FROM " + tableName;
    private final String deleteQuery = "DELETE FROM " + tableName + " WHERE id = ?";

    private final int[] types = {Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};
    private final int[] updateTypes = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};
    public String getReadQuery()
    {
        return readQuery;
    }
    public String getDeleteQuery()
    {
        return deleteQuery;
    }
    private static CRUDHelper crudHelper;

    private static UserDAO instance;

    protected UserDAO() {

    }

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
            crudHelper = new CRUDHelper(LOCATION);
            initialize();
        }
        return instance;
    }

    private static void initialize() {
        instance.readAllObjectsFromTable();
    }

    protected ObservableList<User> userObservableList = FXCollections.observableArrayList();

    public ObservableList<User> getUserObservableList() {
        return userObservableList;
    }

    @Override
    public void insertObjectInDB(User item) {
        //update MemoryRepo
        try {
            super.add(item.getId(), item);
        } catch (DuplicateEntityException e) {
            throw new RuntimeException(e);
        }
        //update DB
        int id = (int) crudHelper.create(
                tableName,
                columns,
                new Object[]{item.getId(), item.getEmail(), item.getUsername(), item.getRole(), item.getSpotifyId()},
                types
        );
    }

    @Override
    public Optional<User> getItemByID(int id) {
        Iterable<User> users = super.getAll();
        for (User user : users) {
            if (user.getId().equals(id))
                return Optional.of(user);
        }
        return Optional.empty();
    }

    @Override
    public void updateObjectInDB(User newItem) {
        //update DB
        long rows = crudHelper.update(
                tableName,
                updateColumns,
                new Object[]{newItem.getEmail(), newItem.getUsername(), newItem.getRole(), newItem.getSpotifyId()},
                updateTypes,
                "id",
                Types.INTEGER,
                newItem.getId()
        );
        if (rows == 0)
            throw new IllegalStateException("User to update with id " + newItem.getId() + "doesn't exist in the database!");
        super.update(newItem.getId(), newItem);
    }

    @Override
    public int deleteObjectByIDInDB(Integer id) {
        instance.delete(id);
        return crudHelper.delete(
                tableName,
                id,
                deleteQuery);
    }

    @Override
    public void readAllObjectsFromTable() {
        String query = "SELECT * FROM " + tableName;
        try (Connection connection = DBUtils.getConnection("userBase")) {
            PreparedStatement statement = connection.prepareStatement(readQuery);
            ResultSet rs = statement.executeQuery();
            userObservableList.clear();
            while (rs.next()) {

                User user = new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("role"),
                        rs.getString("spotifyId"));
                instance.add(rs.getInt("id"), user);
                userObservableList.add(user);
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Users from database ");
            userObservableList.clear();
        } catch (DuplicateEntityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object readItemAttributeFromDB(String fieldName, int fieldDataType, Object index) {
        return crudHelper.read(tableName,
                fieldName,
                fieldDataType,
                columns[0],
                types[0],
                index);
    }

    public static void main(String[] args) {
        UserDAO userDAO = UserDAO.getInstance();
        userDAO.list();
    }
}
