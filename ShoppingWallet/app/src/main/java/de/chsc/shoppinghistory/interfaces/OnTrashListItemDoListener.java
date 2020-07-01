package de.chsc.shoppinghistory.interfaces;

import de.chsc.shoppinghistory.model.ListItem;

public interface OnTrashListItemDoListener {
    void onDeleteClicked(ListItem listItem);
    void onItemRestore(ListItem listItem);
}
