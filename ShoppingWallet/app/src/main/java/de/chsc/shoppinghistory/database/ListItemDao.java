package de.chsc.shoppinghistory.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.chsc.shoppinghistory.model.ListItem;
import de.chsc.shoppinghistory.util.DatabaseConstants;

@Dao
public interface ListItemDao {
    @Insert
    void insertListItem(ListItem listItem);

    @Update
    void updateListItem(ListItem listItem);

    @Delete
    void deleteListItem(ListItem listItem);

    @Query("DELETE FROM " + DatabaseConstants.LIST_ITEM_TABLE_ENTITY_NAME + " WHERE " + DatabaseConstants.LIST_ITEM_TABLE_IS_DELETED + " = 1")
    void emptyTrash();


    @Query("SELECT * FROM " + DatabaseConstants.LIST_ITEM_TABLE_ENTITY_NAME
            + " WHERE " + DatabaseConstants.LIST_ITEM_TABLE_IS_DELETED + " = 0"
            + " ORDER BY " + DatabaseConstants.LIST_ITEM_TABLE_IS_CREATED_TIME_STAMP)
    LiveData<List<ListItem>> getAllListItemsInOverview();

    @Query("SELECT * FROM " + DatabaseConstants.LIST_ITEM_TABLE_ENTITY_NAME
            + " WHERE " + DatabaseConstants.LIST_ITEM_TABLE_IS_DELETED + " = 1"
            + " ORDER BY " + DatabaseConstants.LIST_ITEM_TABLE_IS_CREATED_TIME_STAMP)
    LiveData<List<ListItem>> getAllListItemsInTrash();

    @Query("SELECT * FROM " + DatabaseConstants.LIST_ITEM_TABLE_ENTITY_NAME
        + " WHERE " + DatabaseConstants.LIST_ITEM_TABLE_LIST_TITLE_PK + " = :listTitle")
    LiveData<List<ListItem>> checkIfItemExists(String listTitle);
}
