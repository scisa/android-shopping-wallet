package de.chsc.shoppinghistory.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.chsc.shoppinghistory.model.HistoryItem;
import de.chsc.shoppinghistory.util.DatabaseConstants;

@Dao
public interface HistoryItemDao {
    @Insert
    void insertHistoryItem(HistoryItem historyItem);

    @Update
    void updateHistoryItem(HistoryItem historyItem);

    @Delete
    void deleteHistoryItem(HistoryItem historyItem);

    @Query("SELECT * FROM " + DatabaseConstants.HISTORY_ITEM_TABLE_ENTITY_NAME
            + " WHERE " + DatabaseConstants.HISTORY_ITEM_TABLE_DATE_YEAR + " = :year"
            + " AND " + DatabaseConstants.HISTORY_ITEM_TABLE_DATE_MONTH + " = :month"
            + " AND " + DatabaseConstants.HISTORY_ITEM_TABLE_LIST_NAME_FK + " = :listName"
            + " ORDER BY " + DatabaseConstants.HISTORY_ITEM_TABLE_DATE_DAY_OF_MONTH)
    LiveData<List<HistoryItem>> getAllHistoryItemsOfMonth(int year, int month, String listName);

    @Query("SELECT SUM(" + DatabaseConstants.HISTORY_ITEM_TABLE_PRICE + ")"
            + " FROM " + DatabaseConstants.HISTORY_ITEM_TABLE_ENTITY_NAME
            + " WHERE " + DatabaseConstants.HISTORY_ITEM_TABLE_DATE_YEAR + " = :year"
            + " AND " + DatabaseConstants.HISTORY_ITEM_TABLE_DATE_MONTH + " = :month"
            + " AND " + DatabaseConstants.HISTORY_ITEM_TABLE_LIST_NAME_FK + " = :listName")
    LiveData<Double> getPriceOfMonth(int year, int month, String listName);
}
