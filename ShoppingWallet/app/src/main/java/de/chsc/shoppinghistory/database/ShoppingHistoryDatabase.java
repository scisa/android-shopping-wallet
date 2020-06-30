package de.chsc.shoppinghistory.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.chsc.shoppinghistory.model.HistoryItem;
import de.chsc.shoppinghistory.model.ListItem;
import de.chsc.shoppinghistory.util.DatabaseConstants;

@Database(entities = {ListItem.class, HistoryItem.class}, version = 1, exportSchema = false)
public abstract class ShoppingHistoryDatabase extends RoomDatabase {
    public abstract ListItemDao listItemDao();
    public abstract HistoryItemDao historyItemDao();
    private static ShoppingHistoryDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(DatabaseConstants.NUMBER_OF_DATABASE_THREADS);

    public static synchronized ShoppingHistoryDatabase getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    ShoppingHistoryDatabase.class,
                    DatabaseConstants.DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        private ListItemDao listItemDao;
        private HistoryItemDao historyItemDao;

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            ShoppingHistoryDatabase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    listItemDao = INSTANCE.listItemDao();
                    historyItemDao = INSTANCE.historyItemDao();

                    ListItem listItem = new ListItem();
                    listItem.setListTitle("TestList");
                    listItem.setCreatedTimeStamp(System.currentTimeMillis());
                    listItem.setMarkedForDelete(false);

                    ListItem listItem1 = new ListItem();
                    listItem1.setListTitle("TestList1");
                    listItem1.setCreatedTimeStamp(System.currentTimeMillis());
                    listItem1.setMarkedForDelete(false);

                    ListItem listItem2 = new ListItem();
                    listItem2.setListTitle("TestList2");
                    listItem2.setCreatedTimeStamp(System.currentTimeMillis());
                    listItem2.setMarkedForDelete(false);

                    ListItem listItem3 = new ListItem();
                    listItem3.setListTitle("TestListTrash");
                    listItem3.setCreatedTimeStamp(System.currentTimeMillis());
                    listItem3.setMarkedForDelete(true);

                    ListItem listItem4 = new ListItem();
                    listItem4.setListTitle("TestListTrash1");
                    listItem4.setCreatedTimeStamp(System.currentTimeMillis());
                    listItem4.setMarkedForDelete(true);

                    ListItem listItem5 = new ListItem();
                    listItem5.setListTitle("TestListTrash2");
                    listItem5.setCreatedTimeStamp(System.currentTimeMillis());
                    listItem5.setMarkedForDelete(true);

                    listItemDao.insertListItem(listItem);
                    listItemDao.insertListItem(listItem1);
                    listItemDao.insertListItem(listItem2);
                    listItemDao.insertListItem(listItem3);
                    listItemDao.insertListItem(listItem4);
                    listItemDao.insertListItem(listItem5);
                }
            });
        }
    };
}
