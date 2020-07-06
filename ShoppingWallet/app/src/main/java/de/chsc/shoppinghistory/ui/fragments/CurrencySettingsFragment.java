package de.chsc.shoppinghistory.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.chsc.shoppinghistory.R;
import de.chsc.shoppinghistory.settings.Preferences;

public class CurrencySettingsFragment extends Fragment {
    private Spinner spinnerCurrencies;
    private TextView textViewCurrentCurrency;
    private EditText editTextNewCurrencyMapping;
    private Button buttonApply;
    private LinearLayout linearLayoutCurrencyMapping;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_currency_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.initViews(view);
        this.initSpinner();
        this.handleButtonClicks();
    }

    private void initViews(View view){
        this.spinnerCurrencies = view.findViewById(R.id.spinner_currency_settings);
        this.textViewCurrentCurrency = view.findViewById(R.id.tv_current_currency);
        this.editTextNewCurrencyMapping = view.findViewById(R.id.et_map_new_currency);
        this.buttonApply = view.findViewById(R.id.button_currency_settings_apply);
        this.linearLayoutCurrencyMapping = view.findViewById(R.id.ll_currency_mapping);
        this.linearLayoutCurrencyMapping.setVisibility(View.INVISIBLE);
    }

    private void initSpinner(){
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.currency_array, R.layout.support_simple_spinner_dropdown_item);

        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        this.spinnerCurrencies.setAdapter(arrayAdapter);
    }

    private void handleButtonClicks(){
        this.buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
