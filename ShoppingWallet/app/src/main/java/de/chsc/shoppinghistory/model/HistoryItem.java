package de.chsc.shoppinghistory.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import de.chsc.shoppinghistory.util.DatabaseConstants;

@Entity(foreignKeys = @ForeignKey(entity = ListItem.class,
        parentColumns = DatabaseConstants.LIST_ITEM_TABLE_LIST_TITLE_PK,
        childColumns = DatabaseConstants.HISTORY_ITEM_TABLE_LIST_NAME_FK,
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE),
        tableName = DatabaseConstants.HISTORY_ITEM_TABLE_ENTITY_NAME)
public class HistoryItem {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DatabaseConstants.HISTORY_ITEM_TABLE_ITEM_ID_PK)
    private int itemId;

    @ColumnInfo(name = DatabaseConstants.HISTORY_ITEM_TABLE_PRODUCT_NAME)
    private String productName;

    @ColumnInfo(name = DatabaseConstants.HISTORY_ITEM_TABLE_MARKET_NAME)
    private String marketName;

    @ColumnInfo(name = DatabaseConstants.HISTORY_ITEM_TABLE_PRICE)
    private double price;

    @ColumnInfo(name = DatabaseConstants.HISTORY_ITEM_TABLE_DATE_YEAR)
    private int year;

    @ColumnInfo(name = DatabaseConstants.HISTORY_ITEM_TABLE_DATE_MONTH)
    private int month;

    @ColumnInfo(name = DatabaseConstants.HISTORY_ITEM_TABLE_DATE_DAY_OF_MONTH)
    private int dayOfMonth;

    @ColumnInfo(name = DatabaseConstants.HISTORY_ITEM_TABLE_LIST_NAME_FK)
    private String listName;

    public HistoryItem() {
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}
