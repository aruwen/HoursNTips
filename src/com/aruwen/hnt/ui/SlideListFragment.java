package com.aruwen.hnt.ui;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockListFragment;
import com.aruwen.hnt.R;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class SlideListFragment extends SherlockListFragment{

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.slidelist, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		SampleAdapter adapter = new SampleAdapter(getSherlockActivity());
		
		adapter.add(new SampleItem("Settings", R.drawable.ic_action_settings));
		adapter.add(new SampleItem("Tab 1", R.drawable.ic_action_calendar_day));
		adapter.add(new SampleItem("Tab 2", R.drawable.ic_action_plusone));
		adapter.add(new SampleItem("Tab 3", R.drawable.ic_action_calendar_month));
		
		setListAdapter(adapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		SherlockFragment newTab = null;
		
		switch (position) {
		case 0:
			newTab = new TestFragOne();
			break;

		case 1:
			newTab = new TestFragTwo();
			break;
			
		case 2:
			newTab = new TestFragThree();
			break;
		default:
			break;
		}
		
		if(newTab != null) {
			switchFragment(position);
		}
		
	}
	
	private void switchFragment(int id) {
		if(getSherlockActivity() == null) {
			return;
		}
		
		((Main) getSherlockActivity()).switchFragment(id);
	}

	private class SampleItem {
		public String tag;
		public int iconRes;
		public SampleItem(String tag, int iconRes) {
			this.tag = tag; 
			this.iconRes = iconRes;
		}
	}

	public class SampleAdapter extends ArrayAdapter<SampleItem> {

		public SampleAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.sliderow, null);
			}
			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);

			return convertView;
		}

	}
}