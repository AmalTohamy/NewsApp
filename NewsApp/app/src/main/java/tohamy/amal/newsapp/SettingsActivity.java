package tohamy.amal.newsapp;

import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public static class NewsPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            Log.e("in fragmet", "SUCCESS");
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setting_main);

            Preference searchPillar = findPreference(getString(R.string.setting_search_by_news_key));
            bindPreferenceSummaryToValue(searchPillar);
            Log.e("done Fragment", "SUCESS");
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            Log.e("pref. chang" , "Sucess");
            // The code in this method takes care of updating the displayed preference summary after it has been changed
            String stringValue = value.toString();
            preference.setSummary(stringValue);
//            if (preference instanceof ListPreference) {
//                ListPreference listPreference = (ListPreference) preference;
//                int prefIndex = listPreference.findIndexOfValue(stringValue);
//                if (prefIndex >= 0) {
//                    CharSequence[] labels = listPreference.getEntries();
//                    preference.setSummary(labels[prefIndex]);
//                }
//            } else {
//                preference.setSummary(stringValue);
//            }
            Log.e("done pref. chang", "Success");
            return true;
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            Log.e("in bin va", "success");
            SharedPreferences preferences =
                    PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
            Log.e("Done bin va", "SUCCESS");
        }
    }

}
