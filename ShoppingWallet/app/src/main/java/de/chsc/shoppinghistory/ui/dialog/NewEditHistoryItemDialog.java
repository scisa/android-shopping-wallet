package de.chsc.shoppinghistory.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import de.chsc.shoppinghistory.R;
import de.chsc.shoppinghistory.model.HistoryItem;
import de.chsc.shoppinghistory.model.ListItem;
import de.chsc.shoppinghistory.util.ConstantUtilities;


public class NewEditHistoryItemDialog extends ParentDialog{
    private OnNewHistoryItemListener listener;

    private EditText editTextProductName;
    private EditText editTextMarketName;
    private EditText editTextPrice;
    private DatePicker datePickerBuyDate;
    private Button buttonSave;
    private Button buttonCancel;

    private ListItem currentListItem;
    private boolean isEdit;
    private HistoryItem existingHistoryItem;


    public NewEditHistoryItemDialog(Context context, ListItem currentListItem, boolean isEdit, HistoryItem historyItem) {
        super(context);
        this.currentListItem = currentListItem;
        this.isEdit = isEdit;
        this.existingHistoryItem = historyItem;

        this.initViews();
//        this.handleEditTextsContextClick();
        this.handleButtonClicks();
        this.initOnEditViews();
    }

    private void initViews(){
        this.editTextProductName = this.view.findViewById(R.id.et_new_history_item_product_name);
        this.editTextMarketName = this.view.findViewById(R.id.et_new_history_item_market_name);
        this.editTextPrice = this.view.findViewById(R.id.et_new_history_item_price);
        this.datePickerBuyDate = this.view.findViewById(R.id.dp_insert_date_new_history_item);
        this.buttonSave = this.view.findViewById(R.id.button_store_new_history_item_dialog);
        this.buttonCancel = this.view.findViewById(R.id.button_cancel_new_history_item_dialog);
    }

    private void handleEditTextsContextClick(){
        this.editTextProductName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    Activity activity = (Activity)context;
                    ConstantUtilities.hideSoftKeyboard(activity);
                }
            }
        });

        this.editTextMarketName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    Activity activity = (Activity)context;
                    ConstantUtilities.hideSoftKeyboard(activity);
                }
            }
        });

        this.editTextPrice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    Activity activity = (Activity)context;
                    ConstantUtilities.hideSoftKeyboard(activity);
                }
            }
        });
    }

    private void handleButtonClicks(){
        this.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSavePressed();
            }
        });

        this.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void buttonSavePressed(){
        String productName = editTextProductName.getText().toString().trim();
        String marketName = editTextMarketName.getText().toString().trim();
        String price = editTextPrice.getText().toString().trim();
        int year = datePickerBuyDate.getYear();
        int month = datePickerBuyDate.getMonth();
        int dayOfMonth = datePickerBuyDate.getDayOfMonth();

        if (!productName.isEmpty() && !marketName.isEmpty() && !price.isEmpty()){
            newUpdateHistoryItem(productName, marketName, price, year, month, dayOfMonth);
            dialog.dismiss();
        } else {
            Toast.makeText(context, R.string.empty_input_fields_left, Toast.LENGTH_SHORT).show();
        }
    }

    private void newUpdateHistoryItem(String productName, String marketName,
                                      String price, int year, int month, int dayOfMonth){
        if (this.isEdit){
            editHistoryItem(productName, marketName, price, year, month, dayOfMonth);
        } else {
            createNewHistoryItem(productName, marketName, price, year, month, dayOfMonth);
        }
    }

    private void editHistoryItem(String productName, String marketName,
                                 String price, int year, int month, int dayOfMonth){
        this.existingHistoryItem.setProductName(productName);
        this.existingHistoryItem.setMarketName(marketName);
        this.existingHistoryItem.setPrice(Double.parseDouble(price));
        this.existingHistoryItem.setYear(year);
        this.existingHistoryItem.setMonth(month);
        this.existingHistoryItem.setDayOfMonth(dayOfMonth);
        this.existingHistoryItem.setListName(this.currentListItem.getListTitle());
        this.listener.getNewHistoryItem(this.existingHistoryItem);
    }

    private void createNewHistoryItem(String productName, String marketName,
                                      String price, int year, int month, int dayOfMonth){
        HistoryItem historyItem = new HistoryItem();
        historyItem.setProductName(productName);
        historyItem.setMarketName(marketName);
        historyItem.setPrice(Double.parseDouble(price));
        historyItem.setYear(year);
        historyItem.setMonth(month);
        historyItem.setDayOfMonth(dayOfMonth);
        historyItem.setListName(this.currentListItem.getListTitle());
        this.listener.getNewHistoryItem(historyItem);
    }

    private void initOnEditViews(){
        if (this.isEdit){
            this.editTextProductName.setText(this.existingHistoryItem.getProductName());
            this.editTextMarketName.setText(this.existingHistoryItem.getMarketName());
            this.editTextPrice.setText(String.valueOf(this.existingHistoryItem.getPrice()));
            this.datePickerBuyDate.updateDate(this.existingHistoryItem.getYear(),
                    this.existingHistoryItem.getMonth(), this.existingHistoryItem.getDayOfMonth());
        }
    }

    @Override
    void setLayoutRessource() {
        this.layoutRessource = R.layout.new_history_item_dialog;
    }

    public interface OnNewHistoryItemListener{
        void getNewHistoryItem(HistoryItem historyItem);
    }

    public void setOnNewHistoryItemListener(OnNewHistoryItemListener listener){
        this.listener = listener;
    }
}
