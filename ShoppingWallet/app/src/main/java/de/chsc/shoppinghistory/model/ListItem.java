package de.chsc.shoppinghistory.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import de.chsc.shoppinghistory.util.DatabaseConstants;

@Entity(tableName = DatabaseConstants.LIST_ITEM_TABLE_ENTITY_NAME)
public class ListItem {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = DatabaseConstants.LIST_ITEM_TABLE_LIST_TITLE_PK)
    private String listTitle;

    @ColumnInfo(name = DatabaseConstants.LIST_ITEM_TABLE_IS_CREATED_TIME_STAMP)
    private long createdTimeStamp;

    @ColumnInfo(name = DatabaseConstants.LIST_ITEM_TABLE_IS_DELETED)
    private boolean isMarkedForDelete;

    public ListItem() {
    }

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public long getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(long createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public boolean isMarkedForDelete() {
        return isMarkedForDelete;
    }

    public void setMarkedForDelete(boolean markedForDelete) {
        isMarkedForDelete = markedForDelete;
    }
}
