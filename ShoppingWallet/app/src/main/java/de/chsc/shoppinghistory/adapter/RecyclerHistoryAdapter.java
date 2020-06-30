package de.chsc.shoppinghistory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import de.chsc.shoppinghistory.R;
import de.chsc.shoppinghistory.model.HistoryItem;
import de.chsc.shoppinghistory.util.ConstantUtilities;
import de.chsc.shoppinghistory.util.HistoryTimestamp;

public class RecyclerHistoryAdapter extends ListAdapter<HistoryItem, RecyclerHistoryAdapter.ViewHolder> {
    private OnItemSelectedListener listener;

    public RecyclerHistoryAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<HistoryItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<HistoryItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull HistoryItem oldItem, @NonNull HistoryItem newItem) {
            return oldItem.getItemId() == newItem.getItemId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull HistoryItem oldItem, @NonNull HistoryItem newItem) {
            if (oldItem.getProductName().equals(newItem.getProductName())
            && oldItem.getMarketName().equals(newItem.getMarketName())
            && oldItem.getPrice() == newItem.getPrice()
            && oldItem.getYear() == newItem.getYear()
            && oldItem.getMonth() == newItem.getMonth()
            && oldItem.getDayOfMonth() == newItem.getDayOfMonth()){
                return true;
            }
            return false;
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);
        return new ViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryItem historyItem = getItem(position);
        holder.textViewProductName.setText(historyItem.getProductName());
        holder.textViewProductPrice.setText(ConstantUtilities.setDoublePrecision(historyItem.getPrice(), 2));

        HistoryTimestamp historyTimestamp = new HistoryTimestamp(0);
        historyTimestamp.setTimeStampFromYearMonthDay(historyItem.getYear(), historyItem.getMonth(), historyItem.getDayOfMonth());
        holder.textViewDate.setText(historyTimestamp.getShortDate());

        holder.textViewMarket.setText(historyItem.getMarketName());
    }

    public HistoryItem getHistoryItemAt(int position){
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewProductName;
        private TextView textViewProductPrice;
        private TextView textViewDate;
        private TextView textViewMarket;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.initViews(itemView);
            this.handleClicks();
        }

        private void initViews(View view){
            this.textViewProductName = view.findViewById(R.id.tv_product_name_id);
            this.textViewProductPrice = view.findViewById(R.id.tv_product_price_id);
            this.textViewDate = view.findViewById(R.id.tv_buy_date_id);
            this.textViewMarket = view.findViewById(R.id.tv_market_id);
        }

        private void handleClicks(){
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    HistoryItem historyItem = getItem(position);
                    listener.onViewPressed(historyItem);
                }
            });
        }
    }

    public interface OnItemSelectedListener{
        void onViewPressed(HistoryItem historyItem);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener){
        this.listener = listener;
    }

}
