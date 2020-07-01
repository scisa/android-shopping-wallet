package de.chsc.shoppinghistory.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.chsc.shoppinghistory.R;

public class ThemeSettingsFragment extends Fragment {
    private Spinner spinnerThemeSettings;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_theme_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.initViews(view);
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
    }
}
