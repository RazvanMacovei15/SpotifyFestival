package com.example.spotifyfestival.RepositoryPackage.DBRepos;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.ArtistGenre;
import com.example.spotifyfestival.GenericsPackage.GenericDAO;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.CRUDHelper;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.DBUtils;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.DBGenericRepository;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import com.example.spotifyfestival.Lab_facultate.DuplicateEntityException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArtistDAO extends DBGenericRepository<Integer, Artist> implements GenericDAO<Artist> {
    //DB specific attributes
    private static final String LOCATION = "festivalDB";
    private static final String tableName = "Artists";
    private final String[] columns = {"artist_id", "name", "spotify_id"};
    private final String[] updateColumns = {"name", "spotify_id"};
    private final String readQuery = "SELECT * FROM " + tableName;
    private final String deleteQuery = "DELETE FROM " + tableName + " WHERE artist_id = ?";

    private final int[] types = {Types.INTEGER, Types.VARCHAR, Types.VARCHAR};
    private final int[] updateTypes = {Types.VARCHAR, Types.VARCHAR};
    public String getReadQuery()
    {
        return readQuery;
    }
    public String getDeleteQuery()
    {
        return deleteQuery;
    }
    private static CRUDHelper crudHelper;
    //Singleton Creation
    private static ArtistDAO instance;

    protected ArtistDAO() {

    }

    public static ArtistDAO getInstance() {
        if (instance == null) {
            instance = new ArtistDAO();
            crudHelper = new CRUDHelper(LOCATION);
            genreDAO = GenreDAO.getInstance();
            artistGenreDAO = ArtistGenreDAO.getInstance();
            initialize();
        }
        return instance;
    }

    //TableView JavaFX stuff
    ObservableList<Artist> artistList = FXCollections.observableArrayList();

    public ObservableList<Artist> getArtistList() {
        return artistList;
    }

    //Repo Attributes To fill GenresList from Artist objects

    protected static GenreDAO genreDAO;
    protected static ArtistGenreDAO artistGenreDAO;

    //DB related methods
    public void readAllObjectsFromTable() {
        String query = "SELECT * FROM " + tableName;
        try (Connection connection = DBUtils.getConnection("festivalDB")) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            artistList.clear();
            while (rs.next()) {
                int artist_id = rs.getInt("artist_id");
                ObservableList<Genre> genres = generateListOfGenresForArtist(artistGenreDAO, genreDAO, artist_id);
                Artist artist = new Artist(
                        rs.getInt("artist_id"),
                        rs.getString("name"),
                        genres,
                        rs.getString("spotify_id"));
                instance.add(rs.getInt("artist_id"), artist);
                artistList.add(artist);
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Artists from database ");
            artistList.clear();
        } catch (DuplicateEntityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertObjectInDB(Artist artist) {
        //update MemoryRepo
        try {
            super.add(artist.getId(), artist);
        } catch (DuplicateEntityException e) {
            throw new RuntimeException(e);
        }
        //update DB
        int id = (int) crudHelper.create(
                tableName,
                columns,
                new Object[]{artist.getId(), artist.getName(), artist.getSpotify_id()},
                types
        );
    }

    @Override
    public Optional<Artist> getItemByID(int id) {
        Iterable<Artist> artists = super.getAll();
        for (Artist artist : artists) {
            if (artist.getId().equals(id))
                return Optional.of(artist);
        }
        return Optional.empty();
    }

    @Override
    public void updateObjectInDB(Artist newArtist) {
        //update DB
        long rows = crudHelper.update(
                tableName,
                updateColumns,
                new Object[]{newArtist.getName(), newArtist.getSpotify_id()},
                updateTypes,
                "artist_id",
                Types.INTEGER,
                newArtist.getId()
        );
        if (rows == 0)
            throw new IllegalStateException("Artist to update with id " + newArtist.getId() + "doesn't exist in the database!");
        super.update(newArtist.getId(), newArtist);
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
    public Object readItemAttributeFromDB(String fieldName, int fieldDataType, Object index) {
        return crudHelper.read(tableName,
                fieldName,
                fieldDataType,
                columns[0],
                types[0],
                index);
    }

    public ObservableList<Genre> generateListOfGenresForArtist(ArtistGenreDAO artistGenreDAO, GenreDAO genreDAO, int id){
        ObservableList<Genre> artistGenreList = FXCollections.observableArrayList();
        Iterable<ArtistGenre> artistGenreDAOS = artistGenreDAO.getAll();
        for(ArtistGenre artistGenre : artistGenreDAOS){
            if(artistGenre.getArtist_id() == id){
                int genre_id = artistGenre.getGenre_id();
                Genre genre = genreDAO.getItem(genre_id);
                artistGenreList.add(genre);
            }
        }
        return artistGenreList;
    }

    public static void initialize(){
        instance.readAllObjectsFromTable();
    }
}