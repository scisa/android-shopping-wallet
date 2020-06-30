package de.chsc.shoppinghistory.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import de.chsc.shoppinghistory.database.ShoppingHistoryDatabase;
import de.chsc.shoppinghistory.repository.ShoppingHistoryRepository;

public class ShoppingHistoryViewModel extends AndroidViewModel {
    private ShoppingHistoryRepository repository;
    private LiveData<List<ListItem>> allListItemsInOverview;
    private LiveData<List<ListItem>> allListItemsInTrash;
    private LiveData<List<ListItem>> checkIfItemExists;
    private LiveData<List<HistoryItem>> allHistoryItemsCurrentMonth;
    private LiveData<Double> priceOfMonth;

    public ShoppingHistoryViewModel(@NonNull Application application) {
        super(application);
        this.repository = new ShoppingHistoryRepository(application);

        this.allListItemsInOverview = this.repository.getAllListItemsInOverview();
        this.allListItemsInTrash = this.repository.getAllListItemsInTrash();
    }

    //Get
    public LiveData<List<ListItem>> getAllListItemsInOverview(){
        return allListItemsInOverview;
    }

    public LiveData<List<ListItem>> getAllListItemsInTrash(){
        return this.allListItemsInTrash;
    }

    public LiveData<List<HistoryItem>> getAllHistoryItemsCurrentMonth(int year, int month, String listName){
        this.allHistoryItemsCurrentMonth = this.repository.getAllHistoryItemsCurrentMonth(year, month, listName);
        return this.allHistoryItemsCurrentMonth;
    }

    public LiveData<Double> getPriceOfMonth(int year, int month, String listName){
        this.priceOfMonth = this.repository.getPriceOfMonth(year, month, listName);
        return this.priceOfMonth;
    }

    public LiveData<List<ListItem>> checkIfItemExists(String listTitle){
        this.checkIfItemExists = this.repository.checkIfItemExists(listTitle);
        return this.checkIfItemExists;
    }

    //Delete
    public void emptyTrash(){
        this.repository.emptyTrash();
    }

    public void deleteListItem(ListItem listItem){
        this.repository.deleteListItem(listItem);
    }

    public void deleteHistoryItem(final HistoryItem historyItem){
        this.repository.deleteHistoryItem(historyItem);
    }

    //Update
    public void updateListItem(ListItem listItem){
        this.repository.updateListItem(listItem);
    }

    public void updateHistoryItem(final HistoryItem historyItem){
        this.repository.updateHistoryItem(historyItem);
    }

    //Insert
    public void insertListItem(ListItem listItem){
        this.repository.insertListItem(listItem);
    }

    public void insertHistoryItem(final HistoryItem historyItem){
        this.repository.insertHistoryItem(historyItem);
    }
}
