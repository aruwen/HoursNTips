package com.aruwen.hnt.ui;


import java.util.List;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.aruwen.hnt.R;

public class EditPreferences extends SherlockPreferenceActivity {
	
	@Override
	public void onBuildHeaders(List<Header> target) {
		loadHeadersFromResource(R.xml.app_preferences_header, target);
	}	
}
