package project.epam.com.cinemawaddle.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.epam.com.cinemawaddle.R;
import project.epam.com.cinemawaddle.util.Constants;

/**
 * A placeholder fragment containing a simple view.
 */
public class SettingsActivityFragment extends Fragment {

    @BindView(R.id.spinner_language) Spinner spinner;


    public SettingsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.language_spinner_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//        spinner.setPrompt("prompt");
        SharedPreferences preferences = getContext()
                .getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
        String language = preferences.getString(Constants.PREF_LOCALE, Constants.EN);



        spinner.setAdapter(adapter);

        if (language.equals(Constants.EN)) {
            spinner.setSelection(0);
        } else {
            spinner.setSelection(1);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor edit = getContext()
                        .getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE)
                        .edit();
                if (position == 0) {
                    edit.putString(Constants.PREF_LOCALE, Constants.EN).apply();
                } else {
                    edit.putString(Constants.PREF_LOCALE, Constants.RU).apply();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
