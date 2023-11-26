package com.example.spotifyfestival.MainPackage;

import com.example.spotifyfestival.GenericsPackage.CRUDRepoInterface;
import com.example.spotifyfestival.RepositoryPackage.DBRepos.ArtistDAORepo;
import com.example.spotifyfestival.RepositoryPackage.FileRepos.ArtistFileRepo;
import com.example.spotifyfestival.Lab_facultate.Service.Service;
import com.example.spotifyfestival.Lab_facultate.UI.UI;

import java.io.IOException;

public class Main {

    public static CRUDRepoInterface createDefaultRepo()
    {
        CRUDRepoInterface repo = ArtistDAORepo.getInstance();

//        Artist a1 = new Artist("1", "John");
//        Artist a2 = new Artist("2", "Ana");
//        Artist a3 = new Artist( "3", "Alex");

        //            repo.add(a1.getId(),a1);
//            repo.add(a2.getId(),a2);
//            repo.add(a3.getId(),a3);

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