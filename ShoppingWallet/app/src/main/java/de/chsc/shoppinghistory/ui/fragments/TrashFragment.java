package de.chsc.shoppinghistory.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.chsc.shoppinghistory.R;
import de.chsc.shoppinghistory.interfaces.OnTrashListItemDoListener;
import de.chsc.shoppinghistory.ui.adapter.RecyclerTrashAdapter;
import de.chsc.shoppinghistory.model.ListItem;
import de.chsc.shoppinghistory.model.ShoppingHistoryViewModel;
import de.chsc.shoppinghistory.ui.dialog.DeleteForeverEmptyTrashConfirmationDialog;
import de.chsc.shoppinghistory.ui.dialog.DeleteForeverOneItemConfirmationDialog;

public class TrashFragment extends Fragment {
    private ShoppingHistoryViewModel viewModel;
    private RecyclerView recyclerView;
    private RecyclerTrashAdapter rvAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button buttonEmptyTrash;
    private List<ListItem> listItemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trash, container, false);
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
        this.recyclerView = (RecyclerView) view.findViewById(R.id.rv_trash);
        this.recyclerView.setHasFixedSize(true);

        this.layoutManager = new LinearLayoutManager(view.getContext());
        this.recyclerView.setLayoutManager(this.layoutManager);

        this.rvAdapter = new RecyclerTrashAdapter(view.getContext());
        this.recyclerView.setAdapter(this.rvAdapter);
    }

    private void initButtons(View view){
        this.buttonEmptyTrash = view.findViewById(R.id.button_trash_empty_trash);
    }

    private void handleButtonClicks(){
        this.buttonEmptyTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emptyTrashForever();
            }
        });
    }

    private void observeListViewItems(){
        this.viewModel = new ViewModelProvider(requireActivity()).get(ShoppingHistoryViewModel.class);
        this.viewModel.getAllListItemsInTrash().observe(getViewLifecycleOwner(), new Observer<List<ListItem>>() {
            @Override
            public void onChanged(List<ListItem> listItems) {
                listItemList = listItems;
                rvAdapter.submitList(listItemList);
            }
        });
    }

    private void listenToEventsFromRecyclerView(){
        this.rvAdapter.setOnTrashListItemDoListener(new OnTrashListItemDoListener() {
            @Override
            public void onDeleteClicked(ListItem listItem) {
                deleteListItemForever(listItem);
            }

            @Override
            public void onItemRestore(ListItem listItem) {
                listItem.setMarkedForDelete(false);
                viewModel.updateListItem(listItem);
            }
        });
    }

    private void makeListItemsSwipeable(){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                ListItem listItem = rvAdapter.getListItemAt(viewHolder.getAdapterPosition());
                deleteListItemForever(listItem);
            }
        }).attachToRecyclerView(this.recyclerView);
    }

    private void emptyTrashForever(){
        DeleteForeverEmptyTrashConfirmationDialog dialog = new DeleteForeverEmptyTrashConfirmationDialog(requireContext());
        dialog.setOnConfirmationDeleteButtonListener(new DeleteForeverEmptyTrashConfirmationDialog.OnConfirmationDeleteButtonListener() {
            @Override
            public void onYesClicked() {
                viewModel.emptyTrash();
                Toast.makeText(requireContext(), R.string.toast_empty_trash_forever, Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    private void deleteListItemForever(final ListItem listItem){
        DeleteForeverOneItemConfirmationDialog dialog = new DeleteForeverOneItemConfirmationDialog(requireContext());
        dialog.setOnConfirmationDeleteButtonListener(new DeleteForeverOneItemConfirmationDialog.OnConfirmationDeleteButtonListener() {
            @Override
            public void onYesClicked() {
                viewModel.deleteListItem(listItem);
                Toast.makeText(requireContext(), R.string.toast_item_deleted_forever, Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
}
