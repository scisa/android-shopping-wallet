package de.chsc.shoppinghistory.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import de.chsc.shoppinghistory.R;
import de.chsc.shoppinghistory.settings.Preferences;
import de.chsc.shoppinghistory.ui.fragments.ThemeSettingsFragment;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Preferences preferences = new Preferences(this);
        int themeRessource = preferences.loadThemeSettings().getRessourceId();
        this.setTheme(themeRessource);
        setContentView(R.layout.activity_settings);

        this.initFragments();
    }

    private void initFragments(){
        this.getSupportFragmentManager().beginTransaction().replace(R.id.theme_settings_container,
                new ThemeSettingsFragment()).commit();
    }

}