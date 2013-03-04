
package com.aruwen.hnt.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.aruwen.hnt.R;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.inscription.ChangeLogDialog;

@EActivity(R.layout.activity_main)
public class Main
    extends SherlockActivity
    implements TabListener
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ChangeLogDialog changeLogDialog = new ChangeLogDialog(this);
		changeLogDialog.show();
	}

    @ViewById
    TextView hello;
    private String[] locations;

    @AfterViews
    void afterViews() {
        locations = getResources().getStringArray(R.array.locations);
        configureActionBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.main_itemlist, menu);
        return true;
    }

    private void configureActionBar() {
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        for (String location: locations) {
            Tab tab = getSupportActionBar().newTab();
            tab.setText(location);
            tab.setTabListener(this);
            getSupportActionBar().addTab(tab);
        }
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case R.id.add_item:
			
			return true;
		case R.id.about:
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
    	
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
    }
    @Override
 public void onTabUnselected(Tab tab, FragmentTransaction ft) {}@Override
public void onTabReselected(Tab tab, FragmentTransaction ft) {}
}
