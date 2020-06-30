package de.chsc.shoppinghistory.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import de.chsc.shoppinghistory.R;
import de.chsc.shoppinghistory.model.ListItem;

public class NewEditListItemDialog extends ParentDialog{
    private OnNewListItemListener listener;
    private boolean isEdit;
    private ListItem existingListItem;

    private Button buttonSave;
    private Button buttonCancel;
    private EditText editTextNewListItemTitle;

    @Override
    void setLayoutRessource() {
        this.layoutRessource = R.layout.new_list_overview_dialog;
    }

    public NewEditListItemDialog(Context context, boolean isEdit, ListItem listItem) {
        super(context);
        this.isEdit = isEdit;
        this.existingListItem = listItem;

        this.initButtons();
        this.initEditTexts();
        this.listenButtons();
        this.initEditViews();
    }

    private void initButtons(){
        this.buttonSave = this.view.findViewById(R.id.button_store_new_list_item_dialog);
        this.buttonCancel = this.view.findViewById(R.id.button_cancel_new_list_item_dialog);
    }

    private void initEditTexts(){
        this.editTextNewListItemTitle = this.view.findViewById(R.id.et_new_list_title);
    }

    private void listenButtons(){
        this.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newListItemTitle = editTextNewListItemTitle.getText().toString().trim();
                if (!newListItemTitle.isEmpty()){
                    newEditListItem(newListItemTitle);
                    dialog.dismiss();
                }
            }
        });

        this.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void newEditListItem(String newListItemTitle){
        if (this.isEdit){
            updateListItem(newListItemTitle);
        } else {
            createNewListItem(newListItemTitle);
        }
    }

    private void updateListItem(String newListItemTitle){
        this.existingListItem.setListTitle(newListItemTitle);
        listener.onGetNewEditItem(this.existingListItem);
    }

    private void createNewListItem(String title){
        ListItem listItem = new ListItem();
        listItem.setListTitle(title);
        listItem.setCreatedTimeStamp(System.currentTimeMillis());
        listItem.setMarkedForDelete(false);
        listener.onGetNewEditItem(listItem);
    }

    private void initEditViews(){
        if (this.isEdit){
            this.editTextNewListItemTitle.setText(this.existingListItem.getListTitle());
        }
    }


    public interface OnNewListItemListener{
        void onGetNewEditItem(ListItem listItem);
    }

    public void setOnNewListItemListener(OnNewListItemListener listener){
        this.listener = listener;
    }
}
