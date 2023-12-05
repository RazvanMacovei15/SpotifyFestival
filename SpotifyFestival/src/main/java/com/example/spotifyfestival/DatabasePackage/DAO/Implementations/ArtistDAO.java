package com.example.spotifyfestival.DatabasePackage.DAO.Implementations;


import com.example.spotifyfestival.DatabasePackage.DBHelpers.CRUDHelper;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.DBUtils;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.GenericsPackage.GenericDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArtistDAO implements GenericDAO<Artist> {
    //DB specific attributes
    String tableName = "Artists";
    String[] columns = {"artist_id", "name", "spotify_id"};
    String[] updateColumns = {"name", "spotify_id"};
    private final String readQuery = "SELECT * FROM " + tableName;
    public String getReadQuery() {
        return readQuery;
    }
    private final String deleteQuery = "DELETE FROM " + tableName + " WHERE artist_id = ?";
    int[] types = {Types.INTEGER, Types.VARCHAR, Types.VARCHAR};
    int[] updateTypes = {Types.VARCHAR, Types.VARCHAR};


    //JavaFX stuff
    protected static ObservableList<Artist> artistsList;
    public static ObservableList<Artist> getArtistsList() {
        return artistsList;
    }
    public static void setArtistsList(ObservableList<Artist> artistsList) {
        com.example.spotifyfestival.DatabasePackage.DAO.Implementations.ArtistDAO.artistsList = artistsList;
    }

    //ArtistRepo
    private com.example.spotifyfestival.RepositoryPackage.DBRepos.ArtistDAO artistRepo;

    public ArtistDAO(){
        artistRepo = com.example.spotifyfestival.RepositoryPackage.DBRepos.ArtistDAO.getInstance();
        artistsList = FXCollections.observableArrayList();
    }

    private void refreshObservableList(){
        artistsList.clear();
        artistRepo = com.example.spotifyfestival.RepositoryPackage.DBRepos.ArtistDAO.getInstance();
        Iterable<Artist> iterable = artistRepo.getAll();
        for(Artist artist : iterable){
            artistsList.add(artist);
        }
    }

//    protected void populateRepoFromDB(ArtistDAORepo artistRepo) {
//
//        try(Connection connection = DBUtils.connect("festivalDB")){
//            PreparedStatement statement = connection.prepareStatement(readQuery);
//            ResultSet rs = statement.executeQuery();
//
//            while (rs.next()) {
//                int artist_id = rs.getInt("artist_id");
//                String name = rs.getString("name");
//                String spotify_id = rs.getString("spotify_id");
//                ObservableList<Genre> genres = null;
//
//                Artist artist = new Artist(artist_id, name, genres, spotify_id);
//
//                artistRepo.add(artist_id, artist);
//            }
//        } catch (SQLException e) {
//            Logger.getAnonymousLogger().log(
//                    Level.SEVERE,
//                    LocalDateTime.now() + ": Could not load Artists from database ");
//        } catch (DuplicateEntityException e) {
//            throw new RuntimeException(e);
//        }
//    }
    public Object readArtistAttribute(String attributeField, String indexID, int index) {

        Object thing = CRUDHelper.read(tableName, attributeField, Types.VARCHAR, indexID, Types.INTEGER, index);
        return thing;
    }
    //ArtistDAO implementation redone with generic interface

    @Override
    public void insertObjectInDB(Artist artist) {
        //update DB
        int id = (int) CRUDHelper.create(
                tableName,
                columns,
                new Object[]{artist.getId(), artist.getName(), artist.getSpotify_id()},
                types
        );
        artistsList.add(artist);
    }

    @Override
    public Optional<Artist> getItemByID(int id) {
        return null;
    }

    @Override
//    public Optional<Artist> getItemByID(int id) {
//        for (Artist artist : artistsList) {
//            if (artist.getId().equals(id))
//                return Optional.of(artist);
//        }
//        return Optional.empty();
//    }
    public void updateObjectInDB(Artist newArtist)
    {
        //update DB
        long rows = CRUDHelper.update(
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
//        //update cache
//        Optional<Artist> optionalArtist = getItemByID(newArtist.getId());
//        optionalArtist.ifPresentOrElse((oldArtist) -> {
//            artistsList.remove(oldArtist);
//            artistsList.add(newArtist);
//        }, () -> {
//            throw new IllegalStateException("Artist to update with id " + newArtist.getId() + "doesn't exist in the database!");
//        });
    }
    @Override
    public int deleteObjectByIDInDB(Integer id) {
        if (id == null) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not delete from " + tableName +
                            " because the provided id is null.");
            return -1; // or throw an exception, depending on your error handling strategy
        }
        // Build the SQL delete query

        try(Connection connection = DBUtils.getConnection("festivalDB")){
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

    @Override
    public void readAllObjectsFromTable() {

    }

    @Override
    public Object readItemAttributeFromDB(String fieldName, int fieldDataType, Object index) {
        return null;
    }
    public static void main(String[] args) throws SQLException {
//        VenueDAO venueDAOImplementation = new VenueDAO();
//        venueDAOImplementation.generateVenueRepo();
//        VenueRepo venueRepo = VenueRepo.getInstance();
//        venueRepo.list();

//        System.out.println();
//
//        GenreDAOImplementation genreDAOImplementation = new GenreDAOImplementation();
//
//        GenreRepo genreRepo;
//        genreRepo = genreDAOImplementation.getAllGenres();
//        genreRepo.list();
//
//        System.out.println();
//
        com.example.spotifyfestival.RepositoryPackage.DBRepos.ArtistDAO artistRepo = com.example.spotifyfestival.RepositoryPackage.DBRepos.ArtistDAO.getInstance();
        ArtistDAO artistDAOImplementation = new ArtistDAO();
        System.out.println(artistDAOImplementation.getItemByID(19));
        artistRepo.list();
//
//        System.out.println();
//
//        ArtistGenreRepo artistGenreRepo = ArtistGenreRepo.getInstance();
//        ArtistGenreDAOImplementation artistGenreDAOImplementation = new ArtistGenreDAOImplementation();
//        artistGenreDAOImplementation.populateArtistsWithGenres(artistRepo, genreRepo, artistGenreRepo);
//        artistGenreRepo.list();
//
//        System.out.println();
//
//        FestivalDAOImplementation festivalDAOImplementation = new FestivalDAOImplementation();
//        FestivalRepo festivalRepo = festivalDAOImplementation.getAllFestivals();
//        festivalRepo.list();
//
//        System.out.println();
//
//        FestivalStageDAOImplementation festivalStageDAOImplementation = new FestivalStageDAOImplementation();
//        FestivalStageRepo festivalStageRepo = festivalStageDAOImplementation.getAllStages();
//        festivalStageRepo.list();
//
//        System.out.println();
//
//        for (int i = 1; i < festivalStageRepo.getSize() + 1; i++) {
//            System.out.println(festivalStageRepo.getItem(i).getVenue());
//        }
//
//        System.out.println();
//
//        ConcertDAOImplementation concertDAOImplementation = new ConcertDAOImplementation();
//        ConcertRepo concertRepo = concertDAOImplementation.getAllConcerts();
//        concertRepo.list();
//        System.out.println(concertRepo.getItem(5).getDescription());
//
//        int id = 21;
//        String name = "Eminem";
//        ObservableList<Genre> genres = null;
//        String spotify_id = "not yet determined";
//        Artist newArtist = new Artist(id, name, genres, spotify_id);
////
////        artistDAOImplementation.insertArtistInDB(id, name, genres, spotify_id);
//
////        artistDAOImplementation.update(newArtist);
////        artistDAOImplementation.delete(21);
//        int idToDelete = 20; // Replace this with the actual ID you want to delete
////        int rowsAffected = artistDAOImplementation.delete(idToDelete);
//
//        Object freshArtist = artistDAOImplementation.readArtistAttribute( "name", "artist_id", idToDelete);
//        System.out.println(freshArtist);
    }

}
