package com.aruwen.hnt.ui;


import android.os.Bundle;
import android.preference.PreferenceFragment;


public class AppPreferences extends PreferenceFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		int resId = getActivity().getResources().getIdentifier(
						getArguments().getString("resource"), 
						"xml", 
						getActivity().getPackageName());
		
		addPreferencesFromResource(resId);
	}
}
