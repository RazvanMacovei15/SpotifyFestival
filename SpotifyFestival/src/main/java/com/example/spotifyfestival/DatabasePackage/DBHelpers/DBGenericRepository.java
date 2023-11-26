package com.example.spotifyfestival.DatabasePackage.DBHelpers;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Identifiable;
import com.example.spotifyfestival.GenericsPackage.MemoryRepository;
import com.example.spotifyfestival.Lab_facultate.DuplicateEntityException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DBGenericRepository< K, V extends Identifiable<K>> extends MemoryRepository<K,V>{

    public DBGenericRepository(){

    }

}