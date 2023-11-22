package com.example.spotifyfestival;

import com.example.spotifyfestival.ConcertsAndFestivals.*;
import com.example.spotifyfestival.Tree.Tree;
import com.example.spotifyfestival.UserData.Domain.Artist;
import com.example.spotifyfestival.UserData.DuplicateEntityException;
import com.example.spotifyfestival.UserData.Generics.CRUDRepoInterface;
import com.example.spotifyfestival.UserData.Repos.DBRepos.ArtistRepo;
import com.example.spotifyfestival.UserData.Repos.FileRepos.ArtistFileRepo;
import com.example.spotifyfestival.UserData.Repos.FileRepos.ArtistRepoBinary;
import com.example.spotifyfestival.UserData.Service.Service;
import com.example.spotifyfestival.UserData.UI.UI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public class Main {

    public static CRUDRepoInterface createDefaultRepo()
    {
        CRUDRepoInterface repo = new ArtistRepo();

        Artist a1 = new Artist("1", "John");
        Artist a2 = new Artist("2", "Ana");
        Artist a3 = new Artist( "3", "Alex");

        try{
            repo.add(a1.getId(),a1);
            repo.add(a2.getId(),a2);
            repo.add(a3.getId(),a3);
        }catch (DuplicateEntityException re)
        {
            System.out.println(re.getMessage());
        }

        return repo;
    }

    public static void main(String[] args) throws IOException {
        CRUDRepoInterface artistRepo = new ArtistFileRepo("Artists.txt");
//        try {
//            docRepo.add(1, new Doctor(1, "John", "Cardiology", 9.5));
//            docRepo.add(2, new Doctor(2, "Ana", "neurology", 9.0));
//        } catch (DuplicateEntityException e) {
//            throw new RuntimeException(e);
//        }

        Service service = new Service(artistRepo);
        UI ui=new UI(service);
        ui.main();
    }
//    public static void main(String[] args) {
//        Entity userLocation = new Entity();
//        ConcertJSONUtils utils = new ConcertJSONUtils(userLocation);
//        ObservableList<Concert> concerts = utils.extractConcerts(JSONConstant.getConstant());
//        List<Venue> listOfAllVenues = utils.createListOfALlVenues(concerts);
//        ObservableList<Entity> entityConcerts = FXCollections.observableArrayList();
//        ObservableList<Entity> venueConcerts = FXCollections.observableArrayList();
//        for(Venue venue : listOfAllVenues)
//        {
//            venueConcerts.add(venue);
//        }
//        for(Concert concert : concerts)
//        {
//            entityConcerts.add(concert);
//        }
//        Tree<Entity> tree =  utils.createTree(venueConcerts, userLocation);
//        utils.printTree(tree);
//    }
}