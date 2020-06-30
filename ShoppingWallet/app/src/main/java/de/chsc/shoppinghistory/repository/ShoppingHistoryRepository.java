package de.chsc.shoppinghistory.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import de.chsc.shoppinghistory.database.HistoryItemDao;
import de.chsc.shoppinghistory.database.ListItemDao;
import de.chsc.shoppinghistory.database.ShoppingHistoryDatabase;
import de.chsc.shoppinghistory.model.HistoryItem;
import de.chsc.shoppinghistory.model.ListItem;

public class ShoppingHistoryRepository {
    private ListItemDao listItemDao;
    private HistoryItemDao historyItemDao;
    private LiveData<List<ListItem>> allListItemsInOverview;
    private LiveData<List<ListItem>> allListItemsInTrash;
    private LiveData<List<ListItem>> checkIfItemExists;
    private LiveData<List<HistoryItem>> allHistoryItemsCurrentMonth;
    private LiveData<Double> priceOfMonth;

    public ShoppingHistoryRepository(Application application) {
        ShoppingHistoryDatabase db = ShoppingHistoryDatabase.getInstance(application);
        this.listItemDao = db.listItemDao();
        this.historyItemDao = db.historyItemDao();

        this.allListItemsInOverview = this.listItemDao.getAllListItemsInOverview();
        this.allListItemsInTrash = this.listItemDao.getAllListItemsInTrash();
    }


    //Get
    public LiveData<List<ListItem>> getAllListItemsInOverview(){
        return allListItemsInOverview;
    }

    public LiveData<List<ListItem>> getAllListItemsInTrash(){
        return this.allListItemsInTrash;
    }

    public LiveData<List<HistoryItem>> getAllHistoryItemsCurrentMonth(int year, int month, String listName){
        this.allHistoryItemsCurrentMonth = this.historyItemDao.getAllHistoryItemsOfMonth(year, month, listName);
        return this.allHistoryItemsCurrentMonth;
    }

    public LiveData<Double> getPriceOfMonth(int year, int month, String listName){
        this.priceOfMonth = this.historyItemDao.getPriceOfMonth(year, month, listName);
        return this.priceOfMonth;
    }

    public LiveData<List<ListItem>> checkIfItemExists(String listTitle){
        this.checkIfItemExists = this.listItemDao.checkIfItemExists(listTitle);
        return this.checkIfItemExists;
    }


    //Delete
    public void emptyTrash(){
        ShoppingHistoryDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                listItemDao.emptyTrash();
            }
        });
    }

    public void deleteListItem(final ListItem listItem){
        ShoppingHistoryDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                listItemDao.deleteListItem(listItem);
            }
        });
    }

    public void deleteHistoryItem(final HistoryItem historyItem){
        ShoppingHistoryDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                historyItemDao.deleteHistoryItem(historyItem);
            }
        });
    }


    //Insert
    public void insertListItem(final ListItem listItem){
        ShoppingHistoryDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                listItemDao.insertListItem(listItem);
            }
        });
    }

    public void insertHistoryItem(final HistoryItem historyItem){
        ShoppingHistoryDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                historyItemDao.insertHistoryItem(historyItem);
            }
        });
    }

    //Update
    public void updateListItem(final ListItem listItem){
        ShoppingHistoryDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                listItemDao.updateListItem(listItem);
            }
        });
    }

    public void updateHistoryItem(final HistoryItem historyItem){
        ShoppingHistoryDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                historyItemDao.updateHistoryItem(historyItem);
            }
        });
    }
}
