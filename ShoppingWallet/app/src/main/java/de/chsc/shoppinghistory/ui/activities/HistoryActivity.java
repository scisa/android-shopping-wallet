package de.chsc.shoppinghistory.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import de.chsc.shoppinghistory.R;
import de.chsc.shoppinghistory.interfaces.OnHistoryItemDoListener;
import de.chsc.shoppinghistory.settings.Preferences;
import de.chsc.shoppinghistory.ui.adapter.RecyclerHistoryAdapter;
import de.chsc.shoppinghistory.model.HistoryItem;
import de.chsc.shoppinghistory.model.ListItem;
import de.chsc.shoppinghistory.model.ShoppingHistoryViewModel;
import de.chsc.shoppinghistory.ui.dialog.DeleteForeverOneItemConfirmationDialog;
import de.chsc.shoppinghistory.ui.dialog.NewEditHistoryItemDialog;
import de.chsc.shoppinghistory.util.ConstantUtilities;
import de.chsc.shoppinghistory.util.GlobalConstants;
import de.chsc.shoppinghistory.util.HistoryTimestamp;

public class HistoryActivity extends AppCompatActivity {
    private ShoppingHistoryViewModel viewModel;
    private RecyclerView recyclerView;
    private RecyclerHistoryAdapter rvAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ListItem currentListItem;
    private HistoryTimestamp currentDate;

    private TextView textViewHistoryListTitle;
    private TextView textViewYearMonthName;
    private TextView textViewPrice;
    private FloatingActionButton floatingActionButtonNewHistoryItem;
    private ImageButton imageButtonPreviousMonth;
    private ImageButton imageButtonNextMonth;

    private List<HistoryItem> historyItemList;
    private Double priceOfMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Preferences preferences = new Preferences(this);
        int themeRessource = preferences.loadThemeSettings().getRessourceId();
        this.setTheme(themeRessource);
        setContentView(R.layout.activity_history);

        this.initRecyclerView();
        this.initCurrentListItem();
        this.initCurrentDate();
        this.observeHistoryItems();
        this.observeHistoryPrice();
        this.initViews();
        this.initUi();
        this.handleButtonClicks();
        this.handleRecyclerViewClicks();
        this.makeListItemsSwipeable();
    }

    private void initRecyclerView(){
        this.recyclerView = (RecyclerView) findViewById(R.id.rv_history_list_recycler_view_id);
        this.recyclerView.setHasFixedSize(true);

        this.layoutManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(this.layoutManager);

        this.rvAdapter = new RecyclerHistoryAdapter();
        this.recyclerView.setAdapter(this.rvAdapter);
    }

    private void initCurrentListItem(){
        String currentListTitle = getIntent().getStringExtra(GlobalConstants.INTENT_LIST_ITEM_TITLE);
        long currentListCreatedTimeStamp = getIntent().getLongExtra(GlobalConstants.INTENT_LIST_ITEM_CREATED_TIMESTAMP,0);

        this.currentListItem = new ListItem();
        this.currentListItem.setListTitle(currentListTitle);
        this.currentListItem.setCreatedTimeStamp(currentListCreatedTimeStamp);
        this.currentListItem.setMarkedForDelete(false);
    }

    private void initCurrentDate(){
        this.currentDate = new HistoryTimestamp(System.currentTimeMillis());
    }

    private void observeHistoryItems(){
        this.viewModel = new ViewModelProvider(this).get(ShoppingHistoryViewModel.class);
        this.viewModel.getAllHistoryItemsCurrentMonth(this.currentDate.getCurrentYear(),
                this.currentDate.getCurrentMonth(), this.currentListItem.getListTitle())
                .observe(this, new Observer<List<HistoryItem>>() {
            @Override
            public void onChanged(List<HistoryItem> historyItems) {
                historyItemList = historyItems;
                rvAdapter.submitList(historyItemList);
            }
        });
    }

    private void observeHistoryPrice(){
        this.viewModel = new ViewModelProvider(this).get(ShoppingHistoryViewModel.class);
        this.viewModel.getPriceOfMonth(this.currentDate.getCurrentYear(),
                this.currentDate.getCurrentMonth(), this.currentListItem.getListTitle())
                .observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double price) {
                priceOfMonth = price;
                if (null == priceOfMonth){
                    priceOfMonth = 0.0;
                }
                textViewPrice.setText(ConstantUtilities.setDoublePrecision(priceOfMonth, 2));
            }
        });
    }

    private void initViews(){
        this.textViewHistoryListTitle = findViewById(R.id.tv_history_list_title_id);
        this.textViewYearMonthName = findViewById(R.id.tv_history_year_month_name_id);
        this.textViewPrice = findViewById(R.id.tv_history_price_id);
        this.floatingActionButtonNewHistoryItem = findViewById(R.id.fab_add_new_history_entry);
        this.imageButtonPreviousMonth = findViewById(R.id.image_button_previous_month);
        this.imageButtonNextMonth = findViewById(R.id.image_button_next_month);
    }

    private void initUi(){
        this.textViewHistoryListTitle.setText(this.currentListItem.getListTitle());
        this.textViewYearMonthName.setText(this.currentDate.getHistoryTimeStamp());
    }

    private void handleButtonClicks(){
        this.floatingActionButtonNewHistoryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewEditHistoryItemDialog(null);
            }
        });

        this.imageButtonPreviousMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPreviousMonth();
            }
        });

        this.imageButtonNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextMonth();
            }
        });
    }

    private void goToPreviousMonth(){
        this.currentDate.subtractOneMonth();
        this.observeHistoryItems();
        this.observeHistoryPrice();
        this.initUi();
    }

    private void goToNextMonth(){
        this.currentDate.addOneMonth();
        this.observeHistoryItems();
        this.observeHistoryPrice();
        this.initUi();
    }

    private void handleRecyclerViewClicks(){
        this.rvAdapter.setOnHistoryItemDoListener(new OnHistoryItemDoListener() {
            @Override
            public void onViewPressed(HistoryItem historyItem) {
                addNewEditHistoryItemDialog(historyItem);
            }
        });
    }

    private void addNewEditHistoryItemDialog(HistoryItem historyItem){
        boolean isEdit = false;
        if (historyItem != null){
            isEdit = true;
        }

        NewEditHistoryItemDialog dialog = new NewEditHistoryItemDialog(this, this.currentListItem, isEdit, historyItem);
        final boolean finalIsEdit = isEdit;
        dialog.setOnNewHistoryItemListener(new NewEditHistoryItemDialog.OnNewHistoryItemListener() {
            @Override
            public void getNewHistoryItem(HistoryItem historyItem) {
                if (!finalIsEdit){
                    viewModel.insertHistoryItem(historyItem);
                }else {
                    viewModel.updateHistoryItem(historyItem);
                    rvAdapter.notifyDataSetChanged();
                }
            }
        });
        dialog.show();
    }

    private  void makeListItemsSwipeable(){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                HistoryItem historyItem = rvAdapter.getHistoryItemAt(viewHolder.getAdapterPosition());
                deleteHistoryItem(historyItem);
            }
        }).attachToRecyclerView(this.recyclerView);
    }

    private void deleteHistoryItem(final HistoryItem historyItem){
        DeleteForeverOneItemConfirmationDialog dialog = new DeleteForeverOneItemConfirmationDialog(this);
        dialog.setOnConfirmationDeleteButtonListener(new DeleteForeverOneItemConfirmationDialog.OnConfirmationDeleteButtonListener() {
            @Override
            public void onYesClicked() {
                viewModel.deleteHistoryItem(historyItem);
                Toast.makeText(getApplicationContext(), R.string.toast_item_deleted_forever, Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

}