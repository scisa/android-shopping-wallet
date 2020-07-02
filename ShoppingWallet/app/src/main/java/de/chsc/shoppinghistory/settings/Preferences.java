package de.chsc.shoppinghistory.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import de.chsc.shoppinghistory.R;
import de.chsc.shoppinghistory.interfaces.ThemeSettingsSetter;
import de.chsc.shoppinghistory.util.GlobalConstants;

public class Preferences implements ThemeSettingsSetter {
    private Context context;
    public Preferences(Context context) {
        this.context = context;
    }

    private SharedPreferences getPreferences(){
        return this.context.getSharedPreferences(GlobalConstants.SHARED_PREFERENCES_FILENAME, Context.MODE_PRIVATE);
    }

    @Override
    public Theme loadThemeSettings() {
        SharedPreferences sharedPreferences = this.getPreferences();
        Resources res = context.getResources();
        String[] themes = res.getStringArray(R.array.theme_array);
        String theme = sharedPreferences.getString(GlobalConstants.SHARED_PREFERENCES_THEME_KEY, themes[0]);
        return new Theme(theme, themes);
    }

    @Override
    public void safeThemeSettings(String theme) {
        SharedPreferences sharedPreferences = this.getPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(GlobalConstants.SHARED_PREFERENCES_THEME_KEY, theme);
        editor.apply();
    }


}
