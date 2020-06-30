package de.chsc.shoppinghistory.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import de.chsc.shoppinghistory.R;
import de.chsc.shoppinghistory.adapter.RecyclerOverviewAdapter;
import de.chsc.shoppinghistory.model.ListItem;
import de.chsc.shoppinghistory.model.ShoppingHistoryViewModel;
import de.chsc.shoppinghistory.ui.activities.HistoryActivity;
import de.chsc.shoppinghistory.ui.dialog.NewEditListItemDialog;
import de.chsc.shoppinghistory.util.GlobalConstants;

public class OverviewFragment extends Fragment {
    private ShoppingHistoryViewModel viewModel;
    private RecyclerView recyclerView;
    private RecyclerOverviewAdapter rvAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton buttonAddListItem;
    private List<ListItem> listItemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.initRecyclerView(view);
        this.listenToEventsFromRecyclerView();
        this.initButtons(view);
        this.handleButtonClicks();
        this.observeListViewItems();
        this.makeListItemsSwipeable();
    }

    private void initRecyclerView(View view){
        this.recyclerView = (RecyclerView) view.findViewById(R.id.rv_list_overview);
        this.recyclerView.setHasFixedSize(true);

        this.layoutManager = new LinearLayoutManager(view.getContext());
        this.recyclerView.setLayoutManager(this.layoutManager);

        this.rvAdapter = new RecyclerOverviewAdapter(view.getContext());
        this.recyclerView.setAdapter(this.rvAdapter);
    }

    private void listenToEventsFromRecyclerView(){
        this.rvAdapter.setOnSetDeleteClicked(new RecyclerOverviewAdapter.onItemDoListener() {
            @Override
            public void onSetDeleteClicked(ListItem listItem) {
                movedItemToTrash(listItem);
            }

            @Override
            public void onSetEditClicked(ListItem listItem) {
                addNewEditListItemDialog(true, listItem);
            }

            @Override
            public void onSetItemViewClicked(ListItem listItem) {
                Intent historyIntent = packListItemIntent(listItem);
                startActivity(historyIntent);
            }
        });
    }

    private Intent packListItemIntent(ListItem listItem){
        Intent intent = new Intent(requireActivity(), HistoryActivity.class);
        intent.putExtra(GlobalConstants.INTENT_LIST_ITEM_TITLE, listItem.getListTitle());
        intent.putExtra(GlobalConstants.INTENT_LIST_ITEM_CREATED_TIMESTAMP, listItem.getCreatedTimeStamp());

        return intent;
    }

    private void initButtons(View view){
        this.buttonAddListItem = view.findViewById(R.id.fab_add_new_list_item);
    }

    private void handleButtonClicks(){
        this.buttonAddListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewEditListItemDialog(false, null);
            }
        });
    }

    private void addNewEditListItemDialog(final boolean isEdit, ListItem listItem){
        NewEditListItemDialog dialog = new NewEditListItemDialog(requireContext(), isEdit, listItem);
        dialog.setOnNewListItemListener(new NewEditListItemDialog.OnNewListItemListener() {
            @Override
            public void onGetNewEditItem(ListItem listItem) {
                if (!isEdit){
                    insertNewListItem(listItem);
                } else {
                    updateListItem(listItem);
                }
            }
        });
        dialog.show();
    }

    private void insertNewListItem(ListItem listItem){
        if (checkIfNotItemExists(listItem)){
            viewModel.insertListItem(listItem);
        } else {
            Toast.makeText(requireContext(), R.string.toast_list_with_that_name_already_exists, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateListItem(ListItem listItem){
        if (checkIfNotItemExists(listItem)){
            viewModel.updateListItem(listItem);
            rvAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(requireContext(), R.string.toast_list_with_that_name_already_exists, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkIfNotItemExists(ListItem listItem){
        final boolean[] itemExists = new boolean[1];
        this.viewModel = new ViewModelProvider(requireActivity()).get(ShoppingHistoryViewModel.class);
        this.viewModel.checkIfItemExists(listItem.getListTitle()).observe(getViewLifecycleOwner(), new Observer<List<ListItem>>() {
            @Override
            public void onChanged(List<ListItem> listItems) {
                if (!listItems.isEmpty()){
                    itemExists[0] = true;
                }
            }
        });
        return !itemExists[0];
    }

    private void observeListViewItems(){
        this.viewModel = new ViewModelProvider(requireActivity()).get(ShoppingHistoryViewModel.class);
        this.viewModel.getAllListItemsInOverview().observe(getViewLifecycleOwner(), new Observer<List<ListItem>>() {
            @Override
            public void onChanged(List<ListItem> listItems) {
                listItemList = listItems;
                rvAdapter.submitList(listItemList);
            }
        });
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
                ListItem listItem = rvAdapter.getListItemAt(viewHolder.getAdapterPosition());
                movedItemToTrash(listItem);
            }
        }).attachToRecyclerView(this.recyclerView);
    }

    private void movedItemToTrash(ListItem listItem){
        listItem.setMarkedForDelete(true);
        viewModel.updateListItem(listItem);
        Toast.makeText(requireContext(), R.string.toast_moved_to_trash, Toast.LENGTH_SHORT).show();
    }
}
