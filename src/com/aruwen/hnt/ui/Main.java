
package com.aruwen.hnt.ui;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.aruwen.hnt.R;
import com.aruwen.hnt.util.SharedPrefs_;
import com.devspark.appmsg.AppMsg;
import com.devspark.appmsg.AppMsg.Style;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;
import com.inscription.ChangeLogDialog;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingActivityBase;

@EActivity
public class Main extends SherlockFragmentActivity implements
			SlidingMenu.OnClosedListener,
			SlidingMenu.OnOpenedListener {
	
	private ViewPager 		mViewPager;
	private TabsAdapter 	mTabsAdapter;
	@Pref
	public SharedPrefs_ 	mSharedPrefs;

    private String[] 		locations;
    private SlidingMenu		mSlidingMenu;
    private Boolean			mSlideMenuOpen = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.pager);
		setContentView(mViewPager);
		
		triggerChangeLogDialog();
		
		//initialize TABS
		//TODO change to automatic process for main stuff using for each as
		// 	   seen in the configureactionbar method
		
		ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.setHomeButtonEnabled(true);
		
		mTabsAdapter = new TabsAdapter(this, mViewPager);
		
		mTabsAdapter.addTab(bar.newTab().setText("Tab 1"), TestFragOne_.class, null);
		mTabsAdapter.addTab(bar.newTab().setText("Tab 2"), TestFragTwo.class, null);
		mTabsAdapter.addTab(bar.newTab().setText("Tab 3"), TestFragThree.class, null);

		initializeSlideMenu();
		
	}

//    @AfterViews
//    void afterViews() {
//        locations = getResources().getStringArray(R.array.locations);
//        configureActionBar();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.main_itemlist, menu);
        return true;
    }
    
    @Override
	public boolean onPrepareOptionsMenu(Menu menu) {
    	Log.d("hnt", "on in prepare optionsmenu");
		return super.onPrepareOptionsMenu(menu);
	}

//    private void configureActionBar() {
//        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//        for (String location: locations) {
//            mTabsAdapter.addTab(getSupportActionBar().newTab().setText(location), TestFragOne.class, null);
//        }
//    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case R.id.add_item:
			Log.d("hnt", "clicked add_item");
			AppMsg.makeText(this, "Sneaky, clicked the plus button", AppMsg.STYLE_INFO).show();
			return true;
			
		case R.id.about:
			startActivity(new Intent(this, EditPreferences.class));
			return true;
			
		case android.R.id.home:
			if(mSlideMenuOpen == null) {
				return false;
			}
			mSlidingMenu.toggle();
			return true;
			
		
		default:
			return super.onOptionsItemSelected(item);
		}
    	
    }
    
    /**
     * Movte to prog utils - decide on how to implement singleton/access
     * to the android activity
     * @return
     */
    private String getCurrentAppVersion() {
    	try {
    		PackageInfo pi = this.getPackageManager().getPackageInfo(getPackageName(), 0);
    		return pi.versionName;
    	} catch (NameNotFoundException e) {
    		e.printStackTrace();
    		return "";
    	}
    }
    
    public static class TabsAdapter extends FragmentPagerAdapter implements
    	ActionBar.TabListener, ViewPager.OnPageChangeListener {
    	
    	private final Context mContext;
    	private final ActionBar mActionBar;
    	private final ViewPager mViewPager;
    	private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
    	
    	static final class TabInfo {
    		private final Class<?> cls;
    		private final Bundle args;
    		
    		TabInfo(Class<?> _class, Bundle _args) {
    			cls = _class;
    			args = _args;
    		}
    	}
    	
    	public TabsAdapter(SherlockFragmentActivity activity, ViewPager pager) {
    		super(activity.getSupportFragmentManager());
    		mContext = activity;
    		mActionBar = activity.getSupportActionBar();
    		mViewPager = pager;
    		mViewPager.setAdapter(this);
    		mViewPager.setOnPageChangeListener(this);
    	}
    	
    	public void addTab(ActionBar.Tab tab, Class<?> cls, Bundle args) {
    		TabInfo info = new TabInfo(cls, args);
    		tab.setTag(info);
    		tab.setTabListener(this);
    		mTabs.add(info);
    		mActionBar.addTab(tab);
    		notifyDataSetChanged();
    	}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int pos) {
			mActionBar.setSelectedNavigationItem(pos);
			
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			
			Object tag = tab.getTag();
			int size = mTabs.size();
			for(int i = 0; i < size; i++) {
				if(mTabs.get(i) == tag) {
					mViewPager.setCurrentItem(i);
				}
			}
			
			
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			
		}

		@Override
		public Fragment getItem(int pos) {
			TabInfo info = mTabs.get(pos);
			return Fragment.instantiate(mContext, info.cls.getName(), info.args);
		}

		@Override
		public int getCount() {
			return mTabs.size();
		}
    }

	@Override
	public void onOpened() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//		getSupportActionBar().setDisplayOptions(0, ActionBar.DISPLAY_HOME_AS_UP);
		Log.d("hnt", "on opened called from slidelist");
	}

	@Override
	public void onClosed() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//		getSupportActionBar().setDisplayOptions(1, ActionBar.DISPLAY_HOME_AS_UP);
		Log.d("hnt", "on closed called from slidelist");
	}
    
    private void triggerChangeLogDialog() {
    	if(mSharedPrefs.version().exists()) {
    		if(TextUtils.equals(mSharedPrefs.version().get(), getCurrentAppVersion())) {
    			return;
    		}
    	}
		mSharedPrefs.edit().version().put(getCurrentAppVersion()).apply();
		showChangeLogDialog();
	}
    
    private void showChangeLogDialog() {
    	final ChangeLogDialog changeLogDialog = new ChangeLogDialog(this);
		changeLogDialog.show();
    }
    
    private void initializeSlideMenu() {
		mSlidingMenu = new SlidingMenu(this);
		mSlidingMenu.setMode(SlidingMenu.LEFT);
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);
		mSlidingMenu.setShadowDrawable(R.drawable.shadow);
		mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		mSlidingMenu.setFadeDegree(0.35f);
		mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		mSlidingMenu.setMenu(R.layout.slidemenu);
		mSlidingMenu.setOnClosedListener(this);
		mSlidingMenu.setOnOpenedListener(this);
    }
    
    public void switchFragment(int id) {
    	if(id == 0) {
    		startActivity(new Intent(this, EditPreferences.class));
    	} else {
    		mViewPager.setCurrentItem(id-1);
    	}
    	
    }
}
