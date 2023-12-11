package com.example.spotifyfestival.RepositoryPackage.DBRepos;

import com.example.spotifyfestival.DatabasePackage.DBHelpers.CRUDHelper;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.DBGenericRepository;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.DBUtils;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.User;
import com.example.spotifyfestival.GenericsPackage.GenericDAO;
import com.example.spotifyfestival.Lab_facultate.DuplicateEntityException;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO extends DBGenericRepository<Integer, User> implements GenericDAO<User> {
    //DB specific attributes
    private static final String LOCATION = "Userbase";
    private static final String tableName = "Users";
    private final String[] columns = {"user_id", "email", "username", "encrypted_password", "role", "genre_list_id", "spotify_id"};
    private final String[] updateColumns = {"email", "username", "encrypted_password", "role", "genre_list_id", "spotify_id"};
    private final String readQuery = "SELECT * FROM " + tableName;
    private final String deleteQuery = "DELETE FROM " + tableName + " WHERE user_id = ?";

    private final int[] types = {Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};
    private final int[] updateTypes = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};
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

    protected ObservableList<User> userObservableList;

    public ObservableList<User> getUserObservableList() {
        return userObservableList;
    }

    @Override
    public void insertObjectInDB(User item) {

    }

    @Override
    public Optional<User> getItemByID(int id) {
        return Optional.empty();
    }

    @Override
    public void updateObjectInDB(User item) {

    }

    @Override
    public int deleteObjectByIDInDB(Integer id) {
        return 0;
    }

    @Override
    public void readAllObjectsFromTable() {
        String query = "SELECT * FROM " + tableName;
        try (Connection connection = DBUtils.getConnection("Users")) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            userObservableList.clear();
            while (rs.next()) {

                User user = new User(
                        rs.getInt("user_id"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("role"),
                        rs.getString("spotifyId"));
                instance.add(rs.getInt("user_id"), user);
                userObservableList.add(user);
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Artists from database ");
            userObservableList.clear();
        } catch (DuplicateEntityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object readItemAttributeFromDB(String fieldName, int fieldDataType, Object index) {
        return null;
    }
}
