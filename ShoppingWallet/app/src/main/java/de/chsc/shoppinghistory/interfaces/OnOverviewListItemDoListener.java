package de.chsc.shoppinghistory.interfaces;

import de.chsc.shoppinghistory.model.ListItem;

public interface OnOverviewListItemDoListener {
    void onSetDeleteClicked(ListItem listItem);
    void onSetEditClicked(ListItem listItem);
    void onSetItemViewClicked(ListItem listItem);
}
