package de.chsc.shoppinghistory.interfaces;

import de.chsc.shoppinghistory.settings.Currency;

public interface CurrencySettingsSetter {
    Currency loadCurrencySettings();
    void safeCurrencySettings(String currency);
}
