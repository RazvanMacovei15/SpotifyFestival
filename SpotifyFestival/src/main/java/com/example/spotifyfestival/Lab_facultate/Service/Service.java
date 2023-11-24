package com.example.spotifyfestival.Lab_facultate.Service;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.Lab_facultate.DuplicateEntityException;
import com.example.spotifyfestival.GenericsPackage.CRUDRepoInterface;

public class Service {

    private CRUDRepoInterface repo;

    public Service(CRUDRepoInterface repo) {
        this.repo = repo;
    }

    public void addArtist(Integer id,String name) throws DuplicateEntityException {
//        Artist a = new Artist(id,name);
//        this.repo.add(id,a);
    }

    public Iterable<Artist> getALL()
    {
        return repo.getAll();
    }

}
