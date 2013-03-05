package com.aruwen.hnt.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.ActionMode.Callback;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.aruwen.hnt.R;
import com.devspark.appmsg.AppMsg;


public class TestFragTwo extends SherlockFragment {
	
	Button mButton;
	private ActionMode mActionMode;
	protected ActionMode.Callback mActionModeCallBCallback; 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		View v = inflater.inflate(R.layout.testfragtwo, container, false);
		
		mActionModeCallBCallback = new ActionMode.Callback() {
			
			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onDestroyActionMode(ActionMode mode) {
				mActionMode = null;
			}
			
			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				MenuInflater inflater = mode.getMenuInflater();
				inflater.inflate(R.menu.test_frag_two, menu);
				return true;
			}
			
			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				switch (item.getItemId()) {
				case R.id.dont_like:
					AppMsg.makeText(getActivity(), "Not much to like yet there is", AppMsg.STYLE_ALERT).show();
					mode.finish();
					return true;
				default:
					return false;
				}
			}
		};
		
		mButton = (Button) v.findViewById(R.id.aktschion);	
		
		mButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mActionMode = getSherlockActivity().startActionMode(mActionModeCallBCallback);
			}
		});
//		
		
		
		return v;
	}
	
	
	
	
}
