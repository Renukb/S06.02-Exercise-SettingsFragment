package com.example.android.sunshine;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;

/**
 * Created by Swara on 12/25/2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements OnSharedPreferenceChangeListener{

    private void setPreferenceSummery(Preference preference, Object value){
        String stringValue = value.toString();
        String key = preference.getKey();

        if(preference instanceof ListPreference){

            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);

            if(prefIndex>=0){
                preference.setSummary(listPreference.getEntries()[prefIndex]);

            }

        }else {

            preference.setSummary(stringValue);
        }


    }


    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.pref_screen);
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preScreen = getPreferenceScreen();
        int count = preScreen.getPreferenceCount();
        for(int i=0;i<count; i++){
            Preference p = preScreen.getPreference(i);
            if(!(p instanceof CheckBoxPreference)){

                String value = sharedPreferences.getString(p.getKey(),"");
                setPreferenceSummery(p, value);

            }

        }

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if(null != preference){
            if(!(preference instanceof  CheckBoxPreference)){
                setPreferenceSummery(preference,sharedPreferences.getString(key,""));

            }

        }


    }
    @Override
    public void onStop(){
        super.onStop();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);

    }
    @Override
    public void onStart(){
        super.onStart();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }



}
