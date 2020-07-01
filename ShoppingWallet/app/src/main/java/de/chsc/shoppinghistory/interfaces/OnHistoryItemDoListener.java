package de.chsc.shoppinghistory.interfaces;

import de.chsc.shoppinghistory.model.HistoryItem;

public interface OnHistoryItemDoListener {
    void onViewPressed(HistoryItem historyItem);
}
