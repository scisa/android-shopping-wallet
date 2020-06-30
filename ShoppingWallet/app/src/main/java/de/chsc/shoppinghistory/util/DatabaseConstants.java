package de.chsc.shoppinghistory.util;

public class DatabaseConstants {
    //Database
    public static final String DATABASE_NAME = "shopping_history_database";
    public static final int NUMBER_OF_DATABASE_THREADS = 4;

    //Lists Table
    public static final String LIST_ITEM_TABLE_ENTITY_NAME = "list_overview_table";
    public static final String LIST_ITEM_TABLE_LIST_TITLE_PK = "list_title";
    public static final String LIST_ITEM_TABLE_IS_CREATED_TIME_STAMP = "created_time_stamp";
    public static final String LIST_ITEM_TABLE_IS_DELETED = "is_deleted";

    //History Items Table
    public static final String HISTORY_ITEM_TABLE_ENTITY_NAME = "history_items_table";
    public static final String HISTORY_ITEM_TABLE_ITEM_ID_PK = "item_id"; //PK
    public static final String HISTORY_ITEM_TABLE_PRODUCT_NAME = "product_name";
    public static final String HISTORY_ITEM_TABLE_MARKET_NAME = "market_name";
    public static final String HISTORY_ITEM_TABLE_PRICE = "price";
    public static final String HISTORY_ITEM_TABLE_DATE_YEAR = "year";
    public static final String HISTORY_ITEM_TABLE_DATE_MONTH = "month";
    public static final String HISTORY_ITEM_TABLE_DATE_DAY_OF_MONTH = "day_of_month";
    public static final String HISTORY_ITEM_TABLE_LIST_NAME_FK = "list_name";
}
