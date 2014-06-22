package budgetapp.util;

import android.budgetapp.R;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity_copy extends ListActivity {
	
	final String PREFS_NAME = "MyPrefsFile";

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final ActionBar actionBar = getActionBar();
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			
			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
		};
		
		
		for (int i = 0; i < 3; i++) {
			actionBar.addTab(actionBar.newTab()
					.setText("Tab " + (i + 1))
					.setTabListener(tabListener));
		}
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
/*	public void addExpense(View view) {
		// do something
		Intent intent = new Intent(this, AddExpenseActivity.class);
		startActivity(intent);
	}
	
	public void viewExpenses(View view) {
		// do something
		Intent intent = new Intent(this, ViewExpensesActivity.class);
		startActivity(intent);
	}
	
	public void addType(View view) {
		Intent intent = new Intent(this, AddTypeActivity.class);
		startActivity(intent);
	}
	
	public void viewList(View view) {
		Intent intent = new Intent(this, ListViewActivity.class);
		startActivity(intent);
	}
*/
}