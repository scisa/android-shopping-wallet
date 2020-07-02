package de.chsc.shoppinghistory.interfaces;

import de.chsc.shoppinghistory.settings.Theme;

public interface ThemeSettingsSetter {
    Theme loadThemeSettings();
    void safeThemeSettings(String theme);
}
