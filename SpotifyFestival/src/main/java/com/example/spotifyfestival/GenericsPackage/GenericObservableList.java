package com.example.spotifyfestival.GenericsPackage;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Entity;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.DuplicateEntityException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Optional;

public class GenericObservableList<T extends Entity> {
    protected ObservableList<T> observableList;

    public GenericObservableList() {
        this.observableList = FXCollections.observableArrayList();
    }

    public void add(T item) throws DuplicateEntityException {
        // Check if the ID already exists
        if (idExists(item.getId())) {
            throw new DuplicateEntityException("Duplicate ID: " + item.getId());
        }

        // If the ID does not exist, add the item to the list
        observableList.add(item);
    }

    private boolean idExists(int itemId) {
        // Check if the ID already exists in the list
        for (T existingItem : observableList) {
            if (existingItem.getId().equals(itemId)) {
                return true; // ID already exists
            }
        }
        return false; // ID does not exist
    }

    public void delete(int id) {
        observableList.removeIf(item -> item.getId() == id);
    }

    public void update(T newItem) {
        Optional<T> optional = Optional.ofNullable(getItemByID(newItem.getId()));
        optional.ifPresentOrElse(
                oldItem -> {
                    observableList.remove(oldItem);
                    observableList.add(newItem);
                },
                () -> {
                    throw new IllegalArgumentException("Item to update with id " + newItem.getId() + " doesn't exist in the database!");
                }
        );
    }

    public void list() {
        for (T item : observableList) {
            System.out.println(item);
        }
    }

    public T getItemByID(int id) {
        for (T item : observableList) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public int getSize() {
        return observableList.size();
    }
}
