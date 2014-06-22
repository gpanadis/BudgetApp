package budgetapp.screens;

import android.budgetapp.R;

import budgetapp.constants.BudgetConstants;
import budgetapp.dao.DatabaseHandler;


import android.annotation.TargetApi;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuItem;

public class ViewBudgetActivity extends ListActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_list);
		// Show the Up button in the action bar.
		setupActionBar();
		fillData();
	}
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_expenses, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@SuppressWarnings("deprecation")
	private void fillData() {
		DatabaseHandler db = new DatabaseHandler(this);
		Cursor c = db.fetchAllItems();
		startManagingCursor(c);
		
		String[] from = new String[] { BudgetConstants.KEY_DATE, BudgetConstants.KEY_ITEM_DESCRIPTION, BudgetConstants.KEY_AMOUNT, BudgetConstants.KEY_IS_EXPENSE };
		int[] to = new int[] { R.id.bDate, R.id.bDescription, R.id.bAmount, R.id.bExpense };
		
		SimpleCursorAdapter items =
				new SimpleCursorAdapter(this, R.layout.items_row, c, from, to);
		setListAdapter(items);	
	}

}