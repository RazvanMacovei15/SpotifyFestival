package com.example.spotifyfestival.UserData.Service;

import com.example.spotifyfestival.UserData.Domain.Artist;
import com.example.spotifyfestival.UserData.DuplicateEntityException;
import com.example.spotifyfestival.UserData.Generics.CRUDRepoInterface;

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
