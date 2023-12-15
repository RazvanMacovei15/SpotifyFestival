package com.example.spotifyfestival.UI_Package.DBControllers;

import com.example.spotifyfestival.RepositoryPackage.BinFileRepos.ArtistBinaryRepo;
import com.example.spotifyfestival.RepositoryPackage.TextFileRepos.ArtistTextRepo;
import com.example.spotifyfestival.Services.UniServices.ArtistFileService;
import com.example.spotifyfestival.Services.UniServices.ArtistGenreFileService;
import com.example.spotifyfestival.Services.UniServices.GenreFileService;
import com.example.spotifyfestival.UI_Package.Settings;
import com.example.spotifyfestival.UtilsPackage.AppSwitchScenesMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class LoadFromController {
    private Settings settings = new Settings();
    private ArtistFileService artistFileService;
    private ArtistGenreFileService artistGenreFileService;
    private GenreFileService genreFileService;
    private ArtistTextRepo artistTextRepo;
    private ArtistBinaryRepo artistBinaryRepo;
    @FXML private Button loadFromBinaryFile;
    @FXML private Button loadFromTextFile;
    @FXML private Button loadFromSqLite;
    public void initialize(){
        artistTextRepo = new ArtistTextRepo(settings.getProperty("textFilePath"));
        artistBinaryRepo = new ArtistBinaryRepo(settings.getProperty("binaryFilePath"));
        // Event handling for each button
        loadFromBinaryFile.setOnAction(event -> loadDataFrom(settings.getProperty("binaryFilePath")));
        loadFromTextFile.setOnAction(event -> loadDataFrom(settings.getProperty("textFilePath")));
        loadFromSqLite.setOnAction(event -> loadDataFromDB(settings.getProperty("festivalDBConnection")));
    }

    private void loadDataFrom(String filePath) {
        // Implement loading from the specified file
        System.out.println("Loading data from: " + filePath);

        artistFileService = new ArtistFileService(artistTextRepo, artistBinaryRepo);
        artistFileService.list();
    }

    private void loadDataFromDB(String dbConnection) {
        // Implement loading directly from the database
        System.out.println("Loading data from DB: " + dbConnection);
    }
    public void onLoadFromBinaryFileButtonClicked(ActionEvent event){
        artistFileService = new ArtistFileService(artistTextRepo, artistBinaryRepo);
        artistFileService.list();
    }
    public void onLoadFromTextFileButtonClicked(ActionEvent event){

    }
    public void onLoadFromSqLiteButtonClicked(ActionEvent event){
        try {
            AppSwitchScenesMethods.switchSceneForDatabase(event, "/com/example/spotifyfestival/FXML_Files/DatabaseScenes/MainDatabaseScene.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
