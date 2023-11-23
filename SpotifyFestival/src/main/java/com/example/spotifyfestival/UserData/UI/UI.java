package com.example.spotifyfestival.UserData.UI;

import com.example.spotifyfestival.UserData.Domain.Artist;
import com.example.spotifyfestival.UserData.DuplicateEntityException;
import com.example.spotifyfestival.UserData.Repos.FileRepos.ArtistFileRepo;
import com.example.spotifyfestival.UserData.Service.Service;

import java.util.Iterator;
import java.util.Scanner;

public class UI {
    private Service service;

    public UI(Service artistServ) {
        this.service = artistServ;
    }

    public void menu(){
        System.out.println("-------------");
        System.out.println("1. Add a new artist");
        System.out.println("2. Print all artists");
        System.out.println("0. Exit");
    }
    public void add(){

        Scanner sc = new Scanner(System.in);
        System.out.print("Input id: ");
        String id = sc.next();
        System.out.print("Input name: ");
        String name=sc.next();

        //            service.addArtist(id, name);
    }
    public void printall(){

        Iterable<Artist> iterd = service.getALL();
        Iterator<Artist> i = iterd.iterator();
        while(i.hasNext())
        {
            System.out.println(i.next());
        }
    }
    public void main(){

        while(true) {
            menu();
            Scanner sc = new Scanner(System.in);
            System.out.println("Input your option: ");
            Integer option = sc.nextInt();
            if(option==1)
                add();
            else if(option==2)
                printall();
            else
                break;

        }
    }
}
