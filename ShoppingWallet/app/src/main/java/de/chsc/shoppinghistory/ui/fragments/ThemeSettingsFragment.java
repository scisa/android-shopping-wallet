package de.chsc.shoppinghistory.ui.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.TaskStackBuilder;
import androidx.fragment.app.Fragment;

import de.chsc.shoppinghistory.R;
import de.chsc.shoppinghistory.settings.Preferences;
import de.chsc.shoppinghistory.ui.activities.OverviewActivity;

public class ThemeSettingsFragment extends Fragment {
    private Spinner spinnerThemeSettings;
    private String currentTheme;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_theme_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.initViews(view);
        this.handleItemSelects();
        this.initSpinner();

    }

    private void initViews(View view){
        this.spinnerThemeSettings = view.findViewById(R.id.spinner_theme_settings);
    }

    private void initSpinner(){
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.theme_array, R.layout.support_simple_spinner_dropdown_item);

        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        this.spinnerThemeSettings.setAdapter(arrayAdapter);
        Preferences preferences = new Preferences(requireContext());
        this.currentTheme = preferences.loadThemeSettings().getThemeName();
        int spinnerPosition = arrayAdapter.getPosition(this.currentTheme);
        this.spinnerThemeSettings.setSelection(spinnerPosition);
    }

    private void handleItemSelects(){
        this.spinnerThemeSettings.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Resources res = requireContext().getResources();
                String[] themes = res.getStringArray(R.array.theme_array);
                String theme = themes[position];
                if (!theme.equals(currentTheme)){
                    setTheme(theme);
                    recreateApp();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setTheme(String theme){
        Preferences preferences = new Preferences(requireContext());
        preferences.safeThemeSettings(theme);
    }

    private void recreateApp(){
        TaskStackBuilder.create(requireActivity())
                .addNextIntent(new Intent(getActivity(), OverviewActivity.class))
                .addNextIntent(getActivity().getIntent())
                .startActivities();
    }
}
