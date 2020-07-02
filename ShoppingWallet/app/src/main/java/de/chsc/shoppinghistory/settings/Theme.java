package de.chsc.shoppinghistory.settings;

import android.content.res.Resources;

import de.chsc.shoppinghistory.R;

public class Theme {
    private int ressourceId;
    private String themeName;

    public Theme(String themeName, String[] themes) {
        this.themeName = themeName;
        this.setRessourceFromString(themeName, themes);
    }

    private void setRessourceFromString(String theme, String[] themes){
        int themeRessource = R.style.BlueDarkTheme;
        if (theme.equals(themes[1])){
            themeRessource = R.style.BlueLightTheme;
        } else if (theme.equals(themes[2])){
            themeRessource = R.style.GreenDarkTheme;
        } else if (theme.equals(themes[3])){
            themeRessource = R.style.GreenLightTheme;
        }

        this.setRessourceId(themeRessource);
    }

    public int getRessourceId() {
        return ressourceId;
    }

    public void setRessourceId(int ressourceId) {
        this.ressourceId = ressourceId;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }
}
