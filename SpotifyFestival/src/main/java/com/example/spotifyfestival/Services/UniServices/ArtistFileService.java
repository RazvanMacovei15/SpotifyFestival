package com.example.spotifyfestival.Services.UniServices;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.GenericsPackage.CRUDInterface;
import com.example.spotifyfestival.LabFacultate.DuplicateEntityException;
import com.example.spotifyfestival.RepositoryPackage.BinFileRepos.ArtistBinaryRepo;
import com.example.spotifyfestival.RepositoryPackage.TextFileRepos.ArtistTextRepo;

public class ArtistFileService implements CRUDInterface<Artist> {
    protected ArtistTextRepo artistTextRepo;
    protected ArtistBinaryRepo artistBinaryRepo;

    public ArtistTextRepo getArtistTextRepo() {
        return artistTextRepo;
    }

    public ArtistBinaryRepo getArtistBinaryRepo() {
        return artistBinaryRepo;
    }

    public ArtistFileService(ArtistTextRepo artistTextRepo, ArtistBinaryRepo artistBinaryRepo) {
        this.artistTextRepo = artistTextRepo;
        this.artistBinaryRepo = artistBinaryRepo;
    }

    @Override
    public void add(Artist item) throws DuplicateEntityException {
        artistTextRepo.add(item.getId(), item);
        artistBinaryRepo.add(item.getId(), item);
    }

    @Override
    public void update(Artist newItem) {
        artistTextRepo.update(newItem.getId(), newItem);
        artistBinaryRepo.update(newItem.getId(), newItem);
    }

    @Override
    public void delete(int id) {
        artistTextRepo.delete(id);
        artistBinaryRepo.delete(id);
    }

    @Override
    public Artist getItem(int id) {
        return artistBinaryRepo.getItem(id);
    }

    public Artist getItemFromTextFile(int id){
        return artistTextRepo.getItem(id);
    }

    @Override
    public void list() {
        System.out.println("---Text Repo---");
        artistTextRepo.list();
        System.out.println("---Binary Repo---");
        artistBinaryRepo.list();
    }
}
