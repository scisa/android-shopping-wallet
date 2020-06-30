package de.chsc.shoppinghistory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import de.chsc.shoppinghistory.R;
import de.chsc.shoppinghistory.model.ListItem;

public class RecyclerOverviewAdapter extends ListAdapter<ListItem, RecyclerOverviewAdapter.ViewHolder> {
    private onItemDoListener listener;

    public RecyclerOverviewAdapter(Context context) {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<ListItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<ListItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull ListItem oldItem, @NonNull ListItem newItem) {
            return oldItem.getListTitle().equals(newItem.getListTitle());
        }

        @Override
        public boolean areContentsTheSame(@NonNull ListItem oldItem, @NonNull ListItem newItem) {
            return oldItem.getListTitle().equals(newItem.getListTitle());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.overview_overview_elem, parent, false);
        return new ViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItem listItem = getItem(position);
        holder.listName.setText(listItem.getListTitle());
    }

    public ListItem getListItemAt(int position){
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView listName;
        private ImageButton deleteOverview;
        private ImageButton editOverview;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.initViews();
            this.addListeners();
        }

        private void initViews(){
            this.listName = this.itemView.findViewById(R.id.tv_list_name);
            this.deleteOverview = this.itemView.findViewById(R.id.image_button_delete_overview);
            this.editOverview = this.itemView.findViewById(R.id.image_button_edit_overview);
        }

        private void addListeners(){
            this.deleteOverview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    ListItem listItem = getItem(position);
                    listener.onSetDeleteClicked(listItem);
                }
            });

            this.editOverview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    ListItem listItem = getItem(position);
                    listener.onSetEditClicked(listItem);
                }
            });

            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int positon = getAdapterPosition();
                    ListItem listItem = getItem(positon);
                    listener.onSetItemViewClicked(listItem);
                }
            });
        }
    }

    public interface onItemDoListener{
        void onSetDeleteClicked(ListItem listItem);
        void onSetEditClicked(ListItem listItem);
        void onSetItemViewClicked(ListItem listItem);
    }

    public void setOnSetDeleteClicked(onItemDoListener listener){
        this.listener = listener;
    }
}
